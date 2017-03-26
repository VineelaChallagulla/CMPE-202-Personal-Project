package umlgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.SourceStringReader;

public class SyntaxToUML {
	
	public static void generateUMLFromFile(String path) throws FileNotFoundException{
	
	File file = new File("newfile.png");
	String content = "This is the text content";
	OutputStream png = new FileOutputStream(file);
	String source = new String();
	source+=  "@startuml\n";
	source += "Bob -> Alice : hello\n";
	source += "@enduml\n";

	SourceStringReader reader = new SourceStringReader(source);
	// Write the first image to "png"
	try {
		String desc = reader.generateImage(png);
		System.out.print(desc);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// Return a null string if no generation
	}
	
	public static void main(String[] args){
		try {
			generateUMLFromFile("path");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
