package controller;

import java.io.IOException;

import java.util.*;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.*;


/**
 * @author Alex Louie
 * @author Kush Patel
 * The purpose of this class is to control the actions on the Admin Page
 */
public class AdminController implements LogoutController {
	

	@FXML
	public ListView<String> listview;
	

	@FXML
	public Button mCreate, mDelete, mLogOff;
	

	@FXML
	public TextField tfUsername;
	
	/**
	 * An ArrayList of strings that stores the added users
	 */
	public static ArrayList<String> userlist = new ArrayList<>();
	
	/**
	 * An observable list that helps display the list of users
	 */
	public ObservableList<String> observableList;
	
	/**
	 * An instance of the admin that is created to help keep track of current values
	 */
	public static Superuser adminuser = Main.driver;
	
	/**
	 * On scene start, the listview is updated with the list of current users
	 */
	public void start() {
		update();
		if(!userlist.isEmpty()) {
    		listview.getSelectionModel().select(0);
		}
	}
	
	/**
	 * Logs the user out
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
	}
	
	/**
	 * Adds a user to the user list
	 * @param event
	 * @throws IOException
	 */
	public void addUser(ActionEvent event) throws IOException {
		String username = tfUsername.getText().trim();
		if(username.isEmpty() || username == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Error");
			alert.setContentText("Empty Field: Please enter a username.");
			alert.showAndWait();
			return;
		} else if(adminuser.exists(username)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Error.");
			alert.setContentText("Username already exists. Try entering a new username!");
			alert.showAndWait();
			return;
		} else if (username.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Error");
			alert.setContentText("Cannot add 'admin' to Users.");
			alert.showAndWait();
			return;
		} else {
			adminuser.addUser(username);
			update();
			tfUsername.clear();
		}
		Superuser.save(adminuser);	
	}
	
	/**
	 * Removes a user from the user list
	 * @param event
	 * @throws IOException
	 */
	public void deleteUser(ActionEvent event) throws IOException {

		int index = listview.getSelectionModel().getSelectedIndex();
		int adminList = userlist.indexOf("Admin");
		
				   
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirm Delete");
		   alert.setHeaderText(null);
		   alert.setContentText("Are you sure you want to delete this User?");

		   Optional<ButtonType> result = alert.showAndWait();
		   if (result.get() == ButtonType.OK) { 
			  
			   adminuser.deleteUser(index);
			   update();
			   Superuser.save(adminuser);
			   
			   if(adminuser.getUsers().size() == 1) {
					mDelete.setVisible(false);
		       }
			   else {
				   int lastuserindex = adminuser.getUsers().size();
				   if(adminuser.getUsers().size() == 1) { 
					   listview.getSelectionModel().select(0);
				   }
				   else if(index == lastuserindex) { 
					   listview.getSelectionModel().select(lastuserindex-1);
				   }
				   else { 
					   listview.getSelectionModel().select(index);
				   }
			   }
			      
		   } else {
			   return;
		   }
		   return;
		 
		
	}
	
	/**
	 * Refreshes the listview everytime a user is added or removed
	 */
	public void update() {
		userlist.clear();
		for (int i = 0; i < adminuser.getUsers().size(); i++) {
			userlist.add(adminuser.getUsers().get(i).getUsername());
		}
		listview.refresh();
		observableList = FXCollections.observableArrayList(userlist);
		listview.setItems(observableList);
		listview.refresh();

	
	}
	
	

}
