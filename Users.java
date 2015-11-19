import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Users {
	public User curUser = null;	
	public ArrayList<User> users = new ArrayList<User>();
	
	public Users() {
		try {
			Scanner in = new Scanner(new File("users.txt"));
			int size = Integer.parseInt(in.nextLine().trim());
			for(int i = 0; i < size; i++) {
				User u = new User();
				u.name = in.nextLine().trim();
				u.address = in.nextLine().trim();
				u.username = in.nextLine().trim();
				u.password = in.nextLine().trim();
				u.wons = Integer.parseInt(in.nextLine().trim());
				u.loses = Integer.parseInt(in.nextLine().trim());
				users.add(u);
			}
			in.close();
		}
		catch(Exception e) {}
	}

	public void add(User user) {
		users.add(user);
	}
	
	public boolean exist(String username) {
		for(int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.username.equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public User login(String username, String password) {
		for(int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.username.equals(username) && u.password.equals(password)) {
				return u;
			}
		}
		return null;
	}
	
	public @Override String toString() {
		Collections.sort(users, new Comparator<User>(){
			@Override
			public int compare(User u1, User u2) {
				if(u1.wons > u2.wons) return -1;
				else if(u1.wons < u2.wons) return 1;
				else {
					if(u1.loses < u2.loses) return -1;
					else if(u1.loses > u2.loses) return 1;					
				}
				return 0;
			}			
		});	

		String s = String.format("%-18s", "name") + String.format("%12s", "wons") + String.format("%12s", "loses") + "\r\n";
		s += "--------------------------------------------\r\n";
		for(int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			s += String.format("%-18s", u.name) + String.format("%12d", u.wons) + String.format("%12d", u.loses) + "\r\n";
		}
		return s;
	}
	
	public void save() {
		try {
			PrintWriter w = new PrintWriter(new File("users.txt"));
			w.println(users.size());
			for(int i = 0; i < users.size(); i++) {
				User u = users.get(i);
				w.println(u.name);
				w.println(u.address);
				w.println(u.username);
				w.println(u.password);
				w.println(u.wons);
				w.println(u.loses);
			}
			w.close();
		}
		catch(Exception e) {}
	}
	
}
