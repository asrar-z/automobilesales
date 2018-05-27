$(document).ready(function() {
	
console.log($('#user-add-modal #myModalLabel').data().mode);
	
	var initPage = function() {
		

		  
		CarStore.datatable=$("#account-table").DataTable({
			
			'serverSide' : true,
			'ajax' : {
				url : 'admin/getUsers',
				type : 'POST',
				contentType : "application/json",
				data: function ( d ) {
					// send only data required by backend API

					d.searchParam = d.search.value;
					delete(d.columns);
					delete(d.order);
					delete(d.search);

					d.roleId = $('#user-role-filter').val();
					console.log(d.roleId);
			      return JSON.stringify(d);
			    },
			    dataSrc: "userAccountEntities",
				xhrFields: {
				      withCredentials: true
				   }
			},
			columns: [
	          { data: 'userid' },
	          { data: 'firstName' },
	          { data: 'lastName' },
	          { data: 'email' },
	          { data: 'roles' }
			],
			select: "single"
	
		});
		$.ajax({
			url : 'admin/roles',
			type : 'POST',
			contentType : "application/json",
			data: JSON.stringify({ draw: 0, start: 0, length: 10})
		}).done(function(data) {
			//console.log("displaying data")
			//console.log(data);
			
			var roleSelects = $('.role-selects');
			$.each(data.roles, function(i, role) {
				$.each(roleSelects, function(i, select) {
					$(select).append($('<option data-display = "' + role.name+ '" value="' + role.id + '">' + role.name + '</li>'));
				});
			});
			var roleSelects2 = $('.role-selects2');
			$.each(data.roles, function(i, role) {
				$.each(roleSelects2, function(i, select) {
					$(select).append($('<option data-display = "' + role.name+ '" value="' + role.name + '">' + role.name + '</li>'));
				});
			});
		});	
		
		
		
		$('#user-add-button').click(CarStore.addEmployee);
		$('#user-delete-button').click(CarStore.deleteEmployee);
		CarStore.datatable.on('select', function () {
			$('#user-open-delete-modal-btn').prop('disabled', false);
			$('#user-edit-modal-btn').prop('disabled', false);
		});


		CarStore.datatable.on('deselect', function () {
			$('#user-open-delete-modal-btn').prop('disabled', true);
			$('#user-edit-modal-btn').prop('disabled', true);
		});
		
        $('#user-add-modal').on('hidden.bs.modal', function() {
        	$('#userid').prop('readonly',false);
        	
            $('#user-add-modal #myModalLabel').data().mode = 'add';
            console.log("changed to add");
            $('#user-add-modal #myModalLabel').html('Add new user');
            $('#user-form')[0].reset();
        });
        
        $('#user-edit-modal-btn').on('click', function() {
            var selectedData = CarStore.datatable.row('.selected').data();
            $('#userid').prop('readonly',true)
            $('#user-add-modal #myModalLabel').data().mode = 'update';
            console.log("changed to update");
            $('#user-add-modal #myModalLabel').html('Edit user');
            $('#userid').val(selectedData.userid);
            $('#first-name').val(selectedData.firstName);
            $('#last-name').val(selectedData.lastName);
            $('#email').val(selectedData.email);
          //  $('#roles').val(selectedData.roles);
            $('#roles option[data-display='+selectedData.roles+']').attr('selected', 'selected');
            // $('#password').val(selectedData.password);
        });
        

        $('#user-add-modal #myModalLabel').data().mode = 'add';
	};
initPage();
});


$('#user-role-filter').change(function() { $('#account-table').dataTable().fnReloadAjax(); });






CarStore.addEmployee = function(){
	jQuery(function ($) {
	 $('#user-form').validate({
		  	rules: {
		  		userid: "required",
		  	  password: {
		  			required: true,
		  			minlength: 5
		  		},
		   		password_confirm: {
		  			required: true,
		  			minlength: 5,
		  			equalTo: "#register-form [name=password]"
		  		},
		  		email: {
		  	    required: true,
		  			email: true
		  		},
		  		firstName:"required",
		  		lastName:"required",
		  		roles:"required"
		    },
		    messages: {
            firstName: "Please enter your firstname",
            lastName: "Please enter your lastname",
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            },
            email: "Please enter a valid email address",
            userid: "Please enter a username"
        },
			  errorClass: "form-invalid",
			  errorPlacement: function( label, element ) {
		  	  	label.insertAfter( element ); 
		  	  
		    }
        
        
		  });
	
	if($('#user-form').valid())
		{


	var formData = $('#user-form').serializeObject();
	if (typeof(formData.roles) === 'string') {
		formData.roles = [formData.roles];
	}
	

	var url='admin/' + $('#user-add-modal #myModalLabel').data().mode + 'UserAccount';
	$.ajax({
		url : url,
		datatype: 'json',
		data : JSON.stringify(formData),
		type : 'POST',
		contentType : "application/json",
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function(){
		$('#user-add-modal #myModalLabel').data().mode = 'add';
		$('#account-table').dataTable().fnReloadAjax();
		$('#user-add-modal').modal('toggle');
			
		
	});
	return true;
	}

	});
	

}
	

CarStore.deleteEmployee = function(evt) {
	var selectedId = CarStore.datatable.data()[CarStore.datatable.row('.selected')[0]].userid;
	$.ajax({
		url : "admin/deleteUserAccount?id=" + selectedId,
		type : 'DELETE',
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#user-delete-modal').modal('hide');
		$('#account-table').dataTable().fnReloadAjax();
		
	});
};

