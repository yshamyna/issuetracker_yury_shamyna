<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Projects</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	</head>
	<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
		<%@ include file="/includes/administratorMenu.html" %>
		<div style="padding-left:5px;padding-right:5px;background-color:rgb(243, 245, 245);padding-top:5px;font-family:arial;">
			<table border="1" style="width:100%;border-collapse:collapse;border: 2px solid black;">
				<caption>Projects</caption>
				<th style="border: 2px solid black;">name</th>
				<th style="border: 2px solid black;">manager</th>
				<th style="border: 2px solid black;">description</th>
				<c:forEach var="project" items="${projects}">
 					<tr style="border: 2px solid black;">
						<td style="border: 2px solid black;width:200px;"><a href="/issuetracker/projects/edit?id=${project.id}">${project.name}</a></td>
						<td style="border: 2px solid black;width:200px;">${project.manager.firstName} ${project.manager.lastName}</td>
						<td style="border: 2px solid black;">${project.description}</td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>