package com.gf.fifteen.managers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gf.fifteen.managers.utils.GameManagerUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
public final class GameManagerUtilSanityTest {

	@Test
	public void gameLogicSolvabilityTest() {
		//unsolvable
		int[] position = new int[]{
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 15,
				13, 14, 12, 0
		};
		assertFalse("returned true foe unsolvable sequence", GameManagerUtils.isSolvable(position));
		//unsolvable
		position = new int[]{
				1,3,2,
				4,5,6,
				7,8,0
		};
		assertFalse("returned true foe unsolvable sequence", GameManagerUtils.isSolvable(position));
		//unsolvable
		position = new int[]{
				7,2,8,
				5,3,6,
				4,1,0
		};
		assertFalse("returned true foe unsolvable sequence", GameManagerUtils.isSolvable(position));
	}
}
