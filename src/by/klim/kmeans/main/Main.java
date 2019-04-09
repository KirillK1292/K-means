package by.klim.kmeans.main;

import by.klim.kmeans.controller.Controller;
import by.klim.kmeans.model.KMeans;
import by.klim.kmeans.view.View;

public class Main {
	public static void main(String[] args) {
		KMeans model = new KMeans();
		View view = new View();
		Controller controller = new Controller(model, view);
		view.setVisible(true);
	}
}
