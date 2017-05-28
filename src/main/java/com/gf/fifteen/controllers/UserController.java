package com.gf.fifteen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gf.fifteen.entities.general.GeneralBooleanResponse;
import com.gf.fifteen.entities.general.GeneralStringRequest;
import com.gf.fifteen.entities.user.SignupUserRequest;
import com.gf.fifteen.entities.user.UserEntity;
import com.gf.fifteen.services.UserService;

@RestController
public class UserController {
	private final UserService service;
	@Autowired
	public UserController(final UserService service){
		this.service = service;
	}

	@PostMapping(path="/user/exists", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final GeneralBooleanResponse userExists(
			@RequestBody(required=true)final GeneralStringRequest request){
		try{
			service.loadUserByUsername(request.value);
		}catch(final UsernameNotFoundException ex){
			return new GeneralBooleanResponse(false);
		}
		return new GeneralBooleanResponse(true);
	}
	
	@PostMapping(path="/user/signup", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final UserEntity signup(@RequestBody(required=true)final SignupUserRequest request){
		return service.signupNewUser(request.username, request.password);
	}
}
