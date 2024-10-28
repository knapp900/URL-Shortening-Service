package dev.ak.URL_shortening.exceptions;

public class LinkServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkServiceException() {
		super();
	}

	public LinkServiceException(String message) {
		super(message);
	}

	public LinkServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
