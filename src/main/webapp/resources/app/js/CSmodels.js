$(document).ready(function() {
$.ajax({
	
	url: "customers/list",
	type: 'POST',
	contentType : "application/json",
	data: JSON.stringify({ draw: 0, start: 0, length: 10})
	
})
	
	
});