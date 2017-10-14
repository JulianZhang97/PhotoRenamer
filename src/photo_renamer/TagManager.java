package photo_renamer;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
 * A tag manager that manages tags in this application.
 * @author group_0653
 */
public class TagManager {
	
	/** 
	 * A list of currently available tags.
	 * */
	private ArrayList<String> availableTags;
	
	/**
	 * A new instance of TagManager with an empty list of available tags.
	 */
	public TagManager() {
		availableTags = new ArrayList<String>();
	}

	/**
	 * Sets the list of available tags in this TagManager. 
	 * This is only to be used to restore a serialized TagManager 
	 * and should not be accessible outside the class. 
	 * 
	 * @param tags
	 * 		the ArrayList of String tags to set TagManager's availableTags to. 
	 */
	private void setAvailableTags(ArrayList<String> tags){
    	this.availableTags = tags;
    }
	
	/**
	 * Returns the list of currently available tags.
	 * 
	 * @return the list of available tags.
	 */
	public ArrayList<String> getAvailableTags() {
		return availableTags;
		
	}
	
	/**
	 * Returns all of the available tags recorded by this TagManager 
	 * in a user-friendly format.
	 * 
	 * @return the string representation of all tags in availableTags.
	 */
	public String toString() {
		String allTags = "";
		for (String tag: getAvailableTags()) {
			allTags += "@" + tag + "\n";
		}
		return allTags;	
	}
	
	/**
	 * Adds one or more tags to the existing ArrayList of tags availableTags 
	 * in this TagManager instance. 
	 * 
	 * @param tags
	 * 		the tags to be added to this TagManager instance.
	 * 
	 * @throws DuplicateTagsException
	 * 		if one or more tags to be added already exist in this 
	 * 		TagManager instance. 
	 */
	public void addAvailableTags(ArrayList<String> tags) throws DuplicateTagsException{
		ArrayList<String> validTags = new ArrayList<>();
		ArrayList<String> invalidTags = new ArrayList<>();
		for(String tag: tags){
			if (!availableTags.contains(tag)) {
				validTags.add(tag);
			} else {
				invalidTags.add(tag);
			}
		}
		
		if (invalidTags.size() > 0) {
			throw new DuplicateTagsException("Tags are already in the program!");
		} else {
			for (String validTag: validTags) {
				availableTags.add(validTag);
			}
		}
	}
	
	/**
	 * Removes one or more tags from the existing ArrayList of tags availableTags 
	 * in this TagManager instance. 
	 * 
	 * @param tags
	 * 		the tags to be removed from this TagManager instance.
	 * 
	 * @throws NonExistentTagsException
	 * 		if one or more tags to be removed do not exist in this TagManager instance. 
	 */
	public void removeAvailableTags(ArrayList<String> tags) throws NonExistentTagsException{
		ArrayList<String> validTags = new ArrayList<>();
		ArrayList<String> invalidTags = new ArrayList<>();
		for(String tag: tags){
			if (availableTags.contains(tag)) {
				validTags.add(tag);
			} else {
				invalidTags.add(tag);
			}
		}
		
		if (invalidTags.size() > 0) {
			throw new NonExistentTagsException("Cannot remove non-existant tags!");
		} else {
			for (String validTag: validTags) {
				availableTags.remove(validTag);
			}
		}
	}
	    
