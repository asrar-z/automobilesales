$(document).ready(function() {
//asrar
	var initPage = function() {
		
		$.ajax({
			
			    url: "models/getMakes",
				type: 'POST',
				dataType : "json",	
				contentType: "application/json; charset=utf-8"
			
		}).done(function(data){
			console.log(data);
			$.each(data.makes,function(i, make){
			console.log(make.id);
			console.log(make.name);
			console.log(make.img);
			
			
			var outer=$('#outer');
			$.each(outer, function(i,tbody){
			
				$(tbody).append($('<tr data-toggle="collapse" data-target=#'+make.name+' class="accordion-toggle">\
					<td><h4>'+make.name+'</h4></td><td> <button class="btn btn-default btn-xs">\
					<img alt="'+make.name+'" src="'+make.img+'"></img></button> </td></tr>\
					<tr>\
					<td colspan="col-lg-12" class="hiddenRow" >\
					<div class="accordian-body collapse" id='+make.name+'>\
					<table class="table table-striped" id="inner">\
					<thead> \
						<tr><th>Model</th><th>Engine Displacement</th><th>Torque </th><th>Speed</th><th>Cost</th><th>Image</th></tr>\
					</thead>\
					 <tbody>'+
					 
					$.ajax({
						
					    url: "models/getVehicles?make_id="+make.id,
						type: 'POST',
						dataType : "json",	
						contentType: "application/json;charset=utf-8"
					
				}).done(function(data2){
					console.log(data2);
					
					$.each(data2.vehicles,function(i,vehicle){
						var inner=$('#inner');
						console.log(inner);
						$.each(inner, function(i,tbody){
						$(tbody).append($('<tr><td>'+ vehicle.name +'</td><td>'+vehicle.cc +'</td><td>'+vehicle.torque +'</td><td>'+vehicle.speed +'</td><td>'+ vehicle.cost+'</td><td>\
								<img src="'+vehicle.img +'" width="195" height="115"></img></td></tr>'))
						
						})
					}
					)	
				})+'</tbody></table></div></td></tr>'		))
				
				
				
			});

			});
			
		});
		

}
	initPage();
	
});