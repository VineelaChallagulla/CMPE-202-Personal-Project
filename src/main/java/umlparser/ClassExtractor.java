package umlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.tree.*;

import cmpe202.project.JavaLexer;
import cmpe202.project.JavaParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ClassExtractor {
	public ClassDefinition extractClassDefinition(String filePath) throws FileNotFoundException, IOException {

		File file = new File(filePath);
		ANTLRInputStream input = new ANTLRInputStream(new FileReader(file));

		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		ParseTree tree = parser.compilationUnit(); // parse

		ParseTreeWalker walker = new ParseTreeWalker(); // create standard
														// walker
		JavaCoustomListner extractor = new JavaCoustomListner(parser);
		ClassDefinition classDefinition = new ClassDefinition();
		extractor.setClassDefinition(classDefinition);
		walker.walk(extractor, tree); // initiate walk of tree with listener
		return classDefinition;
	}
}
