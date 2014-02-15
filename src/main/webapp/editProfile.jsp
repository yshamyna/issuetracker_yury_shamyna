<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit profile</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/editProfile.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function editProfile() {
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
		     	
		     	var firstName = document.getElementById("fName").value;
		     	var lastName = document.getElementById("lName").value;
		     	var email = document.getElementById("email").value;
		     
		     	req.open("post", "/issuetracker/profile/edit", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "firstName=" + firstName + "&lastName=" + lastName
					+ "&E-mail=" + email;
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
		<span class="first-name-label">First name:</span> 
		<input id="fName" class="user-first-name" type="text" name="First name" value="${user.firstName}"/>
		
		<span class="last-name-label">Last name:</span>
		<input id="lName" class="user-last-name" type="text" name="Last name" value="${user.lastName}"/>
		
		<span class="email-label">E-mail:</span>
		<input id="email" class="user-email" type="text" name="E-mail" value="${user.emailAddress}"/>
		
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Ok">
	</div>
	<script type="text/javascript">
		var submitBtn = document.getElementById("sbtBtn");
		submitBtn.onclick = editProfile;
	</script>
</body>
</html>