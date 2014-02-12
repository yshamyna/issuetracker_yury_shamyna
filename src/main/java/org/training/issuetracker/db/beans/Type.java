package org.training.issuetracker.db.beans;


public class Type {
	private long id;
	private String value;
	
	public Type() {
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
