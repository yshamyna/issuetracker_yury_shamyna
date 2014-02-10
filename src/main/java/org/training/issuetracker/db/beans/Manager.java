package org.training.issuetracker.db.beans;

import org.training.issuetracker.db.interfaces.Entity;

public class Manager implements Entity {
	private long id;
	private String firstName;
	private String lastName;
	
	public Manager() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
