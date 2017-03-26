package umlparser;

import java.util.ArrayList;

import org.antlr.v4.runtime.TokenStream;

import cmpe202.project.JavaBaseListener;
import cmpe202.project.JavaParser;
import cmpe202.project.JavaParser.TypeContext;
import cmpe202.project.JavaParser.VariableDeclaratorContext;

public class JavaCoustomListner extends JavaBaseListener {

	JavaParser parser;

	public JavaCoustomListner(JavaParser parser) {
		this.parser = parser;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation doesvftfv nothing.
	 * </p>
	 */
	@Override
	public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {

		System.out.println(ctx.qualifiedName().Identifier());
	}

	@Override
	public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) { // need
																					// parser
																					// to
																					// get
																					// tokens
		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
		}
		String args = tokens.getText(ctx.formalParameters());
		System.out.println("\t" + type + " " + ctx.Identifier() + args + ";");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String type = "void";
		if (ctx.type() != null) {
			type = tokens.getText(ctx.type());
		}

		ArrayList<String> implementList = new ArrayList<String>();
		if (ctx.typeList() != null) {
			for (TypeContext interfaceType : ctx.typeList().type()) {
				implementList.add(tokens.getText(interfaceType));
			}
		}

		System.out.println("class " + ctx.Identifier() + "extends" + type + "implements" + implementList + " {");

	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {

		System.out.println("}");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {

		TokenStream tokens = parser.getTokenStream();
		String variableType;
		if (ctx.type() != null) {

			variableType = tokens.getText(ctx.type());

			if (variableType.contains("[")) {

			}
			System.out.println(variableType);
		}

		for (VariableDeclaratorContext variableDeclaratorContext : ctx.variableDeclarators().variableDeclarator()) {
			System.out.println(variableDeclaratorContext.variableDeclaratorId().Identifier());

		}

	}

}
