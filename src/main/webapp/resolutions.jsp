<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Resolutions</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/resolutions.css" type="text/css">
	</head>
	<body>
		<%@ include file="/includes/administratorMenu.html" %>
		<div class="container">
			<table class="info" border="1">
				<th class="border">name</th>
				<c:forEach var="resolution" items="${resolutions}">
 					<tr class="border">
						<td class="border"><a href="/issuetracker/resolutions/edit?id=${resolution.id}">${resolution.name}</a></td>
					</tr>				
				</c:forEach>
			</table>
		</div>
	</body>
</html>