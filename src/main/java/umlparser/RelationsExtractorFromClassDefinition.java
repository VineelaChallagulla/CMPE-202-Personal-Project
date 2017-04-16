package umlparser;

public class RelationsExtractorFromClassDefinition {

	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double
	}

	public ClassRelations extractRelations(ClassDefinition classDefinition) {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		classRelations.addImplementationRelation(classDefinition.getName(),
				classDefinition.getImplementInterfaceNames());
		classRelations.addextendsRelation(classDefinition.getName(), classDefinition.getExtendsClassName());

		return classRelations;

	}

}
