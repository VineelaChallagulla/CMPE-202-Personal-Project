package umlparser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.TokenStream;

import cmpe202.project.JavaBaseListener;
import cmpe202.project.JavaParser;
import cmpe202.project.JavaParser.AnnotationContext;
import cmpe202.project.JavaParser.ConstantDeclaratorContext;
import cmpe202.project.JavaParser.FormalParametersContext;
import cmpe202.project.JavaParser.InterfaceMethodDeclaratorRestContext;
import cmpe202.project.JavaParser.ModifierContext;
import cmpe202.project.JavaParser.TypeContext;
import cmpe202.project.JavaParser.VariableDeclaratorContext;
import umlparser.Variable;

public class JavaCoustomListner extends JavaBaseListener {
	private String modifier;
	private String interfaceModifier;
	private String interfaceVoidIdentifier;
	private Variable variable;

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getInterfaceModifier() {
		return interfaceModifier;
	}

	public void setInterfaceModifier(String interfaceModifier) {
		this.interfaceModifier = interfaceModifier;
	}

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
	public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
		this.modifier = "~";
		for (ModifierContext modifierContext : ctx.modifiers().modifier()) {

			String modifier = modifierContext.getText();
			if (modifier.equals("private")) {
				this.modifier = "-";

			} else if (modifier.equals("public")) {
				this.modifier = "+";

			} else if (modifier.equals("proteced")) {
				this.modifier = "#";

			}

		}

	}

	@Override
	public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();

		String type = "void";
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
		} else {
			type = "void";
		}

		String formalArgs = tokens.getText(ctx.formalParameters());

		String args = formalArgs.replaceAll("\\(", "");
		args = args.replaceAll("\\)", "");
		if (!args.isEmpty()) {
			String[] arsArray = new String[1];
			if (args.contains(",")) {
				arsArray = args.split(",");
			} else {
				arsArray[0] = args;

			}

			for (String arg : arsArray) {
				Variable variable = new Variable();
				if (arg != null && !arg.isEmpty()) {
					arg = arg.trim();

					String[] argSplit = arg.split("\\s+");
					if (argSplit.length == 2) {

						variable.setName(argSplit[1]);
						variable.setType(argSplit[0]);
						variable.setModifier("+");
						this.classDefinition.addMethodVariables(ctx.Identifier().getText(), variable);
					}
				}
			}
		}
		this.classDefinition.addMethodIdentifier(ctx.Identifier().getText());
		this.classDefinition.addMethodSignature(
				"\t" + this.modifier + " " + ctx.Identifier().getText() + formalArgs + " : " + type);
		this.classDefinition.addMethodIdentifierSignature(ctx.Identifier().getText(),
				"\t" + this.modifier + " " + ctx.Identifier().getText() + formalArgs + " : " + type);
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
	public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
		TokenStream tokens = parser.getTokenStream();

		if (ctx.normalInterfaceDeclaration().Identifier().getText() != null) {
			this.classDefinition.setName(ctx.normalInterfaceDeclaration().Identifier().getText());
			this.classDefinition.setInterface(true);
		}
		ArrayList<String> implementList = new ArrayList<String>();
		if (ctx.normalInterfaceDeclaration().typeList() != null) {
			for (TypeContext interfaceType : ctx.normalInterfaceDeclaration().typeList().type()) {
				implementList.add(tokens.getText(interfaceType));

			}
		}
		this.classDefinition.setImplementInterfaceNames(implementList);

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
			Variable variable = new Variable();
			variable.setName(variableDeclaratorContext.variableDeclaratorId().Identifier().getText());
			variable.setType(variableType);
			variable.setModifier(this.modifier);
			this.classDefinition.addVariable(variableDeclaratorContext.variableDeclaratorId().Identifier().getText(),
					variable);

		}

	}

	@Override
	public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		String args = tokens.getText(ctx.formalParameters());

		args = args.replaceAll("\\(", "");
		args = args.replaceAll("\\)", "");
		if (!args.isEmpty()) {
			String[] arsArray = new String[1];
			if (args.contains(",")) {
				arsArray = args.split(",");
			} else {
				arsArray[0] = args;

			}

			for (String arg : arsArray) {
				arg = arg.trim();
				String[] argSplit = arg.split("\\s+");
				Variable variable = new Variable();
				variable.setName(argSplit[1]);
				variable.setType(argSplit[0]);
				variable.setModifier("+");
				this.classDefinition.addConstructorVariables(ctx.Identifier().getText(), variable);
			}
		}
	}

	@Override
	public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String variableType = null;
		if (ctx.type() != null) {

			variableType = tokens.getText(ctx.type());

			if (variableType.contains("[")) {

			}

		}

		for (VariableDeclaratorContext variableDeclaratorContext : ctx.variableDeclarators().variableDeclarator()) {
			Variable variable = new Variable();
			variable.setName(variableDeclaratorContext.variableDeclaratorId().Identifier().getText());
			variable.setType(variableType);
			variable.setModifier("-");
			this.classDefinition.addMethodVariables(
					variableDeclaratorContext.variableDeclaratorId().Identifier().getText(), variable);

		}

	}

	@Override
	public void enterInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx) {

		this.interfaceModifier = "~";
		for (ModifierContext modifierContext : ctx.modifiers().modifier()) {

			String modifier = modifierContext.getText();
			if (modifier.equals("private")) {
				this.interfaceModifier = "-";

			} else if (modifier.equals("public")) {
				this.interfaceModifier = "+";

			} else if (modifier.equals("proteced")) {
				this.interfaceModifier = "#";

			}

		}

	}

	@Override
	public void enterInterfaceMethodOrFieldDecl(JavaParser.InterfaceMethodOrFieldDeclContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String variableType = null;
		String identifier = ctx.Identifier().getText();
		if (ctx.type() != null) {

			variableType = tokens.getText(ctx.type());

			if (variableType.contains("[")) {

			}

		}
		if (ctx.interfaceMethodOrFieldRest().constantDeclaratorsRest() != null) {

			for (ConstantDeclaratorContext constantDeclaratorContext : ctx.interfaceMethodOrFieldRest()
					.constantDeclaratorsRest().constantDeclarator())

			{
				Variable variable = new Variable();
				variable.setName(constantDeclaratorContext.Identifier().getText());
				variable.setType(variableType);
				variable.setModifier(this.modifier);
				this.classDefinition.addVariable(constantDeclaratorContext.Identifier().getText(), variable);

			}

		}

		if (ctx.interfaceMethodOrFieldRest().interfaceMethodDeclaratorRest() != null) {

			InterfaceMethodDeclaratorRestContext formalParametersContext = ctx.interfaceMethodOrFieldRest()
					.interfaceMethodDeclaratorRest();
			String formalArgs = tokens.getText(formalParametersContext.formalParameters());
			this.classDefinition.addMethodIdentifierSignature(ctx.Identifier().getText(),
					"\t" + this.modifier + " " + ctx.Identifier().getText() + formalArgs + " : " + variableType);
			this.classDefinition.addMethodSignature(
					"\t" + this.interfaceModifier + " " + identifier + formalArgs + " : " + variableType);

		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterInterfaceMemberDecl(JavaParser.InterfaceMemberDeclContext ctx) {
		TokenStream tokens = parser.getTokenStream();
		if (ctx.Identifier() != null) {
			String identifier = ctx.Identifier().getText();
			String formalArgs = tokens.getText(ctx.voidInterfaceMethodDeclaratorRest().formalParameters());
			this.classDefinition.addMethodIdentifierSignature(ctx.Identifier().getText(),
					"\t" + this.modifier + " " + ctx.Identifier().getText() + formalArgs + " : " + "void");

			this.classDefinition
					.addMethodSignature("\t" + this.interfaceModifier + " " + identifier + formalArgs + " : " + "void");
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterInterfaceGenericMethodDecl(JavaParser.InterfaceGenericMethodDeclContext ctx) {
		TokenStream tokens = parser.getTokenStream();
		String typeParameters = tokens.getText(ctx.typeParameters());
		String type;
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
		} else {
			type = "void";
		}

		String identifier = ctx.Identifier().getText();
		String formalArgs = tokens.getText(ctx.interfaceMethodDeclaratorRest().formalParameters());
		this.classDefinition
				.addMethodSignature("\t" + this.interfaceModifier + " " + identifier + formalArgs + " : " + type);

	}
}
