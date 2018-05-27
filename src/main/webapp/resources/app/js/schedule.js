   switchActiveTab('a_2');
$(document).ready(function() {

	//moment().format("YYYY-MM-DD HH:MM:SS, HH:mm:ss");
        
        $('#datetimepicker1').datetimepicker({
        		
        	sideBySide: true,
        	minDate: moment(),
        	enabledHours: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],

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

        $('#datetimepicker1').on('dp.change',function (){
    		$('#dateselected').val($("#datetimepicker1").data("DateTimePicker").date().format("DD-MMM-YYYY"));
    		$('#timeselected').val($("#datetimepicker1").data("DateTimePicker").date().format("h:mm a"));
        });
        
        console.log($("#datetimepicker1").data("DateTimePicker").date().format("YYYY-MM-DD HH:mm"));
        
        $('#submit_button').click(function(){
        	if($('#office').val()==0)
        		{
        		alert('select an outlet')
        		return false;
        		}
        	console.log($("#datetimepicker1").find("input").val());
        	
       	 $('#appointment').validate({
 		  	rules: {
 		  		name: "required",
 		  	  phnumber: {
 		  			required: true,
 		  			
 		  		},
 		  		dateselected:"required",
 		  		timeselected:"required",
 		  		
 		  		
 		    },
 		    messages: {
             name: "Please enter your Name",
             phnumber: "Please enter your Phone number.. we'll get in touch with you",
             dateselected: "Please select date and time",
             timeselected: "Please enter date and time"
         },
 			 
         
 		  });
       	 
       	 
       	 if($('#appointment').valid())
       	 {
       		var formData = $('#appointment').serialize(); 
       		var temp=$("#datetimepicker1").data("DateTimePicker").date().format("YYYY-MM-DD HH:mm:ss a")
       		console.log(temp)
       		var sendData =
            {
       			userid: null,
                name: document.getElementById('name').value,
                number: document.getElementById('phnumber').value,
                date:temp,
                officeid:$('#office').val()
            };
       		
       		
       		
       		
       		console.log(JSON.stringify(sendData));
       		
       		$.ajax({
       		
       			url: 'schedule/add',
       			datatype: 'json',
       			data : JSON.stringify(sendData),
       			type : 'POST',
       			contentType : "application/json",

       			   
       		   }).done(function(){
       			$('#success').addClass("alert-success");
				$('#success').html(" <strong>Congrats!</strong>You have successfully booked your appointment");
       			
       		})
       		
       		
       		
       	 }
        	
        	
        	
        });
	
});