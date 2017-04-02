package umlparser;

public class ClassDefinitionsToPlantUmlTransformer {	
	
	public String getTransformedClassDefinition(ClassDefinition classDefinition){		
		StringBuffer buffer = new StringBuffer();
		buffer.append("package" + classDefinition.getPackageName()+ " "+"\n");		
		buffer.append("calss" + classDefinition.getName() + "{");		
		buffer.append(classDefinition.getMethodSignatures());
		buffer.append("}");			
		return buffer.toString();	
		
	}
}
