package org.training.issuetracker.db.beans;


public class Type {
	private long id;
	private String name;
	
	public Type() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
