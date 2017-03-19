package umlparser;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;

import cmpe202.project.Java8BaseListener;
import cmpe202.project.Java8Lexer;
import cmpe202.project.Java8Parser;

import java.io.FileInputStream;
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

        Java8Lexer lexer = new Java8Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.compilationUnit(); // parse

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        Java8BaseListener extractor = new JavaCoustomListner(parser);
        walker.walk(extractor, tree); // initiate walk of tree with listener
    }
}
