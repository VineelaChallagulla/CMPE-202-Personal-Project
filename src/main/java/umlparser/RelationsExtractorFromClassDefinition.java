package umlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelationsExtractorFromClassDefinition {
	
	private static final String  ARRAY_REGEX = "(\\w*)\\s*\\[\\s*(\\d*)\\s*\\]";
	private static final Pattern ARRAY_PATTERN =  Pattern.compile(ARRAY_REGEX);
	
	private static final String  GENERIC_REGEX = "(\\w*)\\s*<\\s*(\\w*)\\s*>";
	private static final Pattern GENERIC_PATTERN =  Pattern.compile(GENERIC_REGEX);

	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double
	}

	public ClassRelations extractRelations(ClassDefinition classDefinition) {
		ClassRelations classRelations = ClassRelations.INSTANCE;
		classRelations.addImplementationRelation(classDefinition.getName(),
				classDefinition.getImplementInterfaceNames());
		classRelations.addExtendsRelation(classDefinition.getName(), classDefinition.getExtendsClassName());
		setAggregationRelation(classDefinition,  classRelations);
		return classRelations;

	}
	
	private void setAggregationRelation(ClassDefinition classDefinition, ClassRelations classRelations){		
		 List<Association>  aggregationRelationList = new ArrayList<>();
		for (Entry<String, String> entry: classDefinition.getVariables().entrySet()){
			Association association = new Association();
			Matcher arrayMatcher = ARRAY_PATTERN.matcher(entry.getValue().toString());
			if(arrayMatcher.matches()){
				String number = null;
				String type = arrayMatcher.group(1);
				if(arrayMatcher.group(2) != null){
					number =  arrayMatcher.group(2);
				}else{
					
					number = "*";
				}
				association.setType(type);
				association.setNumber(number);
				association.setName(entry.getKey());

			}else{
			Matcher genericMatcher = GENERIC_PATTERN.matcher(entry.getValue().toString());
			if(genericMatcher.matches()){
				String type; 
				if(genericMatcher.group(2) != null){
				type = genericMatcher.group(2);
				association.setType(type);
				association.setNumber("*");
				association.setName(entry.getKey());
				}			

			}
			}
			aggregationRelationList.add(association);
		}
		classRelations.addAggregationRelation(classDefinition.getName(), aggregationRelationList);
	}

}
