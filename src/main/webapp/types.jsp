<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Types</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/types.css" type="text/css">
	</head>
	<body>
		<%@ include file="/includes/administratorMenu.html" %>
		<div class="container">
			<table class="info" border="1">
				<th class="border">name</th>
				<c:forEach var="type" items="${types}">
 					<tr class="border">
						<td class="border"><a href="/issuetracker/types/edit?id=${type.id}">${type.name}</a></td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>