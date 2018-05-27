switchActiveTab('update');
$(document).ready(function() {
$('#succ').hide();
$('#hide1').hide();
$('#hide2').hide();

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
    
    $("#make-form").submit(function(){
if($('#make-name').val()==''||$('#make').val()==0)
	{
	alert('Cannot be empty')
	return false;
	}
        var formData = new FormData($(this)[0]);
console.log(formData);
        $.ajax({
            url:'updateinventory/update',
            type: 'POST',
            data: formData,
            async: false,
            success: function (data) {
            	$('#success').show();
              $('#success').html('<strong>Make details updated</strong>')
            },
            cache: false,
            contentType: false,
            processData: false
        });

        return false;
    });
    
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
	
	$('#make').change(function(){
	
		$('#hide1').slideDown("slow", function() {});
		
		$('#success').hide();
		
	})
	
	
		$('#make2').change(function(){
			$('#hide2').hide();
			console.log('make selected');
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');
var office=$('#office').val();
	var id=$('#make2').val();
	$('#success2').hide();
	
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
	
    		$('#hide2').slideDown("slow", function() {});
		var vid=$('#model').val();
		$.each(vehicleObject.vehicles,function(i,vehicle){
	
			if(vehicle.id == vid)
				{
					$('#vehicle-name').val(vehicle.name);
					$('#vehicle-id').val(vehicle.id);
					$('#vehicle-cc').val(vehicle.cc);
					$('#vehicle-torque').val(vehicle.torque);
					$('#vehicle-speed').val(vehicle.speed);
					$('#vehicle-cost').val(vehicle.cost);
					$('#vehicle-bp').val(vehicle.bp);
					$('#vehicle-quantity').val(vehicle.quantity);
					$('#oldid').val(vehicle.id);
			    	$('#makeid').val($('#make2').val());
				}
			
			
		})
		
		
		
	})
    $("#vehicle-form").submit(function(){
    	$('#office-fid').val($('#office').val());

        var formData = new FormData($(this)[0]);
       // formData.append($('#make').val());

        	
console.log(formData);
        $.ajax({
            url:'updateinventory/updatev',
            type: 'POST',
            data: formData,
            async: false,
            success: function (data) {
            	$('#success2').show();
            	$('#success2').html('<strong>Vehicle details updated</strong>')
            },
            cache: false,
            contentType: false,
            processData: false
        });

        return false;
    });
	
	$('#office').change(function(){
		$('#make2').trigger('change');
	})
	
})