package com.gf.fifteen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dao.game.GameState;
import com.gf.fifteen.exceptions.InvalidGameStateException;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.managers.utils.GameManagerUtil;
import com.gf.fifteen.repos.GameRepo;

@Service
public final class GameService {
	private final GameRepo repo;
	private final GameManagerUtil logic;

	@Autowired
	public GameService(final GameRepo repo, final GameManagerUtil logic){
		this.repo = repo;
		this.logic = logic;
	}
	
	public final int[]  getAllowedGameSizes(){
		return logic.getAllowedSizesRange();
	}

	public final GameEntity getGame(final String gameId){
		GameEntity result = repo.findById(gameId);
		if (result == null){
			result = new GameEntity();
			logic.resetGame(result);
			result = repo.save(result);
		}
		return result;
	}
	
	public final GameEntity resetGame(final String gameId){
		GameEntity result = repo.findById(gameId);
		if (result == null){
			result = new GameEntity();
		}
		logic.resetGame(result);
		result = repo.save(result);
		return result;
	}
	
	public final GameEntity startGame(final String gameId, final int size){
		GameEntity game = resetGame(gameId);
		logic.startGame(game, size);
		game = repo.save(game);
		return game;
	}
	
	public final GameEntity updatePosition(final String gameId, final int[] position){
		GameEntity result = getGame(gameId);
		if (result.state == GameState.IN_PROGRESS){
			if (logic.validateMove(result.position, position)){
				final GameState state = result.state = logic.calculateGameState(result.position);
				if (state == GameState.ENDED){
					result.endDate = System.currentTimeMillis();
				}
				result = repo.save(result);
				return result;
			}else{
				throw new InvalidMoveAtemtException("Invalid move atemt. Incedent will be reported.");
			}
		}else{
			throw new InvalidGameStateException("Invalid game state. Incedent will be reported.");
		}
	}
}
