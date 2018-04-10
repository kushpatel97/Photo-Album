package controller;

import java.io.*;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Superuser;
import model.User;

public class PhotoViewController implements LogoutController {
	@FXML
	public ListView<Photo> listview;
	
	@FXML
	public ImageView displayArea;
	private Image image;
	
	@FXML
	public Button mLogOff, mBack, mAdd, mDelete, mSlideshow, mSearch, mDisplay, mCopy, mMove;
	
	@FXML
	public TextField tfCopy, tfMove;
	
	@FXML 
	public Text tCaption;

	public static ArrayList<Photo> photolist = new ArrayList<>();
	public ObservableList<Photo> observableList;	
	public static Superuser adminuser = Main.driver;
	public static ArrayList<Album> albumlist; // for move/copy
	public static Album album; // used to store current user
	
	public void start() {
		displayArea.setFitHeight(100);
		displayArea.setFitWidth(100);
		displayArea.setPreserveRatio(false);
		System.out.println("User Page");
		update();
		if(!photolist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
		
		if (photolist.size() > 0) {
			tCaption.setText("Caption: " + photolist.get(0).caption);
			displayThumbnail();
		}
		listview.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			displayThumbnail();
			updateCaption();
		});
	}
	
	public void move() throws IOException {
		String moveAlbum = tfMove.getText().trim();
		boolean inList = false;
		int albumIndex = 0;
		for (int x = 0; x < albumlist.size(); x++) {
			Album tempalbum = albumlist.get(x);
			if (tempalbum.getName().equals(moveAlbum)) {
				inList = true;
				albumIndex = x;
			}
		}

		// Move
		if (!moveAlbum.isEmpty() && inList) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Logout");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to move this photo to " + moveAlbum + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) { 
				Album newAlbum = albumlist.get(albumIndex);
				Photo photo = listview.getSelectionModel().getSelectedItem();
				newAlbum.addPhoto(photo);
				album.deletePhoto(listview.getSelectionModel().getSelectedIndex());
				
				newAlbum.save(newAlbum);
				album.save(album);
			} else {
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Move Photo Error");
			alert.setHeaderText("Album not found or does not exist.");
			alert.showAndWait();
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
		}
		System.out.println("move");
	}
	
	public void copy() throws IOException {
		String copyAlbum = tfCopy.getText().trim();
		boolean inList = false;
		int albumIndex = 0;
		for (int x = 0; x < albumlist.size(); x++) {
			Album tempalbum = albumlist.get(x);
			if (tempalbum.getName().equals(copyAlbum)) {
				inList = true;
				albumIndex = x;
			}
		}

		// Copy
		if (!copyAlbum.isEmpty() && inList) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Logout");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to copy this photo to " + copyAlbum + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) { 
				Album newAlbum = albumlist.get(albumIndex);
				Photo photo = listview.getSelectionModel().getSelectedItem();
				newAlbum.addPhoto(photo);
				
				newAlbum.save(newAlbum);
			} else {
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Copy Photo Error");
			alert.setHeaderText("Album not found or does not exist.");
			alert.showAndWait();
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
		}
		System.out.println("move");
	}
	
	
	public void displayThumbnail() {
		Photo photo = listview.getSelectionModel().getSelectedItem();
		File file;
		if (photo != null) {
			file = photo.getPic();
			Image image = new Image(file.toURI().toString());
			displayArea.setImage(image);
		} else {
			displayArea.setImage(null);
		}
		return;
	}
	
	public void updateCaption() {
		Photo photo = listview.getSelectionModel().getSelectedItem();
		if (photolist.size() > 0 && photo != null) {
			tCaption.setText("Caption: " + photo.caption);
		} else {
			tCaption.setText("Caption: ");
		}
	}
	
	public void addPhoto() throws IOException {
		FileChooser filechooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
		filechooser.getExtensionFilters().add(extFilterJPG);
		File imgfile = filechooser.showOpenDialog(null);
		
		if (imgfile == null) {
			return;
		} /*else if (album.exists(imgfile.getAbsolutePath())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Photo already exists.");
			alert.setContentText("Try entering a new photo!");
			alert.showAndWait();
			return;
		}*/ else {
			String filepath = imgfile.getAbsolutePath();
			Photo newPhoto = new Photo(imgfile, filepath);
			album.addPhoto(newPhoto);
			update();	
		}
		
		Album.save(album);
		
	}
	
	public void deletePhoto() throws IOException {
		int index = listview.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this photo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			album.deletePhoto(index);
			update();
			   
			if (album.getPhotos().size() == 0) {
				mDelete.setVisible(false);
		    } else {
		    	int lastuserindex = album.getPhotos().size();
				if (album.getPhotos().size() == 1) {
					listview.getSelectionModel().select(0);
				} else if (index == lastuserindex) {
					listview.getSelectionModel().select(lastuserindex-1);
				} else { 
					listview.getSelectionModel().select(index);
				}
			}
			
			Album.save(album);
		} else {
			return;
		}
		return;
	}
	
	
	
	public void update() {
		photolist.clear();
		for (int i = 0; i < album.getPhotos().size(); i++) {
			photolist.add(album.getPhotos().get(i));
		}

		observableList = FXCollections.observableArrayList(photolist);
		listview.setItems(observableList);
		listview.refresh();
	}
	
	
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
	
	public void display(ActionEvent event) throws IOException {
		if (photolist.size() > 0) {
			boolean checked = false;
			for (int x = 0; x < photolist.size(); x++) {
				if (listview.getSelectionModel().isSelected(x)) {
					checked = true;
				}
			}
			
			if (checked) {
				SinglePhotoController.photo = listview.getSelectionModel().getSelectedItem();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SinglePhoto.fxml"));
				Parent sceneManager = (Parent) fxmlLoader.load();
				SinglePhotoController singlePhotoController = fxmlLoader.getController();
				Scene adminScene = new Scene(sceneManager);
				Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				singlePhotoController.start();
				appStage.setScene(adminScene);
				appStage.show();	
			}
		}
		
	}
	
	public void slideshow(ActionEvent event) throws IOException {
		if (photolist.size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Slideshow Error");
			alert.setHeaderText("No Photos to Display.");
			alert.showAndWait();
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
		} else {
			SlideshowController.album = photolist;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Slideshow.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
			SlideshowController slideshowController = fxmlLoader.getController();
			Scene adminScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			slideshowController.start();
			appStage.setScene(adminScene);
			appStage.show();		
		}
	}
	
	
	
	public void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		UserController userController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		userController.start();
		appStage.setScene(adminScene);
		appStage.show();
	}
	
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
	}

}
