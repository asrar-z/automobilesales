switchActiveTab('a_not');
$(document).ready(function() {
	
CarStore.datatable=$("#not_table").DataTable({
	'ajax':{
		
		url:'office/getnot',
		contentType : "application/json",
	    dataSrc: "nots",
		xhrFields: {
		      withCredentials: true
		   }
	
	},
	
columns: [

        { data: "c_id" },
        {data: "subject" },
        { data: "message" },
    ],
    "order": [[1, 'asc']],

	});

})