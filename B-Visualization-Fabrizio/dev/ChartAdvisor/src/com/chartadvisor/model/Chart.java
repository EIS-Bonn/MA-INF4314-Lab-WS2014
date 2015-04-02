package com.chartadvisor.model;

import com.chartadvisor.controller.AllocationGenerator;
import com.chartadvisor.controller.Controller;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.*;

import java.util.*;

public final class Chart {
		
	private static final String chart = "C:/Users/Cristo/Desktop/LAB/chart.rdf";
//	private static final String chart = Controller.findAbsoluteLocation()+"/chart.rdf"; // FOR .JAR 
	
	private static final String[] geoLabels = {"country","city","region","zip","zipcode", "capital"};
	
	private static final String[] coordinates = {"lat", "mag", "latitude", "magnitude"}; 
	
	public static Model getModel(){
		FileManager.get().addLocatorClassLoader(Chart.class.getClassLoader());
		return FileManager.get().loadModel(chart);
	}
	
	public static Allocation toAllocation(StmtIterator stmtIter){
		ArrayList<Property> left = new ArrayList<Property>();
		ArrayList<Property> right = new ArrayList<Property>();
		while (stmtIter.hasNext()){
			Statement stmt = stmtIter.next();
			if(stmt.getPredicate().getLocalName().equals("annotatedTarget"))
				right.add(new Property("",stmt.getString()));
			else if (stmt.getPredicate().getLocalName().equals("annotatedSource"))
				left.add(new Property("",stmt.getString()));
		}
		
		return new Allocation(left, right);
	}
	
	private static List<String[]> findPossibleCharts(String[] propertiesShortNames, Allocation allocation, Allocation prettyPrint, int totalProperties){
		List<String[]> result = new ArrayList<String[]>();
		Model m = getModel();
		ResIterator resources = m.listResourcesWithProperty(RDFS.label);
		while(resources.hasNext()){
			Resource resource = resources.next();
			StmtIterator stmtIter =  resource.listProperties();
			while (stmtIter.hasNext()){
				try{
					Resource resource2 = stmtIter.next().getResource();
					StmtIterator stmtIter2 =  resource2.listProperties();
					Allocation alloc = toAllocation(stmtIter2);
					//System.out.println("\nTO ALLOCATION:\n"+alloc);
					if(alloc.equals(allocation)){
						String[] suggestedChart = new String[]{"","",""};
						suggestedChart[0] = resource.getProperty(RDFS.label).getString();
						suggestedChart[1] = 100*allocation.getLength()/totalProperties + "";
						suggestedChart[2] = prettyPrint.toString();
						boolean add = true;
						for(String[] existingResult:result){
							if(existingResult[0].equals(suggestedChart[0]))
								if(existingResult[1].equals(suggestedChart[1]))
									add = false;
						}
						if(add){
							if(suggestedChart[0].equals("Geo Chart")){
								if(possibleGeoChart(propertiesShortNames, allocation))
									result.add(suggestedChart);
							}
							else{
								if((suggestedChart[0].equals("Pie Chart"))||(suggestedChart[0].equals("Donut Chart"))){
									if(possiblePieDonutChart(propertiesShortNames, allocation))
										result.add(suggestedChart);
								}
								else
									result.add(suggestedChart);
							}
						}
					}
					//System.out.println(stmtIter.next().getProperty(OWL2.annotatedSource).getString());
				}catch (Exception e){
					//e.printStackTrace();
				}
				
			}
			
		}
		return result;
	}
	
	private static boolean possibleGeoChart(String[] propertiesShortNames, Allocation allocation) {
		for(String property: propertiesShortNames){
			for(String gLabel : geoLabels){
				if(property.toLowerCase().contains(gLabel))
					return true;
			}
		}
		
		return false;
	}
	
	private static boolean possiblePieDonutChart(String[] propertiesShortNames, Allocation allocation) {
		for(String property: propertiesShortNames){
			for(String gLabel : coordinates){
				if(property.toLowerCase().contains(gLabel))
					return false;
			}
		}
		
		return true;
	}

