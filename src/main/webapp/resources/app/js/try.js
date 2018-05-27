$(document).ready(function() {
	
	$("#reg_form").validate({
		rules: {
		 user     : "required",
		},
	messages: {
        user: "Please enter your firstname",
	}
	});
	
	$("#reg_button").click(function(){
		
		console.log("button clicked");
		if($("#ref_form").valid())
		{
		
		}
		
		
		
	});
	
});