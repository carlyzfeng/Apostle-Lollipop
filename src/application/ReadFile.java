//did not use

package application;

import java.io.File;
import java.util.Scanner;

public class ReadFile {

private Scanner x;
	
	public void openFile() {
		
		try {
			x = new Scanner(new File(""));
		}
		catch(Exception e) {
			System.out.println("could not find file");
		}
	}
	
	public void readFile() {
		while(x.hasNext()) {
			String a = x.next();
			String b = x.next();
			String c = x.next();
			
			System.out.println(a + " " + b + " " + c);
		}
	}
	
	public void closeFile() {
		x.close();
	}
	
//	public String readData() {
//	
////	int row = s.getRows();
////	int col = s.getColumns();
////	for (int i = 0; i < row; i++) {
////		for (int j = 0; j < col; j++) {
////			System.out.println("");
////		}
////	}
//	
//}

}
