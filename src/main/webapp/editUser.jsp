<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit user</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/editUser.css" type="text/css">
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
		     
		     	var firstName = document.getElementById("fName").value;
		     	var lastName = document.getElementById("lName").value;
		     	var sel = document.getElementById("role");
		     	var role = sel.options[sel.selectedIndex].value;
		     	var email = document.getElementById("email").value;
		     	
		     	req.open("post", "/issuetracker/users/edit", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "id=${usr.id}&firstName=" + firstName
					+ "&lastName=" + lastName + "&roleId=" + role
					+ "&email=" + email;
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
			<span class="first-name-label">First name:</span> 
			<input id="fName" class="user-first-name" type="text" name="First name" value="${usr.firstName}"/>
			
			<span class="last-name-label">Last name:</span>
			<input id="lName" class="user-last-name" type="text" name="Last name" value="${usr.lastName}"/>
			
			<span class="email-label">E-mail:</span>
			<input id="email" class="user-email" type="text" name="E-mail" value="${usr.emailAddress}"/>
			
			<span class="role-label">Role:</span>
			<select id="role" class="user-role" name="Role"/>
				<c:forEach var="role" items="${roles}">
					<c:if test="${role.name != 'guest'}">
						<c:choose>
							<c:when test="${role.id eq usr.role.id}">
								<option value="${role.id}" selected>${role.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${role.id}">${role.name}</option>		
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</select>
		
			<div class="message-container">
				<span id="msg" class="message"></span>
			</div>
			<input id="sbtBtn" class="submitBtn" type="button" value="Update">
		</div>
	<script type="text/javascript">
		var submit = document.getElementById("sbtBtn");
		submit.onclick = update;
	</script>
</body>
</html>