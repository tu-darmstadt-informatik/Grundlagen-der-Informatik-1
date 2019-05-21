package de.tu_darmstadt.gdi1.plumber.exceptions;

/*============================================================================*/

/**
 * Exception that is thrown whenever a method's parameter falls out of the scope
 * of allowed values
 * 
 * @author Steven Arzt, Oren Avni, Guido Roessling
 * @version 1.0
 */
public class ParameterOutOfRangeException extends Exception {

	/* ======================================================================== */

	private static final long serialVersionUID = 1L;

	private String parameterName = "";
	private Object value = null;

	/* ======================================================================== */

	/**
	 * Creates a new instance of the ParameterOutOfRangeException class
	 * 
	 * @param paramName
	 *            The name of the parameter that has failed the validity check
	 */

	public ParameterOutOfRangeException(String paramName) {
		this(paramName, null);
	}

	/* ======================================================================== */

	/**
	 * Creates a new instance of the ParameterOutOfRangeException class with the
	 * value that has caused the error
	 * 
	 * @param paramName
	 *            The name of the parameter that has failed the validity check
	 * @param aValue
	 *            The value that has caused the error
	 */

	public ParameterOutOfRangeException(String paramName, Object aValue) {
		super ("The " + paramName + " parameter got an invalid value");
		parameterName = paramName;
		value = aValue;
	}

	/* ======================================================================== */

	/**
	 * Creates a new instance of the ParameterOutOfRangeException class with an
	 * inner exception for exception chaining
	 * 
	 * @param paramName
	 *            The name of the parameter that has failed the validity check
	 * @param innerException
	 *            The inner exception that has led to this one
	 */

	public ParameterOutOfRangeException(String paramName,
			Throwable innerException) {
		super(innerException);
		parameterName = paramName;
	}

	/* ======================================================================== */

	/**
	 * Gets the name of the parameter that has failed the validity check
	 * 
	 * @return The name of the parameter that has failed the validity check
	 */

	public String getParameterName() {
		return parameterName;
	}

	/* ======================================================================== */

	/**
	 * Gets the value that has caused the error
	 * 
	 * @return The value that has caused the error
	 */

	public Object getValue() {
		return value;
	}

	/* ======================================================================== */

}

/* ============================================================================ */
