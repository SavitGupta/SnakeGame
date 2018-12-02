//@formatter:on

/**
 * Exception class, called when no User of specified name exists
 */
public class NoSuchPlayerException extends Exception
{
	/**
	 * Constructor
	 * @param message Message to be displayed.
	 */
	public NoSuchPlayerException(String message)
	{
		super(message);
	}
}