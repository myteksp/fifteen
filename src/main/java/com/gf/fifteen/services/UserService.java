package com.gf.fifteen.services;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gf.fifteen.entities.user.UserEntity;
import com.gf.fifteen.exceptions.UserAlreadyExistsException;
import com.gf.fifteen.repos.UserRepo;
import com.gf.util.string.MacroCompiler;

@Service
@Transactional
public class UserService implements UserDetailsService{
	private final UserRepo repo;
	@Autowired
	public UserService(final UserRepo repo){
		this.repo = repo;
	}


	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if (username == null)
			throw new NullPointerException("User name can not be null");

		final UserEntity user = repo.findByUserName(username);
		if (user == null)
			throw new UsernameNotFoundException(
					MacroCompiler.compile("User ${0} not found.", Arrays.asList(username)));

		return user;
	}

	public UserEntity signupNewUser(final String username, final String password){
		try{
			loadUserByUsername(username);
			throw new UserAlreadyExistsException(
					MacroCompiler.compile("User ${0} already exists.", Arrays.asList(username)));
		}catch (final UsernameNotFoundException e) {}
		if (password == null){
			throw new NullPointerException("User password can not be null");
		}
		UserEntity user = new UserEntity(username, password, UUID.randomUUID().toString());
		user = repo.save(user);
		return user;
	}
	
	public void deleteUser(final String username){
		final UserEntity user = repo.findByUserName(username);
		if (user == null)
			return;
		repo.delete(user.nodeId);
	}
}
