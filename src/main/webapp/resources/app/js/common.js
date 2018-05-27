var switchActiveTab = function(id) {
	$.each($('#nav-bar').children(), function(i, el) {
		if (el.id === id) {
			console.log('switched to '+ el.id)
			$(el).addClass('active');
		} else {
			$(el).removeClass('active');
		}
	});
};

var CarStore = {};

jQuery.fn.serializeObject = function() {
	var arrayData, objectData;
	arrayData = this.serializeArray();
	objectData = {};

	$.each(arrayData, function() {
		var value;

		if (this.value != null) {
			value = this.value;
		} else {
			value = '';
		}
		if(this.name!="password_confirm")
		{
		if (objectData[this.name] != null ) {
			
			console.log(objectData[this.name]);
			
			if (!objectData[this.name].push) {
				objectData[this.name] = [ objectData[this.name] ];
			}

			objectData[this.name].push(value);
		} else {
			objectData[this.name] = value;
		}
		}
	});

	return objectData;
};


