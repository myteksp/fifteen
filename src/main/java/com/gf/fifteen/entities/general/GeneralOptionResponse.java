package com.gf.fifteen.entities.general;

public class GeneralOptionResponse <T>{
	public final T value;
	public final boolean success;
	
	public GeneralOptionResponse(){
		this.success = false;
		this.value = null;
	}
	
	public GeneralOptionResponse(final T value){
		this.success = true;
		this.value = value;
	}

	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (success ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		final GeneralOptionResponse<?> other = (GeneralOptionResponse<?>) obj;
		if (success != other.success)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "GeneralOptionResponse [value=" + value + ", success=" + success + "]";
	}
}
