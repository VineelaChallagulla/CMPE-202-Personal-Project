package umlparser;

import java.util.ArrayList;

public class ClassDefinition {
	private String packageName;
	private String name;
	private String extendsClassName;
	private String implementsInterfaceName;
	private ArrayList<String> variables = new ArrayList<String>();
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
	public String getImplementsInterfaceName() {
		return implementsInterfaceName;
	}
	public void setImplementsInterfaceName(String implementsInterfaceName) {
		this.implementsInterfaceName = implementsInterfaceName;
	}
	public ArrayList<String> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<String> variables) {
		this.variables = variables;
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
