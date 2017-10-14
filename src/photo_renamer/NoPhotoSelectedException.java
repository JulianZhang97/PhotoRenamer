package photo_renamer;


/**
 * An exception to be thrown when no photo is selected by the program and 
 * the user tries to add tags or revert the photo's name. 
 * 
 * @author group_0653
 *
 */

public class NoPhotoSelectedException extends Exception{
	
	/** The serialID for serialization purposes. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * An instance of NoPhotoSelectedException.
	 * 
	 * @param message
	 * 		The message to be displayed when this exception is thrown.
	 */
	public NoPhotoSelectedException(String message) {
		super(message);
	}
}
