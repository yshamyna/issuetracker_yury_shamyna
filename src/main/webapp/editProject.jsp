<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit project</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/editProject.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function update() {
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
		     
		     	var name = document.getElementById("name").value;
		     	var description = document.getElementById("description").value;
		     	var sel = document.getElementById("manager");
		     	var manager = sel.options[sel.selectedIndex].value;
		     	var sel = document.getElementById("build");
		     	var build = sel.options[sel.selectedIndex].value;
		     	
		     	req.open("post", "/issuetracker/projects/edit", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "id=${project.id}&name=" + name
					+ "&description=" + description + "&managerId=" + manager
					+ "&buildId=" + build;
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="name-label" style="">Name:</span> 
		<input id="name" class="project-name" type="text" name="Project name" value="${project.name}"/>
		
		<span class="description-label">Description:</span>
		<textarea id="description" class="project-description" name="Project description">${project.description}</textarea>
		
		<span class="build-label">Build:</span>
		<select id="build" class="project-build" name="Build">
			<c:forEach var="build" items="${builds}">
				<c:choose>
					<c:when test="${build.isCurrent eq true}">
						<option value="${build.id}" selected>${build.version}</option>
					</c:when>
					<c:otherwise>
						<option value="${build.id}">${build.version}</option>		
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select> 
		<a href="/issuetracker/builds/add?id=${project.id}" class="add-build">Add</a>
		
		<span class="manager-label">Manager:</span>
		<select id="manager" class="project-manager" name="Manager"/>
			<c:forEach var="manager" items="${managers}">
				<c:choose>
					<c:when test="${manager.id eq project.manager.id}">
						<option value="${manager.id}" selected>${manager.firstName} ${manager.lastName}</option>
					</c:when>
					<c:otherwise>
						<option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>		
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="button" value="Update">
	</div>
	
	<script type="text/javascript">
		var submitBtn = document.getElementById("sbtBtn");
		submitBtn.onclick = update;
	</script>
</body>
</html>