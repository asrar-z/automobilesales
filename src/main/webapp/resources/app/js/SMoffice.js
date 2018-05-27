switchActiveTab('a_office');
$(document).ready(function() {


	    function format(data) {
	    	var thead = '',  tbody = '', res='';
	    	
	        $.ajax({
	        	async: false,
	        url:'employees/getall?officeid='+data.id,
	        dataType: "json",
	        contentType : "application/json",
	        complete: function (response) {
	            var data2 = JSON.parse(response.responseText);
	      
	            
		    	
		    	
		    	
		    	for(var key in data2[0])
		    		{
		    		thead += '<th>' + key + '</th>';
		    		}
		    	
		    	$.each(data2,function(i,emp){
		    	
		    		 tbody += '<tr><td>' + emp.id + '</td><td>' + emp.firstname + '</td><td>' + emp.lastname + '</td><td>' + emp.email + '</td><td>' + emp.officeid + '</td><td>' + emp.role + '</td><td>' + emp.salary + '</td></tr>';
		    		
		    	})
		    	
		    	 res+='	<div class="row col-lg-12">       <div class=" panel panel-default">\
	 <div class="panel-heading"><h5>Employees in '+ data.name+' Office </h5></div>\
	<div class="panel-body"><table class="table table-bordered" <thead>'+thead+'</thead><tbody>'+tbody+'</tbody></thead></table></div></div>';
		    	 
		    	
	        }
	        
	    });
	        return res;
	        
	    }

	
	
	
CarStore.datatable=$("#office-table").DataTable({
		
		
		'ajax':{
			
			url:'office/get',
			contentType : "application/json",
		    dataSrc: "offices",
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
            { data: "id" },
            {data: "name" }
        ],
        "order": [[1, 'asc']]
    

	});


$('#office-table tbody').on('click', 'td.details-control', function () {

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

})