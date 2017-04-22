package umlparser;

import java.util.ArrayList;

import org.antlr.v4.runtime.TokenStream;

import cmpe202.project.JavaBaseListener;
import cmpe202.project.JavaParser;
import cmpe202.project.JavaParser.TypeContext;
import cmpe202.project.JavaParser.VariableDeclaratorContext;

public class JavaCoustomListner extends JavaBaseListener {

	private JavaParser parser;
	private ClassDefinition classDefinition;

	public JavaCoustomListner(JavaParser parser) {
		this.parser = parser;

	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	@Override
	public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
		this.classDefinition.setPackageName(ctx.qualifiedName().Identifier(0).getText());


	}

	@Override
	public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
		}
		String args = tokens.getText(ctx.formalParameters());
		
		args= args.replaceAll("\\(", "");
		args= args.replaceAll("\\)", "");
		if (!args.isEmpty()){
		 String[] arsArray = new String[1];
		if(args.contains(",")){
			arsArray= args.split(",");
		}
		else{
			arsArray[0] = args;
			
		}

		 for(String arg: arsArray ){
			 arg = arg.trim();
			 String[] argSplit =  arg.split("\\s+");
			 this.classDefinition.addMethodVariables(argSplit[0], argSplit[1]);
		 }
		}
		this.classDefinition.addMethodSignature("\t" + type + " " + ctx.Identifier() + args );
	}

	@Override
	public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {

	}

	@Override
	public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {

		this.classDefinition.setName(ctx.Identifier().getText());

		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
			this.classDefinition.setExtendsClassName(type);
		}

		ArrayList<String> implementList = new ArrayList<String>();
		if (ctx.typeList() != null) {
			for (TypeContext interfaceType : ctx.typeList().type()) {
				implementList.add(tokens.getText(interfaceType));

			}
		}
		this.classDefinition.setImplementInterfaceNames(implementList);

	}

	@Override
	public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {

	}
	
	@Override
	public void   enterInterfaceDeclaration (JavaParser.InterfaceDeclarationContext ctx) {
		
		if(ctx.normalInterfaceDeclaration().Identifier().getText() != null){
			this.classDefinition.setName(ctx.normalInterfaceDeclaration().Identifier().getText());
			this.classDefinition.setInterface(true);
		}
		
	}


	@Override
	public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String variableType = null;
		if (ctx.type() != null) {

			variableType = tokens.getText(ctx.type());

			if (variableType.contains("[")) {

			}

		}

		for (VariableDeclaratorContext variableDeclaratorContext : ctx.variableDeclarators().variableDeclarator()) {
			this.classDefinition.addVariable(variableDeclaratorContext.variableDeclaratorId().Identifier().getText(),
					variableType);

		}

	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
		
		
		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		String args = tokens.getText(ctx.formalParameters());
		
		args= args.replaceAll("\\(", "");
		args= args.replaceAll("\\)", "");
		if (!args.isEmpty()){
		 String[] arsArray = new String[1];
		if(args.contains(",")){
			arsArray= args.split(",");
		}
		else{
			arsArray[0] = args;
			
		}

		 for(String arg: arsArray ){
			 arg = arg.trim();
			 String[] argSplit =  arg.split("\\s+");
			 this.classDefinition.addConstructorVariables(argSplit[0], argSplit[1]);
		 }
		}
	}

}
