<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit project</title>
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
		
		function update(id, url, inputs) {
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
			for (var i = 0; i < inputs.length; i++) {
				var parameterName = inputs[i].getParameter();
				var parameterValue = inputs[i].getElement().value;
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
		
		function onclick() {
			var name = new Element("entityName", 
					document.getElementById("name"));
			var inputs = [name];
			update("${priority.id}", "/issuetracker/priorities/edit", inputs);
		}
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<div style="position:relative;width:100%;height:250px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px;">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Name:</span> 
			<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="Name" value="${project.name}"/>
			
			<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Description:</span>
			<input style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" name="Description" value="${project.description}"/>
			<textarea style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;max-height:100px;width:200px;height:100px;">${project.description}</textarea>
			
			<span style="position:absolute;right:50%;top:145px;margin-right:100px;">Build:</span>
			<select style="position:absolute;left:50%;top:140px;margin-left:-100px;width:200px;" name="Version">
				<c:forEach var="build" items="${builds}">
					<c:choose>
						<c:when test="${build.isCurrent eq true}">
							<option value="${build.id}" selected>${build.version}</option>
						</c:when>
						<c:otherwise>
							<option value="${build.id}">${build.version}</option>		
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			<a href="/issuetracker/builds/add?id=${project.id}" style="position:absolute;right:50%;top:145px;margin-right:-130px;">Add</a>
			
			<span style="position:absolute;right:50%;top:170px;margin-right:100px;">Manager:</span>
			<select style="position:absolute;left:50%;top:165px;margin-left:-100px;width:204px;" name="Manager"/>
				<c:forEach var="manager" items="${managers}">
					<c:choose>
						<c:when test="${manager.id eq project.manager.id}">
							<option value="${manager.id}" selected>${manager.firstName} ${manager.lastName}</option>
						</c:when>
						<c:otherwise>
							<option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>		
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<div style="text-align:center;position:absolute;top:130px;width:100%">
				<span id="errMsg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input id="submitBtn" style="position:absolute;left:50%;top:200px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit">
		</div>
	
	<script type="text/javascript">
		var submit = document.getElementById("submitBtn");
		submit.onclick = onclick;
	</script>
</body>
</html>