package com.chartadvisor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.chartadvisor.view.*;
import com.chartadvisor.model.*;
import com.hp.hpl.jena.util.FileManager;

public class Controller implements ActionListener {
	
	private MainView view;
	private OutputManager outputManager; 
	private String inputFilePath;
	List<String[]> propertiesList; 
	List<String[]> selectedPropertiesList;
	
	public Controller(MainView view) {
		this.view = view;
		this.outputManager = new OutputManager();
		this.addListeners();
	}
	
	public void addListeners(){
		view.getJbtn_Open().addActionListener(this);
		view.getJbtn_Generate().addActionListener(this);
		view.getJbtn_Cancel().addActionListener(this);
		view.getJmenu_About().addActionListener(this);
		view.getJmenu_Dictionary().addActionListener(this);
		view.getJmenu_Exit().addActionListener(this);
		view.getJmenu_Generate().addActionListener(this);
		view.getJmenu_Open().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getJbtn_Open() || e.getSource() == view.getJmenu_Open()) {
	    	JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				inputFilePath = selectedFile.getAbsolutePath().replace("\\", "/");
				System.out.println(inputFilePath);
				this.propertiesList = DataSets.get_properties(DataSets.create_model(inputFilePath), 0);
				if (propertiesList.isEmpty() || propertiesList == null) {
					JOptionPane.showMessageDialog(this.view,"INPUT FILE NOT VALID! Must be a valid RDF file.","Error!", JOptionPane.ERROR_MESSAGE);
				} else {
					view.getJtxt_FilePath().setText(inputFilePath);
					view.setCheckBoxList(this.getArrayFromList(propertiesList));
				}
	        }
		}
		if (e.getSource() == view.getJbtn_Generate() || e.getSource() == view.getJmenu_Generate()) {
			if (view.getCheckedItems().isEmpty()) {
				JOptionPane.showMessageDialog(this.view,"No properties were selected. Please make sure you have selected some properties.","Warning!", JOptionPane.WARNING_MESSAGE);
			} else {
				List<String[]> results = this.findCharts(this.getSelectedProperties(), inputFilePath); //REPLACE WITH REAL findChard METHOD!
				if (results.isEmpty()) {//REPLACE WITH REAL findChard METHOD!
					JOptionPane.showMessageDialog(this.view,"No Chart matches were found! Please try selecting other properties.","Warning!", JOptionPane.WARNING_MESSAGE);
				} else {
					JFileChooser fileChooser = new JFileChooser();
			        int returnValue = fileChooser.showSaveDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
						outputManager.generateRDFOutput(results, new File(fileChooser.getSelectedFile().toString() + ".rdf"));
			        }
				}
			}
		}
		if (e.getSource() == view.getJbtn_Cancel() || e.getSource() == view.getJmenu_Exit()) {
			view.dispose();
			System.exit(0);
		}
		if (e.getSource() == view.getJmenu_About()) {
			AboutView aboutview = new AboutView();
			aboutview.pack();
			aboutview.setVisible(true);
		}
		if (e.getSource() == view.getJmenu_Dictionary()) {
			DictionaryView dictionaryView = new DictionaryView();
			DictionaryManager dmanager = new DictionaryManager(dictionaryView);
			dictionaryView.pack();
			dictionaryView.setVisible(true);
		}
	}
	
	public String[] getArrayFromList(List<String[]> list) {
		ArrayList<String> arrayStr = new ArrayList<String>();
		for (String[] temp : list) {
			arrayStr.add(temp[2] + "  (" + temp[4]+")");
			//System.out.println(temp[2] + "  -  " + temp[4]);
		}
		return arrayStr.toArray(new String[arrayStr.size()]); 
	}
	
	//Get string array with selected properties
	public String[] getSelectedProperties() {
		ArrayList<String> checkedItems = view.getCheckedItems();
		ArrayList<String> selectedItems = new ArrayList<String>();
		for (String temp : checkedItems) {
			selectedItems.add(temp.split("\\s+")[0]);
			System.out.println(temp.split("\\s+")[0]);
		}
		return selectedItems.toArray(new String[selectedItems.size()]); 
	}
	 
	//temporal method that simulate the real findChart!
	private List<String[]> findCharts(String[] propertiesShortNames, String path){
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"ColumnChart","75"});
		list.add(new String[]{"BarChart","50"});
		list.add(new String[]{"PointChart","50"});
		list.add(new String[]{"GeoChart","25"});
		return list;
	}
}
