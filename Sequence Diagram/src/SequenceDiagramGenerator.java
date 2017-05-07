
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import java.util.logging.Logger;

public class SequenceDiagramGenerator

{

	private final static Logger LOGGER = Logger.getLogger(SequenceDiagramGenerator.class.getName());

	public static void main(String[] args) {

		String inputFilePath = args[0];
		String outputDirectoryPath = args[1];

		try {

			try {
				Class cls = Class.forName(inputFilePath);
				Method meth = cls.getMethod("main", String[].class);
				meth.invoke(null, (Object) null);
				SyntaxToUmlDiagramGenerator.draw(SequencesToPlantumlSyntaxTransformer
						.transform(MethodTracking.getSequences(), MethodTracking.getActor()), outputDirectoryPath);
			} catch (MalformedURLException e) {
			} catch (ClassNotFoundException e) {
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.fine("Input file " + inputFilePath);
		LOGGER.fine("Out Directory " + outputDirectoryPath);

	}

}
