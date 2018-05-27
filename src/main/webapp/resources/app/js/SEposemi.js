   switchActiveTab('a_3');
   var vehicleObject;
$(document).ready(function() {
	
	
	$('#successmsg').hide();

	$('#fetch').click(function(){
		var id=$('#customerid').val();
		
$.ajax({
	url:'emisale/customers?id='+id,
	contentType : "application/json",	
	success:function(data)
	{
		if(data.result)
		{$('#firstName').val(data.customer.firstname);
		$('#lastName').val(data.customer.lastname);
		console.log(data.customer.email);
		$('#email').val(data.customer.email);
		}
		else
			{
			alert("Customer Already has EMI account! Complete all the payments before proceeding!")
			}
	}

})
	})
	
	
		$.ajax({
		url : 'emisale/makes',
		contentType : "application/json",		
	}).done(function(data) {
		//console.log("displaying data")
		//console.log(data);
		
		var makeSelects = $('.make-selects');
		
		
		$.each(data.makes, function(i, make) {
			$.each(makeSelects, function(i, select) {
				$(select).append($('<option data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
			});
		});
	})
	
	
	
		$.ajax({
		url : 'emisale/offices',
		contentType : "application/json",
		
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
	
	$('#office').change(function(){
		var officeid=$('#office').val();
		$('#make').trigger('change');
		$('#base').val("");
		$.ajax({

			url : 'taxmanagement/get?officeid='+officeid,
		}).done(function(data) {
		
		$('#tax').val(data);
		});
		
	})
	
	
	
	$('#make').change(function(){
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');
		$('#imgdiv').hide();
	var id=$('#make').val();
	var office=$('#office').val();
		$.ajax({
			async:false,
			url : 'emisale/models?id='+id+"&office="+office,
			contentType : "application/json",		
		}).done(function(data) {
			//console.log("displaying data")
			//console.log(data);
			
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
	
		
		var vid=$('#model').val();
		$.each(vehicleObject.vehicles,function(i,vehicle){
	
			if(vehicle.id == vid)
				{
					$('#imgdiv').hide();
					

					if($('#bulk').is(':checked'))
						{
					var	val=vehicle.cost
					val=val.replace(/[$,.]/g,'');
					val=val.replace(/Rs/g,'');
					val=parseInt(val)*$('#quantity').val();				
					$('#base').val(val);
						}
					else
						{
					$('#base').val(vehicle.cost);
						}
					//$('#base').val(vehicle.cost);
					$('#vimg').attr("src",'data:image/jpeg;base64,'+vehicle.img);
					$('#imgdiv').slideDown( "slow", function() {});
					updateTotal();
					
				}
			
			
		})
		
		
		
	})
	
	$('#salebutton').click(function(){
		if($('#res_table tbody').children().length == 0){
			alert('Please fill in all the details');
			return false;
		}
		 console.log("Inside sale");
		var temp=$('#base').val();
		temp=temp.replace(/[$,]/g,'');
		temp=temp.replace(/Rs/g,'');
		 var input2 = parseInt($('#tax').val());
		 input2=(input2/100)+1;
		var id=$('#customerid').val();
		var customer_firstname=$('#firstName').val();
		var customer_lastname=$('#lastName').val();
		var vehicle_id=$('#model').val();
		var emi=1;
		var total_amount=temp * input2;
		var officeid=$('#office').val();
		var t=$('#res_table tr:last th:nth-child(4)').text();
	    console.log(t);
	    t=t.replace(/[$]/g,'');
	    t=t.replace(/Rs/g,'');
	    var profit=parseInt(t)-parseInt($('#loan').val());
		console.log(profit);
	    console.log("Inside sale");
	    console.log("id"+id+"temp"+temp+" input 2:"+ input2+ " total: "+ total_amount+ "t: "+t+"profit: "+profit);
    	var data={
	    	
    		id:id,
    		customer_firstname: customer_firstname,
    		customer_lastname:customer_lastname,
    		vehicle_id:vehicle_id,
    		emi:parseInt(emi),
    		total_amount:total_amount.toString(),
    		officeid:parseInt(officeid),
    		profit:profit.toString()

    	}
    	console.log(JSON.stringify(data));
	    $.ajax({
	    	url:'emisale/sale',
	    	type:'POST',
	    	dataType: 'json',
	    	contentType : "application/json",
	    	data:JSON.stringify(data)

	    	
	    })
	    
	    	    	
	    	var data2={
	    			c_id:id,
	    			firstname: customer_firstname,
	    			lastname:customer_lastname,
	    			total:$('#loan').val(),
	    			duration:$('#duration').val(),
	    		    rate:$('#rate').val(),
	    		    office:parseInt($('#office').val()),
	    		    email:$('#email').val()
	    	}
	    
	    console.log(data2);
	    $.ajax({
	    	url:'emisale/emirecord',
	    	type:'POST',
	    	dataType: 'json',
	    	contentType : "application/json",
	    	data:JSON.stringify(data2)
	    })
	    
	    	$.ajax({
	    	url:'emisale/vehiclesold?id='+vehicle_id+"&office="+officeid,
	    	dataType: 'json',
	    	contentType : "application/json",
	    })
	    
	    
	    
	    $('#successmsg').slideDown( "slow", function() {});
	})


	// set up the amortization schedule calculator
	$(".calculator-amortization").accrue({
		mode: "amortization"
	});
	
	$('#base').bind("change paste keyup",function() {  
	    updateTotal();
	});

	$('#tax').bind("change paste keyup",function() {  
	    updateTotal();
	});

	$('#down').bind("change paste keyup",function() {  
	    updateTotal();
	});
	var updateTotal = function () {
		var temp=$('#base').val();
		console.log(temp);
		temp=temp.replace(/[$,.]/g,'');
		temp=temp.replace(/Rs/g,'');
		console.log(temp);
	  var input1 = parseInt(temp);
	  console.log(input1);
	  var input2 = parseInt($('#tax').val());
	  input2=(input2/100)+1;
	  console.log(input2);
	  var input3 = parseInt($('#down').val());
	  console.log(input3);
          if(!(isNaN(input1)||isNaN(input2)||isNaN(input3)))
	    $('#loan').val((input1 * input2) - input3);
	  }
	
	
	$('#user-add-button').click(function()
			{
		jQuery(function ($) {
			 $('#user-form').validate({
				  	rules: {
				  		userid: "required",
				  	  password: {
				  			required: true,
				  			minlength: 5
				  		},

				  		email: {
				  	    required: true,
				  			email: true
				  		},
				  		firstName:"required",
				  		lastName:"required",

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
			formData["roles"]="CUSTOMER";
			if (typeof(formData.roles) === 'string') {
				formData.roles = [formData.roles];
			}
			console.log(formData);

			var url='admin/addUserAccount';
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
				$('#user-add-modal').modal('toggle');
					
				
			});
			return true;
			}

			});
			})
	
$('#bulk').change(function(){
	console.log("changed")
	if(this.checked)
		{
		$('#q').removeClass('hidden');
		}
	else
		{
		$('#q').addClass('hidden');
		
		}
	$('#model').trigger('change');
})


$('#quantity').change(function(){
	$('#model').trigger('change');
})

});