	/**
	 * Renames photo by adding one or more tags to the photo. 
	 * Returns a new Event object that represents the renaming of photo.
	 * 
	 * @param tags 
	 * 		list of tags that are going to be added to the photo.
	 * 
	 * @param photo 
	 * 		the photo to be renamed.
	 * 
	 * @throws DuplicateTagsException if a tag that is going to be added is 
	 * 		already included in the name of photo.
	 * 
	 * @throws FileNotRenamedException if renaming is unsuccessful.
	 * 
	 * @return the Event object that represents the renaming of photo.
	 */
	public Event addPhotoTags(ArrayList<String> tags, Photo photo) throws DuplicateTagsException, FileNotRenamedException {
		
		String toAdd = new String();
		
		/* if one or more tags from tags are already in the photo,
		 * throw exception */
		if (nameContainsTag(tags, photo)) {
			throw new DuplicateTagsException("ERROR: tag is already in the photo!");
		} else {

			/* adds tags into the list of available tags
			 * if they are not already in it. Builds the String of tags to add to the photo name.*/
		   
				/* more than one photo can share the same tags,
				 * so if tag is already in the list of available tags,
				 * we don't have to add it again
				 */
			 for (String tag: tags) {
			    //if (!availableTags.contains(tag)) {
			    //	availableTags.add(tag);
			    //}
			    toAdd += "@" + tag;
			    
			 }
			 
			//Creates the new photo name with the added tag(s) 
			String logOldName = photo.getCurName();
			String logNewName = logOldName.substring(0, logOldName.length() - 4);	
			logNewName += toAdd; 
			logNewName += logOldName.substring(logOldName.length() - 4);	
				
				
			//Creates the new filepath so the photo file can be renamed
			File oldFile = photo.getPhotoFile();
			String oldFileName = oldFile.getPath();
			String newFileName = oldFileName.substring(0, oldFileName.length() - 4);
			newFileName += toAdd;
			newFileName += oldFileName.substring(oldFileName.length() - 4);		
			File newFile = new File(newFileName);
				
			//Renames the photo
			if(oldFile.renameTo(newFile)){
					;
			}
			else{
				throw new FileNotRenamedException("This file could not be renamed.");		
			}				
				
			//Sets the new name of the photo
			photo.setCurName(logNewName);
			// NEW LINE
			photo.setPhotoFile(newFile);
				
			 
			//Gets the current system time for Event creation
			Calendar calendar = Calendar.getInstance();
			String cur_time = calendar.getTime().toString();
				
			//Creates a new event for the log to record
			Event newEvent = new Event(logOldName, logNewName, cur_time);
			return newEvent;
		    }
	
		}

	/**
	 * Utilizes Renamer to remove the chosen tags from photo and
	 * removes these tags from the list of available tags.
	 * Returns a new Event object that represents the renaming of photo.
	 * 
	 * @param tags 
	 * 		the list of tags that are going to be removed from availableTags.
	 * 
	 * @param photo 
	 * 		the photo file that is going to be renamed.
	 * 
	 * @throws NonExistentTagsException if a tag that is going to
	 * 		be removed is not in the name of photo.
	 * 
	 * @throws FileNotRenamedException if renaming is unsuccessful.
	 * 
	 * @return the Event object that represents the renaming of photo.
	 */
	public Event removeTags(ArrayList<String> tags, Photo photo) throws NonExistentTagsException, FileNotRenamedException {
		
		ArrayList<String> toDel = new ArrayList<String>();
		
		 /*if one or more tags from tags are not in the photo,
		 throw exception */
		
		if (!tags.isEmpty() && !nameContainsTag(tags, photo) ) {
			throw new NonExistentTagsException("ERROR: can't remove tag, it doesn't exist!");		
	    	}
	    	
        /* remove tags from the list of available tags
   		if they are in it */	        
    	for (String tag: tags) {
    		String taggedElement = "@" + tag;
    		toDel.add(taggedElement);
	        }

	 
    	//Builds the Array of tags to delete

		
		//Creates the new photo names with the removed tags
		String logOldName = photo.getCurName();
		String logNewName = logOldName;
		for(String element: toDel){
			logNewName = logNewName.replace(element, "");
		}
		
		//Creates the new filepath so the photo file can be renamed
		File oldFile = photo.getPhotoFile();
		String oldName = oldFile.getPath();
		String newName = oldName.replace(logOldName, "");
		String newFileName = newName += logNewName;
		File newFile = new File(newFileName);
		
		//Renames the photo
		if(oldFile.renameTo(newFile)){
			;
		}
		else{
			throw new FileNotRenamedException("This file could not be renamed.");	
			}	
		
		//Sets the new name of the photo
		photo.setCurName(logNewName);
		// NEW LINE
		photo.setPhotoFile(newFile);
		
    	
        //Gets the current system time for Event creation
		Calendar calendar = Calendar.getInstance();
		String cur_time = calendar.getTime().toString();
				
		//Creates a new event for the log to record
		Event newEvent = new Event(logOldName, logNewName, cur_time);
		return newEvent;
	}


	/**
	 * Changes the photo's name from its current name to a certain past name. 
	 * Returns a new Event object that represents the renaming of photo.
	 *
	 * Precondition: String oldName is a name that the photo has had at some 
	 * point in the program history. 
	 * 
	 * @param photo
	 * 		the photo file that is going to be renamed.		
	 * 
	 * @param oldName 
	 * 		the past photo name that the photo is to be reverted to. 
	 * 
	 * @throws FileNotRenamedException if file renaming is unsuccessful.
	 * 
	 * @return the Event object that represents the renaming of photo.
	 */
	public static Event revertName(Photo photo, String oldName) throws FileNotRenamedException{
		String logOldName = photo.getCurName();
		String logNewName = oldName;
		
		//Creates the new filepath so the photo file can be renamed
		File oldFile = photo.getPhotoFile();
		String oldFileName = oldFile.getPath();
		String newFileName = oldFileName.substring(0, oldFileName.length() - logOldName.length());
		newFileName += oldName;	
		File newFile = new File(newFileName);
				
		//Renames the photo
		if(oldFile.renameTo(newFile)){
			;
		}
		
		else{
			throw new FileNotRenamedException("This file could not be renamed.");		
		}
		
		//Sets the new name of the photo
		photo.setCurName(oldName);
		// NEW LINE
		photo.setPhotoFile(newFile);
		
		//Gets the current system time for Event creation
		Calendar calendar = Calendar.getInstance();
		String cur_time = calendar.getTime().toString();
				
		//Creates a new event for the log to record
		Event newEvent = new Event(logOldName, logNewName, cur_time);
		return newEvent;
	}
	
