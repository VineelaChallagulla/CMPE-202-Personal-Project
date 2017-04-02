package umlgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import net.sourceforge.plantuml.SourceStringReader;
import umlparser.ClassExtractor;

public class SyntaxToUML {

	public static void generateUMLFromFile(String path) throws IOException, InterruptedException, URISyntaxException {

		URL filePath = ClassExtractor.class.getResource("uml_syntax_test_input");
		File file;
		file = new File(filePath.toURI());

		SourceFileReader reader = new SourceFileReader(file);
		// Write the first image to "png"
		try {
			List<GeneratedImage> list = reader.getGeneratedImages();
			// Generated files
			File png = list.get(0).getPngFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return a null string if no generation
	}

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		try {
			generateUMLFromFile("uml_syntax_test_input");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
