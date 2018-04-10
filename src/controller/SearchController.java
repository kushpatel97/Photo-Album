package controller;

import java.io.IOException;
import java.time.LocalDate;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;

public class SearchController implements LogoutController {

	
	@FXML
	public Button mBack, mLogOff, mAddTag, mSearchTag, mSearchDate, mCreateAlbum;
	
	@FXML
	public TextField tfName, tfValue;
	
	@FXML
	public ListView<String> listview;
	
	@FXML
	public ImageView imageview;
	
	@FXML
	public DatePicker dTo, dFrom;
	
	
	public ArrayList<Tag> taglist = new ArrayList<Tag>();
	public ArrayList<String> tagdisplay = new ArrayList<String>();
	public ObservableList<String> obsTag;
	
	//Storing photos
	public ArrayList<Photo> photolist = new ArrayList<Photo>();
	
	public void start() {
		System.out.println("At start");
		
	}
	
	public void searchByDate(ActionEvent event) throws IOException {
		LocalDate from = dFrom.getValue();
		LocalDate to = dTo.getValue();
		
		if(from == null || to == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Pleas fill in the date fields!");
			alert.setContentText("Dates cannot be left blank!");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
		   else {
			   alert.close();
		   }
			return;
		}
		
		if(to.isBefore(from)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Invalid Date Range");
			alert.setContentText("The From date should be before the To date.");

			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
			   alert.close();
			}
			return;
		}
		
		this.photolist = Main.driver.getCurrent().getPhotosInRange(from, to);
		//need method to display photos
		
		
		
	}
	
	public void searchByTags(ActionEvent event) throws IOException{
		if(taglist.isEmpty() || taglist == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please add a tag to the list before you search for it!");
			alert.setContentText("List of tags is empty!");

			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
			   alert.close();
			}
			return;	
		}
		this.photolist = Main.driver.getCurrent().getTaggedPhotos(taglist);
		
		// Need to use this list to display pictures some how
		
	}
	
	public void addTag(ActionEvent event) throws IOException {
		if(tfName.getText().trim().isEmpty() || tfValue.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Missing Values");
			 alert.setHeaderText("Enter a key and its value!");
			 alert.setContentText("The key and value must be filled!");

			   Optional<ButtonType> buttonClicked=alert.showAndWait();
			   if (buttonClicked.get()==ButtonType.OK) {
				   alert.close();
			   }
			   else {
				   alert.close();
			   }
			return;		
		}
		Tag tag = new Tag(tfName.getText().trim(), tfValue.getText().trim());
		taglist.add(tag);
		updateTagList();
	}
	
	public void updateTagList() {
		tagdisplay.clear();
		for(Tag tag : taglist) {
			tagdisplay.add("Name: " + tag.name +    " | Value: " + tag.value);
		}
		obsTag = FXCollections.observableArrayList(tagdisplay);
		listview.setItems(obsTag);
		
		tfName.clear();
		tfValue.clear();

	}
	public void createAlbum(ActionEvent event) throws IOException{
		if(photolist.isEmpty()){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error Dialog");
			 alert.setHeaderText("Cannot create album! Searched photos is empty!");
			 alert.setContentText("Please try different search results");

			   Optional<ButtonType> buttonClicked=alert.showAndWait();
			   if (buttonClicked.get()==ButtonType.OK) {
				   alert.close();
			   }
			   else {
				   alert.close();
			   }
			return;		
			
		}
		Dialog<String> dialog = new Dialog<>();
		   dialog.setTitle("Create a New Album from search results");
		   dialog.setHeaderText("Name for album created from search results ");
		   dialog.setResizable(true);
		   
		   Label albumnameLabel = new Label("Album Name: ");
		   TextField albumnameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(albumnameLabel, 1, 1);
		   grid.add(albumnameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, String>() {
			   @Override
			   public String call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   if (albumnameTextField.getText().trim().isEmpty()) {
						   Alert alert = new Alert(AlertType.ERROR);
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText("Album name required!");
						   alert.setContentText("Please try again");

						   Optional<ButtonType> buttonClicked=alert.showAndWait();
						   if (buttonClicked.get()==ButtonType.OK) {
							   alert.close();
						   }
						   else {
							   alert.close();
						   }
						   return null;
					   }
											   
					   return albumnameTextField.getText().trim();
				   }
				   return null;
			   }
			
		   });
		   
		   Optional<String> result = dialog.showAndWait();
		   
		   if (result.isPresent()) {
			   Album albumFromSearch = new Album(result.get());
			   Main.driver.getCurrent().addAlbum(albumFromSearch);
			   for(Photo photo : photolist) {
				   albumFromSearch.addPhoto(photo);
			   }
			   
		   }
		
	}

	public void back(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		UserController userController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		userController.start();
		appStage.setScene(adminScene);
		appStage.show();
	}
	
	public void logOut(ActionEvent event) throws IOException{
		logMeOut(event);
		System.out.println("Logged out from: Search");
	}


}
