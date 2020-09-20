package com.brightcoding.app.ws.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.brightcoding.app.ws.responses.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(value = { UserException.class })

	public ResponseEntity<Object> HandleUserException(UserException ex, WebRequest request) {
		LocalDate myDate= LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		 String formattedDate = myDate.format(myFormatObj);
		ErrorMessage errorMessage = new ErrorMessage(formattedDate, ex.getMessage());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(value = { Exception.class })

	public ResponseEntity<Object> HandleOtherException(Exception ex, WebRequest request) {
		LocalDate myDate= LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		 String formattedDate = myDate.format(myFormatObj);
		ErrorMessage errorMessage = new ErrorMessage(formattedDate, ex.getMessage());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
