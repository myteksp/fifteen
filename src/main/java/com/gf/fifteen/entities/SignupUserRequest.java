package com.gf.fifteen.entities;

public final class SignupUserRequest {
	public String username;
	public String password;
	
	public SignupUserRequest(){}
	
	public SignupUserRequest(final String username, final String password){
		this.username = username;
		this.password = password;
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		SignupUserRequest other = (SignupUserRequest) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "SignupUserRequest [username=" + username + ", password=" + password + "]";
	}
}
