<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome to issue tracker</title>
		<script type="text/javascript">
			function userNotExistsError(errMsg) {
				var span = document.getElementById("errMsg");
				span.innerHTML = errMsg;
			}
			
			function submitForm(form) {
				var errMsg = '';
				if (!form.elements['emailTxtField'].value.trim()) {
					errMsg = 'E-mail is empty. ';
				}
				if (!form.elements['passTxtField'].value.trim()) {
					errMsg += 'Password is empty.';
				}
				if (errMsg) {
					var span = document.getElementById('errMsg');
					span.innerHTML = errMsg;
					return false;
				}
			}
		</script>
	</head>
		<body style="margin:0;padding:0;background-color:rgb(243, 245, 245);">
		<%@ include file="/includes/guestMenu.html" %>
		<c:if test="${not empty errMsg}">
			<script type="text/javascript"> 
				userNotExistsError("${errMsg}");
			</script> 
		</c:if>
		<div style="padding-left:5px;padding-right:5px;background-color:rgb(243, 245, 245);padding-top:5px;font-family:arial;">
			<table border="1" style="width:100%;border-collapse:collapse;border: 2px solid black;">
				<th style="border: 2px solid black;">id</th>
				<th style="border: 2px solid black;">priority</th>
				<th style="border: 2px solid black;">assignee</th>
				<th style="border: 2px solid black;">type</th>
				<th style="border: 2px solid black;">status</th>
				<th style="border: 2px solid black;">summary</th>
				<tr style="border: 2px solid black;">
					<td style="border: 2px solid black;">1</td>
					<td style="border: 2px solid black;">minor</td>
					<td style="border: 2px solid black;">Main Verymain</td>
					<td style="border: 2px solid black;">cosmetic</td>
					<td style="border: 2px solid black;">in progress</td>
					<td style="border: 2px solid black;">Web browsing Error has occurred</td>
				</tr>
				<tr style="border: 2px solid black;">
					<td style="border: 2px solid black;">2</td>
					<td style="border: 2px solid black;">important</td>
					<td style="border: 2px solid black;">Sidr Sidorov</td>
					<td style="border: 2px solid black;">bug</td>
					<td style="border: 2px solid black;">in progress</td>
					<td style="border: 2px solid black;">Do not work increment at a fixed frequency</td>
				</tr>
			</table>
		</div>
	</body>
</html>