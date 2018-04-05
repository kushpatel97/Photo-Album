package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML public Button mLogIn;
	
	@FXML public TextField tfUsername;
	
	public final String admin = "admin";

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
			System.out.print("Clicked Me");
		}
		else if (isUser(username)) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
			// AdminController adminController = fxmlLoader.getController();
			Scene adminScene = new Scene(sceneManager);
			
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			// adminController.start();
			
			appStage.setScene(adminScene);
			
			appStage.show();
			System.out.print("Clicked Me");
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
		}
		
		
	}
	
	public static boolean isUser(String user) {
		return true;
	}
}
