package umlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassDefinition {
	private String packageName;
	private String name;
	private String extendsClassName;
	private ArrayList<String> implementInterfaceNames;
	private Map<String, String> variables = new HashMap<String, String>();
	private ArrayList<String> methodSignatures = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtendsClassName() {
		return extendsClassName;
	}

	public void setExtendsClassName(String extendsClassName) {
		this.extendsClassName = extendsClassName;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public void addVariable(String variable, String type) {
		this.variables.put(variable, type);
	}

	public ArrayList<String> getMethodSignatures() {
		return methodSignatures;
	}

	public void setMethodSignatures(ArrayList<String> methodSignatures) {
		this.methodSignatures = methodSignatures;
	}

	public void addMethodSignature(String methodSignature) {
		this.methodSignatures.add(methodSignature);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public ArrayList<String> getImplementInterfaceNames() {
		return implementInterfaceNames;
	}

	public void setImplementInterfaceNames(ArrayList<String> implementInterfaceNames) {
		this.implementInterfaceNames = implementInterfaceNames;
	}

	public void addImplementInterfaceName(String implementInterfaceName) {
		this.implementInterfaceNames.add(implementInterfaceName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassDefinition other = (ClassDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		return true;
	}
}
