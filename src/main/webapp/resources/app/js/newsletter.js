switchActiveTab('a_crm');
var target=1;
$(document).ready(function() {
	
	
	
	$('#Upload').click(function(){
	
		console.log(target);
		
		
		 $('#newsletter').validate({
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
	        if($('#newsletter').valid()){
	        	  $(".loading-progress").show();
	        	  $('#success').hide();
	        	var progress = $(".loading-progress").progressTimer({
	        		  timeLimit: 20,
	        		  warningThreshold: 50,
	        		  onFinish: function () {
	        		  $(".loading-progress").hide();
	        		}
	        		});
	        	
	        var formData=$('#newsletter').serializeObject();
	        formData["target"]=target;
	        console.log(formData)
	        $.ajax({
	    		url : 'newsletter/send',	    		
	    		data : JSON.stringify(formData),
	    		type : 'POST',
	    		contentType : "application/json",
	    		xhrFields: {
	    	      withCredentials: true
	    	   }
	    	}).error(function(){
      		  progress.progressTimer('error', {
        		  errorText:'ERROR!',
        		  onFinish:function(){
        		    alert('There was an error processing your Request!');
        		  }
        		});
        		}).done(function(){
        		 progress.progressTimer('complete');
        		 $('#success').show();
       			$('#success').addClass("alert-success");
				$('#success').html(" <strong>Sent Newsletters!</strong> Congrats you're in touch with all your customers");
	    	})
		 
	        }
		
		
		
		
	})
	
	
	$('#preview').click(function(){
	
		
		
		 var x = $('#message').val();
		 $('#preview-modal #myModalLabel').html(x);

		
		
	})
	
$('#1').click(function(){
	target=1;
})
$('#2').click(function(){
	target=2;
})
$('#3').click(function(){
	target=3;
})
	
$('#preview-modal').on('hidden.bs.modal', function(){
	console.log('removing')
	$('body').find('style').remove();
})
	
});