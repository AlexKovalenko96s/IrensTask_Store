package ua.kas.main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameController implements Initializable {

	@FXML
	TextField tf_name;
	@FXML
	TextField tf_company;
	@FXML
	TextField tf_year;

	@FXML
	ListView<String> lv_games;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lv_games.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ObservableList<String> items = FXCollections.observableArrayList();

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/store.db");
			Statement statement = connect.createStatement();
			String query = "SELECT * FROM game";
			ResultSet res = statement.executeQuery(query);
			while (res.next()) {
				items.add(res.getString("name") + "___(" + res.getString("year") + ")___" + res.getString("company"));
			}

			FXCollections.sort(items);
			lv_games.setItems(items);

			res.close();
			statement.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void add() {
		String name = tf_name.getText();
		String year = tf_year.getText();
		String company = tf_company.getText();

		if (name.equals("") || year.equals("") || year.length() != 4 || company.equals(""))
			return;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/store.db");
			PreparedStatement pr_stat = connect
					.prepareStatement("INSERT INTO game ('name', 'year', 'company') values (?,?,?)");
			pr_stat.setString(1, name);
			pr_stat.setString(2, year);
			pr_stat.setString(3, company);
			pr_stat.executeUpdate();
			pr_stat.close();

			update();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove() throws ClassNotFoundException, SQLException {
		ObservableList<String> l_list;
		l_list = lv_games.getSelectionModel().getSelectedItems();

		for (String m : l_list) {
			String name = m.substring(0, m.indexOf("___"));
			String year = m.substring(m.indexOf("___(") + 4, m.indexOf("___(") + 4 + 4);
			String company = m.substring(m.indexOf(")___") + 4);
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/store.db");
			PreparedStatement pr_stat = connect
					.prepareStatement("DELETE FROM game WHERE name =? and year =? and company =?");
			pr_stat.setString(1, name);
			pr_stat.setString(2, year);
			pr_stat.setString(3, company);
			pr_stat.executeUpdate();
			pr_stat.close();

			update();
		}
	}

	public void edit() throws ClassNotFoundException, SQLException {
		ObservableList<String> l_list;
		l_list = lv_games.getSelectionModel().getSelectedItems();

		for (String m : l_list) {
			String name = m.substring(0, m.indexOf("___"));
			String year = m.substring(m.indexOf("___(") + 4, m.indexOf("___(") + 4 + 4);
			String company = m.substring(m.indexOf(")___") + 4);

			String newName = tf_name.getText();
			String newYear = tf_year.getText();
			String newCompany = tf_year.getText();

			if (newName.equals("") || newYear.equals("") || newYear.length() != 4 || newCompany.equals(""))
				return;

			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/store.db");
			PreparedStatement pr_stat = connect.prepareStatement(
					"UPDATE game SET name =?, year =?, company=? WHERE name =? and year =? and company =?");
			pr_stat.setString(1, newName);
			pr_stat.setString(2, newYear);
			pr_stat.setString(3, newCompany);
			pr_stat.setString(4, name);
			pr_stat.setString(5, year);
			pr_stat.setString(6, company);
			pr_stat.executeUpdate();
			pr_stat.close();

			update();
		}
	}

	public void back(ActionEvent e) throws IOException {
		Scene newWindow = new Scene(FXMLLoader.load(getClass().getResource("res/main.fxml")));
		newWindow.getStylesheets().add(getClass().getResource("res/application.css").toExternalForm());
		Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		app_stage.setScene(newWindow);
		app_stage.show();
	}

	private void update() {
		tf_name.setText("");
		tf_year.setText("");
		tf_company.setText("");

		lv_games.getItems().clear();

		ObservableList<String> items = FXCollections.observableArrayList();

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/store.db");
			Statement statement = connect.createStatement();
			String query = "SELECT * FROM game";
			ResultSet res = statement.executeQuery(query);
			while (res.next()) {
				items.add(res.getString("name") + "___(" + res.getString("year") + ")___" + res.getString("company"));
			}

			FXCollections.sort(items);
			lv_games.setItems(items);

			res.close();
			statement.close();
			sound();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void sound() {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}

		AudioInputStream inputStream;

		try {
			inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("res/beep5.wav"));
			clip.open(inputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
