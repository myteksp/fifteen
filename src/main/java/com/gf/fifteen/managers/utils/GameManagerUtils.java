package com.gf.fifteen.managers.utils;

import java.util.Arrays;

import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.util.string.MacroCompiler;

public final class GameManagerUtils {

	public static final int[] generateRandomSolvablePosition(final int size){
		//TODO: check if possible to generate analytically with some king of built-in parity
		final int len = size * size;
		final int[] result = new int[len];
		final int lastIndex = len - 1;
		for (int i = 0; i < lastIndex; i++) 
			result[i] = i + 1;
		
		result[lastIndex] = 0;
		final int swapIndexLimit = lastIndex - 1;
		
		do{
			for (int i = 0; i < lastIndex; i++) {
				swap(result, i, randomIndex(swapIndexLimit));
			}
		}while(!isSolvable(result) && isSolved(result));
		
		return result;
	}
	
	public static final boolean isSolved(final int[] position){
		int actualIndex = 1;
		int hasZero = 0;
		for(final int num : position){
			if (num > 0){
				if (num != actualIndex)
					return false;
				
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
		return true;
	}
	
	private static final int[] generateMove(final int[] position, final int zeroindex, final int numIndex){
		final int[] result = new int[position.length];
		System.arraycopy(position, 0, result, 0, position.length);
		swap(result, zeroindex, numIndex);
		return result;
	}
	
	public static final int[] generateNextMove(final int[] position, final int number){
		final int[] validIndexes = validZeroIndexes(position);
		final int valIndex = findValueIndex(position, number);
		
		for(final int index : validIndexes)
			if (index == valIndex)
				return generateMove(position, findValueIndex(position, 0), valIndex);
		
		return null;
	}
	
	public static final boolean validateMove(final int[] prevPosition, final int[] newPosition){
		final int[] validIndexes = validZeroIndexes(prevPosition);
		final int newZeroIndex = findValueIndex(newPosition, 0);
		
		for(final int index : validIndexes)
			if (index == newZeroIndex)
				return true;
		
		return false;
	}
	
	
	
	//=================Helpers and utility methods=====================
	//TODO: consider splitting into separated utilities/finding standard libraries
	private static final boolean isSolvable(final int[] position){
		int inversions = 0;
		
        for (int i = 0; i < position.length - 1; i++) 
            for (int j = 0; j < i; j++) 
                if (position[j] > position[i])
                	inversions++;
            
        return inversions % 2 == 0;
	}
	
	private static final void swap(final int[] arr, final int index1, final int index2){
		final int tmp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tmp;
	}
	
	private static final int randomIndex(final int limit){
		final int result = (int)Math.round((Math.random() * (double)limit));
		if (result > limit)
			return limit;
		return result;
	}
	
	private static final int indexRow(final int index, final int size){
		return (index - indexCol(index, size))/size;
	}

	private static final int indexCol(final int index, final int size){
		return index%size;
	}
	
	private static final int[] validZeroIndexes(final int[] position){
		final int size = (int) Math.sqrt(position.length);
		final int zeroIndex = findValueIndex(position, 0);

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
		if (indexRow(zeroIndex, size) == indexRow(candidate, size)){
			result[2] = candidate;
		}
		candidate = zeroIndex - 1;
		if (indexRow(zeroIndex, size) == indexRow(candidate, size)){
			result[3] = candidate;
		}
		return result;
	}

	private static final int findValueIndex(final int[] position, final int value){
		for (int i = 0; i < position.length; i++) 
			if (position[i] == value)
				return i;
		
		return -1;
	}
}
