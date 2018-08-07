//did not use

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadExcelFile extends Object {

	private String csvFile = "/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv";
	ArrayList<String[]> contents;

	public ReadExcelFile(String csvFile) throws FileNotFoundException {
		// creating File instance to reference text file in Java
		// File dataFile = new File("C:TP53-msk_impact_2017-parsed.tsv");
		this.csvFile = csvFile;
		readFile();
	}
	
	public void readFile(){
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(csvFile));
			
			int idx = 0;
			String[] header = null;

			this.contents = new ArrayList<String[]>();
			// if header = TRUE
			while(scanner.hasNextLine()) {
				String oneLine = scanner.nextLine().toString();
				String[] cells = oneLine.split("\t");
				if(idx == 0) {
					header = cells;
				} else {
					// should be contents
					//content[idx] = cells;
					contents.add(cells);
				}
				idx++;
			}
			
			// printout for fun
			for(idx = 0; idx < contents.size(); idx ++) {
				String[] cells = (String[])contents.get(idx);

				for(int idx2 = 0; idx2 < cells.length; idx2++) {
					System.out.print(header[idx2] + ":" + cells[idx2]);
					
					if(idx2 != cells.length-1) {
						System.out.print(" | ");
					}
				}
				System.out.print("\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void readFile() {
//		try {
////			final CSVParser parser = new CSVParserBuilder().withSeparator('\t').withIgnoreQuotations(true).build();
////			final CSVReader reader = new CSVReaderBuilder(new StringReader(this.csvFile)).withCSVParser(parser).build();
//			CSVReader reader = new CSVReader(new FileReader("/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv"));
//		    List<String[]> myEntries = reader.readAll();
//			
//			// CSVReader reader = new CSVReader(new FileReader(path), '\t');
//			/*
//			CSVReader reader = new CSVReader(new FileReader(path), '\t');
//			String[] nextLine;
//			while ((nextLine = reader.readNext()) != null) {
//				if (nextLine != null) {
//					dataRead[readCount] = nextLine;
//					System.out.println(Arrays.toString(nextLine));
//				}
//			}
//			*/
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		System.out.println("CVS Print Complete");
//	}
//	
//	public static void main(String[] args) throws Exception {
//		//ReadExcelFile r = new ReadExcelFile("/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv");
//		//r.readFile();
////		String file = "/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv";
//		String file = "/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv";
//		
//		Scanner scanner = null;
//		try {
//			scanner = new Scanner(new File(file));
//			
//			int idx = 0;
//			String[] header = null;
//
//			ArrayList<String[]> contents = new ArrayList<String[]>();
//			// if header = TRUE
//			while(scanner.hasNextLine()) {
//				String oneLine = scanner.nextLine().toString();
//				String[] cells = oneLine.split("\t");
//				if(idx == 0) {
//					header = cells;
//				} else {
//					// should be contents
//					//content[idx] = cells;
//					contents.add(cells);
//				}
//				idx++;
//			}
//			
//			// printout for fun
//			for(idx = 0; idx < contents.size(); idx ++) {
//				String[] cells = (String[])contents.get(idx);
//
//				for(int idx2 = 0; idx2 < cells.length; idx2++) {
//					System.out.print(header[idx2] + ":" + cells[idx2]);
//					
//					if(idx2 != cells.length-1) {
//						System.out.print(" | ");
//					}
//				}
//				System.out.print("\n");
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

}