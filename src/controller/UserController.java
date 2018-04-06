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
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import model.Superuser;

public class UserController implements LogoutController{
	@FXML
	public ListView<String> listview;
	
	@FXML
	public Button mSortBy, mLogOff, mAddAlbum, mOpenAlbum, mRenameAlbum, mDeleteAlbum;
	
	@FXML
	public Text tUser;
	
	@FXML
	public TextField tfUsername; //user1 and user2
	
	public static ArrayList<String> albumlist = new ArrayList<>();
	public ObservableList<String> observableList;	
	public static Superuser user = Main.driver;
	
	public static String username;
	
	public void start() {
		System.out.println("User Page");
		update();
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
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
		else if(user.exists(username)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Username already exists.");
			alert.setContentText("Try entering a new username!");
			alert.showAndWait();
			return;
		}else {
			user.addUser(username);
			update();
		}
		Superuser.save(user);	
	}
	
	public void deleteUser(ActionEvent event) {
		
	}
	
	public void update() {
		tUser.setText("User: " + username);
		albumlist.clear();
		for (int i = 0; i < user.getUsers().size(); i++) {
			albumlist.add(user.getUsers().get(i).getUsername());
		}
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();
	}
}
