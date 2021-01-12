package com.book.room.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.book.room.model.GlobalExceptionResponse;

//@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({CustomerNotFoundException.class})
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public GlobalExceptionResponse handleResouceNotFound(final CustomerNotFoundException exception,
															final HttpServletRequest request) {
		GlobalExceptionResponse response = new GlobalExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler({HotelNotFoundException.class})
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public GlobalExceptionResponse handleHotelNotFound(final HotelNotFoundException exception,
															final HttpServletRequest request) {
		GlobalExceptionResponse response = new GlobalExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler({RoomTypeNotFoundException.class})
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public GlobalExceptionResponse handleRoomTypeNotFound(final RoomTypeNotFoundException exception,
															final HttpServletRequest request) {
		GlobalExceptionResponse response = new GlobalExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public GlobalExceptionResponse handleException(final Exception exception,
											final HttpServletRequest request) {
		GlobalExceptionResponse response = new GlobalExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}
}