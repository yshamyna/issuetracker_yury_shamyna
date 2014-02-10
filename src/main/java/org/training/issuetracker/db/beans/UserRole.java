package org.training.issuetracker.db.beans;

import org.training.issuetracker.db.interfaces.Entity;

public class UserRole implements Entity {
	private long id;
	private String value;
	
	public UserRole() {
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
