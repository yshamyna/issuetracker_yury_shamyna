<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add priority</title>
	<link rel=stylesheet href="/issuetracker/css/menu.css" type="text/css">
	<link rel=stylesheet href="/issuetracker/css/addPriority.css" type="text/css">
	<script src="/issuetracker/js/util.js"></script>
	<script type="text/javascript">
		function add() {
			var msg = checkInputs();
			if (msg) {
				printMessage(msg);
				return false;
			} else {
				var req = getXmlHttp();
				
				req.onreadystatechange = function() { 
		        	if (req.readyState == 4 && req.status == 200) {
		        		var msg = req.responseText;
		        		printMessage(msg);
		            }
		     	};
		     
		     	req.open("post", "/issuetracker/priorities/add", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				var body = "name=" + document.getElementById("priority").value;
				printMessage("Please wait...");
				req.send(body);
			}
		}
	</script>
</head>
<body>
	<%@ include file="/includes/administratorMenu.html" %>
	<div class="container">
		<span class="priorityLabel">Name:</span> 
		<input class="priorityName" id="priority" type="text" name="Name"/>
		<div class="message-container">
			<span id="msg" class="message"></span>
		</div>
		<input id="sbtBtn" class="submitBtn" type="submit" value="Add">
	</div>
	<script type="text/javascript">
		var submitBtn = document.getElementById("sbtBtn");
		submitBtn.onclick = add;
	</script>
</body>
</html>