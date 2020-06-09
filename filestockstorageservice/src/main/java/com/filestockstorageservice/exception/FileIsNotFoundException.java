package com.filestockstorageservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileIsNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
   	private static final long serialVersionUID = -668096117498144556L;
	 /**
	   * Instantiates a new File is not found exception.
	   *
	   * @param message
	   *          the message
	   */
	  
	public FileIsNotFoundException(String message) {
        super(message);
    }


}