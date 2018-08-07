package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.opencsv.CSVReader;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	private ScatterChart<Number, Number> sc;
	private XYChart.Series series1;
	private Colors rectcolor = new Colors();
	private double plot_lower_bound;

	ArrayList<AAStats> dataSorted = new ArrayList<AAStats>();
	private String csvFile = "/Users/carlyfeng/Documents/ApostleCS/TP53-msk_impact_2017-parsed.tsv";
	ArrayList<String[]> contents;
	String[] header;
	ArrayList<Integer> AA_Position = new ArrayList<Integer>();

	@Override
	public void start(Stage primaryStage) {
		try {
			/* pre-define some data */
			int proteinStart = 0, proteinEnd = 400;
			int yMin = 0, yMax = 300;

			/* ===================== */

			primaryStage.setTitle("Lollipop");

			primaryStage.setMinHeight(300);
			// primaryStage.setMaxHeight(550);

			BorderPane root = new BorderPane();

			// MENU BAR
			MenuBar menuBar = new MenuBar();
			Menu menuFile = new Menu("File");
			MenuItem importFile = new MenuItem("Import File");
			MenuItem clearFile = new MenuItem("Clear File");
			MenuItem saveAs = new MenuItem("Save As");
			MenuItem save = new MenuItem("Save");
			menuFile.getItems().addAll(importFile, clearFile, saveAs, save);

			Menu menuOptions = new Menu("Options");
			MenuItem zoomIn = new MenuItem("Zoom In");
			MenuItem zoomOut = new MenuItem("Zoom Out");
			menuOptions.getItems().addAll(zoomIn, zoomOut);

			Menu menuHelp = new Menu("Help");
			MenuItem search = new MenuItem("Search");
			menuHelp.getItems().addAll(search);

			menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);

			// SCATTER PLOT
			NumberAxis xAxis = new NumberAxis("protein_domain", proteinStart, proteinEnd, 5);
			NumberAxis yAxis = new NumberAxis("frequency", yMin, yMax, 5);
			int calc = (int)Math.round(Math.abs((((double)yMax/600) * 20)));
			int extra = calc + (5 - (calc%5));
			this.plot_lower_bound = 0 - extra;
			yAxis.setLowerBound(this.plot_lower_bound);
			
			sc = new ScatterChart<>(xAxis, yAxis);
			xAxis.setVisible(true);
			xAxis.setTickLabelsVisible(true);
			xAxis.setTickMarkVisible(true);
			sc.setLegendVisible(true);
			sc.setVerticalGridLinesVisible(true);

			// scatter plot data
			series1 = new XYChart.Series<>();
			series1.setName("Truncating");
//			for (int i = 0; i < dataSorted.size(); i++) { 
//				series1.getData().add(new XYChart.Data(dataSorted.get(i).getPosition(), dataSorted.get(i).getFrequency()));
//			}
			
			sc.getData().addAll(series1);

			// All added to border pane
			root.setTop(menuBar);
			root.setCenter(sc);

			Scene scene = new Scene(root, 1200, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// proteinBar.widthProperty().bind(scene.widthProperty());

			primaryStage.setScene(scene);
//			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.show();

			readFile();
			reGroupData();
			drawDomains();
			drawPops();
			reDrawData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reDrawData() {
		for (int i = 0; i < dataSorted.size(); i++) { 
			series1.getData().add(new XYChart.Data(dataSorted.get(i).getPosition(), dataSorted.get(i).getFrequency()));
		}
	}
	
	public void drawDomains() {
		double top = sc.getPadding().getTop();
		double left = sc.getPadding().getLeft();
		double minX = sc.getBoundsInParent().getMinX();
		double minY = sc.getBoundsInParent().getMinY();

		double chartWidth = sc.getXAxis().getWidth();
		double chartHeight = sc.getYAxis().getHeight();
		
		Number rectheightloc = (sc.getYAxis().getValueForDisplay((sc.getYAxis().getDisplayPosition(this.plot_lower_bound) + sc.getYAxis().getDisplayPosition(0)) / 2));

		// display rectangles
		double[][] domains = { { 5, 70 }, {110, 220 }, { 250, 260 }, { 340, 380 } };
		String[] domainname = { "test1", "test2", "test3", "test4" };

		ArrayList<Double> startlist = new ArrayList<Double>();
		ArrayList<Double> endlist = new ArrayList<Double>();

		double mainrect_startloc = sc.getXAxis().getZeroPosition(); // left + minX +
																	// sc.getXAxis().getDisplayPosition(0);
		double mainrect_endloc = left + minX + sc.getXAxis().getDisplayPosition(0) + sc.getXAxis().getWidth();
		double midpoint = (mainrect_endloc - mainrect_startloc) / 2 + mainrect_startloc;

		Data<Number, Number> main = new Data<Number, Number>(sc.getXAxis().getValueForDisplay(midpoint), rectheightloc);
		Rectangle mainrect = new Rectangle(mainrect_endloc - mainrect_startloc, 20);
		mainrect.setFill(Color.CORNFLOWERBLUE);
		main.setNode(mainrect);
		series1.getData().add(main);

		System.out.println(sc.getXAxis().getWidth());
		System.out.println();

		for (int i = 0; i < domains.length; i++) {
			double start = domains[i][0];
			double end = domains[i][1];
			startlist.add(start);
			endlist.add(end);
			double dmidpoint = (start + end) / 2;

			double _left = left + minX + sc.getXAxis().getDisplayPosition(start);
			double _top = top + minY + sc.getYAxis().getDisplayPosition(0);
			double _w = sc.getXAxis().getDisplayPosition(end) - sc.getXAxis().getDisplayPosition(start);

			System.out.println("Rect [" + start + ", " + end + "] ==> [" + _left + ", " + (_left + _w) + "]");

			// Rectangle rect = new Rectangle(left + minX +
			// sc.getXAxis().getDisplayPosition(start),
			// top + minY + sc.getYAxis().getDisplayPosition(0),
			// sc.getXAxis().getDisplayPosition(end) -
			// sc.getXAxis().getDisplayPosition(start), 20);
			

			Data<Number, Number> rects = new Data<Number, Number>((end - start) / 2 + start, rectheightloc);
			Rectangle rect = new Rectangle(_w, 20);

			rect.setFill(rectcolor.colorpick(domainname[i]));

			rects.setNode(rect);
			series1.getData().add(rects);
			
			Data<Number, Number> names = new Data<Number, Number>(dmidpoint, rectheightloc);
			Label labelname = new Label(domainname[i]);
			names.setNode(labelname);
			labelname.setBackground(null);
			series1.getData().add(names);
		}

	}

	public void drawPops() {
		for (int i = 0; i < dataSorted.size(); i++) {

			double xloc = sc.getXAxis().getDisplayPosition(dataSorted.get(i).getPosition());
			double ytoploc = sc.getYAxis().getDisplayPosition(dataSorted.get(i).getFrequency());
			double yzeroloc = sc.getYAxis().getDisplayPosition(0);

			Data<Number, Number> pop = new Data<Number, Number>(dataSorted.get(i).getPosition(), dataSorted.get(i).getFrequency() / 2);
			Line popLine = new Line(xloc, ytoploc, xloc, yzeroloc);

			// popLine.setStroke(Color.BLACK);

			pop.setNode(popLine);
			series1.getData().add(pop);

			System.out.println(xloc + ", " + ytoploc + ", " + yzeroloc);

		}
	}

	public void readFile() {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(csvFile));

			int idx = 0;
			header = null;

			this.contents = new ArrayList<String[]>();
			// if header = TRUE
			while (scanner.hasNextLine()) {
				String oneLine = scanner.nextLine().toString();
				String[] cells = oneLine.split("\t");
				if (idx == 0) {
					header = cells;
				} else {
					// should be contents
					// content[idx] = cells;
					contents.add(cells);
				}
				idx++;
			}

			// printout for fun
			for (idx = 0; idx < contents.size(); idx++) {
				String[] cells = (String[]) contents.get(idx);

				for (int idx2 = 0; idx2 < cells.length; idx2++) {
					System.out.print(header[idx2] + ":" + cells[idx2]);

					if (idx2 != cells.length - 1) {
						System.out.print(" | ");
					}
				}
				System.out.print("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reGroupData() {
		int col_idx = 0;
		boolean found = false;
		
		// found the header
		// later: AA_Position should be specified by user
		for (int colIdx = 0; colIdx < header.length; colIdx++) {
			if (header[colIdx].equals("AA_Position")) {
				col_idx = colIdx;
				found = true;
			}
		}

		if (!found) {
			System.out.println("Not found AA_Postion");
		}

		// group data from here
		int _cur_aa_pos, _prev_aa_pos = -1;
		AAStats _cur_aa_obj = null;
		for (int idx = 0; idx < contents.size(); idx++) {
			_cur_aa_pos = Integer.parseInt(contents.get(idx)[col_idx]);
			
			// new amino-acid position
			if(_cur_aa_pos != _prev_aa_pos) {
				// to create a new AAStats Object
				AAStats next = new AAStats(_cur_aa_pos, 1);
				dataSorted.add(next);
				_cur_aa_obj = next;
				_prev_aa_pos = _cur_aa_pos;
			} else {
				_cur_aa_obj.addOne();
			}
		}
		
		for (int idx = 0; idx < dataSorted.size(); idx++) {
			System.out.println(((AAStats)dataSorted.get(idx)).print());
		}
		
	}

	public static void main(String[] args) {
		launch(args);

	}
}
