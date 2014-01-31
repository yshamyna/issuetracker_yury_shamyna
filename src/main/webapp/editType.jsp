<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit type</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script src="/issuetracker/js/addEntity.js"></script>
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
		
		function updateType(id) {
			if (!check()) return;
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var msg = req.responseText;
	        		showMessage(msg);
	            }
	     	};
	     	var typeInput = document.getElementById('typeInput');
	     	var value = typeInput.value;
			req.open('POST', '/issuetracker/types/edit', true);
			req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			req.send('entityName=' + value + '&id=' + id);
		}
		
		function showMessage(errMsg) {
			var span = document.getElementById("errMsg");
			span.innerHTML = errMsg;
		}
		
		function check() {
			var errMsg = '';
			var input = document.getElementById('typeInput');
			if (!input.value.trim()) {
				errMsg = 'Type name is empty.';
			}
			if (errMsg) {
				var span = document.getElementById('errMsg');
				span.innerHTML = errMsg;
				return false;
			}
			return true;
		}
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<!-- <form onsubmit="return submitForm(this, 'Type');" method="post" action="/issuetracker/types/edit"> -->
		<div style="position:relative;width:100%;height:100px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Name:</span> 
			<input id="typeInput" style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="entityName" value="${type.value}"/>
			<input type="hidden" name="id" value="${type.id}">
			<div style="text-align:center;position:absolute;top:35px;width:100%">
				<span id="errMsg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input style="position:absolute;left:50%;top:60px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color:#4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit" onclick="updateType('${type.id}');">
		</div>
	<!-- </form> -->
	<c:if test="${not empty errMsg}">
		<script type="text/javascript"> 
			dataExistsError("${errMsg}");
		</script> 
	</c:if>
</body>
</html>