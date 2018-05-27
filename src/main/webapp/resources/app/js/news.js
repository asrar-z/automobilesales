$(document).ready(function(){

	$('#button').click(function(){
		var f = $('#newsletter')[0].files[0]
		if(f){
		  console.log(f);
		}
	
		$.ajax({
			
			url: "newsletter/send",
			type: 'POST',
			 processData: false,
		     contentType: false,
		     enctype: 'multipart/form-data',
	            data: {
	                file: f
	            },
			xhrFields: {
			      withCredentials: true
			   },
			success: function(data){
	            alert(data);
	        },
	        error: function(err){
	            alert(err);
	        }
			
		});
	
	});
	
});


