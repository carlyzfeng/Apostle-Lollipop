package application;

import java.util.Hashtable;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Colors extends Node{
	
	String[] acc_colors = {"0x7FC97F", "0xBEAED4", "0xFDC086", "0xFFFF99", "0x386CB0", "0xF0027F", "0xBF5B17", "0x666666"};
	
	public Colors() {
		super();
	}
	/**
	 * Given a string (protein domain name), return a color according to colorbrewer
	 * @param dname
	 * @return
	 */
	private int color_idx = 0;
	public Color colorpick(String dname) {	
		
		Hashtable<String, Color> hash = new Hashtable<String, Color>();
		
		if (hash.containsKey(dname)) {
			return (Color)hash.get(dname);
		} else {
			Color cur = Color.web(acc_colors[color_idx]);
			color_idx++;
			color_idx = color_idx % acc_colors.length;
			hash.put(dname, cur);
			return cur;
		}
	}
	
	
	
	
	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected boolean impl_computeContains(double localX, double localY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
