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
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function statusIsClosed() {
			if ("${issue.status.name}" === "closed")  {
				document.getElementById("project").disabled = true;
				document.getElementById("type").disabled = true;
				document.getElementById("priority").disabled = true;
				document.getElementById("assignee").disabled = true;
				document.getElementById("build").disabled = true;
				document.getElementById("summary").disabled = true;
				document.getElementById("description").disabled = true;
				document.getElementById("resolution").disabled = true;
				document.getElementById("moEdit").disabled = true;
				document.getElementById("moClose").disabled = true;
				
				var status = document.getElementById("status");
				for (var i = 0; i < status.options.length; i++) {
					var text = status.options[i].innerHTML;
					if (text === "closed" || text === "reopened") {
						status.options[i].disabled = false;
					} else {
						status.options[i].disabled = true;
					}
				}
			} else {
				document.getElementById("project").disabled = false;
				document.getElementById("type").disabled = false;
				document.getElementById("priority").disabled = false;
				document.getElementById("assignee").disabled = false;
				document.getElementById("build").disabled = false;
				document.getElementById("summary").disabled = false;
				document.getElementById("description").disabled = false;
				document.getElementById("resolution").disabled = false;
				document.getElementById("moEdit").disabled = false;
				document.getElementById("moClose").disabled = false;
				changeMode();
			}
		}
		
		function changeMode() {
			var editMode = document.getElementById("moEdit");
			var status = document.getElementById("status");
			if (editMode.checked) {
				for (var i = 0; i < status.options.length; i++) {
					var text = status.options[i].innerHTML;
					if (text === "in progress" || text === "assigned" || text === "new") {
						status.options[i].disabled = false;
					} else {
						status.options[i].disabled = true;
					}
				}
				text = status.options[status.selectedIndex].innerHTML;
				if (text !== "in progress" && text !== "assigned" && text !== "new") {
					status.selectedIndex = 0;
				}
				document.getElementById("resolution").disabled = true;
			} else {
				for (var i = 0; i < status.options.length; i++) {
					var text = status.options[i].innerHTML;
					if (text === "in progress" || text === "resolved" || text === "closed") {
						status.options[i].disabled = false;
					} else {
						status.options[i].disabled = true;
					}
				}
				if (text !== "in progress" && text !== "resolved" && text !== "closed") {
					status.selectedIndex = 2;
				}
				document.getElementById("resolution").disabled = false;
			}
			changeStatus();
		}
	
		function changeProject() {
			var req = getXmlHttp();
			req.onreadystatechange = function() { 
	        	if (req.readyState == 4 && req.status == 200) {
	        		var jsonText = req.responseText;
	        		var jsonData = JSON.parse(jsonText);
	        		var builds = jsonData.builds;
	        		var buildList = document.getElementById("build");
	        		removeChilds(buildList);
	        		for (var i = 0; i < builds.length; i++) {
	        			var id = builds[i].id;
	        			var version = builds[i].version;
	        			var option = document.createElement("option");
	        			option.setAttribute("value", id);
	        			option.innerHTML = version;
	        			buildList.appendChild(option);
	        		}
	            }
	     	};
			var projectList = document.getElementById('project');
			
			var value = projectList.options[projectList.selectedIndex].value;
			req.open('post', '/issuetracker/builds', true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("id=" + value);
		}
		
		function update() {
			var summary = document.getElementById("summary").value.trim();
			var description = document.getElementById("description").value.trim();
			var msg = "";
			if (!summary) {
				msg = "Summary is empty. ";
			}
			if (!description) {
				msg += "Description is empty.";
			}
			if (msg) {
				printMessage(msg);
				return;
			} else {
				var req = getXmlHttp();
				
				req.onreadystatechange = function() { 
		        	if (req.readyState == 4 && req.status == 200) {
		        		var msg = req.responseText;
		        		printMessage(msg);
		            }
		     	};
		     	
		     	var sel = document.getElementById("status");
		     	var status = sel.options[sel.selectedIndex].value;
		     	var statusName = sel.options[sel.selectedIndex].innerHTML;
		     	sel = document.getElementById("assignee");
		     	var assignee = sel.options[sel.selectedIndex].value;
		     	var summary = document.getElementById("summary").value;
		     	var description = document.getElementById("description").value;
		     	sel = document.getElementById("type");
		     	var type = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("priority");
		     	var priority = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("project");
		     	var project = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("build");
		     	var build = sel.options[sel.selectedIndex].value;
		     	sel = document.getElementById("resolution");
		     	var resolution = sel.options[sel.selectedIndex].value;
		     	
		     	req.open("post", "/issuetracker/issues/edit", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				
				var body="";
				if ("${issue.status.name}" === "closed") {
					body = "id=${issue.id}&summary=" + summary + "&description=" + description
					+ "&statusId=" + status + "&typeId=" + type 
					+ "&priorityId=" + priority + "&projectId=" + project
					+ "&buildId=" + build + "&assigneeId=-1&resolutionId=-1";
		     	} else{
		     		var editMode = document.getElementById("moEdit");
					if (editMode.checked) {
						if (assignee == -1 && statusName != 'new') {
				     		printMessage("Status must be 'assigned' or 'in progress' (or you need select assignee).");
				     		return;
				     	}
				     	if (assignee != -1 && statusName == 'new') {
				     		printMessage("Status must be 'assigned' or 'in progress'.");
				     		return;
				     	} 
				     	body = "id=${issue.id}&summary=" + summary + "&description=" + description
						+ "&statusId=" + status + "&typeId=" + type 
						+ "&priorityId=" + priority + "&projectId=" + project
						+ "&buildId=" + build + "&assigneeId=" + assignee + "&resolutionId=-1";
					} else {
						if (assignee == -1) {
							printMessage("You need select assignee.");
							return;
						}
						body = "id=${issue.id}&summary=" + summary + "&description=" + description
						+ "&statusId=" + status + "&typeId=" + type 
						+ "&priorityId=" + priority + "&projectId=" + project
						+ "&buildId=" + build + "&assigneeId=" + assignee;
						if (statusName === "in progress") {
							body += "&resolutionId=-1";
						} else {
							body += "&resolutionId=" + resolution;
						}
					}
		     	}
				printMessage("Please wait...");
				req.send(body);
			}
		}
		
		function send() {
			var comment = document.getElementById("enter-comment-area").value.trim();
			if (!comment) return;
			
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
	        		newComment.setAttribute("class", "comment-block");
	        		
	        		var commentsStorage = document.getElementById("commentsStorage");
	        		commentsStorage.appendChild(newComment);
	        		
	        		document.getElementById("enter-comment-area").value = "";
	        		
	        		var msgDiv = document.getElementById("comment-message-container");
	        		if (msgDiv != null) {
	        			commentsStorage.removeChild(msgDiv);
	        		}
	            }
	     	};
			req.open("post", "/issuetracker/comments/add", true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("issueId=${issue.id}&comment=" + comment);
		}
		
		function changeStatus() {
			var status = document.getElementById("status");
			var statusValue = status.options[status.selectedIndex].innerHTML;
			if (statusValue === 'closed' || statusValue === 'resolved') {
				document.getElementById("resolution").disabled = false;
			} else {
				document.getElementById("resolution").disabled = true;
			}
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
	<c:choose>
		<c:when test="${empty user}">
			<div class="section-delimiter">Edit section</div>
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
						<input class="ceil" type="text" value="${issue.formatCreateDate}" readonly/>
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
						<input class="ceil" type="text" value="${issue.formatModifyDate}" readonly/>
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
				<div class="row">
					<div class="col">
						<span>ID:</span><br/> 
						<input class="ceil" type="text" value="${issue.id}" readonly/>
					</div>
					<div class="col">
						<span>Resolution:</span><br/> 
						<input class="ceil" type="text" value="${issue.resolution.name}" readonly/>
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
			<div style="font-family:arial;font-size:10pt;padding-top:10px;padding-left:10px;padding-bottom:10px;">
				Mode: <input id="moEdit" type="radio" name="mode" value="edit" checked/> Edit
					<input id="moClose" type="radio" name="mode" value="Close" /> Close
			</div>
			<div class="section-delimiter">Edit section</div>
			<div class="container">
				<div class="row">
					<div class="col">
						<span>Create by:</span><br/>
						<input class="ceil" type="text" name="Create by" value="${issue.createBy.firstName} ${issue.createBy.lastName}" readonly/>
					</div>
					<div class="col">
						<span>Status:</span><br/>
						<select id="status" class="down-list" name="status">
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
						<select id="project" name="project" class="down-list">
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
						<input class="ceil" type="text" name="Create date" value="${issue.formatCreateDate}" readonly/>
					</div>
					<div class="col">
						<span>Type:</span><br/>
						<select id="type" name="type" class="down-list">
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
						<select id="build" name="build" class="down-list">
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
						<input class="ceil" type="text" name="Modify by" 
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
						<span>Assignee:</span><br/>
						<select id="assignee" name="assignee" class="down-list">
							<option value="-1"></option>
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
				</div>
				<div class="row">
					<div class="col">
						<span>Modify date:</span><br/>
						<input class="ceil" type="text" name="Modify date" value="${issue.formatModifyDate}" readonly/>
					</div>
					<div class="col">
						<span>ID:</span><br/> 
						<input class="ceil" type="text" value="${issue.id}" readonly/>
					</div>
					<div class="col">
						<span>Resolution:</span><br/> 
						<select id="resolution" name="resolution" class="down-list">
							<c:forEach var="resolution" items="${resolutions}">
								<c:choose>
									<c:when test="${resolution.id eq issue.resolution.id}">
										<option value="${resolution.id}" selected>${resolution.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${resolution.id}">${resolution.name}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<span>Summary:</span><br/>
						<textarea id="summary" class="summary" name="Summary">${issue.summary}</textarea>
					</div>
					<div class="col">
						<span>Description:</span><br/>
						<textarea id="description" class="description" name="Description">${issue.description}</textarea>
					</div>
				</div>
			</div>
			<div class="update-error-msg">
				<div id="msg" class="update-message"></div>
			</div>
			<div class="update-btn-container">
				<input id="sbtBtn" class="submitBtn" type="button" value="Update" />
			</div>
			<div class="section-delimiter">Comment section</div>
			<div id="commentsStorage" class="comment-storage">
				<c:if test="${fn:length(comments) == 0}">
					<div id="comment-message-container" class="comment-message-container">
						<span class="comment-message">Comments not found</span>
					</div>
				</c:if>
				<c:forEach var="comment" items="${comments}">
					<div class="comment-block">
						<span>Added by: ${comment.sender.firstName} ${comment.sender.lastName}</span><br/>
						<span>Add date: ${comment.createDate}</span><br/>
						<span>Comment: ${comment.comment}</span>
					</div>	
				</c:forEach>
			</div><br/>
			<textarea id="enter-comment-area" name="comment"></textarea><br/>
			<input id="addCommentBtn" type="button" value="Send"><br/>
			<script type="text/javascript">
				var submit = document.getElementById("sbtBtn");
				submit.onclick = update;
				var addCommentBtn = document.getElementById("addCommentBtn");
				addCommentBtn.onclick = send;
				var project = document.getElementById("project");
				project.onchange = changeProject;
				document.getElementById("status").onchange = changeStatus;
				
				document.getElementById("moEdit").onchange = changeMode;
				document.getElementById("moClose").onchange = changeMode;
				
				statusIsClosed();
			</script>
			<div class="section-delimiter">Attachment section</div>
			<c:choose>
				<c:when test="${fn:length(attachments) == 0}">
					<div class="message-container">
						<span class="message">Attachments not found</span>
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