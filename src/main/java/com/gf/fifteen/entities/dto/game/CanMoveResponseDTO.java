package com.gf.fifteen.entities.dto.game;

public final class CanMoveResponseDTO {
	public final boolean canMove;
	public final int[] nextPosition;
	
	public CanMoveResponseDTO(){
		this.canMove = false;
		this.nextPosition = null;
	}
	
	public CanMoveResponseDTO(final int[] nextPosition){
		this.nextPosition = nextPosition;
		this.canMove = nextPosition == null?false:true;
	}
	
	
}
