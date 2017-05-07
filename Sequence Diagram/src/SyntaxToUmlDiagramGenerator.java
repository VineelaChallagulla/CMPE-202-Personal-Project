
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import net.sourceforge.plantuml.SourceStringReader;

public class SyntaxToUmlDiagramGenerator {

	public static void draw(String umlSyntax, String path) throws URISyntaxException, IOException {
		System.out.println(umlSyntax);
		File outputFile = new File(path + File.separator + "sequence.png");
		if (!outputFile.exists()) {

			outputFile.createNewFile();
		}

		FileOutputStream png = new FileOutputStream(outputFile);
		SourceStringReader reader = new SourceStringReader(umlSyntax);
		reader.generateImage(png);

	}

}
