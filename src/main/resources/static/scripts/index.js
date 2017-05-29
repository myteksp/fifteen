"use strict";
var stage = {
		init:function(){
			$('body').addClass('js');
			if (game_id.length == 0){
				document.location.href="/lobby";
				return;
			}else if (current_game_state === "ENDED"){
				document.location.href="/lobby";
				return;
			}else if (current_game_state === "WAITING_FOR_START"){
				document.location.href="/lobby";
				return;
			}

			$("#game-board").sortable({
				update: function( event, ui ) {
					$( "#game-board" ).sortable('cancel');
				}
			});
			$("#game-board").disableSelection();
		},
		setupHandlers:function(){
			var tiles = $(".game-cell");
			tiles.click(function(event){
				if (!event.target.id)
					return;
				if (event.target.id === "num_0")
					return;
				var src_num = parseInt(event.target.id.split("_")[1]);
				
				controller.nextPosition(src_num, function(nextPosition){
					controller.updatePostion(nextPosition, function(resp){
						if (!resp.success){
							alert("Illigal game state detected. Incendent will be reported.");
						}else{
							controller.switchIndexes(resp.value.position);
							var newState = resp.value.state;
							current_game_state = newState;
							if (newState === "ENDED"){
								document.location.href="/lobby";
							}
						}
					});
				});
				
			});
			
			$(".game-cell-number").mouseenter(function(event){
				$(event.target).parent().css("text-shadow", "1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue", "color", "white");
			}).mouseleave(function(event){
				$(event.target).parent().css("text-shadow", "");
			});

			$("#lobby-button").button().click(function(){
				document.location.href="/lobby";
			});
		}
};



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
		nextPosition:function(num, onSuccess){
			if (num){
				$.ajax({
					type: 'GET',
					url: '/game/canMove?id=' + game_id + '&num=' + num,
					success: function(data) {
						if (data.canMove){
							onSuccess(data.nextPosition);
						}
					},
					contentType: "application/json",
					dataType: 'json'
				});
			}
		},
		getUiPosition:function(){
			var result = [];
			var arr = $(".game-cell-number");
			for (var i = 0; i < arr.length; i++) {
				result.push(parseInt(arr[i].id.split("_")[1]));
			}
			return result;
		},
		switchIndexes:function(nextPosition){
			var itemsNewOrder = [];
			for (var i = 0; i < nextPosition.length; i++) {
				var li = $("#num_" + nextPosition[i]).parent();
				li.remove();
				itemsNewOrder.push(li[0]);
			}
			var board = $("#game-board");
			for (var i = 0; i < itemsNewOrder.length; i++) {
				$(itemsNewOrder[i]).appendTo(board);
			}
			stage.setupHandlers();
		}
};

$(function() {
	stage.init();
	stage.setupHandlers();
});