switchActiveTab('a_fb');
$('#post').click(function(){
var sendData={
link:$('#link').val(),
content:$('#content').val()
};

$(".loading-progress").show();
$('#success').hide();
var progress = $(".loading-progress").progressTimer({
	  timeLimit: 20,
	  warningThreshold: 50,
	  onFinish: function () {
	  $(".loading-progress").hide();
	}
	});

$.ajax({
	url:'newsletter/fb',
	contentType : "application/json",
	type : 'POST',
	data: JSON.stringify(sendData),

	
}).error(function(){
	  progress.progressTimer('error', {
		  errorText:'ERROR!',
		  onFinish:function(){
		    alert('There was an error processing your Request!');
		  }
		});
		}).done(function(){

			 progress.progressTimer('complete');
			 $('#success').show();
			console.log('test');
			$('#success').html("<strong>Posted</strong>")
		})
})
