package photo_renamer;

/**
 * An exception to be thrown when Renamer is unable to successfully rename a file. 
 * This could be due to a conflicting filename in the same directory, a incorrect 
 * filepath, or other issues. 
 * 
 * @author group_0653
 */

public class FileNotRenamedException extends Exception{

	/** The serialID for serialization purposes. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * An instance of FileNotRenamedException.
	 * 
	 * @param message
	 * 		The message to be displayed when this exception is thrown.
	 */
	
	public FileNotRenamedException(String message) {
		super(message);
	}
}
