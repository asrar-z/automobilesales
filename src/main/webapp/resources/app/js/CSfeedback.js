   switchActiveTab('a_6');
$(document).ready(function() {

	$('#b1').click(function(){

	$('#feedback').validate({
	
		rules:{
			
			firstname:'required',
			lastname:'required',
			email:{
				required: true,
				email: true,
			},
		subject:'required',
		message:'required'
		
		},
	messages: {
		
		firstname:"Please Enter you First name",
		lastname:"Please Enter you Last name",
		email:"Please Enter a valid email address",
		subject:"Cannot be unselected",
		message:"Please enter atleast a sentence"
		
	},
	errorPlacement: function( label, element ) {
  	  	label.insertAfter( element ); 
  	  
    }	
	})
	
	
	
	if($('#feedback').valid())
		{
		
		var formData= $('#feedback').serializeObject();
		
		console.log(formData);
		
		
		
		
   		$.ajax({
       		
   			url: 'feedback/insert',
   			datatype: 'json',
   			data : JSON.stringify(formData),
   			type : 'POST',
   			contentType : "application/json",

   			   
   		   }).done(function(){
   		   

      			$('#success').addClass("alert-success");
				$('#success').html(" <strong>Thanks!</strong>Your Feedback has been recorded :)");
   			
   		})
		
		
		}
	
	
	})
	
});
