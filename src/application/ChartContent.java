//did not use

package application;

import java.util.ArrayList;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChartContent {

	private ScatterChart<Number, Number> sc;
	private XYChart.Series series1;

	public ChartContent() {
		try {
			/* pre-define some data */
			int proteinStart = 0, proteinEnd = 40;
			int yMin = 0, yMax = 100;

			/* ===================== */

			// SCATTER PLOT
			NumberAxis xAxis = new NumberAxis("protein_domain", proteinStart, proteinEnd, 10); 
			NumberAxis yAxis = new NumberAxis("frequency", yMin, yMax, 5); 
			yAxis.setLowerBound(-5);
			sc = new ScatterChart<>(xAxis, yAxis);
			// yAxis.setLabel("Mutations");
			xAxis.setVisible(true);
			xAxis.setTickLabelsVisible(true);
			xAxis.setTickMarkVisible(false);
			sc.setLegendVisible(false);
			sc.setVerticalGridLinesVisible(true);
			// double xAxisMax = sc.getXAxis();

			// scatter plot data
			series1 = new XYChart.Series<>();
			series1.setName("Truncating");
			series1.getData().add(new XYChart.Data(3, 5));
			series1.getData().add(new XYChart.Data(30, 30));

			/*
			 * ArrayList<Double> startlist = new ArrayList<Double>(); ArrayList<Double>
			 * endlist = new ArrayList<Double>();
			 * 
			 * for(int i = 0; i < domains.length; i++) { double start = domains[i][0];
			 * double end = domains[i][1]; startlist.add(start); endlist.add(end);
			 * 
			 * // add rectangle to chart, according to domain definition Data<Number,
			 * Number> rects = new Data<Number, Number>((end-start)/2 + start, 0); Rectangle
			 * rect = new Rectangle(, 20); rect.setFill(Color.BLUE); rects.setNode(rect);
			 * series1.getData().add(rects); }
			 */

			sc.getData().addAll(series1);

			drawDomains();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawDomains() {
		double top = sc.getPadding().getTop();
		double left = sc.getPadding().getLeft();
		double minX = sc.getBoundsInParent().getMinX();
		double minY = sc.getBoundsInParent().getMinY();

		double chartWidth = sc.getXAxis().getWidth();
		double chartHeight = sc.getYAxis().getHeight();

		// display rectangles
		double[][] domains = { { 5, 9 }, { 13, 14 }, { 19, 24 }, { 27, 32 } };

		ArrayList<Double> startlist = new ArrayList<Double>();
		ArrayList<Double> endlist = new ArrayList<Double>();

		double mainrect_startloc = sc.getXAxis().getZeroPosition(); //left + minX + sc.getXAxis().getDisplayPosition(0);
		double mainrect_endloc = left + minX + sc.getXAxis().getDisplayPosition(0) + sc.getXAxis().getWidth();
		double midpoint = (mainrect_endloc - mainrect_startloc)/2 + mainrect_startloc;
	
		Data<Number, Number> main = new Data<Number, Number>(sc.getXAxis().getValueForDisplay(midpoint), -2.5);
		Rectangle mainrect = new Rectangle(mainrect_endloc - mainrect_startloc, 20);
		mainrect.setFill(Color.PINK);
		main.setNode(mainrect);
		series1.getData().add(main);
		
		
		System.out.println(sc.getXAxis().getWidth());
		System.out.println();
		
		
		for (int i = 0; i < domains.length; i++) {
			double start = domains[i][0];
			double end = domains[i][1];
			startlist.add(start);
			endlist.add(end);
			
			double _left = left + minX + sc.getXAxis().getDisplayPosition(start);
			double _top = top + minY + sc.getYAxis().getDisplayPosition(0);
			double _w = sc.getXAxis().getDisplayPosition(end) - sc.getXAxis().getDisplayPosition(start);
			
			System.out.println("Rect [" + start + ", " + end + "] ==> [" + _left + ", " + (_left + _w) + "]");

//			Rectangle rect = new Rectangle(left + minX + sc.getXAxis().getDisplayPosition(start),
//					top + minY + sc.getYAxis().getDisplayPosition(0),
//					sc.getXAxis().getDisplayPosition(end) - sc.getXAxis().getDisplayPosition(start), 20);
			
			Data<Number, Number> rects = new Data<Number, Number>((end - start)/2 + start, -2.5);
			Rectangle rect = new Rectangle(_w, 20);
			
			rects.setNode(rect);
			rect.setFill(Color.BLUE);
			series1.getData().add(rects);
		}

	}
}
