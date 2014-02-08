<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Projects</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<script>
			function back(page) {
				if (page === 1) {
					return false;
				}
				page--;
				var paginationForm = document.getElementById("pagination-form");
				paginationForm.setAttribute("action", "/issuetracker/projects?page=" + page);
			}
			
			function next(page) {
				page++;
				var paginationForm = document.getElementById("pagination-form");
				paginationForm.setAttribute("action", "/issuetracker/projects?page=" + page);
			}
		</script>
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
		<form id="pagination-form" method="post" action="">
			<div style="padding-top:10px">
				<div style="width:100%;background-color:rgb(210, 210, 210);">
					Page: ${page}/${maxPage}
				</div>
				<div style="width:100%; height:26px;background-color:rgb(210, 210, 210);text-align:center;">
					<c:choose>
						<c:when test="${page eq 1}">
							<input type="submit" value="Next" onclick="next(${page})">		
						</c:when>
						<c:when test="${page eq maxPage}">
							<input type="submit" value="Previous" onclick="back(${page})">
						</c:when>
						<c:otherwise>
							<input type="submit" value="Previous" onclick="back(${page})">
							<input type="submit" value="Next" onclick="next(${page})">
						</c:otherwise>
					</c:choose>
				</div>	
			</div>
		</form>
	</body>
</html>