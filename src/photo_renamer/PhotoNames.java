package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * A representation of the names, from original to current, of all photos found by the program.
 * 
 * @author group_0653
 */
public class PhotoNames {
	
	/** The HashMap that associates each photo with all of its names so far. */
	private HashMap<String, ArrayList<String>> photoNamesHistory;
	
	/**
	 * A new instance of PhotoNames with an empty HashMap.
	 */
	public PhotoNames(){
		this.photoNamesHistory = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Adds a new name to be stored in the list of names of a 
	 * given photo in photoNamesHistory. 
	 * 
	 * @param photo
	 * 		the Photo with a new name to be added to its history.
	 */
	public void addNewPhotoName(Photo photo){
		ArrayList<String> photoUpdate = photoNamesHistory.get(photo.getOrigName());
		photoUpdate.add(photo.getCurName());
	}
	
	/** 
	 * Returns an ArrayList of Strings representing all the names 
	 * of a given photo, from the original to the current.
	 * 
	 * @param photo
	 * 		the Photo that will be used to obtain its past names.
	 * 
	 * @return an ArrayList of strings, representing all past names of photo. 
	 */
	public ArrayList<String> listNames(Photo photo){
		String originalPhotoName = photo.getOrigName();
		return photoNamesHistory.get(originalPhotoName);
	}
	
	/**
	 * Populates the photoNamesHistory of this PhotoNames with the 
	 * original photo file names as keys and lists of strings to represent 
	 * all the photo's names as values, starting with the original photo name.  
	 *  
	 * NOTE:This method must be run by the program before any photo file renaming is done.
	 * Precondition: directoryFile is a directory.
	 * 
	 * @param photoFiles
	 * 		a list of photo files whose names are going to be added to photoNamesHistory. 
	 */
	public void populatePhotoNames(ArrayList<File> photoFiles){
		
		for(File element: photoFiles){
			 String fileName = Photo.removeAllTags(element);
			 if(!photoNamesHistory.containsKey(fileName)){
				 ArrayList<String> originalPhotoName = new ArrayList<String>();
				 photoNamesHistory.put(fileName, originalPhotoName);
			 }
		 	}
		}
	
	/**
	 * Sets the photoNamesHistory of this PhotoNames to a given HashMap of 
	 * String keys and ArrayList of String values.
	 * 
	 * NOTE: This method is only to be accessed by the class itself for restoring 
	 * a previous PhotoNamesHistory to PhotoNames when deserializing.  
	 * 
	 * @param photoHistory
	 * 		the HashMap of String and ArrayList of Strings to set the HashMap 
	 * 		photoNameHistory of photoNames to. 
	 */
	private void setPhotoNamesHistory(HashMap<String, ArrayList<String>> photoHistory){
		this.photoNamesHistory = photoHistory;
	}

	/**
	 * Serializes the HashMap of String keys and ArrayList of String values of this PhotoNames to a file for access 
	 * by the program if it is closed and reopened again. 
	 * 
	 * @param fileName
	 * 		the file to serialize the HashMap photoNamesHistory in photoNames to. 
	 * 
	 * @throws IOException if the file to be serialized to is disturbed during 
	 * 		the serializing process. 
	 */
	public void serializePhotoNames(String fileName) throws IOException{
		FileOutputStream file = new FileOutputStream(fileName);
		BufferedOutputStream buffer = new BufferedOutputStream(file);
		ObjectOutputStream output = new ObjectOutputStream(buffer);
		output.writeObject(photoNamesHistory);
		output.close();
			}
	
	/**
	 * Deserializes a serialized HashMap of String keys and ArrayList of 
	 * String values and sets the photoNamesHistory of this instance
	 * of PhotoNames to this deserialized HashMap. 
	 * 
	 * @param fileName
	 * 			the file to deserialize from. 
	 * @throws IOException if the file to be deserialized from is disturbed 
	 * 		during the deserializing process.
	 *
	 * @throws ClassNotFoundException if the PhotoNames class is not on the classpath. 
	 */
	@SuppressWarnings("unchecked")
	public void loadPhotoNames(String fileName) throws IOException, ClassNotFoundException{
		FileInputStream file = new FileInputStream(fileName);
		BufferedInputStream buffer = new BufferedInputStream(file);
		ObjectInputStream input = new ObjectInputStream(buffer);
		Object events = input.readObject();
		input.close();
		setPhotoNamesHistory((HashMap<String, ArrayList<String>>) events);
		}
	
}
