package com.chartadvisor.test;

import com.chartadvisor.controller.Controller;
import com.chartadvisor.view.MainView;

public class Test {
	public static void main(String[] args) {
		MainView view = new MainView();
		Controller controller = new Controller(view);
		
		view.pack();
		view.setVisible(true);
	}
}
