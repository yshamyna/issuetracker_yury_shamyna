<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add issue</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/addIssue.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function changeProject() {
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var jsonText = req.responseText;
	        		var jsonData = JSON.parse(jsonText);
	        		var builds = jsonData.builds;
	        		var buildList = document.getElementById("build");
	        		removeChilds(buildList);
	        		for (var i = 0; i < builds.length; i++) {
	        			var id = builds[i].id;
	        			var version = builds[i].version;
	        			var option = document.createElement("option");
	        			option.setAttribute("value", id);
	        			option.innerHTML = version;
	        			buildList.appendChild(option);
	        		}
	            }
	     	};
			var projectList = document.getElementById('project');
			
			var value = projectList.options[projectList.selectedIndex].value;
			req.open('post', '/issuetracker/builds', true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("id=" + value);
		}
		
		function add() {
			var msg = checkInputs();
			var textarea = document.getElementById("description");
			if (!textarea.value.trim()) {
				msg += textarea.name + ' is empty';
			}
			if (msg) {
				printMessage(msg);
				return false;
			} else {
				var req = getXmlHttp();
				
				req.onreadystatechange = function() { 
		        	if (req.readyState == 4 && req.status == 200) {
		        		var msg = req.responseText;
		        		printMessage(msg);
		            }
		     	};
		     	var sel = document.getElementById("status");
		     	var status = sel.options[sel.selectedIndex].value;
		     	var statusName = sel.options[sel.selectedIndex].innerHTML;
		     	sel = document.getElementById("assignee");
		     	var assignee = sel.options[sel.selectedIndex].value;
		     	if (assignee == -1 && statusName != 'new') {
		     		printMessage("Status can not be assigned.");
		     		return;
		     	}
		     	if (assignee != -1 && statusName == 'new') {
		     		printMessage("Status must be 'assigned'");
		     		return;
		     	}
		     	var summary = document.getElementById("summary").value;
		     	var description = document.getElementById("description").value;
		     	sel = document.getElementById("type");
		     	var type = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("priority");
		     	var priority = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("project");
		     	var project = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("build");
		     	var build = sel.options[sel.selectedIndex].value;
		     	
		     	
		     	req.open("post", "/issuetracker/issues/add", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "summary=" + summary + "&description=" + description
					+ "&statusId=" + status + "&typeId=" + type 
					+ "&priorityId=" + priority + "&projectId=" + project
					+ "&buildId=" + build + "&assigneeId=" + assignee;
				printMessage("Please wait...");
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<c:choose>
			<c:when test="${user.role.name eq 'administrator'}">
				<%@ include file="/includes/administratorMenu.html" %>
			</c:when>
			<c:when test="${user.role.name eq 'user'}">
				<%@ include file="/includes/userMenu.html" %>
			</c:when>
		</c:choose>
	<div class="container">
		<span class="summary-label">Summary:</span> 
		<input id="summary" class="issue-summary" type="text" name="Summary"/>

		<span class="description-label">Description:</span>
		<textarea id="description" class="issue-description" name="Description"></textarea>

		<span class="status-label">Status:</span>
		<select id="status" class="issue-status" name="Status"/>
			<c:forEach var="status" items="${statuses}">
				<c:if test="${status.name == 'new' or status.name == 'assigned'}">
					<option value="${status.id}">${status.name}</option>
				</c:if>
			</c:forEach>
		</select>
		
		<span class="type-label">Type:</span>
		<select id="type" class="issue-type" name="Type"/>
			<c:forEach var="type" items="${types}">
				<option value="${type.id}">${type.name}</option>
			</c:forEach>
		</select>
		
		<span class="priority-label">Priority:</span>
		<select id="priority" class="issue-priority" name="Priority"/>
			<c:forEach var="priority" items="${priorities}">
				<option value="${priority.id}">${priority.name}</option>
			</c:forEach>
		</select>
		
		<span class="project-label">Project:</span>
		<select id="project" class="issue-project" name="Project"/>
			<c:forEach var="project" items="${projects}">
				<option value="${project.id}">${project.name}</option>
			</c:forEach>
		</select>
		
		<span class="build-label">Build found:</span>
		<select id="build" class="issue-project-build" id="select-builds" name="Build"/>
			<c:forEach var="build" items="${builds}">
				<option value="${build.id}">${build.version}</option>
			</c:forEach>
		</select>
		
		<span class="assignee-label">Assignee:</span>
		<select id="assignee" class="issue-assignee" name="Assignee"/>
			<option value="-1" selected></option>
			<c:forEach var="assignee" items="${assignees}">
				<option value="${assignee.id}">${assignee.firstName} ${assignee.lastName}</option>
			</c:forEach>
		</select>
		
		<div class="message-container">
			<span class="message" id="msg"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" style="" type="button" value="Add">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = add;
		
		var project = document.getElementById("project");
		project.onchange = changeProject;
	</script>
</body>
</html>