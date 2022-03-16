package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BillboardSystem {
	ArrayList<Billboard> billboardData;
	int reportCounter;
	
	public BillboardSystem() throws IOException, ClassNotFoundException {
		File file = new File(".\\files\\data.txt");
		if (file.exists() && !file.isDirectory()) {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			ArrayList<Billboard> obj = (ArrayList<Billboard>)ois.readObject();
			billboardData = obj;		
			ois.close();
			fis.close();
		} else {
			billboardData = new ArrayList<Billboard>();
			saveData();
		}
		reportCounter = 0;
	}
	
	public boolean importData(String path){
		try {
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			String line = " START ";
			String[] tempArr;
			while ((line = br.readLine()) != null) {
				tempArr = line.split("\\|");
				billboardData.add(new Billboard(
					Integer.parseInt(tempArr[0]),
					Integer.parseInt(tempArr[1]),
					Boolean.parseBoolean(tempArr[2]),
					tempArr[3])
				);
			}
			br.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void addBillboard(int width, int height, boolean isInUse, String promotedBrand) {
		billboardData.add(new Billboard(width, height, isInUse, promotedBrand));
	}
	
	public String getBillboardData() {
		String s = "Registered Billboards";
		s += String.format("\n%-10s%-10s%-10s%-10s","Width","Height","InUse","Brand");
		int initialLength = s.length();
		for (Billboard b : billboardData) {
			s += String.format("\n%-10s%-10s%-10s%-10s", b.getWidth(), b.getHeight(), b.isInUse(), b.getPromotedBrand());
		}
 		
		if (s.length()==initialLength) {
			s = "There are no registered billboards";
		} else {
			s+="\n\nTOTAL: " + billboardData.size() + " Billboards";
		}
		return s;
	}
	
	public String getDangerousBillboardData() {
		String s = "The dangerous billboards are:";
		int initialLength = s.length();
		int i = 1;
		for (Billboard b : billboardData) {
			if (b.getArea()>=200000) {
				s += "\n" + i + ". Billboard " + b.getPromotedBrand() + " with area " + b.getArea();
				i++;
			}
		}
		
		if (s.length()==initialLength) {
			s= "There are no dangerous billboards";
		}
		return s;
	}

	public String exportDangerousnessReport() {
		String s = "===========================\n" + 
				"DANGEROUS BILLBOARD REPORT\n" + 
				"===========================\n";
		s+= getDangerousBillboardData();
		
		BufferedWriter writer;
		try {			
			File file = new File("./files/Report" + reportCounter + ".txt");
			FileWriter fw= new FileWriter(file);
			writer = new BufferedWriter(fw);
			writer.write(s);
	        writer.close();
	        reportCounter++;
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s + "\n\n<!> Report" + (reportCounter-1) + ".txt saved in files directory";
	}

	public void saveData() throws IOException {
		File file = new File(".\\files\\data.txt");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(billboardData);
		oos.close();
		fos.close();
	}
}
