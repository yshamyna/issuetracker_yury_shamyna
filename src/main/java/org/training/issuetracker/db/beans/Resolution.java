package org.training.issuetracker.db.beans;


public class Resolution {
	private long id;
	private String value;
	
	public Resolution() {
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
