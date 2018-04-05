package controller;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Superuser;

public class UserController {
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
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void LogOut(ActionEvent event) {
		
	}
	
	public void AddUser() {
		
	}
	
	public void DeleteUser() {
		
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
		}
		Superuser.save(adminuser);	
	}
	
	public void deleteUser(ActionEvent event) {
		
	}
	
	public void update() {
		userlist.clear();
		for (int i = 0; i < adminuser.getUsers().size(); i++) {
			userlist.add(adminuser.getUsers().get(i).getUsername());
		}
		observableList = FXCollections.observableArrayList(userlist);
		listview.setItems(observableList);
		listview.refresh();
	}
}
