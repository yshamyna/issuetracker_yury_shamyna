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
			update("${issue.id}", "/issuetracker/issue/edit", data);
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
	<div style="position:relative;width:100%;height:400px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px;">
		<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Create date:</span> 
		<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="Create date" value="${issue.createDate}" readonly/>
	
		<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Create by:</span> 
		<input style="position:absolute;left:50%;top:30px;margin-left:-100px;width:200px;" type="text" name="Create by" value="${issue.createdBy.firstName} ${issue.createdBy.lastName}" readonly/>
		
		<span style="position:absolute;right:50%;top:60px;margin-right:100px;">Modify date:</span> 
		<input style="position:absolute;left:50%;top:55px;margin-left:-100px;width:200px;" type="text" name="Modify date" value="${issue.modifyDate}" readonly/>
		
		<span style="position:absolute;right:50%;top:85px;margin-right:100px;">Modify by:</span> 
		<input style="position:absolute;left:50%;top:80px;margin-left:-100px;width:200px;" type="text" name="Modify by" value="${issue.modifyBy.firstName} ${issue.modifyBy.lastName}" readonly/>
	
		<span style="position:absolute;right:50%;top:110px;margin-right:100px;">Summary:</span> 
		<input id="summary" style="position:absolute;left:50%;top:105px;margin-left:-100px;width:200px;" type="text" name="Summary" value="${issue.summary}"/>

		<span style="position:absolute;right:50%;top:135px;margin-right:100px;">Description:</span>
		<textarea id="description" style="position:absolute;left:50%;top:130px;margin-left:-100px;max-width:200px;max-height:50px;width:200px;height:50px;" name="Description">${issue.description}</textarea>

		<span style="position:absolute;right:50%;top:190px;margin-right:100px;">Status:</span>
		<select id="status" style="position:absolute;left:50%;top:185px;margin-left:-100px;width:204px;" name="status"/>
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
		
		<span style="position:absolute;right:50%;top:212px;margin-right:100px;">Type:</span>
		<select id="type" style="position:absolute;left:50%;top:207px;margin-left:-100px;width:204px;" name="type"/>
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
		
		<span style="position:absolute;right:50%;top:234px;margin-right:100px;">Priority:</span>
		<select id="priority" style="position:absolute;left:50%;top:229px;margin-left:-100px;width:204px;" name="priority"/>
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
		
		<span style="position:absolute;right:50%;top:256px;margin-right:100px;">Project:</span>
		<select id="select-projects" onchange="changeProject();" style="position:absolute;left:50%;top:251px;margin-left:-100px;width:204px;" name="project"/>
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
		
		<span style="position:absolute;right:50%;top:278px;margin-right:100px;">Build found:</span>
		<select id="select-builds" style="position:absolute;left:50%;top:273px;margin-left:-100px;width:204px;" name="build"/>
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
		
		<span style="position:absolute;right:50%;top:300px;margin-right:100px;">Assignee:</span>
		<select id="assignee" style="position:absolute;left:50%;top:295px;margin-left:-100px;width:204px;" name="assignee"/>
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
		
		<div style="text-align:center;position:absolute;top:320px;width:100%">
			<span id="err" style="font-size:10pt;color:red;margin:auto;"></span>
		</div>
		<input id="submitBtn" style="position:absolute;left:50%;top:340px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="button" value="Edit">
	</div>
	<script type="text/javascript">
		var submit = document.getElementById("submitBtn");
		submit.onclick = onClick;
	</script>
</body>
</html>