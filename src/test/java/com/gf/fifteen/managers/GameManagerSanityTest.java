package com.gf.fifteen.managers;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gf.fifteen.entities.dao.game.GameState;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.managers.GameManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class GameManagerSanityTest {
	@Autowired
	private GameManager logic;

	private List<int[]> endedGames;
	private List<int[]> inProgressGames;
	private List<int[]> invalidGames;

	@Before
	public void setup(){
		endedGames = new ArrayList<int[]>();
		inProgressGames = new ArrayList<int[]>();
		invalidGames = new ArrayList<int[]>();

		endedGames.add(new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 0
		});
		endedGames.add(new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 0, 15
		});
		endedGames.add(new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				0, 13, 14, 15
		});

		inProgressGames.add(new int[]{
				2, 1, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				0, 13, 14, 15
		});

		invalidGames.add(new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 16
		});
	}

	@Test
	public void testEndedGamesDetection(){
		for(final int[] position : endedGames){
			assertEquals("Expected 'GameState.ENDED'", GameState.ENDED, logic.calculateGameState(position));
		}
	}

	@Test
	public void testInProgressGameDetection(){
		for(final int[] position : inProgressGames){
			assertEquals("Expected 'GameState.IN_PROGRESS'", GameState.IN_PROGRESS, logic.calculateGameState(position));
		}
	}

	@Test(expected=InvalidMoveAtemtException.class)
	public void testinvalidGameConfigurationDetection(){
		for(final int[] position : invalidGames){
			logic.calculateGameState(position);
		}
	}

	@Test
	public void testMoveValidationValid(){
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
		//valid
		position = new int[]{
				4, 6, 3,
				2, 7, 5,
				1, 0, 8
		};
		move = new int[]{
				4, 6, 3,
				2, 7, 5,
				1, 8, 0
		};
		assertTrue("Validation failed for valid move", logic.validateMove(position, move));
	}


	@Test
	public void testMoveValidationInvalid(){
		//invalid
		int[] position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 0
		};
		int[] move = new int[]{
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
