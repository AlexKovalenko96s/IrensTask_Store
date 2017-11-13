package ua.kas.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("res/main.fxml"));
			Scene scene = new Scene(root, 400 - 10, 300 - 10);
			scene.getStylesheets().add(getClass().getResource("res/application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("res/yellow-archive-icon.png")));
			primaryStage.setTitle("Store");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
