package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML public Button mLogIn;
	
	@FXML public TextField tfUsername;
	
	public final String adminUserName = "admin";

	public void login(ActionEvent event) throws IOException {
		
		//Make lowercase idk????. Also check edge cases
		String username = tfUsername.getText();
		Parent sceneManager;
		System.out.print(username);
		if(username.equals(adminUserName)) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
			sceneManager = (Parent) fxmlLoader.load();
			AdminController adminController = fxmlLoader.getController();
			Scene adminScene = new Scene(sceneManager);
			
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			adminController.start();
			
			appStage.setScene(adminScene);
			
			appStage.show();
			System.out.print("Clicked Me");
		}
		
		
	}
}