	public static List<String[]> findCharts(String[] propertiesShortNames, String path){
		ArrayList<String[]> charts = new ArrayList<String[]>();
		List<String> propertiesSet = new ArrayList<String>(Arrays.asList(propertiesShortNames));
		//Property[] properties = {new Property("pop_count","integer"),new Property("year","integer"),new Property("country","string")};
		List<String[]> literals = DataSets.get_properties(DataSets.create_model(path), 1);
		Property[] properties = new Property[propertiesShortNames.length];
		String[] propertiesCompleteNames = new String[propertiesShortNames.length];
		for(String[] literal : literals){
			if(propertiesSet.contains(literal[2])){
				int index = propertiesSet.indexOf(literal[2]);
				properties[index] = new Property(literal[1], literal[2], literal[4]);
				propertiesCompleteNames[index] = "<"+literal[1]+">";
				System.out.println(literal[1]);
			}
		}
		//findCharts(new Allocation(left, right));
		List<Allocation> allocations = AllocationGenerator.generateAllocations(properties);
		
		List<String[]> propertiesValues = DataSets.sparql_query_property(DataSets.create_model(path), propertiesCompleteNames);
		
		allocations = AllocationGenerator.validateAllocations(allocations,propertiesValues, propertiesShortNames);
		if(allocations.size()==0)
			System.out.println("There are now valid allocations for these properties..");
		System.out.println("\nPossible Valid Allocations:\n"+allocations+"\n");
		
		
//		for (String[] values : propertiesValues){
//			for(String value : values){
//				System.out.print(value+"\t");
//			}
//			System.out.println();
//		}
		
		//System.out.println(findCharts(Allocation.toLOMAllocation(new Allocation(left, right))));
		for(Allocation alloc : allocations){
			Allocation allocLOM = Allocation.toLOMAllocation(alloc);
			List<String[]> foundcharts = findPossibleCharts(propertiesShortNames, allocLOM, alloc, propertiesShortNames.length);
			if(foundcharts.size()==0){
//				System.out.print("No charts found for allocation: ");
//				System.out.println(alloc);
//				System.out.println("-------------------");
			}
			else{
				charts.addAll(foundcharts);
				for(String[] chart:charts){
//					System.out.print("Chart: "+chart[0]);
//					System.out.print(" can be used to visualize allocation: "+ alloc);
//					System.out.println(" with percentage: "+chart[1] + "%");
//					System.out.println("-------------------");
				}
			}
		}
		
		removeDuplicates(charts);
		
		Collections.sort(charts, new Comparator<String[]>() {
			@Override
	        public int compare(String[] o1, String[] o2) {
	        	return (Integer.parseInt(o2[1]) >= Integer.parseInt(o1[1])) ? 1 : -1;
	        }});
		
		return charts;
	}

	
	private static void removeDuplicates(
			ArrayList<String[]> charts) {
		List<String[]> duplicates = new ArrayList<String[]>();
		for(String[] chart1 : charts){
			int encountered = 0;
			for(String[] chart2 : charts){
				if(chart1[0].equals(chart2[0])){
					if(Integer.parseInt(chart1[1])>Integer.parseInt(chart2[1])){
						duplicates.add(chart2);
					}
					else{
						if(Integer.parseInt(chart1[1])<Integer.parseInt(chart2[1])){
							duplicates.add(chart1);
						}
						else{
							encountered++;
							if(encountered>1)
								duplicates.add(chart2);
						}
					}
				}
			}
		}
		if (charts.isEmpty()) {
			System.out.println(" NO VALUES!!");
		}
		charts.removeAll(duplicates);
	}

	public static void main(String[] args) {
//		Model m = ModelFactory.createDefaultModel();//Chart.getModel();
//		m.createResource("http://chartsmetadata.com/ColumnChart").addProperty(RDFS.label, "Column Chart").addProperty(RDFS.isDefinedBy, m.createResource("http://chartsmetadata.com/ColumnChart/ChartPattern1").addProperty(OWL2.annotatedSource, "ORDINAL").addProperty(OWL2.annotatedSource, "CATEGORICAL").addProperty(OWL2.annotatedTarget, "QUANTITATIVE")).addProperty(RDFS.isDefinedBy, m.createResource("http://chartsmetadata.com/ColumnChart/ChartPattern2").addProperty(OWL2.annotatedSource, "ORDINAL").addProperty(OWL2.annotatedTarget, "QUANTITATIVE_"));
//		try {
//			m.write(new FileOutputStream(chart));
//		} catch (FileNotFoundException e) {
//			System.out.println("error saving model");
//		}
//		ArrayList<Property> right = new ArrayList<Property>();
//		right.add(new Property("pop_count","integer"));
//		ArrayList<Property> left = new ArrayList<Property>();
//		left.add(new Property("year","integer"));
//		left.add(new Property("country","string"));
		
		
		//To be provided by the interface
		String[] propertiesNames = {"nameShort", "populationTotal", "populationYear"};
		//String path = "/home/ahmad/Documents/geodata.rdf";
		String path = "C:/Users/Cristo/Desktop/ChartAdvisor/TEST/geodata.rdf";

		
		List<String[]> charts = findCharts(propertiesNames, path);
		for(String[] chart : charts){
			System.out.print("Chart: "+chart[0]);
			System.out.print(" can be used to visualize allocation: "+ chart[2]);
			System.out.println(" with percentage: "+chart[1] + "%");
			System.out.println("-------------------");
		}
	}
}
