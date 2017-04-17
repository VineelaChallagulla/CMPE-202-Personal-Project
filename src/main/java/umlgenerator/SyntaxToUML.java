package umlgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import net.sourceforge.plantuml.SourceStringReader;
import umlparser.ClassExtractor;

public class SyntaxToUML {

	public static void generateUMLFromFile(String path) throws IOException, InterruptedException, URISyntaxException {

		URL filePath = SyntaxToUML.class.getResource("uml_syntax_test_input");
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

	}

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		try {
			generateUMLFromFile("uml_syntax_test_input");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static String getFileContent(
			   FileInputStream fis,
			   Charset          encoding ) throws IOException
			 {
			   try( BufferedReader br =
			           new BufferedReader( new InputStreamReader(fis, encoding )))
			   {
			      StringBuilder sb = new StringBuilder();
			      String line;
			      while(( line = br.readLine()) != null ) {
			         sb.append( line );
			         sb.append( '\n' );
			      }
			      return sb.toString();
			   }
			}
	
	public static void generateUml(String umlSyntax, String path) throws URISyntaxException, IOException{
		
		
		
		File outputFile = new File(path);
		if(! outputFile.exists()){
			
			outputFile.createNewFile();
		}
		
		FileOutputStream png = new FileOutputStream(outputFile);
		SourceStringReader reader = new SourceStringReader(umlSyntax);
		// Write the first image to "png"
		String desc = reader.generateImage(png);		
		
		
	}
}
