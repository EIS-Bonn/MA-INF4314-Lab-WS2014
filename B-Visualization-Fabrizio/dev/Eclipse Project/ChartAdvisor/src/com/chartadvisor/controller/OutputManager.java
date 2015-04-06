package com.chartadvisor.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OutputManager {
	
	public static final String ref01 = "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"";
	public static final String ref02 = "xmlns:chr=\"http://chartrecommender.com/properties/\"";
	public static final String ref03 = "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"";
	public static final String space = "   ";
	
	public void generateRDFOutput(List<String[]> results, File file){
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<rdf:RDF\n");
			bw.write(space + ref01 + "\n");
			bw.write(space + ref02 + "\n");
			bw.write(space + ref03 + " >\n");
			int counter = 1;
			for (String[] temp : results) {
				bw.write(space + "<rdf:Description rdf:about=\"developers.google.com/chart/interactive/docs/gallery/" +temp[0].replaceAll("\\s+","").toLowerCase()+ "\"> \n");
				bw.write(space + space +"<rdfs:label>" +temp[0]+ "</rdfs:label> \n");
				bw.write(space + space +"<chr:rank>" +counter+ "</chr:rank> \n");
				bw.write(space + space +"<chr:accuracy>" +temp[1]+ "</chr:accuracy> \n");
				bw.write(space + "</rdf:Description>\n");
				counter++;
			}
			bw.write("</rdf:RDF>");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateTXTOutput(List<String[]> results, File file){
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Chart Recommendations: \n");
			int counter = 1;
			for (String[] temp : results) {
				bw.write("Rank " +counter+ ": "+temp[0]+" with a "+temp[1]+"% of accuracy.\n");
				counter++;
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateXMLOutput(List<String[]> results, File file){
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			int counter = 1;
			for (String[] temp : results) {
				bw.write("<recommendation>\n");
				bw.write(space + "<rank>" +counter+ "</rank> \n");
				bw.write(space + "<chart>" +temp[0]+ "</chart> \n");
				bw.write(space + "<accuracy>" +temp[1]+ "</accuracy> \n");
				bw.write("</recommendation>\n");
				counter++;
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void replaceStr(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
}
