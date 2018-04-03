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
	
	public static ArrayList<String> nameandusername = new ArrayList<>();
	public ObservableList<String> obsList;
	
	public static PhotoDriver ulistmanager = Main.driver;
	
	public void start() {
		System.out.println("Admin Page");
		
		redolist();
		
		obsList = FXCollections.observableArrayList(nameandusername);
		listview.setItems(obsList);
		
		if(!nameandusername.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void LogOut(ActionEvent event) {
		
	}
	
	public void AddUser(ActionEvent event) throws IOException {
		ulistmanager.addUser(tfUsername.getText().trim());
		update();
		PhotoDriver.writeApp(ulistmanager);
		
	}
	
	public void DeleteUser(ActionEvent event) {
		
	}
	
	public static void redolist() {
		nameandusername.clear();
		for (int i = 0; i < ulistmanager.getUsers().size(); i++) {
			nameandusername.add(ulistmanager.getUsers().get(i).getUsername());
		}
		
	}
	
	public void update() {
		obsList = FXCollections.observableArrayList(nameandusername);
		listview.setItems(obsList);
		listview.refresh();
	}

}
