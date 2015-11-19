import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class History extends JDialog {
	public JTextArea msg;
	
	public History(Users users) {
		this.setTitle("History");
		this.setSize(400, 300);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		
		msg = new JTextArea();
		msg.setBackground(Color.YELLOW);
		msg.setForeground(Color.MAGENTA);
		msg.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 16));
		JScrollPane sp = new JScrollPane(msg);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp, BorderLayout.CENTER);

		msg.setEditable(false);
		msg.setText(users.toString());	
	}
}


