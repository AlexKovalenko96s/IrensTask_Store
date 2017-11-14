package ua.kas.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Queries {

	public void add(String category, String string) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/storeV2.db");
			PreparedStatement pr_stat = connect.prepareStatement("INSERT INTO " + category + " ('name') values (?)");
			pr_stat.setString(1, string);
			pr_stat.executeUpdate();

			pr_stat.close();
			connect.close();

			System.out.println("---" + category + " " + string + " was added");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(String category, String string) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/storeV2.db");
			PreparedStatement pr_stat = connect.prepareStatement("DELETE FROM " + category + " WHERE name = ?");
			pr_stat.setString(1, string);
			pr_stat.executeUpdate();

			pr_stat.close();
			connect.close();

			System.out.println("---" + category + " " + string + " was removed");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("You are mistaken! Please repeat");
		}

	}

	public void editCheck(String category, String string, String newName) {
		boolean check = false;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/storeV2.db");
			PreparedStatement pr_stat = connect.prepareStatement("SELECT * FROM " + category + " WHERE name = ?");
			pr_stat.setString(1, string);
			ResultSet res = pr_stat.executeQuery();
			while (res.next()) {
				pr_stat = connect.prepareStatement("UPDATE " + category + " SET name = ? WHERE name = ?");
				pr_stat.setString(1, newName);
				pr_stat.setString(2, string);

				pr_stat.executeUpdate();

				check = true;
			}

			res.close();
			pr_stat.close();
			connect.close();

			if (check) {
				System.out.println("---" + category + " " + string + " was updated to " + newName);
			} else {
				System.out.println("You are mistaken! Please repeat");
			}
		} catch (ClassNotFoundException | SQLException e) {
		}
	}

	public ArrayList<String> all(String category) {
		ArrayList<String> items = new ArrayList<>();

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/storeV2.db");
			Statement statement = connect.createStatement();
			String query = "SELECT * FROM " + category;
			ResultSet res = statement.executeQuery(query);
			while (res.next()) {
				items.add(res.getString("name"));
			}

			Collections.sort(items);

			res.close();
			statement.close();
			connect.close();

			System.out.println("---all:");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
}
