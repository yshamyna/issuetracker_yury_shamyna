<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add user</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/addUser.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function add() {
			var msg = checkInputs();
			if (msg) {
				printMessage(msg);
				return false;
			} else {
				var password = document.getElementById("password").value;
				var passConfirm = document.
							getElementById("passwordConfirmation").value;
				var regExpPass = /[\da-zA-Z@%$,.:;!?]{5,32}/;
				var isCorrectly = regExpPass.test(password);
				if (!isCorrectly) {
					printMessage("The password must be at least five characters long and can contain letters of the english alphabet in upper and lower case, numbers, punctuation marks and symbols @% $.")
					return false;
				} 
				if (password !== passConfirm) {
					printMessage("The password does not equals password confirmation.");
					return false;
				}
				
				var req = getXmlHttp();
				
				req.onreadystatechange = function() { 
		        	if (req.readyState == 4 && req.status == 200) {
		        		var msg = req.responseText;
		        		printMessage(msg);
		            }
		     	};
		     
		     	req.open("post", "/issuetracker/users/add", true);
		     	
		     	var firstName = document.getElementById("firstName").value;
		     	var lastName = document.getElementById("lastName").value;
		     	var email = document.getElementById("email").value;
		     	var sel = document.getElementById("role");
		     	var role = sel.options[sel.selectedIndex].value;
		     	
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "firstName=" + firstName + "&lastName=" + lastName  
				+ "&E-mail=" + email + "&roleId=" + role + "&password=" + password;
				
				printMessage("Please wait...");
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="first-name-label">First name:</span> 
		<input id="firstName" class="user-first-name" type="text" name="First name"/>
		<span class="last-name-label">Last name:</span>
		<input id="lastName" class="user-last-name" type="text" name="Last name"/>
		<span class="email-label">E-mail:</span>
		<input id="email" class="user-email" type="text" name="E-mail"/>
		<span class="role-label">Role:</span>
		<select id="role" class="user-role" name="Role"/>
			<c:forEach var="role" items="${roles}">
				<c:if test="${role.name != 'guest'}">
					<option value="${role.id}">${role.name}</option>
				</c:if>
			</c:forEach>
		</select>
		<span class="password-label">Password:</span>
		<input id="password" class="user-password"type="password" name="Password"/>
		<span class="password-confirmation-label">Password confirmation:</span>
		<input id="passwordConfirmation" class="user-password-confirmation"type="password" name="Password confirmation"/>
		<div class="message-container">
			<span class="container" id="msg"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="button" value="Add">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = add;
	</script>
</body>
</html>