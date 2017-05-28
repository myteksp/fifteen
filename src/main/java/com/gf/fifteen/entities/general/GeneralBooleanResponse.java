package com.gf.fifteen.entities.general;

public final class GeneralBooleanResponse {
	public final boolean value;
	public GeneralBooleanResponse(final boolean value){
		this.value = value;
	}
	
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		final GeneralBooleanResponse other = (GeneralBooleanResponse) obj;
		if (value != other.value)
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "GeneralBooleanDTO [value=" + value + "]";
	}
}
