package io.agileintellignece.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNameAlreadyExistsException  extends RuntimeException{

	public UserNameAlreadyExistsException(String message)
	{
		super(message);
	}
	
	 
}
