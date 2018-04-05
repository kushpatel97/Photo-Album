package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface LogoutController {
	
	default void logMeOut(ActionEvent e) throws IOException {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		appStage.setScene(adminScene);
		appStage.show();
		
	}

}
