package com.gf.fifteen.logic;

import org.springframework.stereotype.Component;

import com.gf.fifteen.entities.game.GameEntity;
import com.gf.fifteen.entities.game.GameState;

@Component
public final class GameLogicUtil {

	public final void startGame(final GameEntity game, final int size){

	}

	public final void resetGame(final GameEntity game){

	}

	public final boolean validateMove(final int[] prevPosition, final int[] newPosition){
		return false;
	}

	public final GameState calculateGameState(final GameEntity game){
		return GameState.IN_PROGRESS;
	}
}
