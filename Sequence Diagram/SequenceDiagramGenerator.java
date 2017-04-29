
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.runner.JUnitCore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;






public class SequenceDiagramGenerator 

{
	
	private final static Logger LOGGER = Logger.getLogger(SequenceDiagramGenerator.class.getName());
    public static void main( String[] args )
    {
    	

		Options options = new Options();

		Option input = new Option("i", "input", true, "input file path");
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

		String inputFilePath = cmd.getOptionValue("input");
		String outputDirectoryPath = cmd.getOptionValue("output");

		System.out.println("Input file " + inputFilePath);
		
		 try {

			 

			 try {
				 Class cls = Class.forName(inputFilePath);
				 Method meth = cls.getMethod("main", String[].class);
				 meth.invoke(null, (Object) null);
				 
//		            Class c = Class.forName(inputFilePath);
//		            JUnitCore junit = new JUnitCore();
//		            Result result = junit.run(c);
				 SyntaxToUmlDiagramGenerator.draw(SequencesToPlantumlSyntaxTransformer.transform(MethodTracking.getSequences()), outputDirectoryPath);
			 } catch (MalformedURLException e) {
			 } catch (ClassNotFoundException e) {
			 }


		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		LOGGER.fine("Input file " + inputFilePath);
		LOGGER.fine("Out Directory " + outputDirectoryPath);


	}
    
    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
      }
    }
