<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit issue</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/editIssue.css" type="text/css">
	<script>
		function getXmlHttp(){
	  		var xmlhttp;
	  		try {
	    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	  		} catch (e) {
	    		try {
	      			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	    		} catch (E) {
	      			xmlhttp = false;
	    		}
	  		}
	  		if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
	    		xmlhttp = new XMLHttpRequest();
	  		}
	  		return xmlhttp;
		}
	
		function removeChilds(node) {
			while(node.childNodes[0]){
				node.removeChild(node.childNodes[0]);
			}
		}
		
		function changeProject() {
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var jsonText = req.responseText;
	        		var jsonData = JSON.parse(jsonText);
	        		var builds = jsonData.builds;
	        		var buildList = document.getElementById('select-builds');
	        		removeChilds(buildList);
	        		for (var i = 0; i < builds.length; i++) {
	        			var id = builds[i].id;
	        			var version = builds[i].version;
	        			var option = document.createElement('option');
	        			option.setAttribute('value', id);
	        			option.innerHTML = version;
	        			buildList.appendChild(option);
	        		}
	            }
	     	};
			var projectList = document.getElementById('select-projects');
			
			var value = projectList.options[projectList.selectedIndex].value;
			req.open('GET', '/issuetracker/get_builds?id=' + value, true);
			req.send(null);
		}
			
		function printMessage(errMsg) {
			var span = document.getElementById("msg");
			span.innerHTML = errMsg;
		}
		
		function isCorrectData() {
			var msg = checkTextFields();
			if (msg) {
				printMessage(msg);
				return false;
			}
			return true;
		}
		
		function checkTextFields() {
			var inputs = document.getElementsByTagName("input");
			var msg = "";
			for (var i = 0; i < inputs.length; i++) {
				if (!inputs[i].value.trim()) {
					msg += inputs[i].name + " is empty. ";
				}
			}
			var txtAreas = document.getElementsByTagName("textarea");
			for (var i = 0; i < txtAreas.length; i++) {
				if (!txtAreas[i].value.trim()) {
					msg += txtAreas[i].name + " is empty. ";
				}
			}
			return msg;
		}
		
		function Element(parameter, element) {
			this.parameter = parameter;
			this.element = element;
			
			this.getParameter = function() {
				return this.parameter;
			};
			this.getElement = function() {
				return this.element;
			};
		}
		
		function onClick() {
			var summary = new Element("summary", document.getElementById("summary"));
			var description = new Element("description", document.getElementById("description"));
			var status = new Element("statusId", document.getElementById("status"));
			var type = new Element("typeId", document.getElementById("type"));
			var priority = new Element("priorityId", document.getElementById("priority"));
			var project = new Element("projectId", document.getElementById("select-projects"));
			var buildFound = new Element("buildId", document.getElementById("select-builds"));
			var assignee = new Element("assigneeId", document.getElementById("assignee"));
			var data = [summary, description, status, type, priority, project, buildFound, assignee];
			update("${issue.id}", "/issuetracker/issues/edit", data);
		}
		
		function update(id, url, data) {
			if (!isCorrectData()) return;
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var msg = req.responseText;
	        		printMessage(msg);
	            }
	     	};
			req.open("post", url, true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			var body = "";
			for (var i = 0; i < data.length; i++) {
				var parameterName = data[i].getParameter();
				var parameterValue = data[i].getElement().value;
				body += parameterName + "=" + parameterValue + "&";
			}
			body += "id=" + id;
			req.send(body);
		}
		
		function check() {
			var textarea = document.getElementById("comment");
			if (!textarea.trim()) {
				return false;
			} 
			return true;
		}
		
		function add() {
			addComment("${issue.id}", "/issuetracker/add-comment");
		}
		
		function addComment(id, url) {
			if (!check) return;
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var jsonText = req.responseText;
	        		var jsonData = JSON.parse(jsonText);
	        		var data = jsonData.data;
	        		
	        		var addedBySpan = document.createElement("span");
	        		addedBySpan.innerHTML = "Added by: " + data.addedBy;
	        		var addDateSpan = document.createElement("span");
	        		addDateSpan.innerHTML = "Add date: " + data.createDate;
	        		var textSpan = document.createElement("span");
	        		textSpan.innerHTML = "Comment: " + data.comment;
	        		
	        		var newComment = document.createElement("div");
	        		newComment.appendChild(addedBySpan);
	        		newComment.appendChild(document.createElement("br"));
	        		newComment.appendChild(addDateSpan);
	        		newComment.appendChild(document.createElement("br"));
	        		newComment.appendChild(textSpan);
	        		newComment.setAttribute("style", "background-color:rgb(206, 227, 253);width:100%;margin-top:2px;padding-left:2px;");
	        		
	        		var commentsStorage = document.getElementById("commentsStorage");
	        		commentsStorage.appendChild(newComment);
	            }
	     	};
			req.open("post", url, true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			var value = document.getElementById("comment").value;
			req.send("issueId=" + id + "&comment=" + value);
		}
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
	<div class="section-delimiter">Edit section</div>
	<c:choose>
		<c:when test="${empty user}">
			<div class="container">
				<div class="row">
					<div class="col">
						<span>Create by:</span><br/> 
						<input class="ceil" type="text" value="${issue.createBy.firstName} ${issue.createBy.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Status:</span><br/> 
						<input class="ceil" type="text" value="${issue.status.name}" readonly/>
					</div>
					<div class="col">
						<span>Project:</span><br/> 
						<input class="ceil" type="text" value="${issue.project.name}" readonly/>
					</div>					
				</div>
				<div class="row">
					<div class="col">
						<span>Create date:</span><br/> 
						<input class="ceil" type="text" value="${issue.createDate}" readonly/>
					</div>
					<div class="col">
						<span>Type:</span><br/> 
						<input class="ceil" type="text" value="${issue.type.name}" readonly/>
					</div>
					<div class="col">
						<span>Build found:</span><br/> 
						<input class="ceil" type="text" value="${issue.buildFound.version}" readonly/>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Modify by:</span><br/> 
						<input class="ceil" type="text" value="${issue.modifyBy.firstName} ${issue.modifyBy.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Priority:</span><br/> 
						<input class="ceil" type="text" value="${issue.priority.name}" readonly/>
					</div>
					<div class="col">
						<span class="guest-summary-label">Summary:</span><br/> 
						<textarea class="ceil" readonly>${issue.summary}</textarea>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Modify date:</span><br/>
						<input class="ceil" type="text" value="${issue.modifyDate}" readonly/>
					</div>
					<div class="col">
						<span>Assignee:</span><br/> 
						<input class="ceil" type="text" value="${issue.assignee.firstName} ${issue.assignee.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Description:</span><br/>
						<textarea class="ceil" readonly>${issue.description}</textarea>
					</div>
				</div>
			</div>
			<div class="section-delimiter">Comment section</div>
				<c:choose>
					<c:when test="${fn:length(comments) == 0}">
						<div class="message-container">
							<span id="msg" class="message">Comments not found</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="comment-storage">
							<c:forEach var="comment" items="${comments}">
								<div class="comment-block">
									<span>Added by: ${comment.sender.firstName} ${comment.sender.lastName}</span><br/>
									<span>Add date: ${comment.createDate}</span><br/>
									<span>Comment: ${comment.comment}</span>
								</div>	
							</c:forEach>
						</div><br/>
					</c:otherwise>
				</c:choose>
			<div class="section-delimiter">Attachment section</div>
				<c:choose>
					<c:when test="${fn:length(attachments) == 0}">
						<div class="message-container">
							<span id="msg" class="message">Attachments not found</span>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach var="attachment" items="${attachments}">
							<div class="attachment-block">
								<span>Added by: ${attachment.addedBy.firstName} ${attachment.addedBy.lastName}</span><br/>
								<span>Add date: ${attachment.addDate}</span><br/>
								<span>File: ${attachment.filename}</span>
							</div>	
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</c:when>
		<c:otherwise>
			<div class="container">
				<div class="row">
					<div class="col">
						<span>Create by:</span><br/>
						<input type="text" name="Create by" value="${issue.createBy.firstName} ${issue.createBy.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Status: </span><br/>
						<select id="status" style="width:200px;" name="status">
							<c:forEach var="status" items="${statuses}">
								<c:choose>
									<c:when test="${status.id eq issue.status.id}">
										<option value="${status.id}" selected>${status.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${status.id}">${status.name}</option>		
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<span>Project:</span><br/>
						<select id="select-projects" name="project" onchange="changeProject();" style="width:200px;">
							<c:forEach var="project" items="${projects}">
								<c:choose>
									<c:when test="${project.id eq issue.project.id}">
										<option value="${project.id}" selected>${project.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${project.id}">${project.name}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Create date:</span><br/>
						<input type="text" style="width:200px;" name="Create date" value="${issue.createDate}" readonly/>
					</div>
					<div class="col">
						<span>Type:</span><br/>
						<select id="type" name="type" style="width:200px;">
							<c:forEach var="type" items="${types}">
								<c:choose>
									<c:when test="${type.id eq issue.type.id}">
										<option value="${type.id}" selected>${type.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${type.id}">${type.name}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<span>Build found: </span><br/>
						<select id="select-builds" name="build" style="width:200px;">
							<c:forEach var="build" items="${builds}">
								<c:choose>
									<c:when test="${build.id eq issue.buildFound.id}">
										<option value="${build.id}" selected>${build.version}</option>
									</c:when>
									<c:otherwise>
										<option value="${build.id}">${build.version}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Modify by: </span><br/>
						<input type="text" style="width:200px;" name="Modify by" 
										value="${issue.modifyBy.firstName} ${issue.modifyBy.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Priority: </span><br/>
						<select id="priority" name= "priority" style="width:200px;">
							<c:forEach var="priority" items="${priorities}">
								<c:choose>
									<c:when test="${priority.id eq issue.priority.id}">
										<option value="${priority.id}" selected>${priority.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${priority.id}">${priority.name}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<span>Summary:</span><br/>
						<textarea style="width:200px;" name="Summary">${issue.summary}</textarea>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Modify date:</span><br/>
						<input type="text" style="width:200px;" name="Modify date" value="${issue.modifyDate}" readonly/>
					</div>
					<div class="col">
						<span>Assignee:</span><br/>
						<select id="assignee" name="assignee" style="width:200px;">
							<option value="-1" selected></option>
							<c:forEach var="assignee" items="${assignees}">
								<c:choose>
									<c:when test="${assignee.id eq issue.assignee.id}">
										<option value="${assignee.id}" selected>${assignee.firstName} ${assignee.lastName}</option>
									</c:when>
									<c:otherwise>
										<option value="${assignee.id}">${assignee.firstName} ${assignee.lastName}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<span>Description:</span><br/>
						<textarea style="width:200px;" name="Description">${issue.description}</textarea>
					</div>
				</div>
				<span id="msg" style="font-size:10pt;color:red;"></span><br/><br/>
				<input id="sbtBtn" class="submitBtn" type="button" value="Edit">
			</div>
			<div class="section-delimiter">Comment section</div>
			<c:choose>
				<c:when test="${fn:length(comments) == 0}">
					<div class="message-container">
						<span id="msg" class="message">Comments not found</span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="comment-storage">
						<c:forEach var="comment" items="${comments}">
							<div class="comment-block">
								<span>Added by: ${comment.sender.firstName} ${comment.sender.lastName}</span><br/>
								<span>Add date: ${comment.createDate}</span><br/>
								<span>Comment: ${comment.comment}</span>
							</div>	
						</c:forEach>
					</div><br/>
				</c:otherwise>
			</c:choose>
			<textarea id="comment" name="comment"></textarea><br/>
			<input id="addCommentBtn" type="button" value="Add comment"><br/>
			<script type="text/javascript">
				var submit = document.getElementById("sbtBtn");
				submit.onclick = onClick;
				var addCommentBtn = document.getElementById("addCommentBtn");
				addCommentBtn.onclick = add;
			</script>
			<div class="section-delimiter">Attachment section</div>
			<c:choose>
				<c:when test="${fn:length(attachments) == 0}">
					<div class="message-container">
						<span id="msg" class="message">Attachments not found</span>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach var="attachment" items="${attachments}">
						<div class="attachment-block">
							<span>Added by: ${attachment.addedBy.firstName} ${attachment.addedBy.lastName}</span><br/>
							<span>Add date: ${attachment.addDate}</span><br/>
							<span>File: <a href="/issuetracker/download?issue=${issue.id}&filename=${attachment.filename}">${attachment.filename}</a></span>
						</div>	
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<form action="/issuetracker/upload?issue=${issue.id}" enctype="multipart/form-data" method="POST">
    			<input type="file" name="file"><br>
    			<input type="Submit" value="Upload File"><br>
			</form>
		</c:otherwise>
	</c:choose>
		
</body>
</html>