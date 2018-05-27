   switchActiveTab('a_emp');
$(document).ready(function() {
	
	$('#employee-office-filter').change(function() { $('#employee-table').dataTable().fnReloadAjax(); });
	
	CarStore.datatable=$("#employee-table").DataTable({
		
		'serverSide' : true,
		'ordering': false,
		'searching': false,
		'ajax' : {
			url : 'employees/get',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				d.searchParam = d.search.value;
				delete(d.columns);
				delete(d.order);
				delete(d.search);

				d.officeid = $('#employee-office-filter').val();
				console.log(d.officeid);
		      return JSON.stringify(d);
		    },
		    dataSrc: "employees",
			xhrFields: {
			      withCredentials: true
			   }
		},
		columns: [
          { data: 'id' },
          { data: 'firstname' },
          { data: 'lastname' },
          { data: 'email' },
          { data: 'role' }, 
          { data: 'salary' },
          { data: 'office' },

		],
		select: "single"

	});
		


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
	
	
	
	$('#employee-edit-modal-btn').on('click', function() {
        var selectedData = CarStore.datatable.row('.selected').data();
        $('#id').prop('readonly',true)
        $('#firstname').prop('readonly',true)
        $('#lastname').prop('readonly',true)
        $('#email').prop('readonly',true)
        $('#role').prop('readonly',true)
        $('#id').val(selectedData.id);
        $('#firstname').val(selectedData.firstname);
        $('#lastname').val(selectedData.lastname);
        $('#email').val(selectedData.email);
        $('#role').val(selectedData.role);
        // $('#password').val(selectedData.password);
    });
	CarStore.datatable.on('select', function () {
		$('#employee-edit-modal-btn').prop('disabled', false);
	});


	CarStore.datatable.on('deselect', function () {
		$('#employee-edit-modal-btn').prop('disabled', true);
	});
	
	 $('#employee-add-modal #myModalLabel').data().mode = 'update';	
	 
	 
	 $('#employee-add-button').click(function(){
		 
		 var formData = $('#employee-form').serializeObject();
		 
		 console.log(formData);
		 
		$.ajax({
		
		url: 'employees/update',
			data : JSON.stringify(formData),
			type : 'POST',
			contentType : "application/json",
			xhrFields: {
		      withCredentials: true
		   }	
			
		}).done(function(){
		
			$('#employee-table').dataTable().fnReloadAjax();
			$('#employee-add-modal').modal('toggle');
			
			
		})
		
		
		
		
		
		 
		 
		 
		 
		 
		 
		 
		 
		 
	 });
	 
	 
	 
	 
	 
	 
});