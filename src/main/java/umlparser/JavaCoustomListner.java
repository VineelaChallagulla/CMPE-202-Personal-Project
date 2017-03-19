package umlparser;

import cmpe202.project.Java8BaseListener;
import cmpe202.project.Java8Parser;

public class JavaCoustomListner extends Java8BaseListener {
	
    Java8Parser parser;
    public JavaCoustomListner(Java8Parser parser) {this.parser = parser;}
	
	@Override public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		
		
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
		System.out.println("interface I"+ctx.Identifier()+" {");
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
		System.out.println("}");
	}
	
	
}
