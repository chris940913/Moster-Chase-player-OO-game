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

public class Register extends JDialog implements ActionListener {
	public JTextField txtName;
	public JTextField txtAddress;
	public JTextField txtUsername;
	public JPasswordField txtPassword1;
	public JPasswordField txtPassword2;
	public boolean ok = false;
	public String name = "";
	public String address = "";
	public String username = "";
	public String password = "";
	
	public Register() {
		this.setTitle("Register");
		this.setSize(290, 250);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		
		JPanel p1 = new JPanel(new GridLayout(6, 1));
		add(p1, BorderLayout.CENTER);
		
		JPanel p11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p11.add(new JLabel("Name"));
		txtName = new JTextField(15);
		p11.add(txtName);
		p1.add(p11);
		
		JPanel p12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p12.add(new JLabel("Address"));
		txtAddress = new JTextField(15);
		p12.add(txtAddress);
		p1.add(p12);
		
		JPanel p13 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p13.add(new JLabel("Username"));
		txtUsername = new JTextField(15);
		p13.add(txtUsername);
		p1.add(p13);
		
		JPanel p14 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p14.add(new JLabel("Password"));
		txtPassword1 = new JPasswordField(15);
		p14.add(txtPassword1);
		p1.add(p14);
		
		JPanel p15 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p15.add(new JLabel("Re Password"));
		txtPassword2 = new JPasswordField(15);
		p15.add(txtPassword2);
		p1.add(p15);		
		
		JPanel p16 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btn1 = new JButton("Ok");
		p16.add(btn1);
		p1.add(p16);
		btn1.addActionListener(this);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s1 = txtName.getText().trim();
		String s2 = txtAddress.getText().trim();
		String s3 = txtUsername.getText().trim();
		String s4 = String.valueOf(txtPassword1.getPassword());
		String s5 = String.valueOf(txtPassword2.getPassword());
		if(s1.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name is empty!");
			return;
		}
		if(s2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Address is empty!");
			return;
		}
		if(s3.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username is empty!");
			return;
		}
		if(s4.isEmpty() || s5.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Password is empty!");
			return;
		}	
		if(s4.equals(s5) == false) {
			JOptionPane.showMessageDialog(null, "Two password not match!");
			return;
		}	
		
		name = s1;
		address = s2;
		username = s3;
		password = s4;
		ok = true;
		dispose();
	}	
}


