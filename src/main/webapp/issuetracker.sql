drop table if exists comments, issues, builds, projects, users, roles, statuses, types, resolutions, priorities, managers;

create table roles(
    id int primary key auto_increment, 
    name varchar(20) not null unique
);
create table resolutions(
    id int primary key auto_increment, 
    name varchar(20) not null unique
);
create table priorities(
    id int primary key auto_increment, 
    name varchar(20) not null unique
);
create table statuses(
    id int primary key auto_increment, 
    name varchar(20) not null unique
);
create table types(
    id int primary key auto_increment, 
    name varchar(20) not null unique
);
create table managers(
    id int primary key auto_increment, 
    firstName varchar(20) not null, 
    lastName varchar(20) not null
);
create table users(
    id int primary key auto_increment, 
    firstName varchar(20) not null, 
    lastName varchar(20) not null, 
    email varchar(50) not null, 
    roleId int not null references roles(id), 
    password varchar(32)
);
create table projects(
    id int primary key auto_increment, 
    name varchar(50) not null unique, 
    description varchar(4000) not null, 
    managerId int not null references managers(id)
);
create table builds(
    id int primary key auto_increment, 
    version varchar(20) not null, 
    projectId int not null references projects(id),
    isCurrent boolean not null
);
create unique index IND_BUILDS_VERSION_PROJECT on builds(version, projectId);
create table issues(
    id int primary key auto_increment, 
    createDate timestamp not null,
    createBy int not null references users(id),
    modifyDate timestamp not null,
    modifyBy int not null references users(id),
    summary varchar(2000) not null,
    description varchar(4000) not null,
    statusId int not null references statuses(id),
    typeId int not null references types(id),
    priorityId int not null references priorities(id),
    projectId int not null references projects(id),
    buildId int not null references builds(id),
    assignee int null references users(id),
    resolutionId int null references resolutions(id)
);
create table comments(
	id int primary key auto_increment, 
	sender int not null references users(id),
	issueId int not null references issues(id),
	comment varchar(4000) not null,
	createDate timestamp not null
);

insert into roles(name) values('administrator');
insert into roles(name) values('user');
insert into roles(name) values('guest');

insert into priorities(name) values('critical');
insert into priorities(name) values('major');
insert into priorities(name) values('important');
insert into priorities(name) values('minor');

insert into statuses(name) values('new');
insert into statuses(name) values('assigned');
insert into statuses(name) values('in progress');
insert into statuses(name) values('resolved');
insert into statuses(name) values('closed');
insert into statuses(name) values('reopened');

insert into resolutions(name) values('fixed');
insert into resolutions(name) values('invalid');
insert into resolutions(name) values('wontfix');
insert into resolutions(name) values('worksforme');

insert into types(name) values('cosmetic');
insert into types(name) values('bug');
insert into types(name) values('feature');
insert into types(name) values('perfomance');

insert into users(firstName, lastName, email, roleId, password)
    values('Main', 'Verymain', 'Main_Verymain@mail.com', 1, '1Ab@;');
insert into users(firstName, lastName, email, roleId, password)
    values('Sidr', 'Sidorov', 'Sidr_Sidorov@mail.com', 2, '2Bc%.');
insert into users(firstName, lastName, email, roleId, password)
    values('Ivan', 'Ivanov', 'Ivan_Ivanov@mail.com', 2, '3Cd$,');
insert into users(firstName, lastName, email, roleId, password)
    values('Petr', 'Petrov', 'Petr_Petrov@mail.com', 2, '4Cd@:');
    
insert into managers(firstName, lastName) values('John', 'Smith');
insert into managers(firstName, lastName) values('Tom', 'Adams');
insert into managers(firstName, lastName) values('Homer', 'Simpson');
insert into managers(firstName, lastName) values('Bob', 'Davis');
insert into managers(firstName, lastName) values('Ryan', 'Cooper');
insert into managers(firstName, lastName) values('Kyle', 'Garcia');
insert into managers(firstName, lastName) values('Amber', 'Diaz');

insert into projects(name, description, managerId)
    values('Outlook Web App', 'Outlook Web App (OWA)is a webmail service. Outlook Web App is used to access e-mail (including support for S/MIME), calendars, contacts, tasks, documents.', 1);
