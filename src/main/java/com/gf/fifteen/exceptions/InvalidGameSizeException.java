package com.gf.fifteen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public final class InvalidGameSizeException extends RuntimeException{
	private static final long serialVersionUID = -1179894537982650294L;
	public InvalidGameSizeException(final String message){
		super(message);
	}
}
