<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add user</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script type="text/javascript">
		function submitForm(form) {
			var errMsg = '';
			if (!form.elements['firstName'].value.trim()) {
				errMsg = 'First name is empty.';
			}
			if (!form.elements['lastName'].value.trim()) {
				errMsg += ' Last name is empty.';
			}
			if (!form.elements['email'].value.trim()) {
				errMsg += ' E-mail is empty.';
			}
			if (form.elements['roles'].selectedIndex == -1) {
				errMsg += ' Role is not selected.';
			}
			if (!form.elements['password'].value.trim()) {
				errMsg += ' Password is empty.';
			}
			if (!form.elements['passwordConfirmation'].value.trim()) {
				errMsg += ' Password confirmation is empty.';
			}
			if (errMsg) {
				var span = document.getElementById('errMsg');
				span.innerHTML = errMsg;
				return false;
			} else {
				var regExpPass = /[\da-zA-Z@%$,.:;!?]{5,32}/;
				var isCorrectly = regExpPass.test(form.elements['password'].value);
				if (!isCorrectly) {
					var span = document.getElementById('errMsg');
					span.innerHTML = 'The password must be at least five characters long and can contain letters of the english alphabet in upper and lower case, numbers, punctuation marks and symbols @% $.';
					return false;
				} else {
					var pass = form.elements['password'].value;
					var passConfirm = form.elements['passwordConfirmation'].value;
					if (pass !== passConfirm) {
						var span = document.getElementById('errMsg');
						span.innerHTML = 'The password does not equals password confirmation.';
						return false;
					}
				}
			}
		}
					
		function dataExistsError(errMsg) {
			var span = document.getElementById("errMsg");
			span.innerHTML = errMsg;
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="first-name-label">First name:</span> 
		<input class="user-first-name" type="text" name="First name"/>
		<span class="last-name-label">Last name:</span>
		<input class="user-last-name" type="text" name="Last name"/>
		<span class="email-label">E-mail:</span>
		<input class="user-email" type="text" name="email"/>
		<span class="role-label">Role:</span>
		<select class="user-role" name="Role"/>
			<c:forEach var="role" items="${roles}">
				<c:if test="${role.name not eq 'guest'}">
				</c:if>
				<option value="${role.id}">${role.name}</option>
			</c:forEach>
		</select>
		<span class="password-label">Password:</span>
		<input class="user-password"type="password" name="Password"/>
		<span class="password-confirmation-label">Password confirmation:</span>
		<input class="user-password-confirmation"type="password" name="Password confirmation"/>
		<div class="message-container">
			<span class="container" id="msg"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Add">
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>