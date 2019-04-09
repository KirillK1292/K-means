package by.klim.kmeans.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import by.klim.kmeans.model.Cluster;
import by.klim.kmeans.model.KMeans;
import by.klim.kmeans.model.KMeansException;
import by.klim.kmeans.view.View;

public class Controller {
	private KMeans model;
	private View view;
	
	public Controller(KMeans model, View view) {
		this.model = model;
		this.view = view;
		
		view.addInializeButtonListener(new InitializeButtonActionListener());
		view.addLearnButtonListener(new LearnButtonActionListener());
		view.addClusterButtonListener(new ClusterButtonActionListener());
	}
	
	private class InitializeButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				view.setLearnButtonEnabled(false);
				view.setClusterButtonEnabled(false);
				model.initialize(view.getPointCount(), view.getClusterCount(), view.getWidthChart(), view.getHeightChart());
				view.removePoints();
				view.addPoints("init", model.getPoints(), Color.BLACK);
				view.updateChart();
				view.setLearnButtonEnabled(true);
			} catch (KMeansException e1) {
				view.displayErrorMessage(e1.getMessage());
			}
		}
	}
	
	private class LearnButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int i;
			boolean isChanged;
			ArrayList<Cluster> clusters;
			ArrayList<Point> cores = new ArrayList<Point>();
			
			view.setClusterButtonEnabled(false);
			do {
				isChanged = model.learn();
				clusters = model.getClusters();
				view.removePoints();
				cores.clear();
				for(i = 0; i < clusters.size(); i++) {
					view.addPoints("Cluster " + (i + 1), clusters.get(i).getPoints(), clusters.get(i).getColor());
					cores.add(clusters.get(i).getCore());
				}
				view.addCores(cores);
				view.updateChart();
			} while(isChanged);
			view.setClusterButtonEnabled(true);
		}
	}
	
	private class ClusterButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Point point = new Point(view.getXCoordinate(), view.getYCoordinate());
				view.displayInfoMessage("Cluster " + (model.clusterPoint(point) + 1));
			} catch(NumberFormatException e1) {
				view.displayErrorMessage("Incorrect coordinates.");
			}
		}
	}
}
