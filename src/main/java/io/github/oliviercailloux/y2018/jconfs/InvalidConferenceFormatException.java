package io.github.oliviercailloux.y2018.jconfs;


public class InvalidConferenceFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidConferenceFormatException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidConferenceFormatException(String errorMessage, Exception e) {
		super(errorMessage,e);
	}
}
