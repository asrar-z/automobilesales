   switchActiveTab('a_3');
$(document).ready(function() {	
	function format ( d ) {
	    // `d` is the original data object for the row
		console.log(d.track);
	    return '<div class="row shop-tracking-status"><div class="center-block col-lg-8">\
	    <div class="order-status">\
        <div class="order-status-timeline">\
            <!-- class names: c0 c1 c2 c3 and c4 -->\
            <div class="order-status-timeline-completion '+d.track +'"div>\
        </div>\
        <div class="image-order-status image-order-status-new active img-circle">\
            <span class="status">Accepted</span>\
            <div class="icon"></div>\
        </div>\
        <div class="image-order-status image-order-status-active active img-circle">\
            <span class="status">Service in progress</span>\
            <div class="icon"></div>\
        </div>\
        <div class="image-order-status image-order-status-intransit active img-circle">\
            <span class="status">Completed</span>\
            <div class="icon"></div>\
        </div>\
        <div class="image-order-status image-order-status-delivered active img-circle">\
            <span class="status">On the way to Delivery</span>\
            <div class="icon"></div>\
        </div>\
    </div></div></div>';
	}

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
				console.log(d);
				
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
		          {
		                "className":      'details-control',
		                "orderable":      false,
		                "data":           null,
		                "defaultContent": ''
		            },
          { data: 'date' },
          { data: 'name' },
          { data: 'id' },
          { data: 'address' },
          { data: 'status' },
          {data:'problem'},
          {data:'track'},
          
		],
        "columnDefs": [
                       {
                           "targets": [ 7 ],
                           "visible": false,
                       }
                       ],
		"createdRow": function ( row, data, index ){
			$('td', row).eq(1).html(moment($('td', row).eq(1).html()).format('DD-MMM-YYYY hh:mm a'));
		},
		"fnDrawCallback": function(oSettings ) {
			$('#service').find('tbody').find('tr').each(function(){
				if($(this).find('td:nth-child(6)').html()=='Completed')
					{
					$(this).find('td:nth-child(6)').addClass('success');
					}
				else if($(this).find('td:nth-child(6)').html()=='Cancelled')
					{
					$(this).find('td:nth-child(6)').addClass('danger');
					}
				else
					{
					$(this).find('td:nth-child(6)').addClass('warning');
					}
			});
		}

	});


$('#service tbody').on('click', 'td.details-control', function () {
    var tr = $(this).closest('tr');
    var row = CarStore.datatable.row( tr );

    if ( row.child.isShown() ) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
    }
    else {
        // Open this row
        row.child( format(row.data()) ).show();
        tr.addClass('shown');
    }
} );

});