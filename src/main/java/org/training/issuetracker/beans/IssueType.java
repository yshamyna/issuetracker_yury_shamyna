package org.training.issuetracker.beans;

public class IssueType {
	private long id;
	private String value;
	
	public IssueType() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
