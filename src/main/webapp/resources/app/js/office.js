$(document).ready(function() {
	var initPage = function() {
		
CarStore.datatable=$("#office-table").DataTable({
			
			'serverSide' : true,
			"ordering": false,
			'searching': false,
			'ajax' : {
				url : 'admin/offices',
				type : 'POST',
				contentType : "application/json",
				data: function ( d ) {
					delete(d.columns);		
			      return JSON.stringify(d);
			    },
			    dataSrc: "offices",
				xhrFields: {
				      withCredentials: true
				   }
			},
			columns: [
	          { data: 'id' },
	          { data: 'name' }
			],
			select: "single"
	
		});	
		
$('#office-add-button').click(CarStore.addOffice);
$('#office-delete-button').click(CarStore.deleteOffice);

CarStore.datatable.on('select', function () {
	$('#office-open-delete-modal-btn').prop('disabled', false);
});

CarStore.datatable.on('deselect', function () {
	$('#office-open-delete-modal-btn').prop('disabled', true);
});

}
	initPage();
	
});



CarStore.addOffice = function(evt) {
	var formData = $('#office-form').serializeObject();

	$.ajax({
		url : "admin/addOffice",
		data : JSON.stringify(formData),
		type : 'POST',
		contentType : "application/json",
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#office-add-modal').modal('hide');
		$('#office-table').dataTable().fnReloadAjax();
		
	});
};

CarStore.deleteOffice = function(evt) {
	var selectedId = CarStore.datatable.data()[CarStore.datatable.row('.selected')[0]].id;
	$.ajax({
		url : "admin/deleteOffice?id=" + selectedId,
		type : 'DELETE',
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#office-delete-modal').modal('hide');
		$('#office-table').dataTable().fnReloadAjax();
		
	});
};
