switchActiveTab('a_emi');
var EMIObject={};
$(document).ready(function() {
	$('#res').hide();
	$('#b1').click(function(){

		$('#res').show();
		console.log("inside b1")
		
	});
	function format ( d ) {
		
		$(".calculator-amortization").accrue({
			mode: "amortization"
		});
		$('#emi-modal #myModalLabel').html('EMI Installments for ' + d.firstname +' ' +d.lastname);
		$('#loan').val(d.total);
        $('#rate').val(d.rate);
		$('#term').val(d.duration);
		$('#office').val(d.office);
		$.each(EMIObject.emis,function(i,key){
			console.log(key)

				if(key.c_id==d.c_id)
					{
					console.log(key.c_id)
					EMIObject2=key;
					}
		
			})

	}
	
	
CarStore.datatable=$("#emi-table").DataTable({
		
		
		'ajax':{
			
			url:'emi/get',
			contentType : "application/json",
		    dataSrc: "emis",
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
            { data: "firstname" },
            {data: "lastname" },
            { data: "c_id" },
            { data: "total" },
            { data: "duration" },
            { data: "rate" },
            {data: "office"}
        ],
        "order": [[1, 'asc']],
		"initComplete": function(settings, json) {
			EMIObject=json;			
			console.log(json)
		  },

	});

$('#emi-table tbody').on('click', 'td.details-control', function () {
	$('#emi-modal').modal('toggle');
    var tr = $(this).closest('tr');
    var row = CarStore.datatable.row( tr );
        row.child( format(row.data()) );
      
   
} );


$('#emi-modal').on('hidden.bs.modal', function() {
	$('#res').hide();
});


})