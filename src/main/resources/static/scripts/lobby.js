"use strict";
$(function() {
	$('body').addClass('js');
	$("#sizes").selectmenu();
	$("#start-game-button").button().click(function(event){
		$.ajax({
		    type: 'GET',
		    url: '/game/start?id=' + game_id + '&size=' + $("#sizes").val().split("X")[0],
		    success: function(data) { 
		    	document.location.href="/";
		    },
		    contentType: "application/json",
		    dataType: 'json'
		});
	});
	$("#logout-button").button().click(function(event){
		document.location.href="/logout";
	});
});