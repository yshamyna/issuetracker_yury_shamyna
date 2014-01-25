<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome to issue tracker</title>
		<link rel=stylesheet href=menu.css type="text/css">
		<script type="text/javascript">
			function userNotExistsError(errMsg) {
				var span = document.getElementById("errMsg");
				span.innerHTML = errMsg;
			}
			
			function submitForm(form) {
				var errMsg = '';
				if (!form.elements['emailTxtField'].value.trim()) {
					errMsg = 'E-mail is empty. ';
				}
				if (!form.elements['passTxtField'].value.trim()) {
					errMsg += 'Password is empty.';
				}
				if (errMsg) {
					var span = document.getElementById('errMsg');
					span.innerHTML = errMsg;
					return false;
				}
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
			<c:otherwise>
				<%@ include file="/includes/guestMenu.html" %>
			</c:otherwise>
		</c:choose>
		<c:if test="${not empty errMsg}">
			<script type="text/javascript"> 
				userNotExistsError("${errMsg}");
			</script> 
		</c:if>
		<div style="padding-left:5px;padding-right:5px;background-color:rgb(243, 245, 245);padding-top:5px;font-family:arial;">
			<table border="1" style="width:100%;border-collapse:collapse;border: 2px solid black;">
				<th style="border: 2px solid black;">id</th>
				<th style="border: 2px solid black;">priority</th>
				<th style="border: 2px solid black;">assignee</th>
				<th style="border: 2px solid black;">type</th>
				<th style="border: 2px solid black;">status</th>
				<th style="border: 2px solid black;">summary</th>
				<c:forEach var="issue" items="${issues}">
 					<tr style="border: 2px solid black;">
						<td style="border: 2px solid black;">${issue.id}</td>
						<td style="border: 2px solid black;">${issue.priority.value}</td>
						<td style="border: 2px solid black;">${issue.assignee.firstName} ${issue.assignee.lastName}</td>
						<td style="border: 2px solid black;">${issue.type.value}</td>
						<td style="border: 2px solid black;">${issue.status.value}</td>
						<td style="border: 2px solid black;">${issue.summary}</td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>