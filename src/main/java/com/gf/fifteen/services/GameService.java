package com.gf.fifteen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dao.game.GameState;
import com.gf.fifteen.exceptions.InvalidGameStateException;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.managers.GameManager;
import com.gf.fifteen.repos.GameRepo;

@Service
public final class GameService {
	private final GameRepo repo;
	private final GameManager manager;

	@Autowired
	public GameService(final GameRepo repo, final GameManager manager){
		this.repo = repo;
		this.manager = manager;
	}

	public final int[]  getAllowedGameSizes(){
		return manager.getAllowedSizesRange();
	}
	
	private final GameEntity getOrCreateGame(final String gameId){
		GameEntity result = repo.findById(gameId);
		if (result == null){
			result = new GameEntity();
			result.id = gameId;
		}
		return result;
	}
	
	public final int[] getNextMove(final String gameId, final int number){
		final GameEntity result = getOrCreateGame(gameId);
		return manager.getNextMove(result.position, number);
	}

	public final GameEntity getGame(final String gameId){
		return repo.findById(gameId);
	}

	public final GameEntity resetGame(final String gameId){
		GameEntity result = getOrCreateGame(gameId);
		manager.resetGame(result);
		result = repo.save(result);
		return result;
	}

	public final GameEntity startGame(final String gameId, final int size){
		GameEntity result = getOrCreateGame(gameId);
		result.size = size;
		manager.resetGame(result);
		result = repo.save(result);
		return result;
	}

	public final GameEntity updatePosition(final String gameId, final int[] position){
		GameEntity result = getGame(gameId);
		if (result.state == GameState.IN_PROGRESS){
			if (manager.validateMove(result.position, position)){
				result.position = position;
				final GameState state = result.state = manager.calculateGameState(result.position);
				if (state == GameState.ENDED){
					result.endDate = System.currentTimeMillis();
				}
				result = repo.save(result);
				return result;
			}else{
				throw new InvalidMoveAtemtException("Invalid move atempt.");
			}
		}else{
			throw new InvalidGameStateException("Invalid game state. Incedent will be reported.");
		}
	}
}
