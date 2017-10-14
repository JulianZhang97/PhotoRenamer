package photo_renamer;

/**
 * An exception that is thrown when TagManager tries to remove one or more tags 
 * that do not exist in a particular photo or in the list of available tags.
 * 
 * @author group_0653
 *
 */
public class NonExistentTagsException extends Exception {

	/** The serialID for serialization purposes. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * An instance of NonExistentTagsException.
	 * 
	 * @param message
	 * 		The message to be displayed when this exception is thrown.
	 */
	public NonExistentTagsException(String message) {
		super(message);
	}
}
