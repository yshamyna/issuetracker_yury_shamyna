package org.training.issuetracker.db.beans;

public class Build {
	private long id;
	private String version;
	
	public Build() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
