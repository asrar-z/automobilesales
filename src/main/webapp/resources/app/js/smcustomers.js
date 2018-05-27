switchActiveTab('a_crm');
$(document).ready(function() {



	CarStore.datatable=$("#customer-table").DataTable({
		
		'serverSide' : true,
		'ordering':false,
		'ajax' : {
			url : 'customers/list',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				d.searchParam = d.search.value;
				delete(d.columns);
				delete(d.order);
				delete(d.search);
		      return JSON.stringify(d);
		    },
		    dataSrc: "customers",
			xhrFields: {
			      withCredentials: true
			   }
		},

		columns: [
          { data: 'id' },
          { data: 'firstname' },
          { data: 'lastname' },
          { data: 'emi' },
          { data: 'email' },
          {
              "data": null,
              "defaultContent": '<button class="btn btn-default" id="mail_button" data-toggle="modal" data-target="#mail-modal"><i class="fa fa-envelope" style="color:#18bc9c;"></i></button>'
           }
		],


	});
	
	 $('#customer-table tbody').on( 'click', 'button', function () {
	        var data = CarStore.datatable.row( $(this).parents('tr') ).data();
	        $('#emailid').prop('readonly',true)
	       $('#emailid').val(data.email);

	        
	        
	    } );
	
	 $('#send_button').click(function(){
	
		 $('#mail-form').validate({
			  	rules: {

			  		subject:"required",
			  		message:"required",
			  
			    },
			    messages: {
	            subject: "subject cannot be empty",
	            message: "You almost sent an empty message to customer",

	        },
			  errorPlacement: function( label, element ) {
			  	  	label.insertAfter( element ); 
			  	  
			    }   
		 
		 
		 });
	        if($('#mail-form').valid()){
	        var formData=$('#mail-form').serializeObject();
	        console.log(formData)
	        $.ajax({
	    		url : 'customers/mail',
	    		datatype: 'json',
	    		data : JSON.stringify(formData),
	    		type : 'POST',
	    		contentType : "application/json",
	    		xhrFields: {
	    	      withCredentials: true
	    	   }
	    	}).done(function(){
	    		$('#success').html("<strong>Mail Sent</strong>")
	    	});
		 
	        }
	        $('#mail-modal').modal('toggle');
	 })
	 
	

});
