<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Users</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<div style="padding-left:5px;padding-right:5px;background-color:rgb(243, 245, 245);padding-top:5px;font-family:arial;">
		<table border="1" style="width:100%;border-collapse:collapse;border: 2px solid black;">
			<th style="border: 2px solid black;">e-mail</th>
			<th style="border: 2px solid black;">first name</th>
			<th style="border: 2px solid black;">last name</th>
			<th style="border: 2px solid black;">role</th>
			<c:forEach var="user" items="${users}">
				<tr style="border: 2px solid black;">
					<td style="border: 2px solid black;"><a style="text-decoration:none;" href="/issuetracker/users/edit?id=${user.id}" >${user.emailAddress}</a></td>
					<td style="border: 2px solid black;">${user.firstName} ${issue.assignee.lastName}</td>
					<td style="border: 2px solid black;">${user.lastName}</td>
					<td style="border: 2px solid black;">${user.role.value}</td>
				</tr>				
			</c:forEach>
		</table>
		</div>
</body>
</html>