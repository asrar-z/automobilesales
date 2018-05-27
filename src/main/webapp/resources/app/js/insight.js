// Morris.js Charts sample data for SB Admin template
   switchActiveTab('a_1');
$('#sug_table').hide();
var diff={}
$(document).ready(function() {
	var set_graph=function()
	{
		$('#morris-bar-chart').empty();
		var id=$('#make').val();
		var office=$('#office').val();
		var year=$('#year').val();
		$.ajax({
			url : 'insight/graph?id='+id+"&office="+office+"&year="+year,
			contentType : "application/json",		
		}).done(function(data) {
			
			console.log(data);
			var vehicles=data.vehicles;
			Morris.Bar({
				element: 'morris-bar-chart',
		        data: vehicles,
		        xkey: 'name',
		        ykeys: ['sold'],
		        labels: ['sold'],
		        barGap:20,
		        barRatio: 0.4,
		        barSizeRatio:0.1,
		        xLabelAngle: 0,
		        hideHover: 'auto',
		        resize: true,
		        gridIntegers: true,
		        ymin: 0
		    });

			
			

		})
	}
	
	var set_suggestion_board=function(){
		$('#budget').empty();
		$('#mid').empty();
		$('#high').empty();
		var office=$('#office').val();
		var year=$('#year').val();
		$.ajax({
			url:'insight?office='+office+"&year="+year,
			type : 'POST',
			contentType : "application/json",
			success:function(data){
				console.log(data);
				$.each(data.budget,function(i,car){
					$('#budget').append('<li data-toggle="modal" data-target="#whmnotmodal" class="notify-whm1 list-group-item">\
							<i class="fa fa-star faa-flash animated" style="color:#FDA528;"></i><b>\
							'+ car.name+'</b></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Profit Margin:&nbsp;&nbsp;<b>'+car.margin +'</b></li>');
				})
				$.each(data.mid,function(i,car){
					$('#mid').append('<li data-toggle="modal" data-target="#whmnotmodal" class="notify-whm1 list-group-item">\
							<i class="fa fa-star faa-flash animated" style="color:#FDA528;"></i><b>\
							'+ car.name+'</b></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Profit Margin:&nbsp;&nbsp;<b>'+car.margin +'</b></li>');
				})
				$.each(data.high,function(i,car){
					$('#high').append('	<li data-toggle="modal" data-target="#whmnotmodal" class="notify-whm1 list-group-item">\
							<i class="fa fa-star faa-flash animated" style="color:#FDA528;"></i><b>\
							'+ car.name+'</b></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Profit Margin:&nbsp;&nbsp;<b>'+car.margin +'</b></li>');
				})
			}
		})
	}

var set_popular_board=function()
{
	$('#neartable').empty();
	var office=$('#office').val();
	$.ajax({
		url:'insight/s?office='+office,
		contentType:'application/json',
		success:function(data){
			console.log(data)
			$.each(data,function(i,list){
				$('#neartable').append('<tr>\
                                            <td>'+ list.position +'</td>\
                                            <td>'+ list.brand+'</td>\
                                            <td>'+ list.units+'</td>\
                                        </tr>')
			})
		}
	})
}
var ai=function(){
$('#increase').empty();
$('#order').empty();
$('#remove').empty();
	var office=$('#office').val();
	var year=$('#year').val();
$.ajax({
	url:'insight/suggestions?office='+office+"&year="+year,
	type : 'POST',
	contentType : "application/json",
	success:function(data){
		console.log(data);
		console.log(data.j);
		console.log($.parseJSON(data.j))
		diff=$.parseJSON(data.j);
		$.each(data.increase,function(i,car){
			$('#increase').append('<li  class="list-group-item">\
					<i data-toggle="popover" title="'+ car.pop_title+'" data-content="'+car.pop_body +'" class="fa fa-arrow-circle-up faa-flash animated" style="color:#5CB85C;"></i>\
					'+car.list_body+'<span class="pull-right"><i data-toggle="modal" data-target="#whmnotmodal" class="notify-whm fa fa-flag" style="color:#5CB85C;">\
					</i></span></li>');
		})
		$.each(data.order,function(i,car){
			$('#order').append('<li  class="list-group-item">\
					<i data-toggle="popover" title="'+ car.pop_title+'" data-content="'+car.pop_body +'" class="fa fa-line-chart faa-flash animated" style="color:#FF9600;"></i>\
					'+car.list_body+'<i class="pull-right notify-whm2 fa fa-flag" data-toggle="modal" data-target="#whmnotmodal" style="color:#5CB85C;"></i></li>');
		})
		$.each(data.remove,function(i,car){
			$('#remove').append('<li data-toggle="popover" title="'+ car.pop_title+'" data-content="'+car.pop_body +'" class="list-group-item">\
					<i class="fa fa-minus-square faa-flash animated" style="color:#D9534F;"></i>  '+car.list_body+'<i class="pull-right notify-sm fa fa-flag" style="color:#5CB85C;"></i></li>');
		})
	}	
	
})

}

var init=function(){
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
			async:false,
		url : 'emisale/makes',
		contentType : "application/json",		
	}).done(function(data) {
		//console.log("displaying data")
		//console.log(data);
		
		var makeSelects = $('.make-selects');
		
		
		$.each(data.makes, function(i, make) {
			$.each(makeSelects, function(i, select) {
				if(make.id==1){
					$(select).append($('<option selected="selected" data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
					
				}
				else
				$(select).append($('<option data-display = "' + make.name + '" value="' + make.id + '">' + make.name + '</li>'));
			});
		});
	})
	
	
	$.ajax({
		async:false,
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
	
	set_graph();
	set_suggestion_board();
	set_popular_board();	


		
}
init();
var popOverSettings = {
	    placement: 'left',
	    container: 'body',
	    html: true,
	    selector: '[data-toggle="popover"]', //Sepcify the selector here
	    content: function () {
	        return $('#popover-content').html();
	    }
	}

	$('body').popover(popOverSettings);
$('#office').change(function(){
set_graph();
set_suggestion_board();
set_popular_board();
ai();
})


$('#make').change(function(){
set_graph();
	
})

$('#year').change(function(){
set_graph();
set_suggestion_board();
ai();
	
})
$('#suggest').click(function(){
	$(this).hide();
	ai();
	$('#sug_table').slideDown( "slow", function() {});
})

$('body').on('click', function (e) {
    $('[data-toggle="popover"]').each(function () {
        //the 'is' for buttons that trigger popups
        //the 'has' for icons within a button that triggers a popup
        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
            $(this).popover('hide');
        }
    });
});

$('body').on('click','.notify-whm',function(){
	var name=$(this).closest('li').text().trim();
	$('#name').val(name);
	console.log(name)
	q=diff[name];
	console.log(q);
	$('#quantity').val(q);
	 $('#whmnotmodal #myModalLabel').data().mode = 'stock';
})

$('body').on('click','.notify-whm1',function(){
	console.log($(this))
	var name=$(this).find('b:first').text().trim();
	console.log(name)
	$('#name').val(name);
	 $('#whmnotmodal #myModalLabel').data().mode = 'stock';
})


$('body').on('click','.notify-whm2',function(){
	var name=$(this).closest('li').text().trim();
	$('#name').val(name);
	$('#q').hide();
	 $('#whmnotmodal #myModalLabel').data().mode = 'newstock';
})
$('body').on('click','.notify-sm',function(){
	var name=$(this).closest('li').text().trim();
	var office=$('#office').val();
	console.log(name);
	$.ajax({
		url:'newsletter/newsnot?vehicle='+name+"&office="+office,
		type:'post'
	})
})


   $('#whmnotmodal').on('hidden.bs.modal', function() {
        $('#q').show();
        });


$('#whmnotbutton').click(function(){
	var name=$('#name').val();
	var quantity=$('#quantity').val();
	var office=$('#office').val();
	$.ajax({
		url:'updateinventory/'+$('#whmnotmodal #myModalLabel').data().mode+'notification?name='+name+'&quantity='+quantity+"&office="+office,
		type:'post'
	}).done(function(){
		$('#whmnotmodal').modal('hide');
	})
})
})



