package umlparser;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassDefinition {
	private String packageName;
	private String name;
	private String extendsClassName;
	private ArrayList<String> implementInterfaceNames;
	private Map<String, Variable> variables = new ConcurrentHashMap<>();
	private Map<String, Variable> methodVariables = new ConcurrentHashMap<>();
	private Map<String, Variable> constructorVariables = new ConcurrentHashMap<>();
	private ArrayList<String> methodSignatures = new ArrayList<>();
	private ArrayList<String> methodIdentifiers = new ArrayList<>();

	private Map<String, String> methodIdentifierSignatureMap = new ConcurrentHashMap<String, String>();
	private boolean isInterface;

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

	public Map<String, Variable> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Variable> variables) {
		this.variables = variables;
	}

	public void addVariable(String idetifier, Variable variable) {
		this.variables.put(idetifier, variable);
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

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public Map<String, Variable> getConstructorVariables() {
		return constructorVariables;
	}

	public void setConstructorVariables(Map<String, Variable> constructorVariables) {
		this.constructorVariables = constructorVariables;
	}

	public void addConstructorVariables(String idetifier, Variable varable) {
		this.constructorVariables.put(idetifier, varable);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		return result;
	}

	public Map<String, Variable> getMethodVariables() {
		return methodVariables;
	}

	public void setMethodVariables(Map<String, Variable> methodVariables) {
		this.methodVariables = methodVariables;
	}

	public void addMethodVariables(String identifier, Variable varable) {
		this.methodVariables.put(identifier, varable);
	}

	public ArrayList<String> getMethodIdentifiers() {
		return methodIdentifiers;
	}

	public void addMethodIdentifier(String methodIdentifier) {
		this.methodIdentifiers.add(methodIdentifier);
	}

	public Map<String, String> getMethodIdentifierSignatureMap() {
		return methodIdentifierSignatureMap;
	}

	public void addMethodIdentifierSignature(String identifier, String signature) {
		this.methodIdentifierSignatureMap.put(identifier, signature);
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

	@Override
	public String toString() {
		return "ClassDefinition  packageName=" + packageName + ", name=" + name + ", extendsClassName="
				+ extendsClassName + ", implementInterfaceNames=" + implementInterfaceNames + ", variables=" + variables
				+ ", methodSignatures=" + methodSignatures;
	}

}
