package flashcardApp;
import java.io.*;
import java.awt.Desktop;

public class SysManip {
	
	public static File GetFile() { //By Lachlan Miller
		try {
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\OneDrive\\Desktop\\Vanki.txt"); //insert Vanki file.
			if(!Desktop.isDesktopSupported()) {
				System.out.println("System not supported");
			}
			Desktop d = Desktop.getDesktop(); //gets the desktop (note that it is a new desktop being returned in the declaration for getDesktop())
			if(file.exists()) { //boolean value determining if the file exists
				d.open(file); //
				return file;
			}
			else {
				CreateFile();
				System.out.println("File not found");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void CreateFile() {
		try { //createNewFile throws an IOException
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\OneDrive\\Desktop\\Vanki.txt");
			if (file.createNewFile()) {
				System.out.println("File created:" + file.getName());
			}
			else {
				System.out.println("File already existing at directory");
			}
		} catch (IOException e) {
			System.out.println("An error has occurred");
			e.printStackTrace();
		}
	}
		
	
	
	
	}
