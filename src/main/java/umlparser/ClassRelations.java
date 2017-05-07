package umlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ClassRelations {
	INSTANCE;

	private Map<String, List<String>> implementsMap = new HashMap<>();

	private Map<String, List<Association>> compositionMap = new HashMap<>();

	private Map<String, List<Association>> aggregationMap = new HashMap<>();

	private Map<String, List<Association>> associationMap = new HashMap<>();

	private Map<String, String> extendsMap = new HashMap<>();

	private List<String> allInterfaces = new ArrayList<>();

	private Map<LinkKey, Link> links = new HashMap<>();
	private Map<LinkKey, Link> aggregationLinks = new HashMap<>();
	private Map<LinkKey, Link> compositionLinks = new HashMap<>();

	void addImplementationRelation(String className, List<String> implementedClasses) {
		implementsMap.put(className, implementedClasses);

	}

	void addCompositionRelation(String className, List<Association> compositionRelation) {
		compositionMap.put(className, compositionRelation);

	}

	void addAggregationRelation(String className, List<Association> aggregationRelation) {
		aggregationMap.put(className, aggregationRelation);

	}

	void addAssociationRelation(String className, List<Association> associationRelation) {
		associationMap.put(className, associationRelation);

	}

	void addExtendsRelation(String className, String extendedClass) {
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

	public Map<String, List<Association>> getAssociationMap() {
		return associationMap;
	}

	public void setAssociationMap(Map<String, List<Association>> associationMap) {
		this.associationMap = associationMap;
	}

	public Map<LinkKey, Link> getAggregationLinks() {
		return aggregationLinks;
	}

	public Map<LinkKey, Link> getCompositionLinks() {
		return compositionLinks;
	}

	public void addCompositionLinks(LinkKey linkKey, Link link) {
		this.compositionLinks.put(linkKey, link);
	}

	public void addAggregationLinks(LinkKey linkKey, Link link) {
		this.aggregationLinks.put(linkKey, link);
	}

	public List<String> getAllInterfaces() {
		return allInterfaces;
	}

	public void setAllInterfaces(List<String> allInterfaces) {
		this.allInterfaces = allInterfaces;
	}

	public void addInterfaces(String interfaceName) {
		this.allInterfaces.add(interfaceName);
	}

}
