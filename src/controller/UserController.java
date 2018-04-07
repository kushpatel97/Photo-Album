package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import model.Album;
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
	public Text tUser, tNumber, tDateSpan;
	
	@FXML
	public TextField tfName, tfNewAlbum; //user1 and user2
	
	public static String username;
	
	public static ArrayList<String> albumlist = new ArrayList<>();
	public ObservableList<String> observableList;	
	public static Superuser adminuser = Main.driver;
	public static User user; // used to store current user
	
	public void start() {
		System.out.println("User Page");
		update();
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
		
		// Listen for selection changes
		if (albumlist.size() > 0) {
			tfName.setText(albumlist.get(0));	
		}
		listview.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> updateContent(newValue));

	}
	
	// updates 
	public void updateContent(String newValue) {
		tfName.setText(newValue);
	}
	
	public void addAlbum() throws IOException {
		String albumname = tfNewAlbum.getText().trim();
		if(albumname.isEmpty() || albumname == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Field");
			alert.setContentText("Please enter an album name.");
			alert.showAndWait();
			return;
		}
		else if(user.exists(albumname)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Album already exists.");
			alert.setContentText("Try entering a new album!");
			alert.showAndWait();
			return;
		}else {
			user.addAlbum(albumname);
			update();
			tfNewAlbum.clear();
		}
		User.save(user);	
	}
	
	public void renameAlbum() throws IOException {
		String newName = tfName.getText().trim();
		
		int index = listview.getSelectionModel().getSelectedIndex();
		Album album = user.getAlbum(index);
		Optional<ButtonType> result;
		
		if (newName.length() == 0) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Rename Error");
			alert2.setContentText("Please enter a valid album name.");
			alert2.showAndWait();
			return;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Rename");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to rename this album?");
			result = alert.showAndWait();
		}
		
		if (result.get() == ButtonType.OK) {
			album.rename(newName);
			update();
			User.save(user);
		} else {
			return;
		}
		return;
	}
	
	public void openAlbum() {
		
	}
	public void deleteAlbum() throws IOException {
		int index = listview.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			user.deleteAlbum(index);
			update();
			User.save(user);
			   
			if (user.getAlbums().size() == 0) {
				mDeleteAlbum.setVisible(false);
		    } else {
		    	int lastuserindex = user.getAlbums().size();
				if (user.getAlbums().size() == 1) {
					listview.getSelectionModel().select(0);
				} else if (index == lastuserindex) {
					listview.getSelectionModel().select(lastuserindex-1);
				} else { 
					listview.getSelectionModel().select(index);
				}
			}
		} else {
			return;
		}
		return;
	}
	
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
	}
	
	public void update() {
		tUser.setText("User: " + username);
		// tfName.setText(listview.getSelectionModel().getSelectedItem());
		user = adminuser.getUser(username);
		
		albumlist.clear();
		for (int i = 0; i < user.getAlbums().size(); i++) {
			albumlist.add(user.getAlbums().get(i).albumName);
		}
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();
	}
}
