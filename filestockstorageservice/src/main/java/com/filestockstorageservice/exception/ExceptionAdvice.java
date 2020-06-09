package com.filestockstorageservice.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.constants.LoggerConstants;

/**
 * The class ExceptionAdvice
 * 
 * @author harsh.jain
 *
 */
@Controller
@RestControllerAdvice
public class ExceptionAdvice {
	/** The Constant LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

	/**
	 * The handleFileIsNotFoundException
	 * 
	 * @param e
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = FileIsNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleFileIsNotFoundException(FileIsNotFoundException e) {
		LOGGER.error(LoggerConstants.METHOD_INVOKE_FILE_HANDLE, Constant.FILE_NOT_FOUND);
		CustomErrorResponse error = new CustomErrorResponse(Constant.FILE_ERROR, e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * The InValidFileFound
	 * 
	 * @param InValidFileFound inValidFileFound.
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = InValidFileFound.class)
	public ResponseEntity<CustomErrorResponse> InValidFileFound(InValidFileFound inValidFileFound) {
		LOGGER.error(LoggerConstants.METHOD_INVOKE_FILE_HANDLE, Constant.INVALID_FILE);
		CustomErrorResponse error = new CustomErrorResponse(Constant.INVALID_FILE, inValidFileFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * The FileHeaderNotFound
	 * 
	 * @param e
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = FileHeaderNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleFileHeaderNotFoundException(FileHeaderNotFound e) {
		LOGGER.error(LoggerConstants.METHOD_INVOKE_FILE_HANDLE, Constant.FILE_NOT_FOUND);
		CustomErrorResponse error = new CustomErrorResponse(Constant.FILE_HEADER_NOT_FOUND, e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * The InvalidRequestParam
	 * 
	 * @param e
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = InvalidRequestParam.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(InvalidRequestParam e) {
		CustomErrorResponse error = new CustomErrorResponse(Constant.INVALID_REQUEST_PARAM, e.getMessage(), 0, null);
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}