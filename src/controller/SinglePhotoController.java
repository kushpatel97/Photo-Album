package controller;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Superuser;
import model.Tag;

public class SinglePhotoController implements LogoutController {
	@FXML
	public ListView<Tag> listview;
	
	@FXML
	public ImageView displayArea;
	
	@FXML
	public Button mLogOff, mBack, mCaption, mAddTag, mRemoveTag;
	
	@FXML
	public TextField tfCaption, tfTagName, tfTagValue;

	public static ArrayList<Tag> taglist = new ArrayList<>();
	public ObservableList<Tag> observableList;	
	public static Photo photo; // used to store current photo
	
	
	public void start() {
		System.out.println("At single photo");
		update();
		if(!taglist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}
	}
	
	public void saveCaption() throws IOException {
		String caption = tfCaption.getText().trim();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Caption Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to change the caption to: " + caption);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			photo.setCaption(caption);
			photo.save(photo);
		} else {
			return;
		}
	}
	
	public void addTag() {
		String tagName = tfTagName.getText().trim();
		String tagValue = tfTagValue.getText().trim();
		if (tagName.isEmpty() || tagValue.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tag Add Error");
			alert.setContentText("Tag Infomration Incomplete.");
			alert.showAndWait();
			return;
		} else {
			Tag tag = new Tag(tagName, tagValue);
			photo.taglist.add(tag);
		}
		
	}
	
	public void update() {
		tfCaption.setText(photo.getCaption());
		
		File file;
		if (photo != null) {
			file = photo.getPic();
			Image image = new Image(file.toURI().toString());
			displayArea.setImage(image);
		}
		
		taglist.clear();
		for (int i = 0; i < photo.getTagList().size(); i++) {
			taglist.add(photo.getTagList().get(i));
		}

		observableList = FXCollections.observableArrayList(taglist);
		listview.setItems(observableList);
		listview.refresh();
	} 
	
	public void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		PhotoViewController photoViewController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		photoViewController.start();
		appStage.setScene(adminScene);
		appStage.show();
	}
	
	public void logOut(ActionEvent event) throws IOException {
		logMeOut(event);
		System.out.println("Logged Out");
	}
	
}
