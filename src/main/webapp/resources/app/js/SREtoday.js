   switchActiveTab('a_2');
   var current_user_id=$('#getid').html();
$(document).ready(function() {
	var icon="resources/app/img/marker.png";
    function initGeolocation()
    {
            if( navigator.geolocation )
            {

              // Call getCurrentPosition with success and failure callbacks
              navigator.geolocation.getCurrentPosition( success, fail );
        }
        else
        {
              alert("Sorry, your browser does not support geolocation services.");
        }
    }
	initGeolocation();
     var map;
     function fail(){}
     function success(position)
     {
           // Define the coordinates as a Google Maps LatLng Object
           var coords = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

           // Prepare the map options
           var mapOptions =
          {
                      zoom: 12,
                      center: coords,
                      mapTypeControl: false,
                      navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
                      mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            // Create the map, and place it in the map_canvas div
            map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);


            // Place the initial marker
            var marker = new google.maps.Marker({
                      position: coords,
                      map: map,
                      title: "Your current location!"
            });
            
            
        	$.ajax({
        		async:false,
        		url:'servicesoftheday/get',
        	    	type:'POST',
        	    	dataType: 'json',
        	    	contentType : "application/json",
        		
        		
        		
        	}).done(function(data)
        	{
        		console.log(data);
        		var list=$('.todayslist');
        		$.each(data,function(i,today){
        			if(today.assigned==current_user_id){
        			if(today.assigned==null)
        				today.assigned="not-assigned yet"
        				console.log(list);
        			var buttonshow="";
        			var pshow="hidden";
        			var buttonshow2="";
        			var pshow2="hidden";
        			if(today.track!="c0")
        			{
        			var buttonshow="hidden";
        			var buttonshow2="hidden";
        			var pshow="";
        			}
        			if(today.status=="Cancelled")
        				{
        				var buttonshow="hidden";
        				var pshow="hidden";
        				var buttonshow2="hidden";
        				var pshow2="";
        				}
        				$(list).append($('\
        						<li>\
        				          <div class="timeline-badge info"><i class="fa fa-check-circle-o"></i></div>\
        				          <div class=" timeline-panel">\
        				            <div class="timeline-heading">\
        				              <h4 class="timeline-title">Customer Name : '+today.name  +'</h4>\
        				            </div>\
        				            <div class="timeline-body">\
        				              <p> Number : '+ today.number+'</p>\
        				              <p>Service Executive Assigned : '+ today.assigned+'</p>\
        				              <p>Address : '+ today.address+'</p>\
        				              <p>Vehicle: '+ today.make+' '+today.model +'</p>\
        				              <p>Problem : '+ today.problem+'</p>\
        				              <p class="hidden app_id">'+today.app_id+'</p>\
        				              <button class="'+buttonshow+' pick btn btn-success">Picked Up</button>\
        				              <b class='+pshow+'>Picked Up !</b>\
        				              <button class="'+buttonshow2+' drop btn btn-danger">Unable to Pick up</button>\
        				              <b class='+pshow2 +'>Unable to pick up!</b>\
        				              </div>\
        				          </div>\
        				        </li>'));

        				showservice(today.address,today.name,today.model);
        			}
        			});
        			
        		
        		
        		
        	})
            
            
            
			
        }

		function showservice(address,name,model)
		{
            
            geocoder = new google.maps.Geocoder();
            if(geocoder)
            	{
            	 geocoder.geocode( { 'address': address}, function(results, status) {
            	        if (status == google.maps.GeocoderStatus.OK) {
            	          if (status != google.maps.GeocoderStatus.ZERO_RESULTS) {
            	        
            	            var infowindow = new google.maps.InfoWindow(
            	                { content: '<p><b>'+name+'</b></p><p>'+address+'</p><p>'+model+'</p>',
            	                  size: new google.maps.Size(150,50)
            	                });

            	            var marker = new google.maps.Marker({
            	                position: results[0].geometry.location,
            	                map: map, 
            	                icon:icon,
            	                title:name
            	            }); 
            	            google.maps.event.addListener(marker, 'click', function() {
            	                infowindow.open(map,marker);
            	            });

            	          } else {
            	            alert("No results found");
            	          }
            	        } else {
            	          alert("Geocode was not successful for the following reason: " + status);
            	        }
            	      })
            	}
		}
	
	


	
	
	$('body').on('click', '.pick', function(){
		
		$(this).next('b').removeClass("hidden");
		$(this).parent().find('.drop').hide();
		var id=$(this).prev('p').html();
		$(this).hide();

		//alert(id);
		$.ajax({
			url:'servicesoftheday/updatetrack?app_id='+id+'&track=c1',
		}).done()
		
	})
	
		$('body').on('click', '.drop', function(){
			$(this).parent().find('.pick').hide();
		$(this).next('b').removeClass("hidden");
		var id=$(this).parent().find('.app_id').html();
		$(this).hide();

		//alert(id);
		$.ajax({
			url:'servicesoftheday/cancel?app_id='+id,
		}).done()
		
	})
	
})