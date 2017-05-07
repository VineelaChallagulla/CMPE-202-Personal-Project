package umlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelationsExtractorFromClassDefinition {

	private static final String ARRAY_REGEX = "(\\w*)\\s*\\[\\s*(\\d*)\\s*\\]";
	private static final Pattern ARRAY_PATTERN = Pattern.compile(ARRAY_REGEX);

	private static final String GENERIC_REGEX = "(\\w*)\\s*<\\s*(\\w*)\\s*>";
	private static final Pattern GENERIC_PATTERN = Pattern.compile(GENERIC_REGEX);

	public ClassRelations extractRelations(ClassDefinition classDefinition) {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		classRelations.addImplementationRelation(classDefinition.getName(),
				classDefinition.getImplementInterfaceNames());
		classRelations.addExtendsRelation(classDefinition.getName(), classDefinition.getExtendsClassName());
		setAssociationRelation(classDefinition, classRelations);
		setAggregationRelation(classDefinition, classRelations);

		setCompositionRelation(classDefinition, classRelations);
		return classRelations;

	}

	private void setAssociationRelation(ClassDefinition classDefinition, ClassRelations classRelations) {
		List<Association> associationList = new ArrayList<>();

		for (Entry<String, Variable> entry : classDefinition.getVariables().entrySet()) {
			setAssociation(associationList, entry);
		}

		classRelations.addAssociationRelation(classDefinition.getName(), associationList);
	}

	private void setAggregationRelation(ClassDefinition classDefinition, ClassRelations classRelations) {
		List<Association> aggregationList = new ArrayList<>();

		for (Entry<String, Variable> entry : classDefinition.getMethodVariables().entrySet()) {
			if (!classRelations.getAllInterfaces().isEmpty()
					&& classRelations.getAllInterfaces().contains(entry.getValue().getType())) {
				setAssociation(aggregationList, entry);
			}
		}

		classRelations.addAggregationRelation(classDefinition.getName(), aggregationList);
	}

	private void setCompositionRelation(ClassDefinition classDefinition, ClassRelations classRelations) {
		List<Association> compositionList = new ArrayList<>();

		for (Entry<String, Variable> entry : classDefinition.getConstructorVariables().entrySet()) {
			if (!classRelations.getAllInterfaces().isEmpty()
					&& classRelations.getAllInterfaces().contains(entry.getValue().getType())) {
				setAssociation(compositionList, entry);
			}
		}

		classRelations.addCompositionRelation(classDefinition.getName(), compositionList);
	}

	public void setAssociation(List<Association> associationList, Entry<String, Variable> entry) {
		Matcher genericMatcher = GENERIC_PATTERN.matcher(entry.getValue().getType());
		Association association = new Association();
		Matcher arrayMatcher = ARRAY_PATTERN.matcher(entry.getValue().getType());
		if (arrayMatcher.matches()) {
			String number = null;
			String type = arrayMatcher.group(1);
			if (arrayMatcher.group(2) != null && !arrayMatcher.group(2).isEmpty()) {
				number = arrayMatcher.group(2);
			} else {

				number = "*";
			}
			association.setType(type);
			association.setNumber(number);
			association.setName(entry.getValue().getName());

		} else if (genericMatcher.matches()) {
			String type;
			if (genericMatcher.group(2) != null) {
				type = genericMatcher.group(2);
				association.setType(type);
				association.setNumber("*");
				association.setName(entry.getValue().getName());
			}

		} else {
			association.setType(entry.getValue().getType());
			association.setName(entry.getKey());

		}
		associationList.add(association);
	}

}
