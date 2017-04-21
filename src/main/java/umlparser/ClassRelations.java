package umlparser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum ClassRelations {
	INSTANCE;
	
	private Map<String, List<String>> implementsMap = new HashMap<>();
	
	private Map<String, List<Association>> compositionMap = new HashMap<>();
	
	private Map<String, List<Association>> aggregationMap = new HashMap<>();	
	
	private Map<String, String> extendsMap = new HashMap<>();
	
	private Map<LinkKey, Link > links = new HashMap<>();
	
	
	void addImplementationRelation(String className, List<String> implementedClasses){
		implementsMap.put(className, implementedClasses);	
		
	}
	
	void addCompositionRelation(String className, List<Association> compositionRelation){
		compositionMap.put(className, compositionRelation);	
		
	}
	
	
	
	void addAggregationRelation(String className, List<Association> aggregationRelation){
		aggregationMap.put(className, aggregationRelation);	
		
	}
	

	void addExtendsRelation(String className, String extendedClass){
		extendsMap.put(className, extendedClass);	
		
	}

	public Map<String, List<String>> getImplementsMap() {
		return implementsMap;
	}

	public void setImplementsMap(Map<String, List<String>> implementsMap) {
		this.implementsMap = implementsMap;
	}

	public Map<String, List<Association>> getCompositionMap() {
		return compositionMap;
	}

	public void setCompositionMap(Map<String, List<Association>> compositionMap) {
		this.compositionMap = compositionMap;
	}

	public Map<String, List<Association>> getAggregationMap() {
		return aggregationMap;
	}

	public void setAggregationMap(Map<String, List<Association>> aggregationMap) {
		this.aggregationMap = aggregationMap;
	}

	public Map<String, String> getExtendsMap() {
		return extendsMap;
	}

	public void setExtendsMap(Map<String, String> extendsMap) {
		this.extendsMap = extendsMap;
	}

	public Map<LinkKey, Link> getLinks() {
		return links;
	}

	public void setLinks(Map<LinkKey, Link> links) {
		this.links = links;
	}
	public void addLink(LinkKey linkKey, Link link) {
		this.links.put(linkKey, link);
	}

	



	
}
