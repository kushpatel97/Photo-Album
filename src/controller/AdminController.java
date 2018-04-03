package controller;

import java.io.IOException;
import java.util.*;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	
	public static PhotoDriver photodriver = Main.driver;
	
	public void start() {
		System.out.println("Admin Page");
		
		redolist();
		
		observableList = FXCollections.observableArrayList(userlist);
		listview.setItems(observableList);
		
		if(!userlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void LogOut(ActionEvent event) {
		
	}
	
	public void AddUser(ActionEvent event) throws IOException {
		photodriver.addUser(tfUsername.getText().trim());
		update();
		PhotoDriver.writeApp(photodriver);
		
	}
	
	public void DeleteUser(ActionEvent event) {
		
	}
	
	public static void redolist() {
		userlist.clear();
		for (int i = 0; i < photodriver.getUsers().size(); i++) {
			userlist.add(photodriver.getUsers().get(i).getUsername());
		}
		
	}
	
	public void update() {
		observableList = FXCollections.observableArrayList(userlist);
		listview.setItems(observableList);
		listview.refresh();
	}

}
