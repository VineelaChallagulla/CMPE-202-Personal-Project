package umlparser;

public class LinkKey {

	private String source;
	private String target;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode())
				+ ((target == null) ? 0 : target.hashCode());
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
		LinkKey other = (LinkKey) obj;
		if (other.source.equals(source) && other.target.equals(target)
				|| other.target.equals(source) && other.source.equals(target)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return "LinkKey [source=" + source + ", target=" + target + "]";
	}

}
