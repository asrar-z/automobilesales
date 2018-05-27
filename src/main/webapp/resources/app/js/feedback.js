 switchActiveTab('a_4');
$(document).ready(function() {
	
	
	
	
	
	$.ajax({
		
		url:'customerfeedback/get',
	    	type:'POST',
	    	dataType: 'json',
	    	contentType : "application/json",
		
		
		
	}).done(function(data)
	{
		console.log(data);
		var list=$('.timeline');
		flag=false;
		$.each(data.feedbacks,function(i,feedback){
				console.log(list);
				if(flag)
					inv="class=timeline-inverted";
				else
					inv='';
				$(list).append($('\
				        <li '+inv+'>\
				          <div class="timeline-badge success"><i class="glyphicon glyphicon-thumbs-up"></i></div>\
				          <div class="timeline-panel">\
				            <div class="timeline-heading">\
				              <h4 class="timeline-title"><custom><font size=5>'+feedback.firstname +'  '+ feedback.lastname+'</font></custom></h4>\
				            </div>\
				            <div class="timeline-body">\
				              <p>Subject:<custom> '+ feedback.subject +'</custom></p>\
				              <p> e-mail :<custom>'+feedback.email +'</custom></p>\
				              <p><custom>'+ feedback.message+'</custom></p>\
				            </div>\
				          </div>\
				        </li>'));
				flag=!flag;
			});
			

		
		
	})
	
	
	
	
	
	
})