package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;

/**
 * 
 * @author Mac
 *
 */
public class SlideshowController implements LogoutController {

	@FXML
	public ImageView displayArea;
	
	@FXML
	public Button mForward, mBackward, mBack, mLogOff;
	
	@FXML
	public TextField tfCaption, tfTagName, tfTagValue;
	
	@FXML
	public Text tStatus;
	
	/** 
	 * Stores instances of photos going to be displayed 
	 */ 
	public static ArrayList<Photo> album = new ArrayList<>();
	public static final int frontIndex = 0;
	public static int backIndex;
	public static int currentIndex;

	
	/** 
	 * updates slideshow 
	 */ 
	public void start() {
		update();
		
	}
	
	/**
	 * Populates the imageview with the current image 
	 */
	public void update() {
		currentIndex = 0;
		backIndex = album.size()-1;
		
		File file;
		Photo photo = album.get(0);
		if (photo != null) {
			file = photo.getPic();
			Image image = new Image(file.toURI().toString());
			displayArea.setImage(image);
		}
		
		int ci = currentIndex+1;
		int bi = backIndex+1;
		tStatus.setText("Photo: " + ci + " of " + bi);
		
	}
	
	/**
	 *  On next the image view switches to the next photo 
	 */
	public void forward() {
		if (currentIndex+1 > backIndex) {
			return;
		} else {
			currentIndex++;
			File file;
			Photo photo = album.get(currentIndex);
			if (photo != null) {
				file = photo.getPic();
				Image image = new Image(file.toURI().toString());
				displayArea.setImage(image);
				int ci = currentIndex+1;
				int bi = backIndex+1;
				tStatus.setText("Photo: " + ci + " of " + bi);
			}
		}
	}
	
	/**
	 * On previous the imageview switches back to the previous photo 
	 */
	public void backward() {
		if (currentIndex-1 < frontIndex) {
			return;
		} else {
			currentIndex--;
			File file;
			Photo photo = album.get(currentIndex);
			if (photo != null) {
				file = photo.getPic();
				Image image = new Image(file.toURI().toString());
				displayArea.setImage(image);
				int ci = currentIndex+1;
				int bi = backIndex+1;
				tStatus.setText("Photo: " + ci + " of " + bi);
			}
		}
	}
	
	/**
	 * Redirects the user to the previous page: the PhotoView page
	 * @param event
	 * @throws IOException
	 */
	public void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		PhotoViewController photoViewController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		photoViewController.start(appStage);
		appStage.setScene(adminScene);
		appStage.show();
	}
	
	/**
	 * Logs the user out and returns the user to the login page
	 */
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
	}
}
