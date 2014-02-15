package org.training.issuetracker.db.dao.service.constants;

public final class QueriesConstants {
	
	private QueriesConstants() {
		super();
	}
	
	public final static String ATTACHMENT_SELECT_BY_ISSUE_ID = 
			"select attachments.id as aid, addedBy, firstName, " 
			 + "lastName, addDate, filename from attachments, users " 
			 + "where addedBy=users.id and issueId=?";
	public final static String ATTACHMENT_ADD = 
			"insert into attachments(addedBy, addDate, "
			 + "filename, issueId) values(?,?,?,?)";
	
	public final static String BUID_SELECT_BY_ID = 
			"select projectId, version, isCurrent from builds where id=?";
	public final static String BUID_ADD = 
			"insert into builds(projectId, version, isCurrent) values(?, ?, ?)";
	public final static String BUID_SELECT_BY_PROJECT_ID =
			"select id, version, isCurrent from builds where projectId=?";
	public final static String BUILD_CHANGE_VERSION_PART_ONE = 
			"update builds set isCurrent=false where id=?";
	public final static String BUILD_CHANGE_VERSION_PART_TWO = 
			"update builds set isCurrent=true where id=?";
	public final static String BUILD_CURRENT = 
			"select id, version from builds where projectId=? and isCurrent=true";
	
	public final static String COMMENT_SELECT_BY_ISSUE_ID = 
			"select id, sender, comment, createDate from comments where issueId=?";
	public final static String COMMENT_ADD = 
			"insert into comments(sender, createDate, comment, " 
			 + "issueId) values(?, ?, ?, ?)";
	
	public final static String ISSUE_SELECT_BY_ID =
			"select createDate, createBy, modifyDate, modifyBy, " 
			 + "summary, description, statusId, typeId, priorityId, " 
			 + "projectId, buildId, assignee, resolutionId from " 
			 + "issues where id=?";
	
	public final static String ISSUE_ADD = 
			"insert into issues(createDate, createBy, modifyDate, " 
			 + "modifyBy, summary, description, statusId, typeId, " 
			 + "priorityId, projectId, buildId, assignee, resolutionId) " 
			 + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public final static String ISSUE_SELECT_BY_USER_ID = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, " 
			+ "summary, description, statusId, typeId, priorityId, " 
			+ "projectId, buildId, resolutionId from issues where assignee=?";
	public final static String ISSUE_UPDATE =
			"update issues set modifyDate=?, modifyBy=?, summary=?, "
			+ "description=?, statusId=?, typeId=?, priorityId=?, "
			+ "projectId=?, buildId=?, assignee=?, "
			+ "resolutionId=? where id=?";
	public final static String ISSUE_COUNT = 
			"select count(*) as cnt from issues where assignee=?";
	public final static String ISSUE_N_RECORDS_FROM_PAGE_M = 
			"select id, createDate, createBy, modifyDate, " 
			+ "modifyBy, summary, description, statusId, " 
			+ "typeId, priorityId, projectId, buildId, resolutionId " 
			+ "from issues where assignee=? order by id desc limit ? offset ?";
	public final static String ISSUE_LAST_N_RECORDS = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, " 
			+ "summary, description, statusId, typeId, priorityId, " 
			+ "projectId, buildId, assignee, resolutionId from issues " 
			+ "order by id desc limit ?";
	public final static String ISSUE_ALL_COUNT =
			"select count(*) as cnt from issues where assignee=?";
	
	public final static String MANAGER_SELECT_ALL = 
			"select id, firstName, lastName from managers";
	public final static String MANAGER_SELECT_BY_ID = 
			"select firstName, lastName from managers where id=?";
	
	public final static String PRIORITY_SELECT_ALL = 
			"select id, name from priorities";
	public final static String PRIORITY_SELECT_BY_ID = 
			"select name from priorities where id=?";
	public final static String PRIORITY_ADD = 
			"insert into priorities(name) values(?)";
	public final static String PRIORITY_UPDATE = 
			"update priorities set name=? where id=?";
	
	public final static String PROJECT_SELECT_ALL = 
			"select id, name, managerId, description from projects";
	public final static String PROJECT_SELECT_BY_ID = 
			"select name, description, managerId from projects where id=?";
	public final static String PROJECT_ADD = 
			"insert into projects(name, description, managerId) values(?, ?, ?)";
	public final static String PROJECT_GET_MAX_ID = 
			"select max(id) as lastProjectId from projects";
	public final static String PROJECT_UPDATE = 
			"update projects set name=?, description=?, managerId=? where id=?";
	public final static String PROJECT_COUNT = 
			"select count(*) as cnt from projects";
	public final static String PROJECT_N_RECORDS_FROM_PAGE_M = 
			"select projects.id as pid, name, description, " 
			+ "managerId, firstName, lastName from projects, " 
			+ "managers where managerId=managers.id order by pid desc limit ? offset ?";
	public final static String PROJECT_ALL_COUNT =
			"select count(*) as cnt from projects";
	
	public final static String RESOLUTION_SELECT_ALL =
			"select id, name from resolutions";
	public final static String RESOLUTION_SELECT_BY_ID =
			"select name from resolutions where id=?";
	public final static String RESOLUTION_ADD = 
			"insert into resolutions(name) values(?)";
	public final static String RESOLUTION_UPDATE = 
			"update resolutions set name=? where id=?";
	
	public final static String ROLE_SELECT_ALL = "select id, name from roles";
	public final static String ROLE_SELECT_BY_ID =
			"select name from roles where id=?";
	
	public final static String STATUS_SELECT_ALL = 
			"select id, name from statuses";
	public final static String STATUS_SELECT_BY_ID =
			"select name from statuses where id=?";
	public final static String STATUS_ADD = 
			"insert into statuses(name) values(?)";
	public final static String STATUS_UPDATE = 
			"update statuses set name=? where id=?";
		
	public final static String TYPE_SELECT_ALL = "select id, name from types";
	public final static String TYPE_SELECT_BY_ID =
			"select name from types where id=?";
	public final static String TYPE_ADD = "insert into types(name) values(?)";
	public final static String TYPE_UPDATE = 
			"update types set name=? where id=?";
	
	public final static String USER_SELECT_ALL =
			"select id, firstName, lastName, email, password, roleId from users";
	public final static String USER_SELECT_BY_ID =
			"select id, firstName, lastName, roleId, email, " 
			 + "password from users where id=?";
	public final static String USER_ADD = 
			"insert into users(firstName, lastName, email, "
			+ "roleId, password) values(?, ?, ?, ?, ?)";
	public final static String USER_SELECT_BY_EMAIL_AND_PASSWORD = 
			"select users.id as uid, firstName, lastName, name as role, " 
			+ "roleId from users, roles where email=? and password=? " 
			+ "and roleId=roles.id";
	public final static String USER_UPDATE_PASSWORD =
			"update users set password=? where id=?";
	public final static String USER_UPDATE = 
			"update users set firstName=?, lastName=?, "
			+ "email=?, roleId=? where id=?";
}
