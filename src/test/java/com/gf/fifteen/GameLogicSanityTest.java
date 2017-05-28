package com.gf.fifteen;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gf.fifteen.entities.game.GameState;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.logic.GameLogicUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameLogicSanityTest {
	@Autowired
	private GameLogicUtil logic;
	
	@Test
	public void gameStateLogicSanityTest() {
		//Ended
		int[] position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 0
		};
		assertEquals("Expected 'GameState.ENDED'", GameState.ENDED, logic.calculateGameState(position));
		//Ended
		position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 0, 15
		};
		assertEquals("Expected 'GameState.ENDED'", GameState.ENDED, logic.calculateGameState(position));
		//Ended
		position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				0, 13, 14, 15
		};
		assertEquals("Expected 'GameState.ENDED'", GameState.ENDED, logic.calculateGameState(position));
		//In-progress
		position = new int[]{
				2, 1, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				0, 13, 14, 15
		};
		assertEquals("Expected 'GameState.IN_PROGRESS'", GameState.IN_PROGRESS, logic.calculateGameState(position));

		//invalid
		position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 16
		};
		try{
			logic.calculateGameState(position);
			fail("Expected to fail without zero.");
		}catch(final InvalidMoveAtemtException ex){}
	}
	
	
	@Test
	public void gameMovesLogicSanityTest() {
		//valid
		int[] position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 0
		};
		int[] move = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 0,
				13, 14, 15, 12
		};
		assertTrue("Validation failed for valid move", logic.validateMove(position, move));
		
		//invalid
		position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 0
		};
		move = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 0, 12,
				13, 14, 15, 11
		};
		assertFalse("Validation passed for invalid move", logic.validateMove(position, move));
		
		//invalid
		position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 0,
				12, 13, 14, 15
		};
		move = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				0, 13, 14, 15
		};
		assertFalse("Validation passed for invalid move", logic.validateMove(position, move));
	}
}
