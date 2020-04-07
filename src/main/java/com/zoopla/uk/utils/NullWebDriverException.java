package com.zoopla.uk.utils;

public class NullWebDriverException extends Exception {
	/**
	 * This class is to throw custom null webDriver exception
	 */
	private static final long serialVersionUID = 1L;

	public NullWebDriverException(String userMessage) {
		super(userMessage);
	}
}
