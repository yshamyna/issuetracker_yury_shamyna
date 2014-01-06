package org.training.issuetracker.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Issue {
	private long id;
	private Date createDate;
	private User createdBy;
	private Date modifyDate;
	private User modifyBy;
	private String summary;
	private String description;
	private IssueStatus status;
	private IssueType type;
	private IssuePriority priority;
	private Project project;
	private Build buildFound;
	private User assignee;
	
	public Issue() {
		super();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
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

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public IssueType getType() {
		return type;
	}

	public void setType(IssueType type) {
		this.type = type;
	}

	public IssuePriority getPriority() {
		return priority;
	}

	public void setPriority(IssuePriority priority) {
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, YYYY h:mm a");
	    return dateFormat.format(modifyDate);
	}
	
	public String getFormatCreatedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, YYYY h:mm a");
	    return dateFormat.format(createDate);
	}
}
