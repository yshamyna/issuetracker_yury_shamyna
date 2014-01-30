<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add issue</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script type="text/javascript">
		function submitForm(form) {
			var errMsg = '';
			if (!form.elements['summary'].value.trim()) {
				errMsg = 'Issue summary is empty.';
			}
			if (!form.elements['description'].value.trim()) {
				errMsg += ' Issue description is empty.';
			}
			if (form.elements['type'].selectedIndex == -1) {
				errMsg += ' Issue type is not selected.';
			}
			if (form.elements['status'].selectedIndex == -1) {
				errMsg += ' Issue status is not selected.';
			}
			if (form.elements['priority'].selectedIndex == -1) {
				errMsg += ' Issue priority is not selected.';
			}
			if (form.elements['project'].selectedIndex == -1) {
				errMsg += ' Project is not selected.';
			}
			if (form.elements['build'].selectedIndex == -1) {
				errMsg += ' Project build is not selected.';
			}
			if (errMsg) {
				var span = document.getElementById('errMsg');
				span.innerHTML = errMsg;
				return false;
			} 
		}
					
		function dataExistsError(errMsg) {
			var span = document.getElementById("errMsg");
			span.innerHTML = errMsg;
		}
		
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
	</script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<form onsubmit="return submitForm(this);" method="post" action="/issuetracker/issues/add">
		<div style="position:relative;width:100%;height:280px;background-color:rgb(25, 28, 36);font-family:arial;color:white;font-size:10pt;margin-top:1px;">
			<span style="position:absolute;right:50%;top:10px;margin-right:100px;">Summary:</span> 
			<input style="position:absolute;left:50%;top:5px;margin-left:-100px;width:200px;" type="text" name="summary"/>

			<span style="position:absolute;right:50%;top:35px;margin-right:100px;">Description:</span>
			<textarea style="position:absolute;left:50%;top:30px;margin-left:-100px;max-width:200px;max-height:50px;width:200px;height:50px;" name="description"></textarea>

			<span style="position:absolute;right:50%;top:90px;margin-right:100px;">Status:</span>
			<select style="position:absolute;left:50%;top:85px;margin-left:-100px;width:204px;" name="status"/>
				<c:forEach var="status" items="${statuses}">
					<option value="${status.id}">${status.value}</option>
				</c:forEach>
			</select>
			
			<span style="position:absolute;right:50%;top:112px;margin-right:100px;">Type:</span>
			<select style="position:absolute;left:50%;top:107px;margin-left:-100px;width:204px;" name="type"/>
				<c:forEach var="type" items="${types}">
					<option value="${type.id}">${type.value}</option>
				</c:forEach>
			</select>
			
			<span style="position:absolute;right:50%;top:134px;margin-right:100px;">Priority:</span>
			<select style="position:absolute;left:50%;top:129px;margin-left:-100px;width:204px;" name="priority"/>
				<c:forEach var="priority" items="${priorities}">
					<option value="${priority.id}">${priority.value}</option>
				</c:forEach>
			</select>
			
			<span style="position:absolute;right:50%;top:156px;margin-right:100px;">Project:</span>
			<select id="select-projects" onchange="changeProject();" style="position:absolute;left:50%;top:151px;margin-left:-100px;width:204px;" name="project"/>
				<c:forEach var="project" items="${projects}">
					<option value="${project.id}">${project.name}</option>
				</c:forEach>
			</select>
			
			<span style="position:absolute;right:50%;top:178px;margin-right:100px;">Build found:</span>
			<select id="select-builds" style="position:absolute;left:50%;top:173px;margin-left:-100px;width:204px;" name="build"/>
				<c:forEach var="build" items="${builds}">
					<option value="${build.id}">${build.version}</option>
				</c:forEach>
			</select>
			
			<span style="position:absolute;right:50%;top:200px;margin-right:100px;">Assignee:</span>
			<select style="position:absolute;left:50%;top:195px;margin-left:-100px;width:204px;" name="assignee"/>
				<option value="-1" selected></option>
				<c:forEach var="assignee" items="${assignees}">
					<option value="${assignee.id}">${assignee.firstName} ${assignee.lastName}</option>
				</c:forEach>
			</select>
			
			<div style="text-align:center;position:absolute;top:220px;width:100%">
				<span id="errMsg" style="font-size:10pt;color:red;margin:auto;"></span>
			</div>
			<input style="position:absolute;left:50%;top:240px;margin-left:-100px;width:204px;border:1px solid #3079ed;color:#fff;background-color: #4d90fe;border-radius:3px;height:30px;font-size:12pt;font-weight:bold;" type="submit" value="Add">
		</div>
	</form>
	<c:if test="${not empty errMsg}">
		<script type="text/javascript"> 
			dataExistsError("${errMsg}");
		</script> 
	</c:if>
</body>
</html>