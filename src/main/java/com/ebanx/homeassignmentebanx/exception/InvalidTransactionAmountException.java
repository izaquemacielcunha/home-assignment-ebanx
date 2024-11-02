package com.ebanx.homeassignmentebanx.exception;

public class InvalidTransactionAmountException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidTransactionAmountException(String message) {
		super(message);
	}
}// end of class
