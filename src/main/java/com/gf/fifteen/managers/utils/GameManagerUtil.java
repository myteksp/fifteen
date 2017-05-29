package com.gf.fifteen.managers.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dao.game.GameState;
import com.gf.fifteen.exceptions.InvalidGameSizeException;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.managers.GameManager;
import com.gf.util.string.MacroCompiler;

@Component
public final class GameManagerUtil {
	private final int maxSize;
	private final int minSize;
	
	@Autowired
	public GameManagerUtil(
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
	}

	public final void resetGame(final GameEntity game){
		game.state = GameState.WAITING_FOR_START;
		if (game.size < minSize){
			game.size = minSize;
		}else if (game.size > maxSize){
			game.size = maxSize;
		}
		game.position = GameManager.generateRandomSolvablePosition(game.size);
		game.startDate = System.currentTimeMillis();
		game.endDate = 0;
	}

	
	
	public final boolean validateMove(final int[] prevPosition, final int[] newPosition){
		final int[] validIndexes = GameManager.validZeroIndexes(prevPosition);
		final int newZeroIndex = GameManager.zeroIndex(newPosition);
		for(final int index : validIndexes){
			if (index < 0)
				return false;
			
			if (index == newZeroIndex)
				return true;
		}
		return false;
	}

	public final GameState calculateGameState(final int[] position){
		int actualIndex = 1;
		int hasZero = 0;
		for(final int num : position){
			if (num > 0){
				if (num != actualIndex)
					return GameState.IN_PROGRESS;
				
				actualIndex++;
			}else{
				hasZero++;
			}
		}
		if (hasZero != 1){
			throw new InvalidMoveAtemtException(
					MacroCompiler.compile("Invalid move atempt. Each configuration has to have exactly one zero field. Attemted had ${0} instead.", 
							Arrays.asList(Integer.toString(hasZero))));
		}
		return GameState.ENDED;
	}
}
