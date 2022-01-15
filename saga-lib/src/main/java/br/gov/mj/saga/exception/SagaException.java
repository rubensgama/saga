/**
 * 
 */
package br.gov.mj.saga.exception;

/**
 * @author Meta
 */
public class SagaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2745325986688845102L;

	/**
	 * 
	 */
	public SagaException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SagaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SagaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SagaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SagaException(Throwable cause) {
		super(cause);
	}
}
