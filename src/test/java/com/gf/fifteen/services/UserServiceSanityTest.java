package com.gf.fifteen.services;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
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

	private String userName;
	private String password;

	@Before
	public void setup(){
		userName = UUID.randomUUID().toString();
		password = UUID.randomUUID().toString();
	}
	
	@After
	public void cleanup(){
		users.deleteUser(userName);
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testNonexistingUserLoad(){
		users.loadUserByUsername(UUID.randomUUID().toString());
	}

	@Test
	public void testSignupUser(){
		final UserEntity entity = users.signupNewUser(userName, password);
		assertTrue("Node id was null", entity.nodeId != null);
		assertTrue("Game id was null", entity.gameId != null);
		assertEquals("Password was null", password, entity.password);
		assertEquals("Username was null", userName, entity.userName);
		users.deleteUser(userName);
	}

	@Test
	public void testLoadUser(){
		users.signupNewUser(userName, password);
		final UserEntity entity = (UserEntity) users.loadUserByUsername(userName);
		assertTrue("user was null", entity != null);
		assertTrue("Nide id was null", entity.nodeId != null);
		assertTrue("Game id was null", entity.gameId != null);
		assertEquals("Password was null", password, entity.password);
		assertEquals("Username was null", userName, entity.userName);
		users.deleteUser(userName);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testUserDelete(){
		users.deleteUser(userName);
		users.loadUserByUsername(userName);
	}
}
