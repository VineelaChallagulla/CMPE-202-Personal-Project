package umlparser;

import org.apache.commons.cli.*;
import org.apache.commons.cli.DefaultParser;
import java.util.logging.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaUmlGenerator {
	private final static Logger LOGGER = Logger.getLogger(JavaUmlGenerator.class.getName());

	public static void main(String[] args) {

		Options options = new Options();

		Option input = new Option("i", "input", true, "input directory path");
		input.setRequired(true);
		options.addOption(input);

		Option output = new Option("o", "output", true, "output Directory");
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
		String outputDirectoryPath = cmd.getOptionValue("output");

		System.out.println("Input Directory " + inputDirectoryPath);

		LOGGER.fine("Input Directory " + inputDirectoryPath);
		LOGGER.fine("Out Directory " + outputDirectoryPath);
		List<String> listOfJavaSourceFiles = getJavaSorceFiles(inputDirectoryPath);

	}

	public static List<String> getJavaSorceFiles(String inputDirectoryPath) {

		File folder = new File(inputDirectoryPath);
		File[] listOfFiles = folder.listFiles();
		List<String> listOfJavaSourceFiles = new ArrayList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".java")) {
				LOGGER.fine("File " + listOfFiles[i].getName());
				System.out.println("File " + listOfFiles[i].getName());
				listOfJavaSourceFiles.add(listOfFiles[i].getName());

			}
		}

		return listOfJavaSourceFiles;

	}

}
