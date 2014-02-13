<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Statuses</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/statuses.css" type="text/css">
	</head>
	<body>
		<%@ include file="/includes/administratorMenu.html" %>
		<div class="container">
			<table class="info" border="1">
				<th class="border">name</th>
				<c:forEach var="status" items="${statuses}">
 					<tr class="border">
						<td class="border"><a href="/issuetracker/statuses/edit?id=${status.id}">${status.name}</a></td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>