"use strict";
$(function() {
	$('body').addClass('js');
	$("#button").button().click(function(event){
		event.preventDefault();
		$.ajax({
		    type: 'POST',
		    url: '/user/exists',
		    data: JSON.stringify({value:$("#username").val()}),
		    success: function(exists) { 
		    	if (exists.value){
		    		alert("User '" + $("#username").val() + "' already exists");
		    		return;
		    	}
		    	$.ajax({
				    type: 'POST',
				    url: '/user/signup',
				    data: JSON.stringify({username:$("#username").val(), password:$("#password").val()}),
				    success: function(data) { 
				    	document.location.href="/login?p=" + Base64.encode(JSON.stringify({username:$("#username").val(), password:$("#password").val()}));
				    },
				    contentType: "application/json",
				    dataType: 'json'
				});
		    },
		    contentType: "application/json",
		    dataType: 'json'
		});
	});
});