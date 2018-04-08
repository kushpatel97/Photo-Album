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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Superuser;
import model.User;

public class PhotoViewController implements LogoutController {
	@FXML
	public ListView<Photo> listview, displayArea;
	
	@FXML
	public Button mLogOff, mBack, mCaption, mAdd, mDelete, mSlideshow, mSearch, mDisplay;
	
	@FXML
	public TextField tfCaption;
	
	public static ArrayList<Photo> photolist = new ArrayList<>();
	public ObservableList<Photo> observableList;	
	public static Superuser adminuser = Main.driver;
	public static Album album; // used to store current user
	
	public void start() {
		System.out.println("User Page");
		update();
		if(!photolist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}

	}
	
	public void addPhoto() throws IOException {
		String photoname = tfCaption.getText().trim();
		Photo photo = new Photo(photoname);
		
		if(photoname.isEmpty() || photoname == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Field");
			alert.setContentText("Please enter an album name.");
			alert.showAndWait();
			return;
		} else if(album.exists(photo)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Photo already exists.");
			alert.setContentText("Try entering a new photo!");
			alert.showAndWait();
			return;
		}else {
			album.addPhoto(photo);
			update();
			tfCaption.clear();
		}
		Album.save(album);	
	}
	
	public void update() {
		photolist.clear();
		for (int i = 0; i < album.getPhotos().size(); i++) {
			photolist.add(album.getPhotos().get(i));
		}
		if (album.getPhotos().size() == 0) {
			System.out.println("hh");
		}
		observableList = FXCollections.observableArrayList(photolist);
		listview.setItems(observableList);
		listview.refresh();
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
