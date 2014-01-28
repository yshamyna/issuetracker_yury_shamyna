function submitForm(form, entity) {
	var errMsg = '';
	if (!form.elements['typeInput'].value.trim()) {
		errMsg = entity + ' name is empty.';
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