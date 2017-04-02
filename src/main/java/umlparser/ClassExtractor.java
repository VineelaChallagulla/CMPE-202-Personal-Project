package umlparser;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.tree.*;

import cmpe202.project.JavaBaseListener;
import cmpe202.project.JavaLexer;
import cmpe202.project.JavaParser;


import java.io.InputStream;

public class ClassExtractor {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
//        if ( inputFile!=null ) {
//            is = new FileInputStream(inputFile);
//        }
        
        is = ClassExtractor.class.getResourceAsStream("test.properties");
        ANTLRInputStream input = new ANTLRInputStream(is);

        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit(); // parse

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        JavaCoustomListner extractor = new JavaCoustomListner(parser);
        ClassDefinition classDefinition = new ClassDefinition();
        extractor.setClassDefinition(classDefinition);
        walker.walk(extractor, tree); // initiate walk of tree with listener     
        ClassDefinitionsToPlantUmlTransformer ClassDefinitionsToPlantUmlTransformer = new ClassDefinitionsToPlantUmlTransformer();
        System.out.println(ClassDefinitionsToPlantUmlTransformer.getTransformedClassDefinition(classDefinition));
        
    }
}
