package by.klim.kmeans.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Cluster {
	private Point core;
	private Color color;
	private ArrayList<Point> points;
	
	Cluster(Point core) {
		this.core = core;
		Random rand = new Random();
		this.color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		points = new ArrayList<Point>();
		points.add(core);
	}
	
	public Point getCore() {
		return core;
	}
	
	void setCore(Point core) {
		this.core = core;
	}
	
	public Color getColor() {
		return color;
	}
	
	void setColor(Color color) {
		this.color = color;
	}
	
	void addPoint(Point point) {
		if(!points.contains(point))
			points.add(point);
	}
	
	void removePoints() {
		points.clear();
		points.add(core);
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
}
