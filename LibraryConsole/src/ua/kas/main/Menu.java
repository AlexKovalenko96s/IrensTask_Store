package ua.kas.main;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	private Queries queries;

	private static Scanner scn = new Scanner(System.in);

	private String string;

	public Menu() {
		System.out.println("       -===STORE===-");
		System.out.println("        Hello user!");

		queries = new Queries();
	}

	public void start() {

		System.out.println("Please select category(enter number):");
		System.out.println("1. Book; \n" + "2. Movie; \n" + "3. Song; \n" + "4. Game.");

		string = scn.nextLine();

		if (string.equals("1") && string.length() == 1) {
			category("Book");
		} else if (string.equals("2") && string.length() == 1) {
			category("Movie");
		} else if (string.equals("3") && string.length() == 1) {
			category("Song");
		} else if (string.equals("4") && string.length() == 1) {
			category("Game");
		} else {
			System.out.println("You are mistaken! Please repeat");
			start();
		}
	}

	private void category(String category) {
		System.out.println("       ===" + category + "===");
		System.out.println("Сommands in this category:");
		System.out.println("add {name}");
		System.out.println("remove {name}");
		System.out.println("edit {name}");
		System.out.println("all");
		System.out.println("back");

		string = scn.nextLine();

		ArrayList<String> items = new ArrayList<>();

		String categoryLowerCase = category.toLowerCase();

		try {
			if (string.substring(0, 3).equals("add") && string.length() > 4 && string.substring(3, 4).equals(" ")) {
				queries.add(categoryLowerCase, string.substring(4));
				category(category);
			}
		} catch (Exception ex) {
		}

		try {
			if (string.substring(0, 6).equals("remove") && string.length() > 7 && string.substring(6, 7).equals(" ")) {

				queries.remove(categoryLowerCase, string.substring(7));
				category(category);
			}
		} catch (Exception ex) {
		}

		try {
			if (string.substring(0, 4).equals("edit") && string.length() > 5 && string.substring(4, 5).equals(" ")) {
				System.out.println("Please write new name:");
				String newName = scn.nextLine();
				if (newName.length() >= 1 && !newName.equals(" ") && !newName.equals("")) {
					queries.editCheck(categoryLowerCase, string.substring(5), newName);
					category(category);
				}
			}
		} catch (Exception ex) {
		}

		try {
			if (string.equals("all") && string.length() == 3) {
				items = queries.all(categoryLowerCase);
				toConsole(items);
				category(category);
			}
		} catch (Exception ex) {
		}

		try {
			if (string.equals("back") && string.length() == 4) {
				start();
			}
		} catch (Exception ex) {
		}

		System.out.println("You are mistaken! Please repeat");

		category(category);
	}

	private void toConsole(ArrayList<String> items) {
		for (String out : items)
			System.out.println(out);
	}
}
