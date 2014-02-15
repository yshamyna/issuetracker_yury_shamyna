<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Change password</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/changePassword.css" type="text/css">
		<script src="/issuetracker/js/util.js"></script>
		<script type="text/javascript">
			function checkPassword(pass, passConfirm) {
				var regExpPass = /[\da-zA-Z@%$,.:;!?]{5,32}/;
				if (!regExpPass.test(pass)) {
					printMessage("The password must be at least five characters long and can contain letters of the english alphabet in upper and lower case, numbers, punctuation marks and symbols @% $.");
					return false;
				} else {
					if (pass !== passConfirm) {
						printMessage("The password does not equals password confirmation.");
						return false;
					}
				}
				return true;
			}
		
			function change() {
				var msg = checkInputs();
				if (msg) {
					printMessage(msg);
					return false;
				} 
				var pass = document.getElementById("pass").value;
				var passConfirm = document.getElementById("passConfirm").value;
				if (!checkPassword(pass, passConfirm)) return;
				
				var req = getXmlHttp();
				req.onreadystatechange = function() { 
		        	if (req.readyState == 4 && req.status == 200) {
		        		var msg = req.responseText;
		        		printMessage(msg);
		            }
		     	};
				req.open("post", "/issuetracker/profile/change-password", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				printMessage("Please wait...");
				req.send("Password=" + pass);
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
		<span class="password-label">New password:</span> 
		<input id="pass" class="user-password" type="password" name="New password"/>
		
		<span class="password-confirmation-label">Password confirmation:</span>
		<input id="passConfirm" class="user-password-confirmation" type="password" name="Password confirmation"/>
		
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="button" value="Change password">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = change;
	</script>
</body>
</html>