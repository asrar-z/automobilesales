   switchActiveTab('a_5');
$(document).ready(function() {
	var initPage = function() {
	CarStore.datatable=$("#sales").DataTable({
		
		'serverSide' : true,
		"ordering": false,
		'searching': false,
		 //"scrollX": false,
		'ajax' : {
			async:false,
			url : 'appointmenthistory/getSaleApp',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				
				delete(d.columns);
				delete(d.order);
				delete(d.search);

				
		      return JSON.stringify(d);
		    },
		    dataSrc: "schedules",
			xhrFields: {
			      withCredentials: true
			   }
		},
		columns: [
          { data: 'date' },
          { data: 'name' },
          { data: 'id' },
          { data: 'number' },
          { data: 'status' }
		],
		"createdRow": function ( row, data, index ){
			$('td', row).eq(0).html(moment($('td', row).eq(0).html()).format('DD-MMM-YYYY hh:mm a'));
		}

	});
	

	
	
	CarStore.datatable=$("#service").DataTable({
		
		'serverSide' : true,
		"ordering": false,
		'searching': false,
		'ajax' : {
			async:false,
			url : 'appointmenthistory/getServiceApp',
			type : 'POST',
			contentType : "application/json",
			data: function ( d ) {
				// send only data required by backend API

				
				delete(d.columns);
				delete(d.order);
				delete(d.search);

				
		      return JSON.stringify(d);
		    },
		    dataSrc: "services",
			xhrFields: {
			      withCredentials: true
			   }
		},
		columns: [
          { data: 'date' },
          { data: 'name' },
          { data: 'id' },
          { data: 'address' },
          { data: 'status' }
		],
		"createdRow": function ( row, data, index ){
			$('td', row).eq(0).html(moment($('td', row).eq(0).html()).format('DD-MMM-YYYY hh:mm a'));
		}

	});
	}
	initPage();
	
$('#sales').find('tbody').find('tr').each(function(){
if($(this).find('td:last').html()=='Completed')
	{
	$(this).addClass('success');
	}
else
	$(this).addClass('warning');
})

$('#service').find('tbody').find('tr').each(function(){
	console.log('Inside Service')
if($(this).find('td:last').html()=='Completed')
	{
	$(this).addClass('success');
	}
else
	$(this).addClass('warning');
})


	
});