package com.gf.fifteen.services;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.gf.fifteen.entities.dao.user.UserEntity;
import com.gf.fifteen.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceSanityTest {

	@Autowired
	private UserService users;
	
	@Test
	public void userServiceSanityTest() {
		final String userName = UUID.randomUUID().toString();
		final String password = UUID.randomUUID().toString();

		try{
			users.loadUserByUsername(userName);
			fail("User '" + userName + "' not created. Expected to throw exception.");
		}catch(final UsernameNotFoundException ex){}

		UserEntity entity = users.signupNewUser(userName, password);

		assertTrue(entity.nodeId != null);
		assertTrue(entity.gameId != null);
		assertEquals(password, entity.password);
		assertEquals(userName, entity.userName);
		
		entity = (UserEntity) users.loadUserByUsername(userName);
		
		assertTrue(entity != null);
		assertTrue(entity.nodeId != null);
		assertTrue(entity.gameId != null);
		assertEquals(password, entity.password);
		assertEquals(userName, entity.userName);

		users.deleteUser(userName);

		try{
			users.loadUserByUsername(userName);
			fail("User '" + userName + "' not created. Expected to throw exception.");
		}catch(final UsernameNotFoundException ex){}
	}
}
