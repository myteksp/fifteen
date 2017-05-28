package com.gf.fifteen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public final class InvalidGameStateException extends RuntimeException{
	private static final long serialVersionUID = -5243177775496380125L;
	public InvalidGameStateException(final String message){
		super(message);
	}
}
