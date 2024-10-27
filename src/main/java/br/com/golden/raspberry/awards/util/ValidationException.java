package br.com.golden.raspberry.awards.util;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Exception e) {
		super(message, e);
	}
	
}
