   switchActiveTab('a_1');
$(document).ready(function() {
//asrar

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
		var office=$('#office').val();
		var initPage = function(office) {
		$.ajax({
			
			    url: "models/getMakes?office="+office,
				type: 'POST',
				dataType : "json",	
				contentType: "application/json; charset=utf-8"
			
		}).done(function(data){
			console.log(data);
	$.each(data.makes,function(i, make){
					//console.log(make.id);
					//console.log(make.name);
					//console.log(make.img);
					var temp='';
					$.ajax({
						async:false,
					    url: "models/getVehicles?make_id="+make.id+"&office="+office,
						type: 'POST',
						dataType : "json",	
						contentType: "application/json;charset=utf-8"
					
				}).done(function(data2){
					console.log(data2);
					
					$.each(data2.vehicles,function(i,vehicle){
						temp += '<tr><td>'+ vehicle.name +'</td><td>'+vehicle.cc +'</td><td>'+vehicle.torque +'</td><td>'+vehicle.speed +'</td><td>'+ vehicle.cost+'</td><td><img src="data:image/jpeg;base64,'+vehicle.img +'" width="195" height="115"></img></td></tr>'
								//console.log(temp)
						})
						
						
						var outer=$('#outer');
					$.each(outer, function(i,tbody){
						console.log('inside outer')
						console.log(temp);
						$(tbody).append($('<tr data-toggle="collapse" data-target=#'+make.name+' class="accordion-toggle">\
							<td><h4>'+make.name+'</h4></td><td> <button class="btn btn-default btn-xs">\
							<img alt="'+make.name+'" src="data:image/jpeg;base64,'+make.img2+'"></img></button> </td></tr>\
							<tr>\
							<td colspan="col-lg-12" class="hiddenRow" >\
							<div class="accordian-body collapse" id='+make.name+'>\
							<table class="table table-striped" id="inner">\
							<thead> \
								<tr><th>Model</th><th>Engine Displacement</th><th>Torque </th><th>Speed</th><th>Cost</th><th>Image</th></tr>\
							</thead>\
							 <tbody>'+ temp+'</tbody>\
							</table>\
							</div>\
							</td>\
							</tr>'))
	
						
					});	
						
						
						
					})	
				

			});
			
		});
		

}
	initPage(office);
	$('#office').change(function(){
		$('#outer').empty();
		console.log("changed")
		var office=$('#office').val();
		initPage(office);
	})
	
});