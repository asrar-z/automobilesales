switchActiveTab('a_appointments');
var services={};
$(document).ready(function() {
	
	var srerefresh=function(){
		
		
		$.ajax({
			url : 'salesappointments/getse?id=1&type=2',
			contentType : "application/json",
			
		}).done(function(data) {
			//console.log("displaying data")
			//console.log(data);
			
			var temp = $('.sre-selects');
			$.each(data, function(i, se) {
				//console.log(se.id+ se.firstname)
				$.each(temp, function(i, select) {
					//console.log('appending')
					$(select).append($('<option data-display = "' + se.id + '" value="' + se.id + '">' + se.firstname + '('+se.count +')</li>'));
				});
			});
		});
		
	}
	var initPage = function() {
	CarStore.datatable=$("#service").DataTable({
		'serverSide' : true,
		'ordering':false,
		'searching':false,
		'ajax' : {
			async:false,
			url : 'serviceappointments/get',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				
				delete(d.columns);
				delete(d.order);
				delete(d.search);
				d.filter=$('#date-filter').val();
				console.log(d.filter);
				console.log(d);
		      return JSON.stringify(d);
		    },
		    dataSrc: function(json){
		    	console.log(json);
		    	services=json.services;
		    	return json.services;
		    },
			xhrFields: {
			      withCredentials: true
			   }
		},
		columns: [
		          {data:'app_id'},
		          { data: 'id' },
		          { data: 'name' },
		          { data: 'number' },
          { data: 'date' },
          { data: 'status' },
          {
              "data": null,
              "defaultContent": '<select class="form-control sre-selects">\
            	  <option selected="selected" value="0" >Assign Service Executive</option>\
					</select>\
            	  <button type="button" class="assignbutton btn btn-primary ">\
					<span class=" fa fa-forward" aria-hidden="true"></span>\
					<span >Assign Executive</span>\
				</button>'
          },
          {
              "data": null,
              "defaultContent": ''
           }
		],
		"createdRow": function ( row, data, index ){
			$('td', row).eq(4).html(moment($('td', row).eq(4).html()).format('DD-MMM-YYYY hh:mm a'));
		},
		"fnDrawCallback": function(oSettings ) {
			if($('#service').dataTable().fnSettings().aoData.length!=0){
			srerefresh();
			var i=0;
			if($(this).find('td:nth-child(1)')!=null)
			{
			$('#service').find('tbody').find('tr').each(function(){
			if($(this).find('td:nth-child(6)').html()=='Completed')
				{
				$(this).find('td:nth-child(6)').addClass('success');
				$(this).find('td:nth-child(7)').html('Successfully Completed!');
				$(this).find('td:nth-child(8)').html('Successfully Completed!');
				++i;				
				}
			else 
			{	if($(this).find('td:nth-child(6)').html()=='Cancelled')
				$(this).find('td:nth-child(6)').addClass('danger');
			else
				$(this).find('td:nth-child(6)').addClass('warning');
			sre=services[i].assigned;
			track=services[i++].track;
			if(sre!=null)
			{
			
				$(this).find('td:nth-child(7)').html(sre + " assigned!   ");
				if(track!="c0")
					{
					if(track=="c3")
						{

						$(this).find('td:nth-child(8)').html( '<button  type="button" class="complete btn btn-success ">\
							<span class=" fa fa-thumbs-up" aria-hidden="true"></span>\
							<span >Appointment Completed</span>\
						</button>');
						}
					else if(track=="c1"){
					$(this).find('td:nth-child(8)').html('<select class="form-control status-selects">\
	            	  <option selected="selected" value="c1" >Service in progress</option>\
	            	  <option value="c2" >Completed</option>\
	            	  <option value="c3" >On the way to Delivery</option>\
						</select>\
	            	  <button type="button" class="track button btn btn-primary " >\
						<span >Update status</span>\
					</button>');
					}
					else
						{
						$(this).find('td:nth-child(8)').html('<select class="form-control status-selects">\
				            	  <option  value="c1" >Service in progress</option>\
				            	  <option selected="selected" value="c2" >Completed</option>\
				            	  <option value="c3" >On the way to Delivery</option>\
									</select>\
				            	  <button type="button" class="track button btn btn-primary " >\
									<span >Update status</span>\
								</button>');
						}
					}
				
				
			}
			else
				{
				$(this).find('td:nth-child(8)').html("");
				}
			}
			})
			}
			}
			
		}

	});
	
	};
	initPage();

	$('body').on('change','#date-filter',function() { 
		console.log('reload')
		$('#service').dataTable().fnReloadAjax(); 
		console.log('reloadopopoop')
		});
	});




$('body').on('click', '.assignbutton', function(){
	
	
	var app_id=$(this).closest('tr').find('td:nth-child(1)').text();
	var sre_id=$(this).closest('tr').find('select').val();
	console.log(app_id);
	console.log(sre_id);
	$.ajax({
		url : "serviceappointments/updateSRE?app_id="+app_id+"&sre_id="+sre_id,
		type : 'PUT',
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#service').dataTable().fnReloadAjax();

	});
});


$('body').on('click', '.track', function(){
	var app_id=$(this).closest('tr').find('td:nth-child(1)').text();
	var track=$(this).closest('tr').find('select').val();
	$.ajax({
		url : "serviceappointments/updatetrack?app_id="+app_id+"&track="+track,
		type : 'PUT',
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#service').dataTable().fnReloadAjax();

	});
});


$('body').on('click', '.complete', function(){
	var app_id=$(this).closest('tr').find('td:nth-child(1)').text();
	var track=$(this).closest('tr').find('select').val();
	$.ajax({
		url : "serviceappointments/update2?app_id="+app_id,
		type : 'PUT',
		xhrFields: {
	      withCredentials: true
	   }
	}).done(function() {
		$('#service').dataTable().fnReloadAjax();

	});
	
	

});



$('#autoassign').click(function(){
	var i=0;
	$('#service').find('tbody').find('tr').each(function(){
		var tr=$(this);
		if(services[i++].assigned==null)
			{
			$.ajax({
				async:false,
				url : 'salesappointments/getse?id=1&type=2',
				contentType : "application/json",
				
			}).done(function(data) {
											
				console.log(data);
				var se_id=data[0].id;
				var min=data[0].count;
				$.each(data, function(i,se){						
					if(se.count<min)
						{
						min=se.count;
						se_id=se.id;
						}
					console.log(se.id +" "+ se.firstname+" "+se.count);
					
				})
				var select=$(tr).find('.sre-selects');
				$(select).val(se_id);
				var app_id=$(tr).find('td:nth-child(1)').text();
				var se_id=$(tr).find('.sre-selects').val();
				console.log(app_id);
				console.log(se_id);
				$.ajax({
					url : "serviceappointments/updateSRE?app_id="+app_id+"&sre_id="+se_id,
					type : 'PUT',
					xhrFields: {
				      withCredentials: true
				   }
				})
			})
			}
	})
	$('#service').dataTable().fnReloadAjax();
	
})