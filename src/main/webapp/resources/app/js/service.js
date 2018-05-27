   switchActiveTab('a_2');
$(document).ready(function() {

	//moment().format("YYYY-MM-DD HH:MM:SS, HH:mm:ss");
	$.ajax({
			url: "models/getMakes",
			type: 'POST',
			dataType : "json",	
			contentType: "application/json; charset=utf-8"	
	}).done(function(data) {
		var makeSelects = $('.make-selects');
		
		
		$.each(data.makes, function(i, make) {
			$.each(makeSelects, function(i, select) {
				$(select).append($('<option data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
			});
		});
	})
	
	$('#make').change(function(){
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');
		$('#imgdiv').hide();
	var id=$('#make').val();
	
		$.ajax({
			url: "models/getVehicles?make_id="+id+"&office=1",
						type: 'POST',
						dataType : "json",	
						contentType: "application/json;charset=utf-8"
		}).done(function(data) {
			
			vehicleObject=data;
			var modelSelects = $('.vehicle-selects');
	
			
			$.each(data.vehicles, function(i, vehicle) {
				$.each(modelSelects, function(i, select) {
					$(select).append($('<option data-display = "' + vehicle.name + '" value="' + vehicle.id + '">' + vehicle.name + '</li>'));
				});
			});
		})
	})
	
	$('#model').change(function(){
	
	$('#problems').empty().append('<option selected="selected" value="0">Select Problem</option>');
		var vid=$('#model').val();
		$.each(vehicleObject.vehicles,function(i,vehicle){
	
			if(vehicle.id == vid)
				{
					$('#imgdiv').hide();
					$('#vimg').attr("src",'data:image/jpeg;base64,'+vehicle.img);
					$('#imgdiv').slideDown( "slow", function() {});
					
				}	
		})
		
		$.ajax({
			url:'models/problems',
			type:'post',
			contentType: "application/json"
		}).done(function(data){
			$.each(data,function(i,key){
				$('#problems').append($('<option data-display = "' + key.problem + '" value="' + key.id + '">' + key.problem+ '</li>'))
			})
		})

	})
	
        $('#datetimepicker2').datetimepicker({
        		
        	sideBySide: true,
        	minDate: moment(),
        	enabledHours: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
      
        });
        

        $('#datetimepicker2').on('dp.change',function (){
    		$('#dateselected').val($("#datetimepicker2").data("DateTimePicker").date().format("DD-MMM-YYYY"));
    		$('#timeselected').val($("#datetimepicker2").data("DateTimePicker").date().format("h:mm a"));
        });
        
        console.log($("#datetimepicker2").data("DateTimePicker").date().format("YYYY-MM-DD HH:mm"));
        
        $('#submit_button').click(function(){
        	
        	if($('#problems').val()==0)
        		{
        		alert('select a problem category')
        		return false;
        		}
        	console.log($("#datetimepicker2").find("input").val());
        	
       	 $('#appointment').validate({
 		  	rules: {
 		  		name: "required",
 		  	  phnumber: {
 		  			required: true,
 		  			
 		  		},
 		  		dateselected:"required",
 		  		timeselected:"required",
 		  		address:"required"
 		  		
 		    },
 		    messages: {
             name: "Please enter your Name",
             phnumber: "Please enter your Phone number.. we'll get in touch with you",
             dateselected: "Please select date and time",
             timeselected: "Please enter date and time",
             address:"Please enter your address"
         },

         
 		  });
       	 
       	 
       	 if($('#appointment').valid())
       	 {
       		var formData = $('#appointment').serialize(); 
       		var temp=$("#datetimepicker2").data("DateTimePicker").date().format("YYYY-MM-DD HH:mm:ss a")
       		var sendData =
            {
       			userid: null,
                name: document.getElementById('name').value,
                number: document.getElementById('phnumber').value,
                date:temp,
                address:document.getElementById('address').value,
                model:$('#model').val(),
       			make:$('#make').val(),
       			problem:$('#problems').val()
            };
       		
       		
       		
       		
       		console.log(JSON.stringify(sendData));
       		
       		$.ajax({
       		
       			url: 'service/add',
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