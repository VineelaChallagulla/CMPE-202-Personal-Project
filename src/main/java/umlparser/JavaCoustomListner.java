package umlparser;

import org.antlr.v4.runtime.TokenStream;

import cmpe202.project.Java8BaseListener;
import cmpe202.project.Java8Parser;
import cmpe202.project.Java8Parser.FormalParameterListContext;
import cmpe202.project.Java8Parser.MethodDeclaratorContext;
import cmpe202.project.Java8Parser.MethodHeaderContext;

public class JavaCoustomListner extends Java8BaseListener {
	
    Java8Parser parser;
    public JavaCoustomListner(Java8Parser parser) {this.parser = parser;}
	
	@Override public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		
		MethodHeaderContext methodHeader =  ctx.methodHeader();
		MethodDeclaratorContext methodDeclarator = methodHeader.methodDeclarator();
		String methodName = methodDeclarator.Identifier().getText();
		System.out.println(methodName+"()");
		
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		
		
		
		
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
	
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) { }
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
		System.out.println("class "+ctx.Identifier()+" {");
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
		System.out.println("}");
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMethodModifier(Java8Parser.MethodModifierContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMethodModifier(Java8Parser.MethodModifierContext ctx) { }
	
	
}
