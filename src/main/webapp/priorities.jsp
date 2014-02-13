<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Priorities</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/priorities.css" type="text/css">
	</head>
	<body>
		<%@ include file="/includes/administratorMenu.html" %>
		<div class="container">
			<table class="info" border="1">
				<th class="border">name</th>
				<c:forEach var="priority" items="${priorities}">
 					<tr class="border">
						<td class="border"><a href="/issuetracker/priorities/edit?id=${priority.id}">${priority.name}</a></td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>