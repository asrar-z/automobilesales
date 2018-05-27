
$('#hide3').hide();
$('#res').hide();  
switchActiveTab('addrm');

$(document).ready(function() {
	   var initPage = function() {
		

$.ajax({
	async:false,
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
			if(office.id==1){
			$(select).append($('<option selected="selected" data-display = "' + office.name + '" value="' + office.id + '">' + office.name + '</li>'));
			}
			else
				$(select).append($('<option  data-display = "' + office.name + '" value="' + office.id + '">' + office.name + '</li>'));

			});
	});
});
$(document).on('change', '.btn-file :file', function() {
  var input = $(this),
      numFiles = input.get(0).files ? input.get(0).files.length : 1,
      label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
  input.trigger('fileselect', [numFiles, label]);
});


    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
        
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;
        
        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }
        
    });
	   


	
	}
	   initPage();
	   
	   
	    var refreshmakes=function(){
	        $.ajax({
	    		url : 'emisale/makes',
	    		contentType : "application/json",		
	    	}).done(function(data) {
	    		//console.log("displaying data")
	    		//console.log(data);
	    		$('#make').empty().append('<option selected="selected" value="0" >Select Make</option>');
	    		$('#rmake').empty().append('<option selected="selected" value="0" >Select Make</option>');
	    		$('#make4').empty().append('<option selected="selected" value="0" >Select Make</option>');
	    		var makeSelects = $('.make-selects');
	    		
	    		
	    		$.each(data.makes, function(i, make) {
	    			$.each(makeSelects, function(i, select) {
	    				$(select).append($('<option data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
	    			});
	    		});
	    	})
	    	
	        }
	   refreshmakes();
	   
	   
	   $("#vehicle-form").submit(function(){
	    	console.log($('#make').val());
	    	$('#makeid').val($('#make').val());
	        var formData = new FormData($(this)[0]);
	       // formData.append($('#make').val());
	console.log(formData);
	var office=$('#office').val();
	        $.ajax({
	            url:'addinventory/uploadv?office='+office,
	            type: 'POST',
	            data: formData,
	            async: false,
	            success: function (data) {
	              $('#succ').slideDown("slow", function() {});
	            },
	            cache: false,
	            contentType: false,
	            processData: false
	        }).done(function(){
	        	$('#succ2').html('<strong>New Vehicle Added</strong>');
	        })

	        return false;
	    });
		
		$('#rmake').change(function(){
			$('#res').hide();
			$('#res').removeClass("alert-success alert-danger");
		id=$('#rmake').val();
		if(id!=0)
			{
			$('#hide3').slideDown("slow", function() {});

		}
		else{
			$('#hide3').hide();}
		})
		
		$('#rm').click(function(){
			var office=$('#office').val();
				$.ajax({
				url:"removeinventory?id="+id,
				type:'Delete',
					success:function(response){
						
						if(response)
						{
							$('#res').addClass("alert-success");
							$('#res').html(" <strong>Manufacturer Removed!</strong>");
							$('#res').slideDown("slow", function() {});
							refreshmakes();
						}
						else
							{
							$('#res').addClass("alert-danger");
							$('#res').html(" <strong>Unable to remove!</strong> There are still vehicles available from this manufacturer delete them first!");
							$('#res').slideDown("slow", function() {});
							}
					}
			}).done(function(){
				
			})
		})
		
	    $("#make-form").submit(function(){

	        var formData = new FormData($(this)[0]);
	console.log(formData);
	        $.ajax({
	            url:'addinventory/upload',
	            type: 'POST',
	            data: formData,
	            async: false,
	            success: function (data) {
	              $('#succ').slideDown("slow", function() {});
	            },
	            cache: false,
	            contentType: false,
	            processData: false
	        }).done(function(){
	        	
	        	 
	        	  $('#succ').html('<strong>Added new Make</strong>');
	        })
	        refreshmakes();
	        return false;
	    });
	   
		$('#make4').change(function(){
			$('#redpanel').addClass("hidden");
			$('#res2').hide();
			console.log('make selected');
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');

	var id=$('#make4').val();
	var office=$('#office').val();
		$.ajax({
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
		$('#res2').hide();
		var vid=$('#model').val();
		$('#cardetails').empty();
		$.each(vehicleObject.vehicles,function(i,vehicle){
		if(vehicle.id == vid)
		{
			$('#cardetails').html('<custom class="col-lg-4"><font size="6">'+vehicle.name+'</font></custom><img src="data:image/jpeg;base64,'+vehicle.img +'" width="195" height="115"></img><br></br>\
					<custom class="col-lg-4"><font size="7">'+vehicle.cost+'</font></custom><custom calss="col-lg-4"><font size="7">Stock Remaining: '+vehicle.quantity+'</custom>');
		}
		$('#redpanel').removeClass("hidden");
		
	})
	
			$('#rm2').click(function(){
				var office=$('#office').val();
				var vid=$('#model').val();
				$.ajax({
				url:"removeinventory/v?id="+vid+"&office="+office,
				type:'Delete',
					success:function(response){
						
						if(response)
						{
							$('#res2').addClass("alert-success");
							$('#res2').html(" <strong>Vehicle Removed!</strong>");
							$('#res2').slideDown("slow", function() {});
							refreshmakes();
						}
						else
							{
							$('#res2').addClass("alert-danger");
							$('#res2').html(" <strong>Snap! Something went wrong</strong>");
							$('#res2').slideDown("slow", function() {});
							}
					}
			})
		})
	   
	})


$('#office').change(function(){
$('#make4').trigger('change');
})


})  
