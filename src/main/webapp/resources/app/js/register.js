$(document).ready(function(){
	
	console.log("doc loaded");
	
	  var options = {
			  'btn-loading': '<i class="fa fa-spinner fa-pulse"></i>',
			  'btn-success': '<i class="fa fa-check"></i>',
			  'btn-error': '<i class="fa fa-remove"></i>',
			  'msg-success': 'All Good! Account Created...',
			  'msg-error': 'Wrong login credentials!',
			  'useAJAX': true,
		  };
	
	
	jQuery(function ($) {
	 $('#register-form').validate({
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
		  		lastName:"required"
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
	 
	/* $.fn.serializeObject = function()
	 {
	     var o = {};
	     var a = this.serializeArray();
	     $.each(a, function() {
	    	 
	    	 if(o[this.name]!=="password_confirm"){
	         if (o[this.name] !== undefined) {
	             if (!o[this.name].push) {
	                 o[this.name] = [o[this.name]];
	             }
	             o[this.name].push(this.value || '');
	         } else {
	             o[this.name] = this.value || '';
	         }
	     }
	     });
	     o["roles"]="CUSTOMER";
	     
	     return o;
	 };*/

		$('#reg_button').click(function(){
			
			if($('#register-form').valid())
				{
			console.log("inside submit");
			var formData = $('#register-form').serializeObject();
			formData["roles"]="CUSTOMER";
			if (typeof(formData.roles) === 'string') {
				formData.roles = [formData.roles];
			}
			console.log(JSON.stringify(formData))
			var url="register/addUserAccount";
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
				console.log("inside done");	
			  	if($(this).valid())
			  	{
			  		form_loading($(this));
			  		
			  		setTimeout(function() {
			  			form_success($(this));
			  		}, 2000);
			  	}
					
				
			});
			return true;
			}
			return false;
			
			
		});
		
		
		  function form_loading($form)
		  {
		    $form.find('[type=submit]').addClass('clicked').html(options['btn-loading']);
		  }
		  
		  function form_success($form)
		  {
			  $form.find('[type=submit]').addClass('success').html(options['btn-success']);
			  $form.find('.login-form-main-message').addClass('show success').html(options['msg-success']);
		  }
	 
	 
	});


});