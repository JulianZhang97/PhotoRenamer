package photo_renamer;

/**
 * An exception to be thrown when TagManager tries to add tags
 * that's already in Photo or in the list of available tags.
 *
 */
public class DuplicateTagsException extends Exception {

	/** The serialID for serialization purposes. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * An instance of DuplicateTagsExeception.
	 *  
	 * @param message
	 * 		The message to be displayed when this exception is thrown. 
	 */
	public DuplicateTagsException(String message) {
		super(message);
	}
}
