package by.klim.kmeans.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {
	private ArrayList<Point> points;
	private ArrayList<Cluster> clusters;
	
	public void initialize(int pointCount, int clusterCount, int maxX, int maxY) throws KMeansException {
		Random rand = new Random();
		
		if(pointCount <= 0 || clusterCount <= 0)
			throw new KMeansException("Points and clusters count must be greater than zero.");
		if(pointCount < clusterCount)
			throw new KMeansException("Points count must be greater than the count of clusters.");
		points = new ArrayList<Point>(pointCount);
		while(pointCount > 0) {
			points.add(new Point(rand.nextInt(maxX), rand.nextInt(maxY)));
			pointCount--;
		}
		initializeClusters(clusterCount);
	}
	
	private int getMinDistanceSq(Point point) {
		int minDist = (int)point.distanceSq(clusters.get(0).getCore());
		for(int i = 1; i < clusters.size(); i++)
			minDist = Math.min(minDist, (int)point.distanceSq(clusters.get(i).getCore()));
		return minDist;
	}
	
	private void initializeClusters(int clusterCount) {
		int sum;
		double rnd;
		Random rand = new Random();
				
		clusters = new ArrayList<Cluster>(clusterCount);
		clusters.add(new Cluster(points.get(rand.nextInt(points.size()))));
		while(clusterCount > 1) {
			sum = 0;
			for(Point point : points)
				sum += getMinDistanceSq(point);
			rnd = Math.random() * sum;
			sum = 0;
			for(Point point : points) {
				sum += getMinDistanceSq(point);
				if(sum > rnd) {
					clusters.add(new Cluster(point));
					break;
				}
			}
			clusterCount--;
		}
	}
	
	public boolean learn() {
		int i, min, sum;
		boolean isChanged = false;;
		Point newCore;
		ArrayList<Point> clusterPoints;
		
		for(Cluster cluster : clusters)
			cluster.removePoints();
		for(Point point : points)
			clusters.get(clusterPoint(point)).addPoint(point);
		for(Cluster cluster : clusters) {
			newCore = cluster.getCore();
			clusterPoints = cluster.getPoints();
			min = 0;
			for(Point point : clusterPoints)
				min += clusterPoints.get(0).distance(point);
			for(i = 1; i < clusterPoints.size(); i++) {
				sum = 0;
				for(Point point : clusterPoints)
					sum += clusterPoints.get(i).distance(point);
				if(min > sum) {
					min = sum;
					newCore = clusterPoints.get(i);
				}
			}
			if(!cluster.getCore().equals(newCore)) {
				cluster.setCore(newCore);
				isChanged = true;
			}
		}
		return isChanged;
	}
	
	public int clusterPoint(Point point) {
		int i, dist, min, index = 0;
		
		min = (int)point.distance(clusters.get(0).getCore());
		for(i = 1; i < clusters.size(); i++) {
			dist = (int)point.distance(clusters.get(i).getCore());
			if(min > dist) {
				min = dist;
				index = i;
			}
		}
		return index;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public ArrayList<Cluster> getClusters() {
		return clusters;
	}
}