insert into projects(name, description, managerId)
    values('Timer', 'Digital counters that either increment or decrement at a fixed frequency.', 2);
insert into projects(name, description, managerId)
    values('Skype', 'Skype is a freemium voice-over-IP service and instant messaging client. The service allows users to communicate with peers by voice using a microphone, video by using a webcam, and instant messaging over the Internet.', 3);
insert into projects(name, description, managerId)
    values('GoogleTalk', 'Google Talk is an instant messaging service that provides both text and voice communication.', 4);
insert into projects(name, description, managerId)
    values('ICQ', 'ICQ is an instant messaging computer program.', 5);
    
/*To delete*/    
	insert into projects(name, description, managerId)
	    values('asdasda sdad ddf s', 'Outlook Web App (OWA)is a webmail service. Outlook Web App is used to access e-mail (including support for S/MIME), calendars, contacts, tasks, documents.', 1);
	insert into projects(name, description, managerId)
	    values('sdsdf  sd fsdfd', 'Digital counters that either increment or decrement at a fixed frequency.', 2);
	insert into projects(name, description, managerId)
	    values('dfgsdgdfgsfg', 'Skype is a freemium voice-over-IP service and instant messaging client. The service allows users to communicate with peers by voice using a microphone, video by using a webcam, and instant messaging over the Internet.', 3);
	insert into projects(name, description, managerId)
	    values('sdfgsdfgsdfhdfgsdfgfg', 'Google Talk is an instant messaging service that provides both text and voice communication.', 4);
	insert into projects(name, description, managerId)
	    values(' fdssds rs rs srsdr d', 'ICQ is an instant messaging computer program.', 5);
	insert into projects(name, description, managerId)
	    values('sdgf Web App', 'Outlook Web App (OWA)is a webmail service. Outlook Web App is used to access e-mail (including support for S/MIME), calendars, contacts, tasks, documents.', 1);
	insert into projects(name, description, managerId)
	    values('hgf fg df fd sdf d', 'Digital counters that either increment or decrement at a fixed frequency.', 2);
	insert into projects(name, description, managerId)
	    values('gd fgh df', 'Skype is a freemium voice-over-IP service and instant messaging client. The service allows users to communicate with peers by voice using a microphone, video by using a webcam, and instant messaging over the Internet.', 3);
	insert into projects(name, description, managerId)
	    values('saa32 3 ', 'Google Talk is an instant messaging service that provides both text and voice communication.', 4);
	insert into projects(name, description, managerId)
	    values('gd sdfg 4 adsf sdf wr', 'ICQ is an instant messaging computer program.', 5);
/*-------------------------------------------------------*/     

insert into builds(projectId, version, isCurrent) values(1, '0.1', false);
insert into builds(projectId, version, isCurrent) values(1, '0.2', false);
insert into builds(projectId, version, isCurrent) values(1, '0.3', false);
insert into builds(projectId, version, isCurrent) values(1, '0.4', false);
insert into builds(projectId, version, isCurrent) values(1, '0.5', false);
insert into builds(projectId, version, isCurrent) values(1, '0.6', false);
insert into builds(projectId, version, isCurrent) values(1, '0.7', false);
insert into builds(projectId, version, isCurrent) values(1, '0.8', false);
insert into builds(projectId, version, isCurrent) values(1, '0.9', false);
insert into builds(projectId, version, isCurrent) values(1, '0.9.1', true);
insert into builds(projectId, version, isCurrent) values(2, '0.1', false);
insert into builds(projectId, version, isCurrent) values(2, '0.2', false);
insert into builds(projectId, version, isCurrent) values(2, '0.3', false);
insert into builds(projectId, version, isCurrent) values(2, '0.4', true);
insert into builds(projectId, version, isCurrent) values(2, '0.5', false);
insert into builds(projectId, version, isCurrent) values(3, '0.1', false);
insert into builds(projectId, version, isCurrent) values(3, '0.2', false);
insert into builds(projectId, version, isCurrent) values(3, '0.3', false);
insert into builds(projectId, version, isCurrent) values(3, '0.4', false);
insert into builds(projectId, version, isCurrent) values(3, '0.5', false);
insert into builds(projectId, version, isCurrent) values(3, '0.6', false);
insert into builds(projectId, version, isCurrent) values(3, '0.7', false);
insert into builds(projectId, version, isCurrent) values(3, '0.8', false);
insert into builds(projectId, version, isCurrent) values(3, '0.9', true);
insert into builds(projectId, version, isCurrent) values(4, '0.1', false);
insert into builds(projectId, version, isCurrent) values(4, '0.2', true);
insert into builds(projectId, version, isCurrent) values(4, '0.3', false);
insert into builds(projectId, version, isCurrent) values(4, '0.4', false);
insert into builds(projectId, version, isCurrent) values(5, '0.1', false);
insert into builds(projectId, version, isCurrent) values(5, '0.2', false);
insert into builds(projectId, version, isCurrent) values(5, '0.3', false);
insert into builds(projectId, version, isCurrent) values(5, '0.4', false);
insert into builds(projectId, version, isCurrent) values(5, '0.5', false);
insert into builds(projectId, version, isCurrent) values(5, '0.6', false);
insert into builds(projectId, version, isCurrent) values(5, '0.7', true);
insert into builds(projectId, version, isCurrent) values(5, '0.8', false);

insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, 'Web browsing Error has occurred', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-1 10:28:45', 2, '2014-1-1 10:36:24', 2, 'Do not work increment at a fixed frequency', 'Do not work increment at a fixed frequency', 3, 2, 3, 2, 14, 2, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-1 10:32:25', 3, '2014-1-1 10:42:38', 3, 'Skype will not allow to send any messages', 'Skype will not allow to send any messages', 3, 3, 2, 3, 24, 3, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-2 10:04:12', 4, '2014-1-2 10:16:02', 4, 'Trouble signing in to Google Talk', 'Trouble signing in to Google Talk', 3, 4, 1, 4, 26, 4, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-2 10:12:42', 1, '2014-1-2 10:21:49', 1, 'Does not close the main window', 'Does not close the main window', 3, 1, 4, 5, 35, 1, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-2 10:23:23', 2, '2014-1-2 10:31:19', 2, 'Error = -2147205086', 'The browser goes through redirect loops and gives the error: Error=-2147205086 in the address bar, with a blank web page, user locked out and can not regain access.', 1, 2, 3, 1, 10, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-3 10:32:13', 3, '2014-1-3 10:42:42', 3, 'Do not work decrement at a fixed frequency', 'Do not work decrement at a fixed frequency', 1, 3, 2, 2, 14, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-3 10:41:55', 4, '2014-1-3 10:52:36', 4, 'Webcam not working on Skype', 'Webcam not working on Skype', 1, 4, 1, 3, 24, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-3 10:53:15', 1, '2014-1-3 10:57:18', 1, 'Can not change the window size', 'Can not change the window size', 1, 1, 4, 4, 26, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-4 10:13:55', 2, '2014-1-4 10:22:22', 2, 'Can not change the window size', 'Can not change the window size', 1, 2, 3, 5, 35, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-4 10:26:26', 3, '2014-1-4 10:31:31', 3, 'Error when user try login to the system', 'Error when user try login to the system', 1, 3, 2, 1, 10, null, null);
insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
values('2014-1-4 10:36:36', 4, '2014-1-4 10:41:41', 4, 'Do not work increment and decrement at a fixed frequency', 'Do not work increment and decrement at a fixed frequency', 1, 4, 1, 2, 14, null, null);


/*To delete*/ 
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '1', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '2', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '3', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '4', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '5', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '6', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '7', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '8', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '9', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '10', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '11', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '12', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '13', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '14', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '15', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '16', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '17', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '18', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '19', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '20', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '21', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '22', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '23', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
	insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) 
	values('2014-1-1 10:15:15', 1, '2014-1-1 10:16:42', 1, '24', 'Web browsing Error has occurred', 3, 1, 4, 1, 10, 1, null);
/*----------------------------------------------------------*/ 

insert into comments(sender, issueId, comment, createDate) values(1, 5, 'First comment', '2014-1-1 10:16:42');
insert into comments(sender, issueId, comment, createDate) values(1, 5, 'Second comment', '2014-1-2 8:56:14');
insert into comments(sender, issueId, comment, createDate) values(1, 5, 'Third comment', '2014-1-3 9:13:25');
insert into comments(sender, issueId, comment, createDate) values(1, 5, 'Fourth comment', '2014-1-4 17:37:36');
insert into comments(sender, issueId, comment, createDate) values(1, 5, 'Fifth comment', '2014-1-5 14:48:59');

