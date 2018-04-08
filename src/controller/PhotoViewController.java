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

public class PhotoViewController {
	@FXML
	public ListView<Photo> listview, displayArea;
	
	@FXML
	public Button mLogOff, mBack, mCaption, mAdd, mDelete, mSlideshow, mSearch, mDisplay;
	
	@FXML
	public TextField tfCaption;
	
	public static ArrayList<Photo> albumlist = new ArrayList<>();
	public ObservableList<Photo> observableList;	
	public static Superuser adminuser = Main.driver;
	public static Album album; // used to store current user
	
	public void start() {
		System.out.println("User Page");
		update();
		if(!albumlist.isEmpty()) {
    		listview.getSelectionModel().select(0); //select first user
		}

	}
	
	public void update() {
	}

}
