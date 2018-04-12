package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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



/** 
 * User controller controls all the actions from opening/adding/deleting albums to searching 
 * @author Alex Louie 
 * @author Kush Patel 
 * 
 */
public class UserController implements LogoutController{
	@FXML
	public ListView<Album> listview;
	
	@FXML
	public Button mLogOff, mDisplay, mOpenAlbum, mRenameAlbum, mDeleteAlbum, mSearch, mAddAlbum;
	
	@FXML
	public MenuButton mSortBy;
	
	@FXML
	public Text tUser, tNumber, tDateSpan;
	
	@FXML
	public TextField tfName, tfNewAlbum; //user1 and user2
	
	/**
	 * Current Username
	 */
	public static String username;
	
	/**
	 * Stores instances of all albumss
	 */
	public static ArrayList<Album> albumlist = new ArrayList<>();
	
	/**
	 * Helps display the albumlist
	 */
	public ObservableList<Album> observableList;	
	
	/**
	 * A Superuser instance that helps maintain the state of the program
	 */
	public static Superuser adminuser = Main.driver;
	
	/**
	 * Stores current user
	 */
	public static User user;
	
	/**
	 * Current stock photo
	 */
	public static boolean stock;
	
	/**
	 * When the scene loads the page updates the album listview
	 * @param app_stage
	 */
	public void start(Stage app_stage) {
		update();
		
		app_stage.setTitle(adminuser.getCurrent().getUsername() + " Collection of Photo's");
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	
		// Listen for selection changes
		if (albumlist.size() > 0) {
			tfName.setText(albumlist.get(0).albumName);	
			tNumber.setText("Number of Photos: " + albumlist.get(0).photoCount);
			tDateSpan.setText("Date Span (First, Last): \n\t" + albumlist.get(0).getFirstDate() + "\n\t" + albumlist.get(0).getLastDate());
		}
		listview.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> updateContent(newValue) );
	}
	
	/**
	 * Sorts alphabetically, A-first to Z-last
	 * @throws IOException
	 */
	public void sortByAZ() throws IOException {
		Collections.sort(albumlist, Album.sortByAZ);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Sorts alphabetically, Z-first to A-last
	 * @throws IOException
	 */
	public void sortByZA() throws IOException {
		Collections.sort(albumlist, Album.sortByZA);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Sorts by album size, increasing
	 * @throws IOException
	 */
	public void sortByIP() throws IOException {
		Collections.sort(albumlist, Album.sortByIP);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Sorts by album size, decreasing
	 * @throws IOException
	 */
	public void sortByDP() throws IOException {
		Collections.sort(albumlist, Album.sortByDP);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Sort by oldest to newest date
	 * @throws IOException
	 */
	public void sortByID() throws IOException {
		Collections.sort(albumlist, Album.sortByID);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Sort by newest to oldest date
	 * @throws IOException
	 */
	public void sortByDD() throws IOException {
		Collections.sort(albumlist, Album.sortByDD);
		observableList = FXCollections.observableArrayList(albumlist);
		listview.setItems(observableList);
		listview.refresh();	
		User.save(user);
	}
	
	/**
	 * Updates the values of the Album properties
	 * @param newValue The new album value
	 */
	private void updateContent(Album newValue) {
		if (newValue != null) {
			tfName.setText(newValue.albumName);	
			tNumber.setText("Number of Photos: " + newValue.photoCount);
			tDateSpan.setText("Date Span: \n\t" + newValue.getFirstDate() + " \n\t" + newValue.getLastDate());
		}
	}
	
	/**
	 * Updates properties of the album
	 */
	public void updateContentBack() {
		if (albumlist.size() > 0) {
			Album alb = listview.getSelectionModel().getSelectedItem();
			tNumber.setText("Number of Photos: " + alb.photoCount);
			tDateSpan.setText("Date Span: \n\t" + alb.getFirstDate() + "\n\t" + alb.getLastDate());
		}
	}
	
	/**
	 * Add album to as user's album list
	 * @throws IOException
	 */
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
	
	/**
	 * Renames an album
	 * @throws IOException
	 */
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
	
	/**
	 * Opens an album and sends it to the photoview scene
	 * @param event
	 * @throws IOException
	 */
	public void openAlbum(ActionEvent event) throws IOException {
		PhotoViewController.user = user;
		PhotoViewController.album = listview.getSelectionModel().getSelectedItem();
		PhotoViewController.albumlist = albumlist;
		
		//Changed
		int albumindex = listview.getSelectionModel().getSelectedIndex();
		int currentuserindex = adminuser.getUserIndex();
		if(adminuser.getUsers().get(currentuserindex).getAlbums().size() == 0) { 
		      Alert alert = new Alert(AlertType.ERROR); 
		      alert.setTitle("Empty Deletion"); 
		      alert.setHeaderText(null); 
		      alert.setContentText("Cannot delete something that isn't there"); 
		      alert.showAndWait(); 
		      return; 
		    } 
		Album album = adminuser.getUsers().get(currentuserindex).getAlbums().get(albumindex);
		
		adminuser.getUsers().get(currentuserindex).setCurrentAlbum(album);
		//End Change
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		PhotoViewController photoController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		photoController.start(appStage);
		appStage.setScene(adminScene);
		appStage.show();
	}
	
	/**
	 * Deletes an album from the user list
	 * @throws IOException
	 */
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
	
	/**
	 * Redirects the user to the search page
	 * @param event
	 * @throws IOException
	 */
	public void search(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		SearchController searchController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		searchController.start();
		appStage.setScene(adminScene);
		appStage.show();
		
	}
	
	/**
	 * Logs the current user out
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		//System.out.println("Logged Out");
	}
	
	/**
	 * Updates the albums contents
	 */
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
