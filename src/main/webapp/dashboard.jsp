<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome to issue tracker</title>
		<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/table.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/content.css" type="text/css">
		<link rel=stylesheet href="/issuetracker/css/pagination.css" type="text/css">
		<script type="text/javascript" src="/issuetracker/js/util.js"></script>
		<script type="text/javascript">
			function login() {
				var msg = checkInputs();
				if (msg) {
					printMessage(msg);
					return false;
				}
				return true;
			} 
			
			var back = function back() {
				var page = "${page}";
				page--;
				var paginationForm = document.getElementById("pagination-form");
				paginationForm.setAttribute("action", "/issuetracker/dashboard?page=" + page);
			};

			var next = function next() {
				var page = "${page}";
				page++;
				var paginationForm = document.getElementById("pagination-form");
				paginationForm.setAttribute("action", "/issuetracker/dashboard?page=" + page);
			};
		</script>
	</head>
		<body>
		<c:choose>
			<c:when test="${user.role.name eq 'administrator'}">
				<%@ include file="/includes/administratorMenu.html" %>
			</c:when>
			<c:when test="${user.role.name eq 'user'}">
				<%@ include file="/includes/userMenu.html" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/includes/guestMenu.html" %>
			</c:otherwise>
		</c:choose>
		<c:if test="${not empty msg}">
			<script type="text/javascript">
				printMessage('${msg}');
			</script>
		</c:if>
		<c:choose>
			<c:when test="${fn:length(issues) == 0 }">
				<div class="issue-not-found">
					<img src="/issuetracker/img/sorrow_face.jpg" />
					<br/>
					Issues not found
				</div>
			</c:when>
			<c:otherwise>
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
									<td>${issue.priority.name}</td>
									<td>${issue.assignee.firstName} ${issue.assignee.lastName}</td>
									<td>${issue.type.name}</td>
									<td>${issue.status.name}</td>
									<td>${issue.summary}</td>
								</tr>				
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:choose>
					<c:when test="${not empty user}">
						<form id="pagination-form" method="post" action="">
							<div style="padding-top:10px">
								<div style="width:100%;background-color:rgb(210, 210, 210);">
									Page: ${page}/${maxPage}
								</div>
								<div style="width:100%; height:26px;background-color:rgb(210, 210, 210);text-align:center;">
									<c:choose>
										<c:when test="${page eq maxPage and page eq 1}"></c:when>
										<c:when test="${page eq 1}">
											<input id="nextPageBtn" type="submit" value="Next">		
										</c:when>
										<c:when test="${page eq maxPage}">
											<input id="prevPageBtn" type="submit" value="Previous">
										</c:when>
										<c:otherwise>
											<input id="prevPageBtn" type="submit" value="Previous">
											<input id="nextPageBtn" type="submit" value="Next">
										</c:otherwise>
									</c:choose>
								</div>	
							</div>
						</form>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<script type="text/javascript" src="/issuetracker/js/sort.js"></script>
		<script type="text/javascript">
			var nextPageBtn = document.getElementById("nextPageBtn");
			if (nextPageBtn != null) {
				nextPageBtn.onclick = next;
			}
	
			var prevPageBtn = document.getElementById("prevPageBtn");
			if (prevPageBtn != null) {
				prevPageBtn.onclick = back;
			}
		</script>
	</body>
</html>