drop user if exists administrator;
drop user if exists user;
drop user if exists guest;

create user administrator password 'Issue_tracker_administrator';
create user user password 'Issue_tracker_user';
create user guest password 'Issue_tracker_guest';

grant select on issues, builds, projects, users, roles, statuses, types, resolutions, priorities, managers,comments to user;
grant update on issues, users to user;
grant insert on comments to user;
grant select on issues, builds, projects, users, roles, statuses, types, resolutions, priorities, managers to guest;
grant all on issues, builds, projects, users, roles, statuses, types, resolutions, priorities, managers,comments to administrator;

drop alias if exists updateBuildVersionOfProject;
create alias updateBuildVersionOfProject as $$
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

@CODE
void updateBuilds(Connection connection, long buildId, long projectId) throws Exception {
	PreparedStatement getIsCurrentFieldOfBuildTable = null;
	ResultSet rsIsCurrent = null;
	try {
		getIsCurrentFieldOfBuildTable = connection.prepareStatement("select isCurrent from builds where id=? and projectId=?");
		getIsCurrentFieldOfBuildTable.setLong(1, buildId);
		getIsCurrentFieldOfBuildTable.setLong(2, projectId);
		
		rsIsCurrent = getIsCurrentFieldOfBuildTable.executeQuery();
		if (!(rsIsCurrent.next() && rsIsCurrent.getBoolean("isCurrent"))) {
			PreparedStatement uncheckIsCurrent = null;
			PreparedStatement checkIsCurrent = null;
			try {
				uncheckIsCurrent = connection.
					prepareStatement("update builds set isCurrent=false where projectId=? and isCurrent=true");
				uncheckIsCurrent.setLong(1, projectId);
				uncheckIsCurrent.executeUpdate();
				
				checkIsCurrent = connection.
					prepareStatement("update builds set isCurrent=true where id=?");
				checkIsCurrent.setLong(1, buildId);
				checkIsCurrent.executeUpdate();
			} finally {
				if (checkIsCurrent != null) {
				 	checkIsCurrent.close();
				}
				if (uncheckIsCurrent != null) {
					uncheckIsCurrent.close();
				}
			}
		} 
	} finally {
		if (rsIsCurrent != null) {
			rsIsCurrent.close();
		}
		if (getIsCurrentFieldOfBuildTable != null) {
			getIsCurrentFieldOfBuildTable.close();
		}
	}
}
$$;

drop alias if exists getNIssuesFromPageY;
create alias getNIssuesFromPageY as $$
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

@CODE
ResultSet getIssues(Connection connection, long userId, 
									long pageNumber,long recordsPerPage) throws Exception {
	PreparedStatement getRecordsNumber = null;
	ResultSet rs = null;
	PreparedStatement getIssues = null;
	try {
		getRecordsNumber = connection.prepareStatement("select count(*) as n from issues where assignee=?");
		getRecordsNumber.setLong(1, userId);
		rs = getRecordsNumber.executeQuery();
		long recordsNumber = 0;
		if (rs.next()) {
			recordsNumber = rs.getLong("n");
		}
		
		long offset = (pageNumber - 1) * recordsPerPage;
		if (offset < 0) {
			offset = 0;
		}
		if (offset >= recordsNumber) {
			offset = ((long) java.lang.Math.ceil((double)recordsNumber / recordsPerPage) - 1) * recordsPerPage;
		}
		
		getIssues = connection.prepareStatement("select id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, resolutionId from issues where assignee=? order by id desc limit ? offset ?");
		getIssues.setLong(1, userId);
		getIssues.setLong(2, recordsPerPage);
		getIssues.setLong(3, offset);
		ResultSet rs2 = getIssues.executeQuery();
		
		return rs2;
	} finally {
//		if (getIssues != null) {
//			getIssues.close();
//		}
//		if (rs != null) {
//			rs.close();
//		}
//		if (getRecordsNumber != null) {
//			getRecordsNumber.close();
//		}
	}
}
$$;
