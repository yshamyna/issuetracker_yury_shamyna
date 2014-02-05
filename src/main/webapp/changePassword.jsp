<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Change password</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<script>
		function getXmlHttp(){
	  		var xmlhttp;
	  		try {
	    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	  		} catch (e) {
	    		try {
	      			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	    		} catch (E) {
	      			xmlhttp = false;
	    		}
	  		}
	  		if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
	    		xmlhttp = new XMLHttpRequest();
	  		}
	  		return xmlhttp;
		}
		
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
		
		function change(id, url, data) {
			if (!isCorrectData()) return;
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
			req.open("post", url, true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			var body = "";
			for (var i = 0; i < data.length; i++) {
				var parameterName = data[i].getParameter();
				var parameterValue = data[i].getElement().value;
				body += parameterName + "=" + parameterValue + "&";
			}
			body += "id=" + id;
			req.send(body);
		}
		
		function printMessage(errMsg) {
			var span = document.getElementById("msg");
			span.innerHTML = errMsg;
		}
		
		function isCorrectData() {
			var msg = checkTextFields();
			if (msg) {
				printMessage(msg);
				return false;
			}
			return true;
		}
		
		function checkTextFields() {
			var inputs = document.getElementsByTagName("input");
			var msg = "";
			for (var i = 0; i < inputs.length; i++) {
				if (!inputs[i].value.trim()) {
					msg += inputs[i].name + " is empty. ";
				}
			}
			var textareas = document.getElementsByTagName("textarea");
			for (var i = 0; i < textareas.length; i++) {
				if (!textareas[i].value.trim()) {
					msg += textareas[i].name + " is empty. ";
				}
			}
			return msg;
		}
		
		function Element(parameter, element) {
			this.parameter = parameter;
			this.element = element;
			
			this.getParameter = function() {
				return this.parameter;
			};
			this.getElement = function() {
				return this.element;
			};
		}
		
		function onclick() {
			var newPassword = new Element("newPassword", document.getElementById("pass"));
			var passwordConfirmation = new Element("passwordConfirmation", document.getElementById("passConfirm"));
			var data = [newPassword, passwordConfirmation];
			change("${user.id}", "/issuetracker/profile/change-password", data);
		}
	</script>
	</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<c:choose>
		<c:when test="${user.role.value eq 'administrator'}">
			<%@ include file="/includes/administratorMenu.html" %>
		</c:when>
		<c:when test="${user.role.value eq 'user'}">
			<%@ include file="/includes/userMenu.html" %>
		</c:when>
	</c:choose>
	
	<div style="position:relative;width:100%;height:230px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
		<span style="position:absolute;right:50%;top:10px;margin-right:100px;">New password:</span> 
		<input id="pass" style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="password" name="New password"/>
		
		<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Password confirmation:</span>
		<input id="passConfirm" style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="password" name="Password confirmation"/>
		
		<div style="text-align:center;position:absolute;top:70px;width:100%">
			<span id="msg" style="font-size:10pt;color:red;margin:auto;"></span>
		</div>
		<input id="submitBtn" style="position:absolute;left:50%;top:94px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="submit" value="Ok">
	</div>
	
	<script type="text/javascript">
		var submit = document.getElementById("submitBtn");
		submit.onclick = onclick;
	</script>
</body>
</html>