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

function checkInputs() {
	var inputs = document.getElementsByTagName("input");
	var msg = "";
	for (var i = 0; i < inputs.length; i++) {
		if (!inputs[i].value.trim()) {
			msg += inputs[i].name + " is empty. ";
		}
	}
	return msg;
}

function removeChilds(node) {
	while(node.childNodes[0]){
		node.removeChild(node.childNodes[0]);
	}
}

function printMessage(msg) {
	var span = document.getElementById("msg");
	span.innerHTML = msg;
}