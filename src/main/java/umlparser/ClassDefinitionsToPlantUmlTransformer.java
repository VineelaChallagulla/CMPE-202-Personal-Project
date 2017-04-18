package umlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double
	}

	
	
	public String getTransformedClassDefinition(ClassDefinition classDefinition){		
		StringBuffer buffer = new StringBuffer("@startuml"+"\n");
		if(classDefinition.getPackageName() !=null && classDefinition.getPackageName().isEmpty()){
			buffer.append("package " + classDefinition.getPackageName()+ " {" +"\n");	
		}			
		buffer.append("class " + classDefinition.getName() + " {"+ "\n");		
		buffer.append(getMethodSyntax(classDefinition.getMethodSignatures())+ "\n");
		buffer.append(getVariablesSyntax(classDefinition.getName()) );
		
		if(classDefinition.getPackageName() !=null && classDefinition.getPackageName().isEmpty()){
			buffer.append("}"+"\n");	
		}	
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
			if (isPrimitive(association.getType())){
			buffer.append(association.getName()+" : "+ association.getType()  );
			if(association.getNumber() != null){
				buffer.append(" ("  +association.getNumber()+ ")");
				
			}
			buffer.append("\n");
			}
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
	private boolean isPrimitive(String type){
		boolean isPrimitive = false;
		for(Primitive primitive:Primitive.values()){
			if(primitive.toString().equalsIgnoreCase(type)){
				isPrimitive =true;
			}
			
		}
		return isPrimitive;
	}
	
	public String  getAssociations( ClassDefinition classDefinition){
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();
		
		
		Map<String, List<Association>> map = classRelations.getAggregationMap();
		
		for(Entry<String, List<Association>> entry: map.entrySet()){
			
			for(Association asociation: entry.getValue()){
				Link link= new Link();
				LinkKey linkKey= new LinkKey();
				link.setSource(entry.getKey());
				link.setTarget(asociation.getName());
				link.setTarget_number(asociation.getNumber());
				linkKey.setSource(entry.getKey());
				linkKey.setTarget(asociation.getName());
				
				if(classRelations.getLinks().get(linkKey) == null){
					classRelations.getLinks().put(linkKey, link);
				}else{
					link = classRelations.getLinks().get(linkKey);
					link.setTarget_number(asociation.getNumber());
					classRelations.getLinks().put(linkKey, link);
				
			}
			
		}

		
		
			
			
	}
		/*Class01 "1" *-- "many" Class028*/
		for (Link link:classRelations.getLinks().values()){
			
			
		}
		return buffer
	
}
}
