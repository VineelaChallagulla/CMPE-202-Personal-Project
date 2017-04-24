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
class ConcreteDecoratorB {
	String operation
	String addedBehaviorString in

}
ConcreteDecoratorB---|>Decorator
interface Component << (Interface) >> {

}
class ConcreteComponent {
	String operation

}
ConcreteComponent ....|> Component
class Decorator {
	String operation

}
Decorator ....|> Component
class Tester {
	void mainString[] args

}
class ConcreteDecoratorA {
	String operation
	String addedBehaviorString in

}
ConcreteDecoratorA---|>Decorator
Tester "1" ---- "1" Component
ConcreteDecoratorB "1" ---- "1" String
Tester "1" ---- "1" String
Decorator "1" ---- "1" Component
ConcreteDecoratorA "1" ---- "1" String
Tester "1" -- "1" args
ConcreteDecoratorA "1" -- "1" in
ConcreteDecoratorB "1" -- "1" in
ConcreteDecoratorB "1" *-- "1" Component
Decorator "1" *-- "1" Component
ConcreteDecoratorA "1" *-- "1" Component
@enduml




 */

public class ClassDefinitionsToPlantUmlTransformer {

	private static final String AGGREGATION = "..>";
	private static final String COMPOSITION = "..>";
	private static final String EXTENDS = "---|>";
	private static final String IMPLEMENTS = "..|>";
	private static final String ASSOSCIATION = "--";
	private static final String BI_ASSOSCIATION = "--";

	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double, String
	}

	public String getUmlSyntax(List<ClassDefinition> listOfClassDefinitions) {
		RelationsExtractorFromClassDefinition relationsExtractorFromClassDefinition = new RelationsExtractorFromClassDefinition();
		ClassDefinitionsToPlantUmlTransformer classDefinitionsToPlantUmlTransformer = new ClassDefinitionsToPlantUmlTransformer();
		StringBuffer stringBuffer = new StringBuffer("@startuml" + "\n");
		stringBuffer.append("skinparam ").append("classAttributeIconSize ").append("0").append("\n");
		for (ClassDefinition classDefinition : listOfClassDefinitions) {

			ClassRelations classRelations = relationsExtractorFromClassDefinition.extractRelations(classDefinition);
			stringBuffer.append(classDefinitionsToPlantUmlTransformer.getTransformedClassDefinition(classDefinition));

			// classDefinitionsToPlantUmlTransformer.getTransformedClassDefinition(classDefinition);
			// System.out.println(classDefinition.toString());
		}
		stringBuffer.append(getAssociations());
		stringBuffer.append(getAggregations());
		stringBuffer.append(getCompositions());
		stringBuffer.append("@enduml" + "\n");
		return stringBuffer.toString();

	}

	public String getTransformedClassDefinition(ClassDefinition classDefinition) {
		StringBuffer buffer = new StringBuffer();
		if (classDefinition.getPackageName() != null && classDefinition.getPackageName().isEmpty()) {
			buffer.append("package " + classDefinition.getPackageName() + " {" + "\n");
		}
		if (!classDefinition.isInterface()) {
			/* class System << (S,#FF7700) Singleton >> */
			buffer.append("class " + classDefinition.getName());
			buffer.append(" {" + "\n");

		} else if (classDefinition.isInterface()) {

			/* class System << (S,#FF7700) Singleton >> */
			buffer.append("interface " + classDefinition.getName());
			buffer.append(" << (Interface) >>");
			buffer.append(" {" + "\n");
		}
		buffer.append(getMethodSyntax(classDefinition.getMethodSignatures()) + "\n");
		buffer.append(getVariablesSyntax(classDefinition.getVariables()));

		if (classDefinition.getPackageName() != null && classDefinition.getPackageName().isEmpty()) {
			buffer.append("}" + "\n");
		}
		buffer.append("}" + "\n");
		if (classDefinition.getExtendsClassName() != null) {
			buffer.append(getExtendsSyntax(classDefinition));
		}

		if (classDefinition.getImplementInterfaceNames() != null
				&& !classDefinition.getImplementInterfaceNames().isEmpty()) {
			buffer.append(getImplementsSyntax(classDefinition));
		}
		ClassRelations classRelations = ClassRelations.INSTANCE;
		Map<String, List<Association>> aggregationMap = classRelations.getAggregationMap();
		populateAggregations(classDefinition, aggregationMap, classRelations);
		Map<String, List<Association>> associationMap = classRelations.getAssociationMap();
		populateAssociations(classDefinition, associationMap, classRelations);
		Map<String, List<Association>> compositionMap = classRelations.getCompositionMap();
		populateCompositions(classDefinition, compositionMap, classRelations);

		return buffer.toString();

	}

	public String getVariablesSyntax(Map<String, String> variables) {
		StringBuffer buffer = new StringBuffer();

		for (Entry<String, String> variable : variables.entrySet()) {
			buffer.append(variable.getValue()).append(" : ").append(variable.getKey()).append("\n");
		}

		return buffer.toString();

	}

	public String getMethodSyntax(ArrayList<String> methodList) {
		StringBuffer buffer = new StringBuffer();

		for (String methodSignature : methodList) {
			buffer.append(methodSignature).append("\n");
		}

		return buffer.toString();

	}

	public String getExtendsSyntax(ClassDefinition classDefinition) {
		StringBuffer buffer = new StringBuffer(classDefinition.getName());

		buffer.append(EXTENDS + classDefinition.getExtendsClassName());
		buffer.append("\n");

		return buffer.toString();

	}

	public String getImplementsSyntax(ClassDefinition classDefinition) {
		StringBuffer buffer = new StringBuffer();

		for (String interfaceName : classDefinition.getImplementInterfaceNames()) {

			buffer.append(classDefinition.getName() + " " + IMPLEMENTS + " " + interfaceName + "\n");
		}

		return buffer.toString();

	}

	private boolean isPrimitive(String type) {
		boolean isPrimitive = false;
		for (Primitive primitive : Primitive.values()) {
			if (primitive.toString().equalsIgnoreCase(type)) {
				isPrimitive = true;
			}

		}
		return isPrimitive;
	}

	public void populateAssociations(ClassDefinition classDefinition, Map<String, List<Association>> map,
			ClassRelations classRelations) {

		StringBuffer buffer = new StringBuffer();

		for (Entry<String, List<Association>> entry : map.entrySet()) {

			for (Association association : entry.getValue()) {
				if (!isPrimitive(association.getType())) {

					Link link = new Link();
					LinkKey linkKey = new LinkKey();
					link.setSource(entry.getKey());
					link.setTarget(association.getType());
					link.setTarget_number(association.getNumber());
					linkKey.setSource(entry.getKey());
					linkKey.setTarget(association.getType());

					if (classRelations.getLinks().get(linkKey) == null) {
						System.out.println("first");
						System.out.println(link);
						classRelations.getLinks().put(linkKey, link);
					} else {
						link = classRelations.getLinks().get(linkKey);
						link.setSource_number(association.getNumber());
						System.out.println("second");
						System.out.println(link);

					}
				}
			}

		}

	}

	public void populateAggregations(ClassDefinition classDefinition, Map<String, List<Association>> map,
			ClassRelations classRelations) {

		StringBuffer buffer = new StringBuffer();

		for (Entry<String, List<Association>> entry : map.entrySet()) {

			for (Association association : entry.getValue()) {
				if (!isPrimitive(association.getType())) {

					Link link = new Link();
					LinkKey linkKey = new LinkKey();
					link.setSource(entry.getKey());
					link.setTarget(association.getType());
					link.setTarget_number(association.getNumber());
					linkKey.setSource(entry.getKey());
					linkKey.setTarget(association.getType());

					if (classRelations.getAggregationLinks().get(linkKey) == null) {
						System.out.println("first");
						System.out.println(link);
						classRelations.getAggregationLinks().put(linkKey, link);
					} else {
						link = classRelations.getAggregationLinks().get(linkKey);
						link.setSource_number(association.getNumber());
						System.out.println("second");
						System.out.println(link);

					}
				}
			}

		}

	}

	public void populateCompositions(ClassDefinition classDefinition, Map<String, List<Association>> map,
			ClassRelations classRelations) {

		StringBuffer buffer = new StringBuffer();

		for (Entry<String, List<Association>> entry : map.entrySet()) {

			for (Association association : entry.getValue()) {
				if (!isPrimitive(association.getType())) {

					Link link = new Link();
					LinkKey linkKey = new LinkKey();
					link.setSource(entry.getKey());
					link.setTarget(association.getType());
					link.setTarget_number(association.getNumber());
					linkKey.setSource(entry.getKey());
					linkKey.setTarget(association.getType());

					if (classRelations.getCompositionLinks().get(linkKey) == null) {
						System.out.println("first");
						System.out.println(link);
						classRelations.getCompositionLinks().put(linkKey, link);
					} else {
						link = classRelations.getCompositionLinks().get(linkKey);
						link.setSource_number(association.getNumber());
						System.out.println("second");
						System.out.println(link);

					}
				}
			}

		}

	}

	public String getAssociations() {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();

		/* Class01 "1" *-- "many" Class028 */
		for (Link link : classRelations.getLinks().values()) {
			if(link.getSource_number() !=null && link.getTarget_number() != null){
				buffer.append(link.getSource()).append(" ").append('"')
				.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
		buffer.append(BI_ASSOSCIATION).append(" ").append('"')
				.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
				.append(link.getTarget());
		buffer.append("\n");
				
			}else{
			buffer.append(link.getSource()).append(" ").append('"')
					.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
			buffer.append(ASSOSCIATION).append(" ").append('"')
					.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
					.append(link.getTarget());
			buffer.append("\n");
			}

		}
		return buffer.toString();

	}

	public String getAggregations() {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();

		/* Class01 "1" *-- "many" Class028 */
		for (Link link : classRelations.getAggregationLinks().values()) {
			if(link.getSource_number() !=null && link.getTarget_number() != null){
				buffer.append(link.getSource()).append(" ").append('"')
				.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
		buffer.append(BI_ASSOSCIATION).append(" ").append('"')
				.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
				.append(link.getTarget());
		buffer.append("\n");
		
				
			}else
			buffer.append(link.getSource()).append(" ").append('"')
					.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
			buffer.append(AGGREGATION).append(" ").append('"')
					.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
					.append(link.getTarget());
			buffer.append("\n");
			

		}
		return buffer.toString();

	}

	public String getCompositions() {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		StringBuffer buffer = new StringBuffer();

		/* Class01 "1" *-- "many" Class028 */
		for (Link link : classRelations.getCompositionLinks().values()) {
			if(link.getSource_number() !=null && link.getTarget_number() != null){
				buffer.append(link.getSource()).append(" ").append('"')
				
				.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
		buffer.append(BI_ASSOSCIATION).append(" ").append('"')
				.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
				.append(link.getTarget());
		buffer.append("\n");
				
			}else{
				buffer.append(link.getSource()).append(" ").append('"')
			
					.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"').append(" ");
			buffer.append(COMPOSITION).append(" ").append('"')
					.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
					.append(link.getTarget());
			buffer.append("\n");

		}
		

	}
		return buffer.toString();
}
}
