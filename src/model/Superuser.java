package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import application.Main;

/**
 * The admin class that controls functions for adding users
 * @author Kush Patel
 * @author Alex Louie
 *
 */
public class Superuser implements Serializable {
	
	/**
	 * Class that manages the photo album/
	 * Also takes care of user persistence
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * List of users
	 */
	public ArrayList<User> users;
	
	/**
	 * Current user
	 */
	public User current;
	
	/**
	 * Is the user logged in
	 */
	public boolean loggedIn;

	/**
	 * Constructor
	 */
	public Superuser() {
		users = new ArrayList<User>();
		users.add(new User("admin"));
		this.current = null;
		this.loggedIn = false;
	}
	
	/**
	 * Adds a user to a list
	 * @param username
	 */
	public void addUser(String username) {
		users.add(new User(username));
	}
	
	/**
	 * Deletes a user
	 * @param index int
	 */
	public void deleteUser(int index) {
		users.remove(index);
		System.out.println(users);
	}
	
	/**
	 * deletes a user
	 * @param username username
	 */
	public void deleteUser(String username) {
		User temp = new User(username);
		users.remove(temp);
	}
	
	/**
	 * check if a user exists
	 * @param username
	 * @return
	 */
	public boolean exists(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * check if user exists
	 * @param user  username
	 * @return true, exists
	 */
	public boolean checkUser(String user) {
		int index = 0;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(user)) {
				index = i;
			}
		}
		
		if(index == -1) {
			return false;
		}
		this.setCurrent(users.get(index));
		this.loggedIn = true;
		return true;
		
	}
	
	/**
	 * Get users current index
	 * @return index umber of user
	 */
	public int getUserIndex() {
		int index = 0;
		for(User user : users) {
			if(user.getUsername().equals(Main.driver.getCurrent().getUsername())) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 *  
	 * @return list of users
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * Gets a user by username
	 * @param username username
	 * @return User instance
	 */
	public User getUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		
		return null;
	}
	
	/**
	 * Set user list
	 * @param users
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	/**
	 * get current user
	 * @return current user
	 */
	public User getCurrent() {
		return current;
	}

	/**
	 * Sets current user
	 * @param current
	 */
	public void setCurrent(User current) {
		this.current = current;
	}
	
	/**
	 * Save's state to .dat file
	 * @param pdApp
	 * @throws IOException
	 */
	public static void save(Superuser pdApp) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(pdApp);
			oos.close();
	}
	
	/**
	 * Loads from dat file
	 * @return userlist
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Superuser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Superuser userList = (Superuser) ois.readObject();
		ois.close();
		return userList;
		
	}

}
