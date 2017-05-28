package com.gf.fifteen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 4256736621929877764L;
	
	public UserAlreadyExistsException(final String message){
		super(message);
	}
}
