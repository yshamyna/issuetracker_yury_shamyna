<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit resolution</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/editResolution.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function update() {
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
		     
		     	req.open("post", "/issuetracker/resolutions/edit", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "id=${resolution.id}&name=" + document.getElementById("resolution").value;
				req.send(body);
			}
		}
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="resolutionLabel">Name:</span> 
		<input class="resolutionName" id="resolution" type="text" name="Name" value="${resolution.name}"/>
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Update">
	</div>
	<script type="text/javascript">
		var submitBtn = document.getElementById("sbtBtn");
		submitBtn.onclick = update;
	</script>
</body>
</html>