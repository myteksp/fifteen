package com.gf.fifteen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public final class InvalidMoveAtemtException extends RuntimeException{
	private static final long serialVersionUID = 2569275813228454967L;
	
	public InvalidMoveAtemtException(final String message){
		super(message);
	}
}
