package com.usermanagementservice.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * The class ExceptionAdvice
 * 
 * @author harsh.jain
 *
 */
@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * The validationError.
	 * 
	 * @param WebExchangeBindException ex.
	 * 
	 * @return ValidationFailureResponse
	 */
	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationFailureResponse validationError(WebExchangeBindException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<FieldErrorDto> fieldErrorDtos = fieldErrors.stream().map(FieldErrorDto::new).collect(Collectors.toList());
		return new ValidationFailureResponse(
				(FieldErrorDto[]) (fieldErrorDtos.toArray(new FieldErrorDto[fieldErrorDtos.size()])));
	}

	/**
	 * The MethodArgumentNotValidException
	 * 
	 * MethodArgumentNotValidException methodArgumentNotValidException.
	 * 
	 * @return ResponseEntity
	 * 
	 */
	@ExceptionHandler(ValidFieldException.class)
	public ResponseEntity<CustomErrorResponse> HandleMethodArgumentNotValidExceptionException(
			MethodArgumentNotValidException ex) {
		CustomErrorResponse error = new CustomErrorResponse(ex.getBindingResult().getFieldError().getDefaultMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.ACCEPTED.value()));
		return new ResponseEntity<>(error, HttpStatus.ACCEPTED);
	}

	/**
	 * @param InvalidNumericeFound
	 * 
	 * @return ResponseEntity
	 */

	@ExceptionHandler(value = InvalidNumericeFound.class)
	public ResponseEntity<CustomErrorResponse> handleInvalidNumericeFoundException(
			InvalidNumericeFound invalidNumericeFound) {
		CustomErrorResponse error = new CustomErrorResponse(invalidNumericeFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param UserOrderHistoryNotFound
	 * 
	 * @return ResponseEntity
	 */

	@ExceptionHandler(value = UserOrderHistoryNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleUserOrderHistoryNotFoundException(
			UserOrderHistoryNotFound userOrderHistoryNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(userOrderHistoryNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * @param UserNotFound
	 * 
	 * @return ResponseEntity
	 */

	@ExceptionHandler(value = SellOrderNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(SellOrderNotFound sellOrderNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(sellOrderNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param UserNotFound
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = UserNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleSellStockNotFoundException(UserNotFound userNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(userNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * @param UserAlreadyExist
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = UserAlreadyExist.class)
	public ResponseEntity<CustomErrorResponse> handleUserAndException(UserAlreadyExist userAlreadyExist) {
		CustomErrorResponse error = new CustomErrorResponse(userAlreadyExist.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param AvailableQuantityNotFound availableQuantityNotFound
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = AvailableQuantityNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleNullPointerException(
			AvailableQuantityNotFound availableQuantityNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(availableQuantityNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param BrokerServiceNotFound brokerServiceNotFound
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = BrokerServiceNotFound.class)
	public ResponseEntity<CustomErrorResponse> handlebrokerServiceNotFoundException(
			BrokerServiceNotFound brokerServiceNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(brokerServiceNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param AuthenticationNotFound authenticationNotFound
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = AuthenticationNotFound.class)
	public ResponseEntity<CustomErrorResponse> handleAuthenticationNotFoundException(
			AuthenticationNotFound authenticationNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(authenticationNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.FORBIDDEN.value()));
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	/**
	 * @param NotValidQuantity notValidQuantity
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = NotValidQuantity.class)
	public ResponseEntity<CustomErrorResponse> handleNotValidQuantityException(NotValidQuantity notValidQuantity) {
		CustomErrorResponse error = new CustomErrorResponse(notValidQuantity.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param UserPortfolioException userPortfolioException
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = UserPortfolioException.class)
	public ResponseEntity<CustomErrorResponse> handleUserPortfolioExceptionException(
			UserPortfolioException userPortfolioException) {
		CustomErrorResponse error = new CustomErrorResponse(userPortfolioException.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param AvailableQuantityNotFound accountBalanceException
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = AccountBalanceException.class)
	public ResponseEntity<CustomErrorResponse> handleAccountBalanceException(
			AccountBalanceException accountBalanceException) {
		CustomErrorResponse error = new CustomErrorResponse(accountBalanceException.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param HandleSymbolNotFoundException
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = SymbolNotFound.class)
	public ResponseEntity<CustomErrorResponse> HandleSymbolNotFoundException(SymbolNotFound symbolNotFound) {
		CustomErrorResponse error = new CustomErrorResponse(symbolNotFound.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}