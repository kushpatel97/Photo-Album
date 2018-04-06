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
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import model.Superuser;
import model.User;

public class UserController implements LogoutController{
	@FXML
	public ListView<String> listview;
	
	@FXML
	public Button mLogOff, mAddAlbum, mOpenAlbum, mRenameAlbum, mDeleteAlbum;
	
	@FXML
	public MenuButton mSortBy;
	
	@FXML
	public Text tUser;
	
	@FXML
	public TextField tfUsername; //user1 and user2
	
	public static String username;
	
	public static ArrayList<String> albumlist = new ArrayList<>();
	public ObservableList<String> observableList;	
	public static Superuser adminuser = Main.driver;
	public static User user; // = adminuser.getUser(username);
	
	public void start() {
		System.out.println("User Page");
		update();
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void addAlbum() {
		
	}
	
	public void renameAlbum() {
		
	}
	public void openAlbum() {
		
	}
	public void deleteAlbum() {
		
	}
	
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
	}
	
	public void update() {
		tUser.setText("User: " + username);
		user = adminuser.getUser(username);
		
		//user.addAlbum("Hello testing");
		
		albumlist.clear();
		for (int i = 0; i < user.getAlbums().size(); i++) {
			albumlist.add(user.getAlbums().get(i).albumName);
		}
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();
	}
}
