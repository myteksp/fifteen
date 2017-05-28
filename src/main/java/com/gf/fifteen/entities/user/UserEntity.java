package com.gf.fifteen.entities.user;

import java.util.Arrays;
import java.util.Collection;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NodeEntity
public final class UserEntity implements UserDetails{
	private static final long serialVersionUID = 6414029983591600717L;

	@GraphId
	public Long nodeId;
	
	@Index(primary=false, unique=true)
	public String userName;
	
	public String password;
	
	public String gameId;
	
	
	public UserEntity(){}
	
	public UserEntity(final String userName, final String password, final String gameId){
		this.userName = userName;
		this.password = password;
		this.gameId = gameId;
	}

	@Override
	public final Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new GrantedAuthority() {
			private static final long serialVersionUID = 1368947095984075277L;
			@Override
			public final String getAuthority() {
				return "ROLE_USER";
			}
		});
	}
	@Override
	public final String getPassword() {
		return password;
	}
	@Override
	public final String getUsername() {
		return userName;
	}
	@Override
	public final boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public final boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public final boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public final boolean isEnabled() {
		return true;
	}
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		final UserEntity other = (UserEntity) obj;
		if (gameId == null) {
			if (other.gameId != null)
				return false;
		} else if (!gameId.equals(other.gameId))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public final String toString() {
		return "UserEntity [nodeId=" + nodeId + ", userName=" + userName + ", password=" + password + ", gameId="
				+ gameId + "]";
	}
}
