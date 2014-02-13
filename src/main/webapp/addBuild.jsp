<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add build</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/addBuild.css" type="text/css">
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
		     
		     	req.open("post", "/issuetracker/builds/add", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "id=${projectId}&version=" + document.getElementById("version").value;
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="version-label">Version:</span> 
		<input class="build-version" id="version" type="text" name="Version"/>
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Add">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = add;
		
		var back = document.createElement("a");
		back.setAttribute("href", "/issuetracker/projects/edit?id=${projectId}");
		back.setAttribute("class", "back");
		back.innerHTML = "Back";
		
		var addMenu = document.getElementById("add-menu");
		addMenu.appendChild(back);
	</script>
</body>
</html>