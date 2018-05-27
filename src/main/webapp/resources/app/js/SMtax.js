switchActiveTab('a_office');

$(document).ready(function() {	
	$('#a_tax').addClass('active');
$.ajax({
		url : 'admin/offices',
		type : 'POST',
		contentType : "application/json",
		data: JSON.stringify({ draw: 0, start: 0, length: 10})
	}).done(function(data) {
		//console.log("displaying data")
		//console.log(data);
		
		var officeSelects = $('.office-selects');
		$.each(data.offices, function(i, office) {
			$.each(officeSelects, function(i, select) {
				$(select).append($('<option data-display = "' + office.name + '" value="' + office.id + '">' + office.name + '</li>'));
			});
		});
	});

$('#tax-office-filter').change(function() {
$('#success').hide();
	var officeid=$('#tax-office-filter').val();
	
$.ajax({

	url : 'taxmanagement/get?officeid='+officeid,
}).done(function(data) {
$('#current').val(data);
});

});



$('#b1').click(function(){
	var oid=$('#tax-office-filter').val();
	var r=$('#new').val();
	$('#current').val(r);
	sendData={officeid:oid , rate:r};
	console.log( JSON.stringify(sendData));
	
	$.ajax({
		
	url:'taxmanagement/update',
	type : 'POST',
	 contentType : "application/json",
	data: JSON.stringify(sendData)
	
	}).done(function(){
		$('#success').show();
		$('#success').addClass("alert-success");
		$('#success').html(" <strong>Tax Updated!</strong>");
	})
	
});




})