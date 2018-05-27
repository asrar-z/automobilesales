   switchActiveTab('a_3');
$(document).ready(function() {
		
	$('#successmsg').hide();
	var vehicleObject;
$.ajax({
		url : 'emisale/makes',
		contentType : "application/json",		
	}).done(function(data) {
		//console.log("displaying data")
		console.log(data);
		
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
		console.log(data);
		
		var officeSelects = $('.office-selects');
		$.each(data.offices, function(i, office) {
			$.each(officeSelects, function(i, select) {
				$(select).append($('<option data-display = "' + office.name + '" value="' + office.id + '">' + office.name + '</li>'));
			});
		});
	});

$('#make').change(function(){
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');
		$('#imgdiv').hide();
	var id=$('#make').val();
	var office=$('#office').val();
	if(office!=0){
		$.ajax({
			url : 'emisale/models?id='+id+"&office="+office,
			contentType : "application/json",		
		}).done(function(data) {
			//console.log("displaying data")
			console.log(data);
			
			vehicleObject=data;
			var modelSelects = $('.vehicle-selects');
	
			
			$.each(data.vehicles, function(i, vehicle) {
				$.each(modelSelects, function(i, select) {
					$(select).append($('<option data-display = "' + vehicle.name + '" value="' + vehicle.id + '">' + vehicle.name + '</li>'));
				});
			});
		})
	}
	})

		$('#office').change(function(){
			$('#make').trigger('change');
		var officeid=$('#office').val();
	if(officeid!=0){
		$.ajax({

			url : 'taxmanagement/get?officeid='+officeid,
		}).done(function(data) {
		
		$('#tax').val(data);
		updateTotal();
		});
	}
	})
	
	$('#model').change(function(){
	
		
		var vid=$('#model').val();
		$.each(vehicleObject.vehicles,function(i,vehicle){
	
			if(vehicle.id == vid)
				{
					$('#imgdiv').hide();
					$('#base').val(vehicle.cost);
					$('#vimg').attr("src",'data:image/jpeg;base64,'+vehicle.img);
					$('#imgdiv').slideDown( "slow", function() {});
					updateTotal();
				}
			
			
		})

		
	})
	
	
	$('#salebutton').click(function(){

		var id=$('#firstName').val();
		var customer_firstname=$('#firstName').val();
		var customer_lastname=$('#lastName').val();
		var vehicle_id=$('#model').val();
		var emi=0;
		var total_amount=$('#down').val();
		var officeid=$('#office').val();
		if(id==null || id==""||customer_firstname==""||customer_lastname==""||vehicle_id==""||vehicle_id==0||total_amount==""||officeid==0)
			{
			alert('Fill in all the fields')
			return false;
			}
		console.log("null dude")
    	var data={
	    	
    		id:id,
    		customer_firstname:customer_firstname,
    		customer_lastname:customer_lastname,
    		vehicle_id:vehicle_id,
    		emi:parseInt(emi),
    		total_amount:total_amount.toString(),
    		officeid:parseInt(officeid)

    	}
    	console.log(JSON.stringify(data));
	    $.ajax({
	    	url:'emisale/sale',
	    	type:'POST',
	    	dataType: 'json',
	    	contentType : "application/json",
	    	data:JSON.stringify(data)

	    	
	    })

	    
	    	$.ajax({
	    	url:'emisale/vehiclesold?id='+vehicle_id+"&office="+officeid,
	    	dataType: 'json',
	    	contentType : "application/json",
	    })
	    
	    
	    
	    $('#successmsg').slideDown( "slow", function() {});
	})
	
	
			
		var updateTotal = function () {
		var temp=$('#base').val();
		console.log(temp);
		temp=temp.replace(/[Rs.$,]/g,'');
		console.log(temp);
	  var input1 = parseInt(temp);
	  console.log(input1);
	  var input2 = parseInt($('#tax').val());
	  input2=(input2/100)+1;
	  console.log(input2);

          if(!(isNaN(input1)||isNaN(input2)))
	    $('#down').val((input1 * input2));
	  }

})