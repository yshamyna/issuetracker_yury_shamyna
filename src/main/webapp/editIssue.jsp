<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit issue</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
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
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<c:choose>
		<c:when test="${user.role.value eq 'administrator'}">
			<%@ include file="/includes/administratorMenu.html" %>
		</c:when>
		<c:when test="${user.role.value eq 'user'}">
			<%@ include file="/includes/userMenu.html" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/includes/guestMenu.html" %>
		</c:otherwise>
	</c:choose>
	
	
	<c:choose>
		<c:when test="${empty user}">
			<div style="position:relative;width:100%;height:400px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px;">
				<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Create date:</span> 
				<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" value="${issue.createDate}" readonly/>
			
				<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Create by:</span> 
				<input style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" value="${issue.createdBy.firstName} ${issue.createdBy.lastName}" readonly/>
				
				<span style="position:absolute;right:50%;top:60px;margin-right:100px;">Modify date:</span> 
				<input style="position:absolute;left:50%;top:55px;margin-left:-100px;width:200px;" type="text" value="${issue.modifyDate}" readonly/>
				
				<span style="position:absolute;right:50%;top:85px;margin-right:100px;">Modify by:</span> 
				<input style="position:absolute;left:50%;top:80px;margin-left:-100px;width:200px;" type="text" value="${issue.modifyBy.firstName} ${issue.modifyBy.lastName}" readonly/>
				
				<span style="position:absolute;right:50%;top:110px;margin-right:100px;">Summary:</span> 
				<input style="position:absolute;left:50%;top:105px;margin-left:-100px;width:200px;" type="text" value="${issue.summary}" readonly/>
		
				<span style="position:absolute;right:50%;top:135px;margin-right:100px;">Description:</span>
				<textarea style="position:absolute;left:50%;top:130px;margin-left:-100px;max-width:200px;max-height:50px;width:200px;height:50px;" readonly>${issue.description}</textarea>
				
				<span style="position:absolute;right:50%;top:190px;margin-right:100px;">Status:</span> 
				<input style="position:absolute;left:50%;top:185px;margin-left:-100px;width:200px;" type="text" value="${issue.status.value}" readonly/>
				
				<span style="position:absolute;right:50%;top:212px;margin-right:100px;">Type:</span> 
				<input style="position:absolute;left:50%;top:207px;margin-left:-100px;width:200px;" type="text" value="${issue.type.value}" readonly/>
				
				<span style="position:absolute;right:50%;top:234px;margin-right:100px;">Priority:</span> 
				<input style="position:absolute;left:50%;top:229px;margin-left:-100px;width:200px;" type="text" value="${issue.priority.value}" readonly/>
				
				<span style="position:absolute;right:50%;top:256px;margin-right:100px;">Project:</span> 
				<input style="position:absolute;left:50%;top:251px;margin-left:-100px;width:200px;" type="text" value="${issue.project.name}" readonly/>
				
				<span style="position:absolute;right:50%;top:278px;margin-right:100px;">Build found:</span> 
				<input style="position:absolute;left:50%;top:273px;margin-left:-100px;width:200px;" type="text" value="${issue.buildFound.version}" readonly/>
				
				<span style="position:absolute;right:50%;top:300px;margin-right:100px;">Assignee:</span> 
				<input style="position:absolute;left:50%;top:295px;margin-left:-100px;width:200px;" type="text" value="${issue.assignee.firstName} ${issue.assignee.lastName}" readonly/>
			</div>
		</c:when>
		<c:otherwise>
			<div style="width:700px; height:250px;font-family:arial;font-size:10pt; padding-left:5px;padding-top:5px;">
				<div style="height:50px">
					<div style="float:left;width:220px;">
						<span>Create by: </span><br/>
						<input type="text"  style="width:200px;" name="Create by" value="${issue.createdBy.firstName} ${issue.createdBy.lastName}" readonly/>
					</div>
					
					<div style="float:left;width:220px;">
						<span>Status: </span><br/>
						<select id="status" style="width:200px;" name="status">
							<c:forEach var="status" items="${statuses}">
								<c:choose>
									<c:when test="${status.id eq issue.status.id}">
										<option value="${status.id}" selected>${status.value}</option>
									</c:when>
									<c:otherwise>
										<option value="${status.id}">${status.value}</option>		
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					
					<div style="float:left;width:220px; ">
						<span>Project: </span><br/>
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
				
				
				<div style="height:50px;">
					<div style="float:left; width:220px;">
						<span>Create date: </span><br/>
						<input type="text" style="width:200px;" name="Create date" value="${issue.createDate}" readonly/>
					</div>
					
					<div style="float:left; width:220px;">
						<span>Type:</span><br/>
						<select id="type" name="type" style="width:200px;">
							<c:forEach var="type" items="${types}">
								<c:choose>
									<c:when test="${type.id eq issue.type.id}">
										<option value="${type.id}" selected>${type.value}</option>
									</c:when>
									<c:otherwise>
										<option value="${type.id}">${type.value}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					
					<div style="float:left; width:220px;">
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
				
				
				<div style="height:50px;">
					<div style="float:left; width:220px;">
						<span>Modify by: </span><br/>
						<input type="text" style="width:200px;" name="Modify by" 
										value="${issue.modifyBy.firstName} ${issue.modifyBy.lastName}" readonly/>
					</div>
					
					<div style="float:left; width:220px;">
						<span>Priority: </span><br/>
						<select id="priority" name= "priority" style="width:200px;">
							<c:forEach var="priority" items="${priorities}">
								<c:choose>
									<c:when test="${priority.id eq issue.priority.id}">
										<option value="${priority.id}" selected>${priority.value}</option>
									</c:when>
									<c:otherwise>
										<option value="${priority.id}">${priority.value}</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					
					<div style="float:left; width:220px;">
						<span>Summary:</span><br/>
						<textarea style="width:200px;" name="Summary">${issue.summary}</textarea>
					</div>
				</div>
				
				
				<div style="height:50px;">
					<div style="float:left; width:220px;">
						<span>Modify date:</span><br/>
						<input type="text" style="width:200px;" name="Modify date" value="${issue.modifyDate}" readonly/>
					</div>
					
					<div style="float:left; width:220px;">
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
					
					<div style="float:left; width:220px;">
						<span>Description:</span><br/>
						<textarea style="width:200px;" name="Description">${issue.description}</textarea>
					</div>
				</div>
				
				<span id="msg" style="font-size:10pt;color:red;"></span><br/><br/>
				<input id="submitBtn" style="width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit">
			</div>
			<div id="commentsStorage" style="overflow-y:auto;overflow-x:hidden;margin-top:20px;width:100%; height:200px;background-color:rgb(25,28,36)">
				<c:forEach var="comment" items="${comments}">
					<div style="background-color:rgb(206, 227, 253);width:100%;margin-top:2px;padding-left:2px;">
						<span>Added by: ${comment.sender.firstName} ${comment.sender.lastName}</span><br/>
						<span>Add date: ${comment.createDate}</span><br/>
						<span>Comment: ${comment.comment}</span>
					</div>	
				</c:forEach>
			</div><br/>
				<textarea id="comment" name="comment"></textarea><br/>
				<input id="addCommentBtn" type="button" value="Add comment">
			<script type="text/javascript">
				var submit = document.getElementById("submitBtn");
				submit.onclick = onClick;
				var addCommentBtn = document.getElementById("addCommentBtn");
				addCommentBtn.onclick = add;
			</script>
		</c:otherwise>
	</c:choose>
		
</body>
</html>