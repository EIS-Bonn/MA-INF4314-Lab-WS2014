package com.chartadvisor.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.chartadvisor.controller.Controller;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.*;

public final class Dictionary {
	

	private static final String dict = Controller.findAbsoluteLocation()+"/resource/dictionary.rdf"; // FOR .JAR 
	
	public static Model getModel(){
		return FileManager.get().loadModel(dict);
	}
	
	public static void addResource(String propertyName, String propertyType, String LOM) throws Exception{
		String alreadyExists = Dictionary.getLOM(propertyName, propertyType);
		if(alreadyExists != null){
			Exception e = new Exception("level of measurement for "+propertyName+" of type "+propertyType+" already exists in the dictionary as a subclass of "+alreadyExists);
			throw e;
		}
		Model m = Dictionary.getModel();
		m.createResource("http://levelofmeasurement.com/"+propertyType+"/"+propertyName+"#").addProperty(DC.description,LOM).addProperty(DC.format, propertyType).addProperty(RDFS.label, propertyName);
		try {
			m.write(new FileOutputStream(dict));
		} catch (FileNotFoundException e) {
			System.out.println("error saving model");
		}
	}
	
	public static String getLOM(String propertyName, String propertyType){
		Model m = Dictionary.getModel();
		ResIterator resources = m.listResourcesWithProperty(RDFS.label);
		while(resources.hasNext()){
			Resource res = resources.next();
			if(res.getProperty(RDFS.label).getString().equalsIgnoreCase(propertyName)){
				if(res.getProperty(DC.format).getString().equalsIgnoreCase(propertyType)){
					return res.getProperty(DC.description).getString();
				}
			}
		}
		
		return null;
		
	}
	

	public static void main(String[] args) {
		System.out.println(Dictionary.getLOM("YEar", "double"));
		System.out.println(Dictionary.getLOM("YEar", "integer"));
		System.out.println(Dictionary.getLOM("pop_count", "double"));
		System.out.println(Dictionary.getLOM("pop_count", "integer"));
		try {
			Dictionary.addResource("year", "integer", "ordinal");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
