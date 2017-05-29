package com.gf.fifteen.managers.utils;

import java.util.Arrays;

import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.util.string.MacroCompiler;

public final class GameManagerUtils {

	private static final boolean isSolvable(final int[] position){
		//TODO implement after UI
		return true;
	}

	public static final int[] generateRandomSolvablePosition(final int size){
		//TODO: find analytic way for generation
		final int len = size * size;
		final int[] result = new int[len];
		final int lastIndex = len - 1;
		for (int i = 0; i < lastIndex; i++) 
			result[i] = i + 1;
		
		result[lastIndex] = 0;
		
		do{
			for (int i = 0; i < lastIndex; i++) {
				final int newindex = (int) Math.round(Math.random() * lastIndex);
				final int tmp = result[newindex];
				result[newindex] = result[i];
				result[i] = tmp;
			}
		}while(!isSolvable(result));
		return result;
	}

	public static final int[] validZeroIndexes(final int[] position){
		final int size = (int) Math.sqrt(position.length);
		final int zeroIndex = zeroIndex(position);

		final int[] result = new int[]{-1, -1, -1, -1};
		int candidate = zeroIndex - size;
		if (candidate >= 0){
			result[0] = candidate;
		}
		candidate = zeroIndex + size;
		if (candidate < position.length){
			result[1] = candidate;
		}
		candidate = zeroIndex + 1;
		if (zeroRow(zeroIndex, size) == zeroRow(candidate, size)){
			result[2] = candidate;
		}
		candidate = zeroIndex - 1;
		if (zeroRow(zeroIndex, size) == zeroRow(candidate, size)){
			result[3] = candidate;
		}
		return result;
	}

	public static final int zeroRow(final int index, final int size){
		return (index - zeroCol(index, size))/size;
	}

	public static final int zeroCol(final int index, final int size){
		return index%size;
	}

	public static final int zeroIndex(final int[] position){
		for (int i = 0; i < position.length; i++) {
			switch(position[i]){
			case 0:
				return i;
			}
		}
		throw new InvalidMoveAtemtException(
				MacroCompiler.compile("Invalid move atempt. Each configuration has to have exactly one zero field. Attemted had ${0} instead.", 
						Arrays.asList(Integer.toString(0))));
	}
}