	/**
	 * Returns an array list of tags that are included in
	 * the name of photo.
	 * 
	 * @param photo 
	 * 		the photo which the list of tags is going to be
	 * 		obtained from.
	 *
	 * @return the array list of tags that are included in the name of the photo.
	 */
    public static ArrayList<String> getTagsInNameList(Photo photo) {
    	ArrayList<String> tagsInNameList = new ArrayList<String>();

    	int firstTagIndex = photo.getCurName().indexOf('@');
    	int extensionIndex = photo.getCurName().indexOf('.');
    	if (firstTagIndex > 0) {
    		String tagsInName = photo.getCurName().substring(firstTagIndex + 1, extensionIndex); 
    		String[] tagsInNameSplit = tagsInName.split("@");
    		for (String tag: tagsInNameSplit) {
    			tagsInNameList.add(tag);
    		}
    	} else {
    		
    	}
		return tagsInNameList;
    }
    
    /**
     * Returns true if the name of photo file includes one or more tags 
     * from the list of tags specified by the user.
     * 
     * @param tags 
     * 		the list of tags specified by the user.
     * 
     * @param photo 
     * 		the photo file that is going to be renamed.
     * 
     * @return true if the name of photo includes one or more tags 
     * 		from tags, false otherwise.
     */
    public static boolean nameContainsTag(ArrayList<String> tags, Photo photo) {
    	ArrayList<String> tagsInNameList = getTagsInNameList(photo);
    	
    	/* creates a new array list that keeps track of the
    	 * tags that are in both tags and tagsInNameList */
    	ArrayList<String> sameTags = new ArrayList<String>();
    	/* checks if each tag in tags is in tagsInNameList*/
    	for (String tag: tags) {
    		if (tagsInNameList.contains(tag)) {
    			sameTags.add(tag);
    		} else {
    			/* do nothing */
    		}
    	}
    	/* returns false if sameTags is empty, since it means that 
    	 * the name of the photo file doesn't contain any tags from tags
    	 * 
    	 * returns true if sameTags is NOT empty, since it means that the 
    	 * name of the photo file contains one or more tags from tags
    	 * */
    	return !sameTags.isEmpty();
     }
 
    /**
     * Serializes the ArrayList of Tags to a file for access by the program 
     * if it is closed and reopened again. 
     * 
     * @param fileName
	 * 		the file to serialize the ArrayList availableTags in TagManager to. 
	 * 
	 * @throws IOException if the file to be serialized to is disturbed during 
	 * 		the serializing process. 
     */
    public void serializeTagList(String fileName) throws IOException {

    	FileOutputStream file = new FileOutputStream(fileName);
    	BufferedOutputStream buffer = new BufferedOutputStream(file);
    	ObjectOutputStream output = new ObjectOutputStream(buffer);
		// serialize the list of available tags
		output.writeObject(availableTags);
		output.close();
    	}
    
    /**
     * Deserializes a serialized ArrayList of Tags and sets the availableTags 
     * of this TagManager to this ArrayList of Tag objects. 
	 * Precondition: The file fileName represents a serialized Object of type 
	 * ArrayList of Tag objects. 
     *  
     * @param fileName
	 * 		The file to deserialize from.
	 * 
	 * @throws IOException if the file to be deserialize from is disturbed 
	 * 		during the read process. 
	 * 
	 * @throws ClassNotFoundException if the TagManager and Tag classes are 
	 * 		not on the classpath. 
     */
    @SuppressWarnings("unchecked")
	public void loadTagList(String fileName) throws IOException, ClassNotFoundException {
    	FileInputStream file = new FileInputStream(fileName);
    	BufferedInputStream buffer = new BufferedInputStream(file);
		ObjectInputStream input = new ObjectInputStream(buffer);
		Object tags = input.readObject();
		input.close();
		setAvailableTags((ArrayList<String>) tags);
    	}

}