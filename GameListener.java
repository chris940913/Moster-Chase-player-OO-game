import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;


public class GameListener extends KeyAdapter implements ActionListener {
	private Game p;
	private char dir = ' ';
	private int times = 0;
	private boolean flag = false;
	private int during = 0;

	public GameListener(final Game p) {
		this.p = p;
		new RunThread().start();
	}

	class RunThread extends Thread {
		@Override
		public void run() {
			while(true) {
				if(flag) {
					during++;
					if(during >= 10) {
						flag = false;
						movePlayer();
					}
				}
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {}
			}
		}
	}

	private void movePlayer() {
		if(dir != ' ') {
			p.player.setDirection(dir);
			if(times > 3) times = 3;
			if(times == 1) {
				p.player.move();
				p.player.decEnergy(2);
			}
			else if(times == 2) {
				p.player.move();
				p.player.move();
				p.player.decEnergy(2 + 4);
			}
			else if(times == 3) {
				p.player.move();
				p.player.move();
				p.player.move();
				p.player.decEnergy(2 + 4 + 8);
			}
			p.repaint();
			dir = ' ';
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		p.boardPanel.requestFocus();

		String cmd = e.getActionCommand();
		if(p.player.isReady()) {
			if(cmd.equalsIgnoreCase("pause")) execAction(7);		
			if(p.pause == false) {
				if(cmd.equalsIgnoreCase("up")) execAction(1);
				else if(cmd.equalsIgnoreCase("down")) execAction(2);
				else if(cmd.equalsIgnoreCase("left")) execAction(3);
				else if(cmd.equalsIgnoreCase("right")) execAction(4);
				else if(cmd.equalsIgnoreCase("trap")) execAction(5);			
				else if(cmd.equalsIgnoreCase("escape")) execAction(6);
			}
		}
		else if(cmd.equalsIgnoreCase("start")) execAction(8);
		
		if(cmd.equalsIgnoreCase("Login")) execAction(11);
		else if(cmd.equalsIgnoreCase("Register")) execAction(12);
		else if(cmd.equalsIgnoreCase("History")) execAction(13);
		else if(cmd.equalsIgnoreCase("Exit")) execAction(14);	
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		int key = e.getKeyCode();
		if(p.player.isReady()) {
			if(key == KeyEvent.VK_P) execAction(7);
			if(p.pause == false) {
				if(key == KeyEvent.VK_UP) execAction(1);
				else if(key == KeyEvent.VK_DOWN) execAction(2);
				else if(key == KeyEvent.VK_LEFT) execAction(3);
				else if(key == KeyEvent.VK_RIGHT) execAction(4);
				else if(key == KeyEvent.VK_T) execAction(5);			
				else if(key == KeyEvent.VK_E) execAction(6);
			}
		}
		else if(key == KeyEvent.VK_S) execAction(8);
	}
	
	private void execAction(int action) {
		if(p.player.isReady()) {
			if(action == 7) p.pause = !p.pause;	
			
			if(p.pause == false) {
				switch(action) {
				case 1:
					if(dir != 'U') {
						flag = true;
						dir = 'U';
						times = 1;
					}
					else times++;
					during = 0;
					break;
					
				case 2:
					if(dir != 'D') {
						flag = true;
						dir = 'D';
						times = 1;
					}
					else times++;
					during = 0;
					break;
					
				case 3:
					if(dir != 'L') {
						flag = true;
						dir = 'L';
						times = 1;
					}
					else times++;
					during = 0;
					break;
					
				case 4:
					if(dir != 'R') {
						flag = true;
						dir = 'R';
						times = 1;
					}
					else times++;
					during = 0;
					break;

				case 5:
					p.player.placeTrap();
					p.repaint();
					break;

				case 6:
					try {
						if(p.player.escapeTimes < 3) {
							p.player.escapeTimes++;
							p.player.setCell(p.grid.getCell(5, 5));
							p.repaint();
						}
					}
					catch(Exception e1) {}
				}
			}
		}
		else {
			if(action == 8) {
				if(p.users.curUser != null) p.restart();
				else JOptionPane.showMessageDialog(null, "Please login first!");
			}
		}	
		
		switch(action) {
		case 11:
			Login dlg1 = new Login();
			dlg1.setVisible(true);
			if(dlg1.ok) {
				p.users.curUser = p.users.login(dlg1.username, dlg1.password);
			}
			break;

		case 12:
			Register dlg2 = new Register();
			dlg2.setVisible(true);
			if(dlg2.ok) {
				User u = new User();
				u.name = dlg2.name;
				u.address = dlg2.address;
				u.username = dlg2.username;
				u.password = dlg2.password;
				
				if(p.users.exist(u.username)) {
					JOptionPane.showMessageDialog(null, "Username has already exist!");
					return;
				}			
				p.users.add(u);				
				p.users.curUser = u;
			}
			break;
			
		case 13:
			History dlg = new History(p.users);
			dlg.setVisible(true);
			break;
			
		case 14:
			p.dispose();
			System.exit(0);
			break;
		}
	}

}



