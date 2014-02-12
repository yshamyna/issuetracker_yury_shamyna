package org.training.issuetracker.db.beans;

import java.sql.Timestamp;

public class Comment {
	private long id;
	private User sender;
	private long issueId;
	private String comment;
	private Timestamp createDate;
	
	public Comment() {
		super();
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
