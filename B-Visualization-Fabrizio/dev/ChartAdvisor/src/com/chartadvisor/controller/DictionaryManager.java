package com.chartadvisor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.chartadvisor.model.Dictionary;
import com.chartadvisor.view.DictionaryView;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDFS;

public class DictionaryManager implements ActionListener {
	
	private static final String dict = "C:/Users/Cristo/Desktop/dictionary.rdf";
	private DictionaryView dview; 
	
	public DictionaryManager(DictionaryView dview) {
		this.dview = dview;
		this.dview.getJbtn_add().addActionListener(this);
		this.setJListValues();
		
	}
	
	public void addValueToJList(){
		String propertyName = dview.getJtxt_label().getText(); 
		String propertyType = dview.getJcbox_DataType().getSelectedItem().toString().toLowerCase(); 
		String LOM = dview.getJcbox_LOM().getSelectedItem().toString().toLowerCase(); 
		System.out.println("+++ " + propertyName + "  " + propertyType + "  " + LOM);
		try {
			this.addResource(propertyName, propertyType, LOM);
			this.setJListValues();
			this.dview.repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.dview,"Not valid value!.","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setJListValues(){
		dview.getJlist_dictionary().setListData(getDictionaryList());
	}
	
	public String[] getDictionaryList(){
		Model m = this.getModel();
		ResIterator resources = m.listResourcesWithProperty(RDFS.label);
		List<String> resultList = new ArrayList<String>();
		while(resources.hasNext()){
			Resource res = resources.next();
			resultList.add(res.getProperty(RDFS.label).getString() + "  /  "+res.getProperty(DC.type).getString() + "  /  " + res.getProperty(RDFS.subClassOf).getString());
		}
		return resultList.toArray(new String[resultList.size()]); 
	}
	
	public void addResource(String propertyName, String propertyType, String LOM) throws Exception{
		String alreadyExists = this.getLOM(propertyName, propertyType);
		if(alreadyExists != null){
			JOptionPane.showMessageDialog(this.dview, "level of measurement for "+propertyName+" of type "+propertyType+" already exists in the dictionary as a subclass of "+alreadyExists ,"Warning!", JOptionPane.WARNING_MESSAGE);
			Exception e = new Exception("level of measurement for "+propertyName+" of type "+propertyType+" already exists in the dictionary as a subclass of "+alreadyExists);
			throw e;
		}
		Model m = this.getModel();
		m.createResource("http://levelofmeasurement.com/"+propertyType+"/"+propertyName+"#").addProperty(RDFS.subClassOf,LOM).addProperty(DC.type, propertyType).addProperty(RDFS.label, propertyName);
		try {
			m.write(new FileOutputStream(dict));
		} catch (FileNotFoundException e) {
			System.out.println("error saving model");
		}
	}
	
	public String getLOM(String propertyName, String propertyType){
		Model m = this.getModel();
		ResIterator resources = m.listResourcesWithProperty(RDFS.label);
		while(resources.hasNext()){
			Resource res = resources.next();
			if(res.getProperty(RDFS.label).getString().equalsIgnoreCase(propertyName)){
				if(res.getProperty(DC.type).getString().equalsIgnoreCase(propertyType)){
					return res.getProperty(RDFS.subClassOf).getString();
				}
			}
		}
		return null;
	}
	
	public Model getModel(){
		return FileManager.get().loadModel(dict);
	}
	
	public static void main(String[] args) {
		DictionaryManager dm = new DictionaryManager(new DictionaryView());
		String[] str = dm.getDictionaryList();
		for (String temp : str) {
			System.out.println(temp);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dview.getJbtn_add()) {
			this.addValueToJList();
		}
		
	}
}
