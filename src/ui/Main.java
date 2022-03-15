package ui;

import java.io.IOException;
import java.util.Scanner;
import model.BillboardSystem;

public class Main {
	private BillboardSystem backend;
	Scanner sc;

	public Main() {
		backend = new BillboardSystem();
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.showMenu();
	}
	
	public void showMenu() {
		boolean exit = false;
		
		while(!exit) {
			System.out.println(
				"--- MAIN MENU ---\n"	+
				"Type the number of the option\n" +
				"(1) Import data from CSV\n" +
				"(2) Add a new billboard\n" +
				"(3) Show all the billboards\n" +
				"(4) Export dangerousness report\n" +
				"(0) Exit"
			);
			int selected = sc.nextInt();
			sc.nextLine();
			if (selected>-1 && selected<5) {
				switch(selected) {
				case 1:
					importData();
					break;
				case 2:
					addBillboard();
					break;
				case 3:
					showBillboards();
					break;
				case 4:
					showDangerousBillboards();
					break;
				case 0:
					exit = true;
					sc.close();
					break;
				}
				System.out.println("");
			} else {
				System.out.println("Please type a valid option");
			}
		}
	}

	public void importData() {
		System.out.println("Please type the absolute path of the file\nor drag the file to the terminal and press\n[Enter] if your terminal supports it");
		String path = sc.nextLine(); 
		try {
			backend.importData(path);
			System.out.println("Data loaded succesfully");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addBillboard() {
		System.out.println("Please type the data for the new billboard");
		System.out.print("Width: ");
		int width = sc.nextInt();
		System.out.print("Height: ");
		int height = sc.nextInt();
		sc.nextLine();
		System.out.print("Brand: ");
		String brand= sc.nextLine();
		int preBoolean = 0;
		
		while (preBoolean>3 || preBoolean<1) {
			System.out.print("Is in use? \n (1) Yes\n (2) No\n");
			preBoolean = sc.nextInt();
			sc.nextLine();
			if (preBoolean>3 || preBoolean<1) {
				System.out.println("Please type a valid option");
			}
		}
		
		backend.addBillboard(width, height, preBoolean==1? true : false, brand);
		System.out.println("Billboard added succesfully");
	}
	
	public void showBillboards() {
		System.out.println(backend.getBillboardData());
	}
	
	public void showDangerousBillboards() {
		System.out.println(backend.exportDangerousnessReport());		
	}
}
