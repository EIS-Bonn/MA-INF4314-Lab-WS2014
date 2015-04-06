package com.chartadvisor.model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import javax.swing.JOptionPane;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;


public class DataSets {

	public static void main(String[] args) throws IOException {


		// Create model from the file.
		String filename = "K:/9- Master in Computer Sciences/Fourth Semester/Lab Web Semantics/New folder/WebSemanticsLab/src/main/worldbank-slice-5000.rdf";
		Model model =	create_model(filename);

		//Load model in certain format to a file.
		String output 	= 	"K:/9- Master in Computer Sciences/Fourth Semester/Lab Web Semantics/New folder/WebSemanticsLab/src/main/worldbank-slice-5000.rdf";
		String format	=	"TURTLE";
		//load_model_file(model, output,format);
	

		// Get the array of properties with the type, complete name, local name, complete datatype and short name of datatype for Literals
		// Type property {0= all, 1= Literals, 2= Resources}
		List<String[]> Properties_Array;
		int	type_property	=2;
		Properties_Array = get_properties(model,type_property);

		
		//Get values from the array of properties
		
		String 	property1 = "<http://www.w3.org/2004/02/skos/core#notation>";
		String 	property2 = "<http://dbpedia.org/property/capital>";
		String 	property3 = "<http://www.w3.org/2004/02/skos/core#inScheme>";
		String 	property4 = "<http://www.w3.org/2002/07/owl#sameAs>";
	
		String[] propertyArray = {property1,property2,property3,property4};

		List<String[]> ValueProperties; 	
		
		ValueProperties = sparql_query_property(model,propertyArray);

	

	}

	
	//Create model from the file.
	
	public static Model create_model(String filename){
		FileManager.get().addLocatorClassLoader(DataSets.class.getClassLoader());
		Model model	= FileManager.get().loadModel(filename);
		
		return model;
	}
	
	//Load model in certain format to a file.
	public static void load_model_file(Model model,String fileName,String format) throws IOException{
		
		FileWriter output = new FileWriter( fileName );
		try {
		    model.write( output, format );
		}
		finally {
			output.close();
		}
	}
	
	
	//Get the array of properties with the type, complete name, local name, complete datatype and short name of datatype for Literals
	// Type property {0= all, 1= Literals, 2= Resources}
	public static List<String[]> get_properties(Model model, int type_property){
		
		StmtIterator iter = model.listStatements();
	    Set<String> setProperties 	= new HashSet<String>();
	    List<String[]>	ResultList= new ArrayList<String[]>();
	    
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    com.hp.hpl.jena.rdf.model.Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    String Datatype 	= get_datatype(object).toString();
		    String ShortName_DataType	=	Datatype.substring(Datatype.lastIndexOf("#")+1);

		    
		    String type;
		    if(setProperties.add(predicate.getLocalName())){
		    	
		    	
		    	if(object instanceof Resource){
		    		type= "Resource";
			    	Datatype	= "Resource";
			    	ShortName_DataType	=	"Resource";
		    	
		    	}else{
		    		type= "Literal";
		    	}
		    	
		    	if(type_property==0){	
		    	
				    if(!(Datatype.contains("#")&&(type== "Literal"))){
				    	Datatype	= "String";
				    	ShortName_DataType	=	"String";
				    }
				    
			    	String result[] = {type,predicate.getURI(),predicate.getLocalName(),Datatype,ShortName_DataType};
			    	
			    	ResultList.add(result);
		    	}else if (type == "Literal" && type_property ==1){
		    		

				    if(!(Datatype.contains("#"))){
				    	Datatype	= "String";
				    	ShortName_DataType	=	"String";
				    }

		    		
		    		String result[] = {type,predicate.getURI(),predicate.getLocalName(),Datatype,ShortName_DataType};
		    		
			    	ResultList.add(result);
		    	}else if(type== "Resource" && type_property ==2){
		    	
		    		String result[] = {type,predicate.getURI(),predicate.getLocalName(),Datatype,ShortName_DataType};
			    	
			    	ResultList.add(result);
			    	}
		    	
		    }
		    
		}

		return ResultList;
		
		

	}
	
	
	// Get DataType of properties
	public static String get_datatype(RDFNode object){
		String Output = object.asNode().toString();
		return Output;
	}
	
	//Get values from the array of properties
	
		
	public static List<String[]> sparql_query_property(Model model,String[] propertyArray){
		int Arraysize =propertyArray.length; 

		String whereString = "WHERE {";
		for (int i=0;i<Arraysize;i++){
			whereString +=	"?object0" +propertyArray[i] +" ?object"+(i+1)+".";
			
		}
		
		
		String StringQuery = "SELECT *"	+ whereString + "}";
		System.out.println(StringQuery);
		List<String[]> ResultList = new ArrayList<String[]>();
		try{
		Query query = QueryFactory.create(StringQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query,model);
		
		
			
			ResultSet results = qexec.execSelect();

			while( results.hasNext()){
				
				QuerySolution slon = results.nextSolution();
				
				String[] objectResult = new String[Arraysize];
				
				for (int j=0;j<Arraysize;j++){

					objectResult[j] = slon.getLiteral("object"+(j+1)).toString();

					System.out.println(slon.getLiteral("object"+(j+1)).toString());
				}
				
				  ResultList.add(objectResult);
				  
			} 
			
			qexec.close();
		}
		catch(Exception Ex)
		{
			JOptionPane.showMessageDialog(null, "Error", "Properties selected are of different resources!", JOptionPane.ERROR_MESSAGE);
		}finally
		{

		}
		
		return ResultList;
		
	}
	

}
