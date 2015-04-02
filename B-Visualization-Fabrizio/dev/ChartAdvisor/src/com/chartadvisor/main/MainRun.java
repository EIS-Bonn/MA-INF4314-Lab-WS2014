package com.chartadvisor.main;

import com.chartadvisor.controller.Controller;
import com.chartadvisor.view.MainView;

public class MainRun {
	public static void main(String[] args) {
		MainView view = new MainView();
		Controller controller = new Controller(view);

		view.pack();
		view.setVisible(true);
	}
}
