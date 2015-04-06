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
	
	private DictionaryView dview; 
	
	public DictionaryManager(DictionaryView dview) {
		this.dview = dview;
		this.dview.getJbtn_add().addActionListener(this);
		this.setJListValues();
		
	}
	
	public void addValueToJList(){
		String propertyName = dview.getJtxt_label().getText(); 
		String propertyType = dview.getJtxt_DataType().getText();  
		String LOM = dview.getJcbox_LOM().getSelectedItem().toString().toLowerCase(); 
		System.out.println("+++ " + propertyName + "  " + propertyType + "  " + LOM);
		try {
			Dictionary.addResource(propertyName, propertyType, LOM);
			this.setJListValues();
			this.dview.repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.dview,"Not valid value! The value already exist.","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setJListValues(){
		dview.getJlist_dictionary().setListData(getDictionaryList());
	}
	
	public String[] getDictionaryList(){
		Model m = Dictionary.getModel();
		ResIterator resources = m.listResourcesWithProperty(RDFS.label);
		List<String> resultList = new ArrayList<String>();
		while(resources.hasNext()){
			Resource res = resources.next();
			resultList.add(res.getProperty(RDFS.label).getString() + "  /  "+res.getProperty(DC.type).getString() + "  /  " + res.getProperty(RDFS.subClassOf).getString());
		}
		return resultList.toArray(new String[resultList.size()]); 
	}
	
	
	public static void main(String[] args) {
		DictionaryManager dm = new DictionaryManager(new DictionaryView());
		String[] str = dm.getDictionaryList();
		for (String temp : str) {
			System.out.println(temp);
		}
	}
	
    public boolean validateData(String str){
        if (str.matches("[a-zA-Z]+$")) {
            return true;
        } else {
            return false;
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dview.getJbtn_add()) {
			if (!this.validateData(dview.getJtxt_DataType().getText())) {
	            JOptionPane.showMessageDialog(dview,"IVALID INPUT DATA! please do not insert special characters","Error!", JOptionPane.ERROR_MESSAGE);
	        } else if(!this.validateData(dview.getJtxt_label().getText())) {
	            JOptionPane.showMessageDialog(dview,"IVALID INPUT DATA! please do not insert special characters","Error!", JOptionPane.ERROR_MESSAGE);
	        } else {
	        	this.addValueToJList();
	        }
		}
		
	}
}
