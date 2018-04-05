package application;
	
import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Superuser;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	public static Superuser driver = new Superuser();
	public Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();

			
			Scene scene = new Scene(root);
			mainStage.setResizable(false);
			mainStage.setTitle("Photo Library");
			mainStage.setScene(scene);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				try {
					Superuser.save(driver);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("Closed");
			}
		});
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			driver = Superuser.load();
		}catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}
}
