/*============================================================================*/

package de.tu_darmstadt.gdi1.plumber.exceptions;

/*============================================================================*/

/**
 * Exception that occurs in the case of internal errors. If a method throws this
 * exception, this normally indicates a bug and there is nothing that the caller
 * could do.
 * 
 * @author Steven Arzt, Oren Avni, Guido Roessling
 * @version 1.0
 */
public class InternalFailureException extends Exception {

	/* ======================================================================== */

	private static final long serialVersionUID = 0L;

	/* ======================================================================== */

	/**
	 * Creates a new instance of the InternalFailureException class based upon
	 * the reason for creation (treated as an inner exception).
	 * 
	 * @param cause
	 *            The cause why the internal error has occurred. Pass the
	 *            original exception or a more detailed internal one here.
	 */
	public InternalFailureException(Throwable cause) {
		super(cause);
	}

	/* ======================================================================== */

	/**
	 * Creates a new instance of the InternalFailureException class based upon a
	 * textual reason
	 * 
	 * @param errorMessage
	 *            The reason why this error has occurred
	 */
	public InternalFailureException(String errorMessage) {
		super(errorMessage);
	}

	/* ======================================================================== */

}

/* ============================================================================ */
