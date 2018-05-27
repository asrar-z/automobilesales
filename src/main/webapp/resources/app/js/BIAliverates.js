 switchActiveTab('a_3');
 $('#hide').hide();
var MAKE={}
$(document).ready(function() {	
	
	var init=function(){
$.ajax({
		url : 'emisale/makes',
		contentType : "application/json",		
	}).done(function(data) {
		//console.log("displaying data")
		//console.log(data);
		
		var makeSelects = $('.make-selects');
		MAKE=data;
		
		$.each(data.makes, function(i, make) {
			$.each(makeSelects, function(i, select) {
				$(select).append($('<option data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
			});
		});
	})


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


$.ajax({
	url:'insight/getyears',
	contentType : "application/json",
}).done(function(data) {
	
	var yearselects=$('#year');
	$.each(data,function(i,year){
		if(year=='2014')
			yearselects.append($('<option selected="selected" data-display = "' + year + '" value="' + year + '">' + year + '</li>'));		
		else
			yearselects.append($('<option data-display = "' + year + '" value="' + year + '">' + year + '</li>'));
	})
	
})


}
	
	init();
	
$('#make').change(function(){
$('#hide').show();
	var id=$('#make').val();
	$.each(MAKE.makes,function(i,m){
		if(m.id==id)
			{
			$('#logo').html('<img src="data:image/jpeg;base64,'+m.img2+'"></img>');
			}
	})
	
$('#logo').slideDown("slow", function() {});
	$('#live').empty();

	var office=$('#office').val();
		$.ajax({
			async:false,
			url : 'emisale/models?id='+id+"&office="+office,
			contentType : "application/json",		
		}).done(function(data) {
			//console.log("displaying data")
			//console.log(data);
			
			vehicleObject=data;
			var modelSelects = $('#live');
	
			jsonObj = [];
			$.each(data.vehicles, function(i, vehicle) {
				$.each(modelSelects, function(i, select) {
					v={}
					v["name"]=vehicle.name 
					v["price"]=vehicle.cost
					jsonObj.push(v);
					$(select).append($('<tr><td>'+ vehicle.name + '</td><td>' + vehicle.cost + '</td><td></td></tr>'));
				});
			});
			console.log(jsonObj);
			sendData={
					office:office,
					make:id,
					j:jsonObj
					
			}
			$.ajax({
				url:'customerfeedback/liverates',
				type:'POST', 
				data: JSON.stringify(sendData),
				contentType : "application/json",
				success:function(data)
				{
					var table=$('#live');
					var i=0;
					$.each(table.find('tr'),function(i,tr){
						var price=$(tr).find('td:nth-child(2)').text();
						var live=data[i];
						price=price.replace(/[$,Rs.]/g,'');
						live=live.replace(/[$,Rs.]/g,'');
						live=live.trim();
						console.log(price )
						console.log(live)
						if(parseInt(price)<=parseInt(live))							
						$(tr).find('td:nth-child(3)').html('<b>'+data[i++].replace(/ /g,'')+'</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-up fa-2x faa-flash animated" style="color:#5CB85C;"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="report btn btn-primary">Notify Price Change</button>')
						else
						$(tr).find('td:nth-child(3)').html('<b>'+data[i++].replace(/ /g,'')+'</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down fa-2x faa-flash animated" style="color:#D9534F;"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="report btn btn-primary">Notify Price Change</button>')
					})
				}
			})
		})
	
		$('#model').empty().append('<option selected="selected" value="0" >Select Vehicle</option>');
		console.log('contents removed')
		var office=$('#office').val();
		
		$('#morris-line-chart').empty();
		var id=$('#make').val();
		var year=$('#year').val();
			$.ajax({
				url : 'insight/getall?id='+id+"&office="+office+"&year="+year,
				type:'post',
				contentType : "application/json",		
			}).done(function(data) {
				//console.log("displaying data")
				//console.log(data);
				
				vehicleObject=data;
				var modelSelects = $('.vehicle-selects');
		
				
				$.each(data, function(i, vehicle) {
					$.each(modelSelects, function(i, select) {
						$(select).append($('<option data-display = "' + vehicle.name + '" value="' + vehicle.id + '">' + vehicle.name + '</li>'));
					});
				});
			})
		
		
	})
	$('#year').change(function(){
	$('#make').trigger('change');
	})

	$('#model').change(function(){	
		var office=$('#office').val();
			var id=$('#model').val();
			console.log(id);
			$('#morris-line-chart').empty();
		$.each(vehicleObject,function(i,car){
			console.log('matching with' +car.id)
			if(car.id==id)
				{
				$('#revenue').html(car.totalrevenue);
				$('#investment').html(car.investment);
				var rev=parseInt(car.totalrevenue.replace(/[$,]/g,'').replace(/[Rs]/g,''))
				var inv=parseInt(car.investment.replace(/[$,]/g,'').replace(/[Rs]/g,''))
				if(office==2)
				$('#grossprofit').html('Rs '+(rev-inv));
				else
					$('#grossprofit').html('$ '+(rev-inv));
				$('#tax').html(car.tax);
				$('#netprofit').html(car.netprofit);
				$('#margin').html(car.margin);
				return false;
				}
		})
			$.ajax({
				url : 'marketperformance/model?id='+id+"&office="+office,
				contentType : "application/json",		
			}).done(function(data) {
				console.log("chart");
				console.log(data)
			    Morris.Line({
			        // ID of the element in which to draw the chart.
			        element: 'morris-line-chart',

			        data:data,
			        // The name of the data record attribute that contains x-visitss.
			        xkey: 'year',
			        // A list of names of data record attributes that contain y-visitss.
			        ykeys: ['count'],
			        // Labels for the ykeys -- will be displayed when you hover over the
			        // chart.
			        labels: ['No.Sold'],
			        // Disables line smoothing
			        resize: true,
			        gridIntegers: true,
			        ymin: 0,
			        
		
			    });

				

			})
		
})

$('#office').change(function(){
		$('#make').trigger('change');
})


$('body').on('click','.report',function(){
var name=$(this).closest('tr').find('td:nth-child(1)').text();
var old=$(this).closest('tr').find('td:nth-child(2)').text();
var new_price=$(this).closest('tr').find('td:nth-child(3)').find('b').text();
var office=$('#office').val();
console.log(name + old+new_price)
$.ajax({
	url:'updateinventory/pricenotification?name='+name+'&old='+old+'&new_price='+new_price+"&office="+office,
	type:'POST',
})
});
});