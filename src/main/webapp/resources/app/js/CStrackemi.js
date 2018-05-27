   switchActiveTab('a_4');
$(document).ready(function(){
	   switchActiveTab('a_4');
	$('#res').hide();
	
	
	
	$(".calculator-amortization").accrue({
		mode: "amortization"
	});
	
	$.ajax({
   		
			url: 'trackemi/get',
			datatype: 'json',
			type : 'POST',
			   
		   }).done(function(data){
		   console.log(data);
		   EMIObject=data;
			$('#loan').val(data.total);
            $('#rate').val(data.rate);
			$('#term').val(data.duration);
			$('#office').val(data.office);
			
		   // $( "#results" ).load( "trackemi.html #results" );
		});
		
		$('#b1').click(function(){
			$('#res_table').find('tbody').find('tr').each(function(){
				if($(this).find('td:nth-child(6)').html()=='paid')
					{
					$(this).find('td:nth-child(6)').addClass('success');
					$(this).find('td:nth-child(7)').find('button').prop("disabled",true);
					}
				})
			$('#res').show();
		});
		
		$('body').on('click', '.pay', function(){
		console.log('pay clicked')
		alert('forwarding to payment gateway...');
		var id=$(this).closest('tr').find('td:nth-child(1)').text();
		
		$.ajax({
			url:'trackemi/update?idx='+id,

		}).done(function(){
			window.location.href = "pay";
		})
	})
		
	 });
	
