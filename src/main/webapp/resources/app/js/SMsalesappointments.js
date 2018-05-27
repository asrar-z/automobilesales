var schedules={};
switchActiveTab('a_appointments');
$(document).ready(function() {

	$('#b2').hide();

	var initPage = function() {
	CarStore.datatable=$("#sales").DataTable({
		'serverSide' : true,
		'ordering':false,
		'searching':false,
		'ajax' : {
			//async:false,
			url : 'salesappointments/get',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				
				delete(d.columns);
				delete(d.order);
				delete(d.search);
				d.filter=$('#date-filter').val();
				//console.log(d.filter);
				console.log(d);
		      return JSON.stringify(d);
		    },
		    dataSrc: function(json){
		    	console.log(json)
		    	schedules=json.schedules;
		    	return json.schedules
		    },
			xhrFields: {
			      withCredentials: true
			   },
		 
		},
		columns: [
		          {data: 'app_id'},
		          { data: 'id' },
		          { data: 'name' },
		          { data: 'number' },
          { data: 'date' },
          { data: 'status' },
          {data:'office'},
          {
              "data": null,
              "defaultContent": '<select class="form-control se-selects">\
            	  <option selected="selected" value="0" >Assign Sales Executive</option>\
					</select>\
            	  <button type="button" class="assignbutton btn btn-primary ">\
					<span class=" fa fa-forward" aria-hidden="true"></span>\
					<span >Assign Executive</span>\
				</button>\
            	  <button  type="button" class="complete btn btn-success ">\
					<span class=" fa fa-thumbs-up" aria-hidden="true"></span>\
					<span >Appointment Completed</span>\
				</button>'
           }
		],
      /*  "columnDefs": [
                       {
                           "targets": [ 6 ],
                           "visible": false,
                       }
                       ],*/
		"createdRow": function ( row, data, index ){
			$('td', row).eq(4).html(moment($('td', row).eq(4).html()).format('DD-MMM-YYYY hh:mm a'));
			
			
	},

	"fnDrawCallback": function(oSettings) {
		if($('#sales').dataTable().fnSettings().aoData.length!=0){
		console.log(schedules);
		var i=0;
		$('#sales').find('tbody').find('tr').each(function(){
			
		
			
			
			
			if($(this).find('td:nth-child(6)').html()=='Completed')
				{
				$(this).find('td:nth-child(6)').addClass('success');
				$(this).find('td:nth-child(8)').html('Successfully Completed!');
				++i;
				}
			else
				{
				$(this).find('td:nth-child(6)').addClass('warning');
				se=schedules[i++].assigned;
				if(se!=null)
				{
				
					$(this).find('td:nth-child(8)').html(se + " assigned!   "+ '<button type="button" class="complete btn btn-success ">\
							<span class=" fa fa-thumbs-up" aria-hidden="true"></span>\
							<span >Appointment Completed</span>\
						</button>&nbsp&nbsp&nbsp&nbsp<i class="flag hidden fa fa-flag" style="color:#d9534f;"></i>');
					if($(this).find('td:nth-child(6)').html()=='SE confirmed')
						$(this).find('td:nth-child(8)').find('.flag').removeClass('hidden');
				}
				}
			id=$(this).find('td:nth-child(7)').text();
			var select=$(this).find('select');
			$.ajax({
				async:false,
				url : 'salesappointments/getse?id='+id+"&type=1",
				contentType : "application/json",
				
			}).done(function(data) {
				
					
				console.log(data);
				$.each(data, function(i,se){
					console.log(se.id +" "+ se.firstname+" "+se.count);
					//console.log($(this).find('td:nth-child(8)').html())
					$(select).append('<option data-display = "' + se.id + '" value="' + se.id + '">' + se.firstname + '('+ se.count+')</li>')
				})
				
			})
		
			})
		
		}
		
	    },
	    
		"initComplete": function(settings, json) {
		
			console.log('table loaded')
		  },
	
	});
	
	
	
	};
	initPage();

	//serefresh();
	
	

	$('#date-filter').change(function() { 
		console.log("refersh")
		$('#sales').dataTable().fnReloadAjax();

	//	serefresh();
	});




	$('body').on('click', '.assignbutton', function(){
		
		
		var app_id=$(this).closest('tr').find('td:nth-child(1)').text();
		var se_id=$(this).closest('tr').find('select').val();
		console.log(app_id);
		console.log(se_id);
		$.ajax({
			url : "salesappointments/updateSE?app_id="+app_id+"&se_id="+se_id,
			type : 'PUT',
			xhrFields: {
		      withCredentials: true
		   }
		}).done(function() {
			$('#sales').dataTable().fnReloadAjax();

		});
		
	});


	$('body').on('click', '.complete', function(){

		
		var id=$(this).closest('tr').find('td:nth-child(1)').text();
		
		
		$.ajax({
			url : "salesappointments/update?id="+id,
			type : 'PUT',
			xhrFields: {
		      withCredentials: true
		   }
		}).done(function() {
			$('#sales').dataTable().fnReloadAjax();
			
		});
		
		
	});
	
	
		$('#autoassign').click(function(){
			var i=0;
			$('#sales').find('tbody').find('tr').each(function(){
				var tr=$(this);
				if(schedules[i++].assigned==null)
					{
					id=$(this).find('td:nth-child(7)').text();
					$.ajax({
						async:false,
						url : 'salesappointments/getse?id='+id+"&type=1",
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
						var select=$(tr).find('select');
						$(select).val(se_id);
						var app_id=$(tr).find('td:nth-child(1)').text();
						var se_id=$(tr).find('select').val();
						console.log(app_id);
						console.log(se_id);
						$.ajax({
							url : "salesappointments/updateSE?app_id="+app_id+"&se_id="+se_id,
							type : 'PUT',
							xhrFields: {
						      withCredentials: true
						   }
						})
					})
					}
			})
			$('#sales').dataTable().fnReloadAjax();
			
		})
	
	});


