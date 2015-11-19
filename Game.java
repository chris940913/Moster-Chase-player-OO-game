import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Game extends JFrame {
	public static final int TIMEALLOWED = 100;
	
	public Grid grid;
	public String message = "";
	public Player player;
	public Monster monster;
	public int remainTime = 0;
	public BoardPanel boardPanel;
	public JTextArea txtMessage;
	public ArrayList<Nougat> nougats = new ArrayList<Nougat>();
	public ArrayList<Trap> traps = new ArrayList<Trap>();
	public Users users = new Users();
	public Random rand = new Random(new Date().getTime());
	public GameListener gameListener;
	public boolean pause = false;


	// constructor
	public Game() {
		init();
		new RunThread().start();
	}

	class RunThread extends Thread {
		@Override
		public void run() {
			while(true) {
				if(player.isReady() && pause == false) {
					if(monster.removeTrapTime > 0) {
						monster.removeTrapTime--;
						if(monster.removeTrapTime == 0) {
							traps.remove(monster.meetTrap);								
						}
					}
					if(monster.removeTrapTime == 0) monster.move();

					if(monster.currentCell == player.currentCell) {
						player.readyToStart = false;
						player.energy = 0;
						message = "Player lose!";
						users.curUser.loses++;
					}

					createNougats();

					for(int i = traps.size() - 1; i >= 0; i--) {
						Trap trap = traps.get(i);
						if(trap.meet == false) {
							trap.time--;
							if(trap.time <= 0) traps.remove(i);								
						}
					}

					if(grid.distance(player.currentCell, monster.currentCell) <= 5) {
						monster.canView = false;
					}
					else monster.canView = true;

					remainTime--;
					if(remainTime <= 0) {
						player.setReady(false);
						message = "Playe won!";
						users.curUser.wons++;
					}						
				}
				
				updateMessage();
				try {
					Thread.sleep(1000);
					repaint();
				}
				catch(Exception e) {}
				
			}
		}
	}
	
	private void createNougats() {
		for(int i = 0; i < 15; i++) {
			if(nougats.size() < 15) {
				int row = rand.nextInt(11);
				int col = rand.nextInt(11);
				try {
					Cell cell = grid.getCell(row, col);
					if((hasNougat(cell.row, cell.col) == false)
							&& (hasTrap(cell.row, cell.col) == null)
							&& (cell != player.currentCell)
							&& (cell != monster.currentCell)) {
						Nougat nougat = new Nougat(cell);
						nougats.add(nougat);
					}
				}
				catch(Exception e) {}
			}						
		}
	}
	
	public void restart() {	
		pause = false;
		remainTime = TIMEALLOWED;
		player.placeTrapTimes = 0;
		player.escapeTimes = 0;
		monster.removeTrapTime = 0;
		player.energy = Player.DEF_ENERGY;
		monster.meetTrap = null;
		monster.removeTrapTime = 0;
		monster.canView = true;
		message = "";
		player.setReady(true);
		nougats.clear();
		traps.clear();
		try {
			player.setCell(grid.getCell(0, 0));
			monster.setCell(grid.getCell(5, 5));
		}
		catch(Exception e1) {}		
		repaint();
	}
	
	public Trap hasTrap(int row, int col) {
		for(int i = 0; i < traps.size(); i++) {
			Cell cell = traps.get(i).cell;
			if(cell.row == row && cell.col == col) return traps.get(i);			
		}
		return null;
	}	

	public boolean hasNougat(int row, int col) {
		for(int i = 0; i < nougats.size(); i++) {
			Cell cell = nougats.get(i).cell;
			if(cell.row == row && cell.col == col) return true;			
		}
		return false;
	}

	public void removeNougat(int row, int col) {
		for(int i = 0; i < nougats.size(); i++) {
			Cell cell = nougats.get(i).cell;
			if(cell.row == row && cell.col == col) {
				nougats.remove(i);
				break;
			}
		}
		repaint();
	}

	private void updateMessage() {
		txtMessage.setText("");
		if(users.curUser != null) {
			String s = message + "\r\n";
			s += "Player won times: " + users.curUser.wons + "             " + "loses times: "
					+ users.curUser.loses + "\r\n";;
			s += "Player energy: " + player.energy + "              Remain Trap: " 
					+ (3 - player.placeTrapTimes);
			s += "              Remain escape: " + (3 - player.escapeTimes) + "\r\n";
			s += "Time Remaining : " + remainTime + "\r\n";
			txtMessage.setText(s);
		}
	}
	
	private void init() {
		setTitle("Monster Chase Player Game");
		this.getContentPane().setLayout(new BorderLayout());
		setSize(640, 640);
		setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				users.save();
			}
		});

		gameListener = new GameListener(this);
		
		grid = new Grid();
		try {
			player = new Player(this, 0, 0);
			monster = new Monster(this, 5, 5);
		} 
		catch (Exception e) {}
		
		createMainMenuBar();
		createToolPanel();
		createMessagePanel();
		
		boardPanel = new BoardPanel(this);
		boardPanel.addKeyListener(gameListener);
		this.getContentPane().add(boardPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
		boardPanel.requestFocus();
	}
	
	private void createMainMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenuItem m11 = new JMenuItem("Login");
		JMenuItem m12 = new JMenuItem("Register");
		JMenuItem m13 = new JMenuItem("History");
		JMenuItem m14 = new JMenuItem("Exit");		
		m1.add(m11);
		m1.add(m12);
		m1.addSeparator();
		m1.add(m13);
		m1.add(m14);	
		menubar.add(m1);
		m11.addActionListener(gameListener);
		m12.addActionListener(gameListener);
		m13.addActionListener(gameListener);
		m14.addActionListener(gameListener);	
		this.setJMenuBar(menubar);		
	}
	
	private void createToolPanel() {
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(8, 1));
		JButton up = new JButton("up"); 
		JButton down = new JButton("down"); 
		JButton left = new JButton("left"); 
		JButton right = new JButton("right");
		JButton trap = new JButton("trap");
		JButton escape = new JButton("escape");	
		JButton pause = new JButton("pause");	
		JButton start = new JButton("start");			
		p1.add(up); 
		p1.add(down); 
		p1.add(left); 
		p1.add(right);
		p1.add(trap);
		p1.add(escape);	
		p1.add(pause);			
		p1.add(start);	
		up.addActionListener(gameListener);
		down.addActionListener(gameListener);
		left.addActionListener(gameListener);
		right.addActionListener(gameListener);
		trap.addActionListener(gameListener);
		escape.addActionListener(gameListener);
		pause.addActionListener(gameListener);
		start.addActionListener(gameListener);
		this.getContentPane().add(p1, BorderLayout.WEST);
	}
	
	private void createMessagePanel() {
		txtMessage = new JTextArea();
		txtMessage.setPreferredSize(new Dimension(100, 80));
		txtMessage.setEditable(false);
		txtMessage.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 12));
		txtMessage.setBackground(Color.YELLOW);
		txtMessage.setForeground(Color.BLUE);		
		JScrollPane sp = new JScrollPane(txtMessage);
		this.getContentPane().add(sp, BorderLayout.SOUTH);
	}

	public static void main(String args[]) {
		new Game();
	}	
}




