<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit user</title>
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
		
		function change(id, url, data) {
			if (!isCorrectData()) return;
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
		
		function adminOnClick() {
			var firstName = new Element("fName", document.getElementById("fName"));
			var lastName = new Element("lName", document.getElementById("lName"));
			var email = new Element("email", document.getElementById("email"));
			var role = new Element("roleId", document.getElementById("role"));
			var data = [firstName, lastName, email, role];
			change("${usr.id}", "/issuetracker/users/edit", data);
		}
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
			<%@ include file="/includes/administratorMenu.html" %>
	<div style="position:relative;width:100%;height:230px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">First name:</span> 
			<input id="fName" style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="First name" value="${usr.firstName}"/>
			
			<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Last name:</span>
			<input id="lName" style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" name="Last name" value="${usr.lastName}"/>
			
			<span style="position:absolute;right:50%;top:60px;margin-right:100px;">E-mail:</span>
			<input id="email" style="position:absolute;left:50%;top:55px;margin-left:-100px;width:200px;" type="text" name="E-mail" value="${usr.emailAddress}"/>
			
			<span style="position:absolute;right:50%;top:85px;margin-right:100px;">Role:</span>
			<select id="role" style="position:absolute;left:50%;top:80px;margin-left:-100px;width:204px;" name="Role"/>
				<c:forEach var="role" items="${roles}">
					<c:choose>
						<c:when test="${role.id eq usr.role.id}">
							<option value="${role.id}" selected>${role.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${role.id}">${role.value}</option>		
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		
			<div style="text-align:center;position:absolute;top:120px;width:100%">
				<span id="msg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input id="submitBtn" style="position:absolute;left:50%;top:154px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit">
		</div>
	<script type="text/javascript">
		var submit = document.getElementById("submitBtn");
		submit.onclick = adminOnClick;
	</script>
</body>
</html>