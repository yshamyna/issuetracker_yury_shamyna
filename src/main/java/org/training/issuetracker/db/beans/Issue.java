package org.training.issuetracker.db.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.training.issuetracker.web.constants.GeneralConsants;

public class Issue {
	private long id;
	private Timestamp createDate;
	private User createBy;
	private Timestamp modifyDate;
	private User modifyBy;
	private String summary;
	private String description;
	private Status status;
	private Type type;
	private Priority priority;
	private Project project;
	private Build buildFound;
	private User assignee;
	private Resolution resolution;
	
	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Issue() {
		super();
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createdBy) {
		this.createBy = createdBy;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public User getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(User modifyBy) {
		this.modifyBy = modifyBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Build getBuildFound() {
		return buildFound;
	}

	public void setBuildFound(Build buildFound) {
		this.buildFound = buildFound;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	
	public String getFormatModifyDate() {
	    return new SimpleDateFormat(GeneralConsants.FORMAT_DATE).
	    		format(modifyDate);
	}
	
	public String getFormatCreateDate() {
	    return new SimpleDateFormat(GeneralConsants.FORMAT_DATE).
	    		format(createDate);
	}
	
}
