package dev.antoniogrillo.primoprogetto.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomHandler {

	@ExceptionHandler(value = {PiattoGiaPresenteException.class})
	public ResponseEntity<String> piattoGiaPresenteHandler(PiattoGiaPresenteException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> sqlConstraintHandler(SQLIntegrityConstraintViolationException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
} 
