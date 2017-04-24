package umlparser;

public class Link {
	
	private String source;
	private String target;
	private String source_number;
	private String target_number;
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
	public String getSource_number() {
		return source_number;
	}
	public void setSource_number(String source_number) {
		this.source_number = source_number;
	}
	public String getTarget_number() {
		return target_number;
	}
	public void setTarget_number(String target_number) {
		this.target_number = target_number;
	}
	@Override
	public String toString() {
		return "Link [source=" + source + ", target=" + target + ", source_number=" + source_number + ", target_number="
				+ target_number + "]";
	}

}
