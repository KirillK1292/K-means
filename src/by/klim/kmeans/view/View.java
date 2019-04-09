package by.klim.kmeans.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;

public class View extends JFrame {
	private JSpinner pointCount, clusterCount;
	private JTextField xCoordinate, yCoordinate;
	private JLabel pointCountLabel, clusterCountLabel, xCoordinateLabel, yCoordinateLabel;
	private JButton initialize, learn, cluster;
	private XYChart chart;
	private XChartPanel chartPanel;
	
	public View() {
		super("K-means");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setResizable(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initializeComponents();
	}
	
	private void initializeComponents() {
		pointCountLabel = new JLabel("Point count");
		pointCount = new JSpinner(new SpinnerNumberModel(1000, 1000, 10000, 1000));
		((DefaultEditor)pointCount.getEditor()).getTextField().setEditable(false);
		clusterCountLabel = new JLabel("Cluster count");
		clusterCount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		((DefaultEditor)clusterCount.getEditor()).getTextField().setEditable(false);
		initialize = new JButton("Initialize");
		learn = new JButton("Learn");
		learn.setEnabled(false);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3, 2, 5, 5));
		panel1.setBorder(BorderFactory.createTitledBorder("Parameters"));
		panel1.add(pointCountLabel);
		panel1.add(pointCount);
		panel1.add(clusterCountLabel);
		panel1.add(clusterCount);
		panel1.add(initialize);
		panel1.add(learn);
		
		xCoordinateLabel = new JLabel("X coordinate");
		xCoordinate = new JTextField(1);
		yCoordinateLabel = new JLabel("Y coordinate");
		yCoordinate = new JTextField(1);
		cluster = new JButton("Cluster");
		cluster.setEnabled(false);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(3, 2, 5, 5));
		panel2.setBorder(BorderFactory.createTitledBorder("Cluster"));
		panel2.add(xCoordinateLabel);
		panel2.add(xCoordinate);
		panel2.add(yCoordinateLabel);
		panel2.add(yCoordinate);
		panel2.add(cluster);
		
		chart = new XYChartBuilder().width(697).height(562).build();
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
	    chart.getStyler().setLegendVisible(true);
	    chart.getStyler().setMarkerSize(7);
		chartPanel = new XChartPanel(chart);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(panel1, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(panel2, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 3;
		panel.add(chartPanel, constraints);
		
		this.add(panel);
	}
	
	public int getPointCount() {
		return (int)pointCount.getValue();
	}
	
	public int getClusterCount() {
		return (int)clusterCount.getValue();
	}
	
	public int getXCoordinate() {
		return Integer.valueOf(xCoordinate.getText());
	}
	
	public int getYCoordinate() {
		return Integer.valueOf(yCoordinate.getText());
	}
	
	public void addPoints(String seriesName, ArrayList<Point> points, Color color) {
		ArrayList<Integer> xData = new ArrayList<Integer>();
		ArrayList<Integer> yData = new ArrayList<Integer>();
	    
	    for (Point point : points) {
	      xData.add(point.x);
	      yData.add(point.y);
	    }
	    chart.addSeries(seriesName, xData, yData).setMarkerColor(color);
	}
	
	public void addCores(ArrayList<Point> points) {
		ArrayList<Integer> xData = new ArrayList<Integer>();
		ArrayList<Integer> yData = new ArrayList<Integer>();
	    
	    for (Point point : points) {
	      xData.add(point.x);
	      yData.add(point.y);
	    }
	    XYSeries series = chart.addSeries("Cores", xData, yData);
	    series.setMarkerColor(Color.BLACK);
	    series.setShowInLegend(false);
	}
	
	public void removePoints() {
		chart.getSeriesMap().clear();	
	}
	
	public int getWidthChart() {
		return chart.getWidth();
	}
	
	public int getHeightChart() {
		return chart.getHeight();
	}
	
	public void updateChart() {
		chartPanel.paintImmediately(chartPanel.getVisibleRect());
	}
	
	public void setLearnButtonEnabled(boolean enabled) {
		learn.setEnabled(enabled);
	}
	
	public void setClusterButtonEnabled(boolean enabled) {
		cluster.setEnabled(enabled);
	}
	
	public void addInializeButtonListener(ActionListener listener) {
		initialize.addActionListener(listener);
	}
	
	public void addLearnButtonListener(ActionListener listener) {
		learn.addActionListener(listener);
	}
	
	public void addClusterButtonListener(ActionListener listener) {
		cluster.addActionListener(listener);
	}
	
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void displayInfoMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Clustering result", JOptionPane.INFORMATION_MESSAGE);
	}
}
