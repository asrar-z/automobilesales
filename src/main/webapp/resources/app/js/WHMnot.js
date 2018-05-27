	var count;
	var flag=1;
$(document).ready(function(){

	$.ajax({
		async:false,
		url:"updateinventory/getc",
		success:function(data){
			count=data;
		}
	})
	var poll=function(){
		$.ajax({
			
			url:"updateinventory/notifications",
			contentType : "application/json",
			success:function(json){
				console.log(json.count)
				if(json.count>count)
					{
					flag=2;
					count=json.count
					$('#list').empty();
					$('.fa-bell').css({'color': 'red'});
					$.each(json.notifications,function(i,n){
					$('#list').prepend('<li class="list-group-item"><p>\
							<span class="label label-default">'+n.subject+'</span></p><p>'+n.name+'</p><p>'+n.message+'</p></li>');
					})
					}
				if(flag==1)
					{
					$.each(json.notifications,function(i,n){
						$('#list').prepend('<li class="list-group-item"><p><span class="label label-default">'+ n.subject+'</span></p><p>'+n.name+'</p><p>'+n.message+'</p></li>');
						})
						flag=2;
					}
			}
		})
	};
	poll();
	setInterval(function(){
		console.log('polling')
		poll();
	},2000);
})

$('#notification').click(function(){
	$('.fa-bell').css({'color': 'white'});
	$.ajax({
		url:"updateinventory/updatec?count="+count
	})
})