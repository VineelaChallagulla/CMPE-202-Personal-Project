package umlparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRelations {
	
	private Map<String, List<String>> implementsMap = new HashMap<>();
	
	private Map<String, List<String>> containsMap = new HashMap<>();
	
	private Map<String, String> extendsMap = new HashMap<>();

	public Map<String, List<String>> getImplementsMap() {
		return implementsMap;
	}

	public void setImplementsMap(Map<String, List<String>> implementsMap) {
		this.implementsMap = implementsMap;
	}

	public Map<String, List<String>> getContainsMap() {
		return containsMap;
	}

	public void setContainsMap(Map<String, List<String>> containsMap) {
		this.containsMap = containsMap;
	}

	public Map<String, String> getExtendsMap() {
		return extendsMap;
	}

	public void setExtendsMap(Map<String, String> extendsMap) {
		this.extendsMap = extendsMap;
	}	
	
}
