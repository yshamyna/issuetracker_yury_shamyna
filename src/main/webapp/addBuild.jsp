<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add build</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script type="text/javascript">
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
		
		function add(id, url, inputs) {
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
		
		function onclick() {
			var version = new Element("version", 
					document.getElementById("vrsn"));
			var inputs = [version];
			add("${projectId}", "/issuetracker/builds/add", inputs);
		}
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<div id="add-menu" style="position:relative;width:100%;height:100px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
		<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Version:</span> 
		<input id="vrsn" style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="Version"/>
		<div style="text-align:center;position:absolute;top:35px;width:100%">
			<span id="msg" style="font-size:10pt;color:red;margin:auto;"></span>
		</div>
		<input id="submitBtn" style="position:absolute;left:50%;top:60px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color:#4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Add">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("submitBtn");
		submit.onclick = onclick;
		
		var back = document.createElement("a");
		back.setAttribute("href", "/issuetracker/projects/edit?id=${projectId}");
		back.setAttribute("style", "position:absolute;left:5px;bottom:7px;color:rgb(153,249,163);");
		back.innerHTML = "Back";
		
		var addMenu = document.getElementById("add-menu");
		addMenu.appendChild(back);
	</script>
</body>
</html>