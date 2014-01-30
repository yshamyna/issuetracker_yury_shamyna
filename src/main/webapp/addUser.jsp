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
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<form onsubmit="return submitForm(this);" method="post" action="/issuetracker/users/add">
		<div style="position:relative;width:100%;height:230px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">First name:</span> 
			<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="firstName"/>
			<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Last name:</span>
			<input style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" name="lastName"/>
			<span style="position:absolute;right:50%;top:60px;margin-right:100px;">E-mail:</span>
			<input style="position:absolute;left:50%;top:55px;margin-left:-100px;width:200px;" type="text" name="email"/>
			<span style="position:absolute;right:50%;top:85px;margin-right:100px;">Role:</span>
			<select style="position:absolute;left:50%;top:80px;margin-left:-100px;width:204px;" name="roles"/>
				<c:forEach var="role" items="${roles}">
					<option value="${role.id}">${role.value}</option>
				</c:forEach>
			</select>
			<span style="position:absolute;right:50%;top:108px;margin-right:100px;">Password:</span>
			<input style="position:absolute;left:50%;top:103px;margin-left:-100px;width:200px;" type="password" name="password"/>
			<span style="position:absolute;right:50%;top:133px;margin-right:100px;">Password confirmation:</span>
			<input style="position:absolute;left:50%;top:128px;margin-left:-100px;width:200px;" type="password" name="passwordConfirmation"/>
			<div style="text-align:center;position:absolute;top:156px;width:100%">
				<span id="errMsg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input style="position:absolute;left:50%;top:190px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="submit" value="Add">
		</div>
	</form>
	<c:if test="${not empty errMsg}">
		<script type="text/javascript"> 
			dataExistsError("${errMsg}");
		</script> 
	</c:if>
</body>
</html>