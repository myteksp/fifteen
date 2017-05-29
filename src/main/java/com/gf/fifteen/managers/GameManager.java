package com.gf.fifteen.managers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dao.game.GameState;
import com.gf.fifteen.exceptions.InvalidGameSizeException;
import com.gf.fifteen.managers.utils.GameManagerUtils;
import com.gf.util.string.MacroCompiler;

@Component
public final class GameManager {
	private final int maxSize;
	private final int minSize;
	
	@Autowired
	public GameManager(
			@Value("${game.config.maxSize}")final int maxSize, 
			@Value("${game.config.minSize}")final int minSize){
		this.maxSize = maxSize;
		this.minSize = minSize;
	}
	
	public final int[] getAllowedSizesRange(){
		final int len = maxSize - minSize;
		final int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			result[i] = minSize + i;
		}
		return result;
	}

	public final void startGame(final GameEntity game, final int size){
		if (size < minSize || size > maxSize)
			throw new InvalidGameSizeException(
					MacroCompiler.compile("Invalud game size ${0}. Expected between ${1} and ${2}", 
							Arrays.asList(
									Integer.toString(size), 
									Integer.toString(minSize), 
									Integer.toString(maxSize))));
		
		game.size = size;
		resetGame(game);
		game.state = calculateGameState(game.position);
	}
	

	public final void resetGame(final GameEntity game){
		game.state = GameState.WAITING_FOR_START;
		if (game.size < minSize){
			game.size = minSize;
		}else if (game.size > maxSize){
			game.size = maxSize;
		}
		game.position = GameManagerUtils.generateRandomSolvablePosition(game.size);
		game.startDate = System.currentTimeMillis();
		game.endDate = 0;
		game.state = calculateGameState(game.position);
	}
	
	public final int[] getNextMove(final int[] position, final int number){
		return GameManagerUtils.generateNextMove(position, number);
	}

	public final boolean validateMove(final int[] prevPosition, final int[] newPosition){
		return GameManagerUtils.validateMove(prevPosition, newPosition);
	}
	

	public final GameState calculateGameState(final int[] position){
		return GameManagerUtils.isSolved(position)?GameState.ENDED:GameState.IN_PROGRESS;
	}
}
