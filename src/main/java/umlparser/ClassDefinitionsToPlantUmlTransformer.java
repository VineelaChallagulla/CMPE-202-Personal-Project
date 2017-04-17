package umlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*@startuml
package Classic <<Folder>> {
class Dummy {
  String data
  void methods()
}
}
package paa <<Folder>> {
class Flight {
   flightNumber : Integer
   departureTime : Date
}
}
List <|-- AbstractList
Collection <|-- AbstractCollection
 Dummy --> "1" Flight
@enduml*/

/*
@startuml
package umlparser {
class Bird {
	void fly(int varA, str[] B)
	void walk(int varC, str[] D)

varable1 : int
list : String
}
}
@enduml

*/

public class ClassDefinitionsToPlantUmlTransformer {	
	
	private static final String AGGREGATION = "0--";
	private static final String COMPOSITION = "*--";
	private static final String EXTENDS = "---|>";
	private static final String IMPLEMENTS = "....|>";
	
	
	
	public String getTransformedClassDefinition(ClassDefinition classDefinition){		
		StringBuffer buffer = new StringBuffer("@startuml"+"\n");
		buffer.append("package " + classDefinition.getPackageName()+ " {" +"\n");		
		buffer.append("class " + classDefinition.getName() + " {"+ "\n");		
		buffer.append(getMethodSyntax(classDefinition.getMethodSignatures())+ "\n");
		buffer.append(getVariablesSyntax(classDefinition.getName()) );
		buffer.append("}"+"\n");
		buffer.append("}"+"\n");
		if (classDefinition.getExtendsClassName() != null){
			buffer.append(getExtendsSyntax(classDefinition));
		}
		
		if (!classDefinition.getImplementInterfaceNames().isEmpty()){
			buffer.append(getImplementsSyntax(classDefinition));
		}
		
		
		buffer.append("@enduml"+ "\n");
		
		
		return buffer.toString();	
		
	}
	
	public String  getVariablesSyntax(String name){
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();
		
		Map<String, List<Association>> map = classRelations.getAggregationMap();
		List<Association> associations = map.get(name) ;
		for(Association association: associations)
		
		{
			
			buffer.append(association.getName()+" : "+ association.getType()+ "\n");
		}
	
		return buffer.toString();
		
	}
	
	public String  getMethodSyntax(ArrayList<String> methodList){
		StringBuffer buffer = new StringBuffer();

		for (String methodSignature : methodList){
			buffer.append(methodSignature).append("\n");
		}
	
		return buffer.toString();
		
	}
	
	public String  getExtendsSyntax( ClassDefinition classDefinition){
		StringBuffer buffer = new StringBuffer(classDefinition.getName());
		
		buffer.append(EXTENDS + classDefinition.getExtendsClassName() );

		return buffer.toString();
		
	}
	
	public String  getImplementsSyntax( ClassDefinition classDefinition){
		StringBuffer buffer = new StringBuffer();
		
		for (String interfaceName: classDefinition.getImplementInterfaceNames()){
			
			buffer.append(classDefinition.getName() + " "+IMPLEMENTS+  " "+  interfaceName + "\n");
		}

		return buffer.toString();
		
	}
	
}
