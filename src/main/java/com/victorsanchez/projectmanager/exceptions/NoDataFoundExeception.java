package com.victorsanchez.projectmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class NoDataFoundExeception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundExeception() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundExeception(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundExeception(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundExeception(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoDataFoundExeception(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
