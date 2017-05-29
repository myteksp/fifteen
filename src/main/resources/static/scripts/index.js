"use strict";
var controller = {
		getGame:function(onResponse){
			$.ajax({
			    type: 'GET',
			    url: '/game/get?id=' + game_id,
			    success: function(data) { 
			    	onResponse(data);
			    },
			    contentType: "application/json",
			    dataType: 'json'
			});
		},
		updatePostion:function(position, onResponse){
			$.ajax({
			    type: 'POST',
			    url: '/game/position?id=' + game_id,
			    data: JSON.stringify(position),
			    success: function(data) { 
			    	onResponse(data);
			    },
			    contentType: "application/json",
			    dataType: 'json'
			});
		},
		getUiPosition:function(){
			var result = [];
			var arr = $(".game-cell-number");
			for (var i = 0; i < arr.length; i++) {
				result.push(parseInt(arr[i].id.split("_")[1]));
			}
			return result;
		}
};

$(function() {
	$('body').addClass('js');
	if (current_game_state === "ENDED"){
		document.location.href="/lobby";
		return;
	}else if (current_game_state === "WAITING_FOR_START"){
		document.location.href="/lobby";
		return;
	}
	
	var hasChanged = false;
	$( "#game-board" ).sortable({
		  update: function( event, ui ) {
			  hasChanged = true;
		  },
		  stop: function( event, ui ) {
			  if (hasChanged){
				  hasChanged = false;
				  controller.updatePostion(controller.getUiPosition(), function(resp){
					  if (!resp.success){
						  $( "#game-board" ).sortable('cancel');
					  }else{
						  var newState = resp.value.state;
						  current_game_state = newState;
						  if (newState === "ENDED"){
							  document.location.href="/lobby";
							  return;
						  }
					  }
				  });
			  }
		  }
	});
    $( "#game-board" ).disableSelection();
    $("#lobby-button").button().click(function(){
    	document.location.href="/lobby";
    });
});