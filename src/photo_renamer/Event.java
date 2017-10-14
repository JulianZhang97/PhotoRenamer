package photo_renamer;

import java.io.Serializable;

/**
 * An event representing the renaming of an image file.
 * @author group_0653
 *
 */
public class Event implements Serializable {
	
	/** The serialID for serialization purposes. */
	private static final long serialVersionUID = -8340846714462692418L;

	/** The prior name of a photo before the renaming event.*/
	private String oldName;
	
	/** The new name of a photo after the renaming event.*/
	private String newName;
	
	/** The date and time when the renaming happens.*/
	private String timestamp;
	
	/**
	 * A new event occurrence with the prior and new names as well as the time of the naming event.
	 * 
	 * @param oldName 
	 * 		the prior name of a photo.
	 * @param newName 
	 * 		the new name of a photo.
	 * @param timestamp the date and time when the photo is renamed,
	 * 			in DD/MM/YYYY, hh:mm AM/PM format
	 */
	public Event(String oldName, String newName, String timestamp) {
		this.oldName = oldName;
		this.newName = newName;
		this.timestamp = timestamp;
	}

	/**
	 * Returns the prior name of the photo being renamed in this Event.
	 * 
	 * @return the old name of the photo.
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * Returns the new name of the photo being renamed in this Event.
	 * 
	 * @return the new name of the photo.
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * Returns the date and time of this Event occurrence.
	 *
	 *
	 * @return the time and date of this Event. 
	 */
	public String getTimestamp() {
		return timestamp;
	}
	
}
