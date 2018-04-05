package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Superuser implements Serializable {
	
	/**
	 * Class that manages the photo album/
	 * Also takes care of user persistence
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	public ArrayList<User> users;
	public User current;
	public boolean loggedIn;
	
	

	public Superuser() {
		users = new ArrayList<User>();
		users.add(new User("admin"));
		current = null;
		loggedIn = false;
	}
	
	
	public void addUser(String username) {
		users.add(new User(username));
	}
	
	public void deleteUser(String username) {
		int index = users.indexOf(new User(username));
		users.remove(index);
	}
	
	public boolean exists(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public User getCurrent() {
		return current;
	}

	public void setCurrent(User current) {
		this.current = current;
	}
	
	public static void save(Superuser pdApp) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(pdApp);
			oos.close();
	}
	
	public static Superuser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Superuser userList = (Superuser) ois.readObject();
		ois.close();
		return userList;
		
	}

}
