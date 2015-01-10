package main;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class DataSets {

	public static void main(String[] args) {

	FileManager.get().addLocatorClassLoader(DataSets.class.getClassLoader());
	Model model	= FileManager.get().loadModel("K:/9- Master in Computer Sciences/Fourth Semester/Lab Web Semantics/New folder/WebSemanticsLab/src/main/data.rdf");
	model.write(System.out,"RDF/JSON");
	}

}
