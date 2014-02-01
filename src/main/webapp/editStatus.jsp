<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit status</title>
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
		
		function updateStatus(id) {
			alert("Lalala");
			if (!isCorrectData()) return;
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var msg = req.responseText;
	        		printMessage(msg);
	            }
	     	};
	     	var statusInput = document.getElementById("statusInput");
	     	var value = statusInput.value;
			req.open("POST", "/issuetracker/statuses/edit", true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("entityName=" + value + "&id=" + id);
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
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<div style="position:relative;width:100%;height:100px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
		<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Name:</span> 
		<input id="statusInput" style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="Name" value="${status.value}"/>
		<div style="text-align:center;position:absolute;top:35px;width:100%">
			<span id="msg" style="font-size:10pt;color:red;margin:auto;"></span>
		</div>
		<input style="position:absolute;left:50%;top:60px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color:#4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit" onclick="updateStatus('${status.id}');">
	</div>
</body>
</html>