<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Projects</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/projects.css" type="text/css">
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
	<body>
		<%@ include file="/includes/administratorMenu.html" %>
		<div class="container">
			<table class="info">
				<caption>Projects</caption>
				<th class="border">name</th>
				<th class="border">manager</th>
				<th class="border">description</th>
				<c:forEach var="project" items="${projects}">
 					<tr class="border">
						<td class="ceil"><a href="/issuetracker/projects/edit?id=${project.id}">${project.name}</a></td>
						<td class="ceil">${project.manager.firstName} ${project.manager.lastName}</td>
						<td class="border">${project.description}</td>
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
						<c:when test="${page eq maxPage && page eq 1}"></c:when>
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