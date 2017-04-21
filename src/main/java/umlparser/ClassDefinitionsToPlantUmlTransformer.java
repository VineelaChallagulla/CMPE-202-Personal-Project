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



@startuml
class A {

x : int
y : int (many)
}
A 0 *-- many b
A 0 *-- 0 c
A 0 *-- many d
A 0 *-- 0 x
A 0 *-- many y
@enduml

*/

public class ClassDefinitionsToPlantUmlTransformer {	
	
	private static final String AGGREGATION = "0--";
	private static final String COMPOSITION = "*--";
	private static final String EXTENDS = "---|>";
	private static final String IMPLEMENTS = "....|>";
	private static final String ASSOSCIATION = "----";
	
	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double
	}

	
	public String getUmlSyntax(List<ClassDefinition> listOfClassDefinitions){	
		RelationsExtractorFromClassDefinition relationsExtractorFromClassDefinition = 
				new RelationsExtractorFromClassDefinition();
		ClassDefinitionsToPlantUmlTransformer classDefinitionsToPlantUmlTransformer = new ClassDefinitionsToPlantUmlTransformer();
		StringBuffer stringBuffer = new StringBuffer("@startuml"+"\n");
		for (ClassDefinition classDefinition : listOfClassDefinitions) {
			
			ClassRelations classRelations = relationsExtractorFromClassDefinition.extractRelations(classDefinition);	
			stringBuffer.append(classDefinitionsToPlantUmlTransformer.getTransformedClassDefinition(classDefinition));

//			classDefinitionsToPlantUmlTransformer.getTransformedClassDefinition(classDefinition);
//			System.out.println(classDefinition.toString());
		}
		stringBuffer.append(getAssociations());
		stringBuffer.append("@enduml"+ "\n");
		return stringBuffer.toString();
		
	}
	
	
	public String getTransformedClassDefinition(ClassDefinition classDefinition){		
		StringBuffer buffer = new StringBuffer();
		if(classDefinition.getPackageName() !=null && classDefinition.getPackageName().isEmpty()){
			buffer.append("package " + classDefinition.getPackageName()+ " {" +"\n");	
		}
		if(!classDefinition.isInterface()){
		/*class System << (S,#FF7700) Singleton >>*/
		buffer.append("class " + classDefinition.getName());
		buffer.append(" {"+ "\n");
		
		}
		else if(classDefinition.isInterface()){
			
			/*class System << (S,#FF7700) Singleton >>*/
			buffer.append("interface " + classDefinition.getName());
			buffer.append(" << (Interface) >>");
			buffer.append(" {"+ "\n");
		}
		buffer.append(getMethodSyntax(classDefinition.getMethodSignatures())+ "\n");
		buffer.append(getVariablesSyntax(classDefinition.getName()) );
		
		if(classDefinition.getPackageName() !=null && classDefinition.getPackageName().isEmpty()){
			buffer.append("}"+"\n");	
		}	
		buffer.append("}"+"\n");
		if (classDefinition.getExtendsClassName() != null){
			buffer.append(getExtendsSyntax(classDefinition));
		}
		
		if (classDefinition.getImplementInterfaceNames() !=null &&!classDefinition.getImplementInterfaceNames().isEmpty()){
			buffer.append(getImplementsSyntax(classDefinition));
		}
		
		populateAssociations(classDefinition);
		
		
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
				buffer.append(   " "+association.getNumber() );
				
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
		buffer.append("\n");

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
	
	public void  populateAssociations( ClassDefinition classDefinition){
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();
		
		
		Map<String, List<Association>> map = classRelations.getAggregationMap();
		
		for(Entry<String, List<Association>> entry: map.entrySet()){
			
			for(Association association: entry.getValue()){
				if (!isPrimitive(association.getType())){
				
				Link link= new Link();
				LinkKey linkKey= new LinkKey();
				link.setSource(entry.getKey());
				link.setTarget(association.getType());
				link.setTarget_number(association.getNumber());
				linkKey.setSource(entry.getKey());
				linkKey.setTarget(association.getType());
				
				if(classRelations.getLinks().get(linkKey) == null){
					System.out.println("first");
					System.out.println(link);
					classRelations.getLinks().put(linkKey, link);
				}else{
					link = classRelations.getLinks().get(linkKey);
					link.setSource_number(association.getNumber());
					System.out.println("second");
					System.out.println(link);
					
				
			}
				}
		}

		
		
			
			
	}

	
}
	
	
	public String  getAssociations(  ){
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();
		
		
		/*Class01 "1" *-- "many" Class028*/
		for (Link link:classRelations.getLinks().values()){
			buffer.append(link.getSource()).append(" ").append('"').append(link.getSource_number() != null?link.getSource_number(): "1").append('"').append(" ");
			buffer.append(ASSOSCIATION).append(" ").append('"').append(link.getTarget_number()!= null? link.getTarget_number() : "1" ).append('"').append(" ").append(link.getTarget());
			buffer.append("\n");
			
		}
		return buffer.toString();
	
}
	
	
}
