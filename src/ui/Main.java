package ui;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.BillboardSystem;

public class Main {
	private BillboardSystem backend;
	Scanner sc;

	public Main() throws ClassNotFoundException, IOException {
		backend = new BillboardSystem();
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
			Main main;
			try {
				main = new Main();
				main.showMenu();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
	}
	
	public void showMenu() throws IOException, ClassNotFoundException {
		boolean exit = false;
		
		while(!exit) {
			System.out.println(
				"--- MAIN MENU ---\n"	+
				"Type the number of the option\n" +
				"(1) Import data from CSV\n" +
				"(2) Add a new billboard\n" +
				"(3) Add a new billboard the oneline way\n" + 
				"(4) Show all the billboards\n" +
				"(5) Export dangerousness report\n" +
				"(0) Exit and save"
			);
			int selected = sc.nextInt();
			sc.nextLine();
			switch(selected) {
			case 1:
				importData();
				break;
			case 2:
				addBillboard();
				break;
			case 3:
				addBillboardOneLine();
				break;
			case 4:
				showBillboards();
				break;
			case 5:
				showDangerousBillboards();
				break;
			case 0:
				System.out.println("Saving data...");
				backend.saveData();
				exit = true;
				sc.close();
				System.out.println("End of execution");
				break;
			default:
				System.out.println("<!> Please type a valid option");
				break;
			}
			System.out.println("");
		}
	}

	public void importData() throws ClassNotFoundException, IOException {
		System.out.println("Please type the absolute path of the file");
		System.out.println("templates: ./files/Datos1.csv or \"./files/Datos2.csv\"");
		String path = sc.nextLine(); 
		if (backend.importData(path)) {
			System.out.println("<!> Data loaded succesfully");
		} else {
			System.out.println("<!> Please check the path of the file");
		}
	}
	
	private void addBillboard() {
		try {
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
			System.out.println("<!> Billboard added succesfully");
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("<!> You are using an invalid value");
		}
	}
	
	private void addBillboardOneLine() {
		try {
			System.out.println("Please type the data for the new billboard\nin a single line with parameters separated with ++\ni.e. width++height++isInUse++Brand");
			String[] parameters = sc.nextLine().split("\\+\\+");
			int width = Integer.parseInt(parameters[0]);
			int height = Integer.parseInt(parameters[1]);
			boolean isInUse = Boolean.parseBoolean(parameters[2]);
			String brand = parameters[3];
			backend.addBillboard(width, height, isInUse, brand);
			System.out.println("<!> Billboard added succesfully");
		} catch (NumberFormatException e) {
			System.out.println("<!> You are using invalid values");
		}
	}
	
	public void showBillboards() {
		System.out.println(backend.getBillboardData());
	}
	
	public void showDangerousBillboards() {
		System.out.println(backend.exportDangerousnessReport());		
	}
}
