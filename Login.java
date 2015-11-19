

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JDialog implements ActionListener {
	public JTextField txtUsername;
	public JPasswordField txtPassword;
	public boolean ok = false;
	public String username = "";
	public String password = "";
	
	public Login() {
		this.setTitle("Login");
		this.setSize(300, 160);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
				
		JPanel p1 = new JPanel(new GridLayout(3, 1));
		add(p1, BorderLayout.CENTER);
		
		JPanel p11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p11.add(new JLabel("Username"));
		txtUsername = new JTextField(15);
		p11.add(txtUsername);
		p1.add(p11);
		
		JPanel p12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p12.add(new JLabel("Password"));
		txtPassword = new JPasswordField(15);
		p12.add(txtPassword);
		p1.add(p12);
		
		JPanel p13 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btn1 = new JButton("Ok");
		p13.add(btn1);
		p1.add(p13);
		btn1.addActionListener(this);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s1 = txtUsername.getText().trim();
		String s2 = String.valueOf(txtPassword.getPassword());
		if(s1.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username is empty!");
			return;
		}
		if(s2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Password is empty!");
			return;
		}		
		username = s1;
		password = s2;
		ok = true;
		dispose();
	}	
}


