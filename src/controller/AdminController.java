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

public class AdminController {
	
	@FXML
	public ListView<String> listview;
	
	@FXML
	public Button mCreate, mDelete, mLogOff;
	
	@FXML
	public TextField tfUsername;
	
	public static ArrayList<String> userlist = new ArrayList<>();
	public ObservableList<String> observableList;	
	public static Superuser adminuser = Main.driver;
	
	public void start() {
		System.out.println("Admin Page");
		update();
		if(!userlist.isEmpty()) {
    		listview.getSelectionModel().select(0);
		}
		System.out.println(userlist.size());
	}
	
	public void LogOut(ActionEvent event) {
		
	}
	
	public void addUser(ActionEvent event) throws IOException {
		String username = tfUsername.getText().trim();
		if(username.isEmpty() || username == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Field");
			alert.setContentText("Please enter a username.");
			alert.showAndWait();
			return;
		}
		else if(adminuser.exists(username)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Username already exists.");
			alert.setContentText("Try entering a new username!");
			alert.showAndWait();
			return;
		}else {
			adminuser.addUser(username);
			update();
			tfUsername.clear();
		}
		Superuser.save(adminuser);	
	}
	
	public void deleteUser(ActionEvent event) throws IOException {

		int index = listview.getSelectionModel().getSelectedIndex();
		int adminList = userlist.indexOf("Admin");
		
		
		System.out.println(adminList);
		   
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
	public void update() {
		userlist.clear();
		for (int i = 0; i < adminuser.getUsers().size(); i++) {
			userlist.add(adminuser.getUsers().get(i).getUsername());
		}
		listview.refresh();
		observableList = FXCollections.observableArrayList(userlist);
		listview.setItems(observableList);
		listview.refresh();
		System.out.println(userlist);
	}
	
	

}
