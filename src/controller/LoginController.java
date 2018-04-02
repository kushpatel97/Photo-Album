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
	
	public final String admin = "admin";

	public void login(ActionEvent event) throws IOException {
		
		//Make lowercase idk????. Also check edge cases
		//Parent sceneManager;
		

		if(tfUsername.getText().equals(admin)) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
			Parent sceneManager = (Parent) fxmlLoader.load();
//			AdminController adminController = fxmlLoader.getController();
			Scene adminScene = new Scene(sceneManager);
			
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
//			adminController.start();
			
			appStage.setScene(adminScene);
			
			appStage.show();
			System.out.print("Clicked Me");
		}
		else {
			System.out.print("Failed");
		}
		
		
	}
}
