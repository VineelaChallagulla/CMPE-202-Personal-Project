package umlparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ClassRelations {
	INSTANCE;
	
	private Map<String, List<String>> implementsMap = new HashMap<>();
	
	private Map<String, List<Map<String, Integer>>> compositionMap = new HashMap<>();
	
	private Map<String, List<Map<String, Integer>>> aggregationMap = new HashMap<>();	
	
	private Map<String, String> extendsMap = new HashMap<>();
	
	
	void addImplementationRelation(String className, List<String> implementedClasses){
		implementsMap.put(className, implementedClasses);	
		
	}
	
	void addCompositionRelation(String className, List<Map<String, Integer>> compositionRelation){
		compositionMap.put(className, compositionRelation);	
		
	}
	
	
	
	void aggregationRelation(String className, List<Map<String, Integer>> aggregationRelation){
		aggregationMap.put(className, aggregationRelation);	
		
	}
	

	void addextendsRelation(String className, String extendedClass){
		extendsMap.put(className, extendedClass);	
		
	}
	
}
