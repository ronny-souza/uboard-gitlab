package br.com.uboard.exceptions;

public class SynchronizeMilestonesException extends Exception {

	private static final long serialVersionUID = 1L;

	public SynchronizeMilestonesException() {

	}

	public SynchronizeMilestonesException(String message) {
		super(message);
	}

	public SynchronizeMilestonesException(String message, Exception e) {
		super(message, e);
	}

}
