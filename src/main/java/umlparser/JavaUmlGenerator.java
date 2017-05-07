package umlparser;

import org.apache.commons.cli.*;
import org.apache.commons.cli.DefaultParser;

import umlgenerator.SyntaxToUML;

import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JavaUmlGenerator {
	private final static Logger LOGGER = Logger.getLogger(JavaUmlGenerator.class.getName());

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Options options = new Options();

		Option input = new Option("i", "input", true, "input directory path");
		Option output = new Option("o", "output", true, "output directory path");
		input.setRequired(true);
		options.addOption(input);
		output.setRequired(true);
		options.addOption(output);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("UmlGenerator", options);
			System.exit(1);
			return;
		}

		String inputDirectoryPath = cmd.getOptionValue("input");
		String outputPath = cmd.getOptionValue("output");

		// String inputDirectoryPath = args[0];
		LOGGER.fine("Input Directory " + inputDirectoryPath);
		File file = new File(inputDirectoryPath);
		// String outptImageName = file.getName()+".png";
		// String outputPath = file.getParent();
		String outptImageName = "classdiagram" + ".png";
		List<String> listOfJavaSourceFiles = getJavaSorceFiles(inputDirectoryPath);
		ClassExtractor classExtractor = new ClassExtractor();
		List<ClassDefinition> listOfClassDefinitions = new ArrayList<>();

		for (String filePath : listOfJavaSourceFiles) {

			listOfClassDefinitions
					.add(classExtractor.extractClassDefinition(inputDirectoryPath + File.separator + filePath));
		}
		ClassDefinitionsToPlantUmlTransformer classDefinitionsToPlantUmlTransformer = new ClassDefinitionsToPlantUmlTransformer();

		String umlSyntax = classDefinitionsToPlantUmlTransformer.getUmlSyntax(listOfClassDefinitions);

		try {
			SyntaxToUML.generateUml(umlSyntax, outputPath, outptImageName);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String> getJavaSorceFiles(String inputDirectoryPath) {

		File folder = new File(inputDirectoryPath);
		File[] listOfFiles = folder.listFiles();
		List<String> listOfJavaSourceFiles = new ArrayList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".java")) {
				LOGGER.fine("File " + listOfFiles[i].getName());
				// System.out.println("File " + listOfFiles[i].getName());
				listOfJavaSourceFiles.add(listOfFiles[i].getName());

			}
		}

		return listOfJavaSourceFiles;

	}

}
