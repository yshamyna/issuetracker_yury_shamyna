<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add project</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script type="text/javascript">
		function submitForm(form) {
			var errMsg = '';
			if (!form.elements['projectName'].value.trim()) {
				errMsg = 'Project name is empty.';
			}
			if (!form.elements['projectDescription'].value.trim()) {
				errMsg += ' Project description is empty.';
			}
			if (form.elements['managers'].selectedIndex == -1) {
				errMsg += ' Project manager is not selected.';
			}
			if (!form.elements['projectVersion'].value.trim()) {
				errMsg += ' Project build is empty.';
			}
			if (errMsg) {
				var span = document.getElementById('errMsg');
				span.innerHTML = errMsg;
				return false;
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
	<form onsubmit="return submitForm(this);" method="post" action="/issuetracker/projects/add">
		<div style="position:relative;width:100%;height:170px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px;">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Name:</span> 
			<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="projectName"/>
			<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Description:</span>
			<input style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" name="projectDescription"/>
			<span style="position:absolute;right:50%;top:60px;margin-right:100px;">Build:</span> 
			<input style="position:absolute;left:50%;top:55px;margin-left:-100px;width:200px;" type="text" name="projectVersion"/>
			<span style="position:absolute;right:50%;top:83px;margin-right:100px;">Manager:</span>
			<select style="position:absolute;left:50%;top:80px;margin-left:-100px;width:204px;" name="managers"/>
				<c:forEach var="manager" items="${managers}">
					<option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
				</c:forEach>
			</select>
			<div style="text-align:center;position:absolute;top:105px;width:100%">
				<span id="errMsg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input style="position:absolute;left:50%;top:125px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="submit" value="Add">
		</div>
	</form>
	<c:if test="${not empty errMsg}">
		<script type="text/javascript"> 
			dataExistsError("${errMsg}");
		</script> 
	</c:if>
</body>
</html>