package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import model.Album;
import model.Superuser;
import model.User;

public class UserController implements LogoutController{
	@FXML
	public ListView<Album> listview;
	
	@FXML
	public Button mLogOff, mDisplay, mOpenAlbum, mRenameAlbum, mDeleteAlbum;
	
	@FXML
	public MenuButton mSortBy;
	
	@FXML
	public Text tUser, tNumber, tDateSpan;
	
	@FXML
	public TextField tfName, tfNewAlbum; //user1 and user2
	
	public static String username;
	
	public static ArrayList<Album> albumlist = new ArrayList<>();
	public ObservableList<Album> observableList;	
	public static Superuser adminuser = Main.driver;
	public static User user; // used to store current user
	
	public void start() {
		update();
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
		
		// Listen for selection changes
		if (albumlist.size() > 0) {
			tfName.setText(albumlist.get(0).albumName);	
			tNumber.setText("Number of Photos: " + albumlist.get(0).photoCount);
			tDateSpan.setText("Date Span: " + albumlist.get(0).firstDate + " - " + albumlist.get(0).lastDate);
		}
		listview.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> updateContent(newValue) );
	}
	
	// updates with listener
	private void updateContent(Album newValue) {
		if (newValue != null) {
			tfName.setText(newValue.albumName);	
			tNumber.setText("Number of Photos: " + newValue.photoCount);
			tDateSpan.setText("Date Span: " + newValue.firstDate + " - " + newValue.lastDate);
		}
	}
	
	public void addAlbum() throws IOException {
		String albumname = tfNewAlbum.getText().trim();
		Album album = new Album(albumname);
		
		if(albumname.isEmpty() || albumname == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Field");
			alert.setContentText("Please enter an album name.");
			alert.showAndWait();
			return;
		} else if(user.exists(album)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Album already exists.");
			alert.setContentText("Try entering a new album!");
			alert.showAndWait();
			return;
		}else {
			user.addAlbum(album);
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
		Album tempAlbum = new Album(newName);
		
		if (newName.length() == 0) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Rename Error");
			alert2.setContentText("Please enter a valid album name.");
			alert2.showAndWait();
			return;
		} else if (newName.equals(album.albumName)) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Rename Error");
			alert2.setContentText("No changes made. Please enter a valid album name before clicking 'Rename'.");
			alert2.showAndWait();
			return;
		} else if (user.exists(tempAlbum)) {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Rename Error");
			alert2.setContentText("Album name already in use.");
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
	
	public void openAlbum(ActionEvent event) throws IOException {
		PhotoViewController.album = listview.getSelectionModel().getSelectedItem();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		Scene photoScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(photoScene);
		appStage.show();
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
		tUser.setText(username + "'s Album List:");
		// tfName.setText(listview.getSelectionModel().getSelectedItem());
		user = adminuser.getUser(username);
		
		albumlist.clear();
		for (int i = 0; i < user.getAlbums().size(); i++) {
			albumlist.add(user.getAlbums().get(i));
		}
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();
	}
}
