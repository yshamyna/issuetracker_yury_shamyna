<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add project</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/addProject.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function add() {
			var msg = checkInputs();
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
		     
		     	req.open("post", "/issuetracker/projects/add", true);
		     	
		     	var name = document.getElementById("name").value;
		     	var description = document.getElementById("description").value;
		     	var sel = document.getElementById("manager");
		     	var manager = sel.options[sel.selectedIndex].value;
		     	var version = document.getElementById("build").value;
		     	
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "name=" + name + "&description=" + description
				 + "&version=" + version + "&managerId=" + manager;
				printMessage("Please wait...");
				req.send(body);
			}
		}
	</script>
</head>
<body style="">
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="name-label">Name:</span> 
		<input class="project-name" id="name" type="text" name="Project name"/>
		<span class="description-label">Description:</span>
		<input class="project-description" id="description" type="text" name="Project description"/>
		<span class="build-label">Build:</span> 
		<input class="project-build" id="build" type="text" name="Project build"/>
		<span class="manager-label">Manager:</span>
		<select id="manager" class="project-manager" name="Project manager"/>
			<c:forEach var="manager" items="${managers}">
				<option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
			</c:forEach>
		</select>
		<div class="message-container">
			<span class="message" id="msg" style=""></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Add">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = add;
	</script>
</body>
</html>