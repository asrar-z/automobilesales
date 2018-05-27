   switchActiveTab('a_1');
   var current_user_id=$('#getid').html();
$(document).ready(function() {
	
	
	
	
	$.ajax({
	
		url:'appointmentsoftheday/get',
	    	type:'POST',
	    	dataType: 'json',
	    	contentType : "application/json",
		
		
		
	}).done(function(data)
	{
		console.log(data);
		var list=$('.todayslist');
		$.each(data.todays,function(i,today){
				console.log(today.assigned+" "+current_user_id);
				if(today.assigned==current_user_id){
					var bclass="";
					var pclass="hidden"
					if(today.completed=='2'||today.completed=='1')
						{
						pclass="";
						bclass="hidden";
						}
				$(list).append($('\
						<li>\
				          <div class="timeline-badge info"><i class="fa fa-check-circle-o"></i></div>\
				          <div class="timeline-panel">\
				            <div class="timeline-heading">\
				              <h4 class="timeline-title">Customer Name : '+today.name  +'</h4>\
				            </div>\
				            <div class="timeline-body">\
				              <p >App ID:<b class="app_id">#'+today.app_id+'</b></p>\
				              <p> Number : <b>'+ today.number+'</b></p>\
				              <p> Time: <b>'+moment(today.date).format('DD-MMM-YYYY hh:mm a')+'</b></p>\
				              <p>Sales Executive Assigned : <b>'+ today.assigned+'</b></p>\
				              <button class="'+bclass +' complete btn btn-success">Complete</button>\
				              <p class="'+pclass+'">Notified Manager</p>\
				            </div>\
				          </div>\
				        </li>'));
				}
			});
			
	
		
		
	})
	
	$('body').on('click','.complete',function(){
		
		var b=$(this)
		var id=$(this).closest('div').find('.app_id').html().replace(/#/g,'');
		console.log(id);
		$.ajax({
			url : "salesappointments/update?id="+id,
			type : 'PUT',
			xhrFields: {
		      withCredentials: true
		   }
		}).done(function() {
			console.log($(this))
			$(b).next('p').removeClass('hidden')
			$(b).hide();
			
			
		});
		
	})
	
	
	
})