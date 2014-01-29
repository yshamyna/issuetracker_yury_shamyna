<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add resolution</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<script src="/issuetracker/js/addEntity.js"></script>
</head>
<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
	<%@ include file="/includes/administratorMenu.html" %>
	<form onsubmit="return submitForm(this, 'Resolution');" method="post" action="/issuetracker/resolutions/add">
		<%@ include file="/includes/addEntityMenu.html" %>
	</form>
	<c:if test="${not empty errMsg}">
		<script type="text/javascript"> 
			dataExistsError("${errMsg}");
		</script> 
	</c:if>
</body>
</html>