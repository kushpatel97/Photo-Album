package controller;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Superuser;

public class LoginController {
	
	@FXML public Button mLogIn;
	
	@FXML public TextField tfUsername;
	
	public final String admin = "admin";
	
	public static Superuser driver = Main.driver;

	public void login(ActionEvent event) throws IOException {
		
		//Make lowercase idk????. Also check edge cases
		String username = tfUsername.getText().trim();
		//Parent sceneManager;
		

		if (username.equals(admin)) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
			AdminController adminController = fxmlLoader.getController();
			Scene adminScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			adminController.start();
			appStage.setScene(adminScene);
			appStage.show();
		}
		else if (isUser(username)) {
			UserController.username = username;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
			UserController userController = fxmlLoader.getController();
			Scene userScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			userController.start();
			appStage.setScene(userScene);
			appStage.show();
		}
		else if (username.isEmpty() || username == null) {
			System.out.print("Empty String");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Please enter a username");
			//alert.showAndWait();
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
		} else {
			System.out.println("Incorrect Input");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Issue Encountered");
			alert.setHeaderText("Please enter a valid username");
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
			
		}
		
		
	}
	
	public static boolean isUser(String user) {
		for (int i = 0; i < driver.getUsers().size(); i++) {
			if (driver.getUsers().get(i).getUsername().equals(user)) {
				return true;
			} 
		}
		
		return false;
	}
}
