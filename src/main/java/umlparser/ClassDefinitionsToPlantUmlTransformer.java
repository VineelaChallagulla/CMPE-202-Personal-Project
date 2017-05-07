package umlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ClassDefinitionsToPlantUmlTransformer {

	private static final String AGGREGATION = "..>";
	private static final String COMPOSITION = "..>";
	private static final String EXTENDS = "---|>";
	private static final String IMPLEMENTS = "..|>";
	private static final String ASSOSCIATION = "--";
	private static final String BI_ASSOSCIATION = "--";
	private static final String ARRAY_REGEX = "(\\w*)\\s*\\[\\s*(\\d*)\\s*\\]";
	private static final Pattern ARRAY_PATTERN = Pattern.compile(ARRAY_REGEX);

	private static final String GENERIC_REGEX = "(\\w*)\\s*<\\s*(\\w*)\\s*>";
	private static final Pattern GENERIC_PATTERN = Pattern.compile(GENERIC_REGEX);

	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double, String
	}

	public String getUmlSyntax(List<ClassDefinition> listOfClassDefinitions) {
		RelationsExtractorFromClassDefinition relationsExtractorFromClassDefinition = new RelationsExtractorFromClassDefinition();
		ClassDefinitionsToPlantUmlTransformer classDefinitionsToPlantUmlTransformer = new ClassDefinitionsToPlantUmlTransformer();
		StringBuffer stringBuffer = new StringBuffer("@startuml" + "\n");
		stringBuffer.append("skinparam ").append("classAttributeIconSize ").append("0").append("\n");

		for (ClassDefinition classDefinition : listOfClassDefinitions) {

			ClassRelations classRelations = ClassRelations.INSTANCE;
			for (String interfaceName : classDefinition.getImplementInterfaceNames()) {
				classRelations.addInterfaces(interfaceName);
			}

		}
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
		modifyVisibilityForJavaSetterGetters(classDefinition);
		buffer.append(getMethodSyntax(new ArrayList<String>(classDefinition.getMethodIdentifierSignatureMap().values()))
				+ "\n");
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

	public String getVariablesSyntax(Map<String, Variable> variables) {
		StringBuffer buffer = new StringBuffer();

		for (Entry<String, Variable> variable : variables.entrySet()) {
			getMutiplicity(variable.getValue());
			if (variable.getValue().getModifier().equals("-") || variable.getValue().getModifier().equals("+")) {
				buffer.append(variable.getValue().getModifier()).append(variable.getValue().getName()).append(" : ")
						.append(variable.getValue().getType()).append("\n");
			}
		}

		return buffer.toString();

	}

	public void modifyVisibilityForJavaSetterGetters(ClassDefinition classDefinition) {
		for (Entry<String, Variable> entry : classDefinition.getVariables().entrySet()) {
			Boolean isGetMethod = false;
			Boolean isSetMethod = false;

			String getMethod = "get" + entry.getValue().getName();
			String settMethod = "set" + entry.getValue().getName();

			for (Entry<String, String> methodEntry : classDefinition.getMethodIdentifierSignatureMap().entrySet()) {
				if (methodEntry.getKey().equalsIgnoreCase(getMethod)) {
					isGetMethod = true;

				} else if (methodEntry.getKey().equalsIgnoreCase(settMethod)) {
					isSetMethod = true;

				}
				if (isGetMethod || isSetMethod) {
					classDefinition.getMethodIdentifierSignatureMap().remove(methodEntry.getKey());

					entry.getValue().setModifier("+");
				}

			}

		}
	}

	public String getMethodSyntax(ArrayList<String> methodList) {
		StringBuffer buffer = new StringBuffer();

		for (String methodSignature : methodList) {
			if (methodSignature.contains("+")) {
				buffer.append(methodSignature).append("\n");
			}
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
						classRelations.getLinks().put(linkKey, link);
					} else {
						link = classRelations.getLinks().get(linkKey);
						link.setSource_number(association.getNumber());
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
						classRelations.getAggregationLinks().put(linkKey, link);
					} else {
						link = classRelations.getAggregationLinks().get(linkKey);
						link.setSource_number(association.getNumber());

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

						classRelations.getCompositionLinks().put(linkKey, link);
					} else {
						link = classRelations.getCompositionLinks().get(linkKey);
						link.setSource_number(association.getNumber());
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
			if (link.getSource_number() != null && link.getTarget_number() != null) {
				buffer.append(link.getSource()).append(" ").append('"')
						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
				buffer.append(BI_ASSOSCIATION).append(" ").append('"')
						.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
						.append(link.getTarget());
				buffer.append("\n");

			} else {
				buffer.append(link.getSource()).append(" ").append('"')
						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
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
			if (link.getSource_number() != null && link.getTarget_number() != null) {
				buffer.append(link.getSource()).append(" ").append('"')
						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
				buffer.append(BI_ASSOSCIATION).append(" ").append('"')
						.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
						.append(link.getTarget());
				buffer.append("\n");

			} else
				buffer.append(link.getSource()).append(" ").append('"')
						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
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
			if (link.getSource_number() != null && link.getTarget_number() != null) {
				buffer.append(link.getSource()).append(" ").append('"')

						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
				buffer.append(BI_ASSOSCIATION).append(" ").append('"')
						.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
						.append(link.getTarget());
				buffer.append("\n");

			} else {
				buffer.append(link.getSource()).append(" ").append('"')

						.append(link.getSource_number() != null ? link.getSource_number() : "1").append('"')
						.append(" ");
				buffer.append(COMPOSITION).append(" ").append('"')
						.append(link.getTarget_number() != null ? link.getTarget_number() : "1").append('"').append(" ")
						.append(link.getTarget());
				buffer.append("\n");

			}

		}
		return buffer.toString();
	}

	public void getMutiplicity(Variable variable) {
		Matcher genericMatcher = GENERIC_PATTERN.matcher(variable.getType());
		Matcher arrayMatcher = ARRAY_PATTERN.matcher(variable.getType());
		if (arrayMatcher.matches()) {
			String number = null;
			String type = arrayMatcher.group(1);
			if (arrayMatcher.group(2) != null && !arrayMatcher.group(2).isEmpty()) {
				number = arrayMatcher.group(2);

			} else {

				number = "0--*";
			}
			if (number != null) {
				variable.setType(type + " " + number + " ");
			}

		} else if (genericMatcher.matches()) {
			String type;
			if (genericMatcher.group(2) != null) {
				type = genericMatcher.group(2);
				variable.setType(type + " 0--* ");

			}

		}
	}
}
