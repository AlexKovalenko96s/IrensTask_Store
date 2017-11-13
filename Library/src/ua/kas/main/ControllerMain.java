package ua.kas.main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMain {

	@FXML
	Button btn_book;
	@FXML
	Button btn_move;
	@FXML
	Button btn_song;
	@FXML
	Button btn_game;

	private String enter = "";

	public void openNewWindow(ActionEvent e) throws IOException {

		if (e.getSource().equals(btn_book))
			enter = "book";
		else if (e.getSource().equals(btn_game))
			enter = "game";
		else if (e.getSource().equals(btn_move))
			enter = "move";
		else if (e.getSource().equals(btn_song))
			enter = "song";

		Scene newWindow = new Scene(FXMLLoader.load(getClass().getResource("res/" + enter + ".fxml")));
		newWindow.getStylesheets().add(getClass().getResource("res/application.css").toExternalForm());
		Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		app_stage.setScene(newWindow);
		app_stage.show();
	}
}
