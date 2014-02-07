<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome to issue tracker</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/table.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/content.css" type="text/css">
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
		<div class="content">
			<table class="table">
				<thead>
					<tr>
						<td>id</td>
						<td>priority</td>
						<td>assignee</td>
						<td>type</td>
						<td>status</td>
						<td>summary</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="issue" items="${issues}">
	 					<tr>
							<td><a href="/issuetracker/issues/edit?id=${issue.id}">${issue.id}</a></td>
							<td>${issue.priority.value}</td>
							<td>${issue.assignee.firstName} ${issue.assignee.lastName}</td>
							<td>${issue.type.value}</td>
							<td>${issue.status.value}</td>
							<td>${issue.summary}</td>
						</tr>				
					</c:forEach>
				</tbody>
			</table>
		</div>
		<script type="text/javascript" src="/issuetracker/js/sort.js"></script>
	</body>
</html>