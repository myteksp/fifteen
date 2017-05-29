package com.gf.fifteen.entities.dto.game;

import java.util.Arrays;

import com.gf.fifteen.entities.dao.game.GameState;

public final class GameDTO {
	public String id;
	public GameState state;
	public long startDate;
	public long endDate;
	public int size;
	public int[] position;
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GameDTO other = (GameDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public final String toString() {
		return "GameDTO [id=" + id + ", state=" + state + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", size=" + size + ", position=" + Arrays.toString(position) + "]";
	}
}
