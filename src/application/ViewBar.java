//did not use

package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ViewBar extends Shape{

	public double viewBarHeight = 100.0;
	public Rectangle rect;
	public double width;
	public double height;
	public double locX;
	public double locY;
	
	public ViewBar() {
		this.rect = new Rectangle(this.getScene().getX(), 500.0, this.getScene().getWidth(), viewBarHeight);
		this.width = this.getScene().getWidth();
		this.height = viewBarHeight;
	}
	
	public ViewBar(double start, double end) {
		this.rect = new Rectangle(this.getScene().getX(), this.getScene().getY(), end - start, viewBarHeight); //x, y, width, height
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getlocX() {
		return locX;
	}
	
	

	@SuppressWarnings("restriction")
	@Override
	public com.sun.javafx.geom.Shape impl_configShape() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
