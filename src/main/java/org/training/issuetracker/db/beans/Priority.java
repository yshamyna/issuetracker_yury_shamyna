package org.training.issuetracker.db.beans;


public class Priority {
	private long id;
	private String value;
	
	public Priority() {
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
