package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The PhotoRenamer Program that implements all of the user features.   
 * 
 * @author group_0653
 */
public class Program {
	
	/** Responsible for managing and storing all the names for each photo in the program. */
	private PhotoNames newPhotoNames;
	
	/** Responsible for managing and storing Events in the program. */
	private Log newLog;
	
	/** Responsible for managing and storing all Tags in the program. */  
	private TagManager newTagManager;
	
	/** The selected Photo for the program to rename or access information through. */
	private Photo selectedPhoto;
	
	/** All the photo files in the current directory. */
	private ArrayList<File> directoryFiles;
	
	/** The current directory selected by the program. */
	private File curDirectory; 
	
	
	/**
	 * An instance of the PhotoRenamer program.
	 */
	public Program(){
		this.newPhotoNames = new PhotoNames();
		this.newLog = new Log();
		this.newTagManager = new TagManager();
		this.directoryFiles = new ArrayList<File>();
	}
	
	/**
	 * A method that must be called after an instance of the program is instantiated. 
	 * Checks if a previous instance of the program has run before and serializes 
	 * instances of the PhotoNames class, Log class, and TagManager class. 
	 * Loads these instances into the newly instantiated classes of this instance 
	 * of the Photo Renamer program. 
	 * 
	 * @see PhotoNames
	 * @see Log
	 * @see TagManager
	 */
	public void open(){
		
		String curDir = System.getProperty("user.dir");
		File savedPhotoNames = new File(curDir, "PhotoNames");
		File savedLog = new File(curDir, "Log");
		File savedTagManager = new File(curDir, "TagManager");
		
		try{
			if (savedPhotoNames.canRead()){
				newPhotoNames.loadPhotoNames(savedPhotoNames.getPath());
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			if (savedLog.canRead()){
				newLog.loadLog(savedLog.getPath());
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			if (savedTagManager.canRead()){
				newTagManager.loadTagList(savedTagManager.getPath());
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the Directory File currently selected by the user. 
	 * 
	 * @return the selected directory file.
	 */
	public File getCurDirectory(){
		return curDirectory;
	}
	
	/**
	 * Returns the list of files in the currently selected directory.
	 * 
	 * @return the list of files in the selected directory.
	 */
	public ArrayList<File> getDirectoryFiles(){
		return directoryFiles;
	}
	
	/**
	 * Returns the photo file currently selected by the user.
	 * 
	 * @return the selected photo file. 
	 */
	public Photo getSelectedPhoto(){
		return selectedPhoto;
	}
	
	/**
	 * Selects and sets a valid file as the photo for the program to 
	 * rename or access information through.
	 * 
	 * @param photoFile
	 * 		a valid photo file for the program.
	 */

	public void setSelectedPhoto(File photoFile){
		Photo photo = new Photo(photoFile);
		selectedPhoto = photo;
	}
	
	/**
	 * Returns the instance of Log for this instance of Program.
	 * 
	 * @return the instance of Log for this instance of Program.
	 */
	public Log getLog(){
		return newLog;
	}
	
	/**
	 * Returns the instance of TagManager for this instance of Program.
	 * 
	 * @return the instance of TagManager of this instance of Program. 
	 */
	public TagManager getTagManager(){
		return newTagManager;
	}
	
	/**
	 * Finds all valid photo files under a specified directory and adds them 
	 * to ArrayList photos. The directory can potentially have nested subdirectories. 
	 * 
	 * Precondition: directoryFile represents a directory. 
	 * 
	 * @param directoryFile
	 * 		the file directory to search in.
	 *
	 * @see CheckExtension
	 */
	public void populatePhotos(File directoryFile){
		
		if (directoryFile.isDirectory()){
			for(File item: directoryFile.listFiles()){
				
				if(item.isDirectory() == false && CheckExtension(item) == true){
					directoryFiles.add(item);
				}
				else if(item.isDirectory() == false && CheckExtension(item) == false){
					;
				}
				else{
					populatePhotos(item);	
				}		
			}
		}
		else{
			;
		}
	}
	
	/**
	 * Selects a directory file for the program. Populates the ArrayList of 
	 * files in FileExplorer and populates the PhotoNames HashMap of files 
	 * and history of file names. 
	 * 
	 * Precondition: directoryFile represents a file directory.
	 * 
	 * @param directoryFile
	 * 		the directory file to be selected.
	 */

	public void selectDirectory(File directoryFile){
		curDirectory = directoryFile;
		directoryFiles = new ArrayList<File>();
		populatePhotos(directoryFile);
		newPhotoNames.populatePhotoNames(directoryFiles);
		}
	
	
	/**
	 * Reverts the name of the selected Photo to one of its past names. 
	 * 
	 * Precondition: oldName must be a past name of the photo. 
	 * 
	 * @param oldName
	 * 		the valid previous name of the photo to revert to. 
	 */
	public void revertPhotoName(String oldName){
		
		try{
		Event renameEvent = TagManager.revertName(selectedPhoto, oldName);
		newLog.addEvent(renameEvent);
		}
		catch(FileNotRenamedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds one or more Tags to the selected Photo.
	 * 
	 * @param tags
	 * 		the list of tags to be added to the photo. 
	 *
	 * @throws DuplicateTagsException if the photo already contains 
	 * 		one or more of the tags that are going to be added.
	 */
	public void addPhotoTags(ArrayList<String> tags) throws DuplicateTagsException{
		try {
			newPhotoNames.addNewPhotoName(selectedPhoto);
			Event newEvent = newTagManager.addPhotoTags(tags, selectedPhoto);
			newLog.addEvent(newEvent);
		} 
		catch (FileNotRenamedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns all the past names of the Photo selected by the program. 
	 * 
	 * @return
	 * 		an ArrayList of Strings representing the past names of the Photo.
	 */

	public ArrayList<String> showPhotoHistory(){
		return newPhotoNames.listNames(selectedPhoto);
	}

	/**
	 * Returns the parent directory of the currently selected Photo file.
	 * 
	 * @return
	 * 		the parent directory file of the current Photo file.
	 */
	public File openCurrentFileParent(){
		return selectedPhoto.getParentDirectory();
	}
	
	/**
	 * A method that must be run before this instance of the program is closed. 
	 * Serializes or reserializes the instances of PhotoNames, Log, and TagManager 
	 * so all names of the photos, the log of the program, and the tags used 
	 * by the program can be made available next time the program is run. 
	 * 
	 */
	public void close(){
		
		String curDir = System.getProperty("user.dir");
		
		try {
			File savePhotoNames = new File(curDir, "PhotoNames");
			newPhotoNames.serializePhotoNames(savePhotoNames.getPath());
			//newPhotoNames.serializePhotoNames("C:\\Users\\Julian2\\Documents\\PhotoNames");
			//newPhotoNames.serializePhotoNames("/Users/shawneerizqa/Desktop/CSC207/rizqatsa/PhotoNames.txt");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		try{
			File saveLog = new File(curDir, "Log");
			newLog.serializeLog(saveLog.getPath());	
			//newLog.serializeLog("C:\\Users\\Julian2\\Documents\\Log");	
			//newLog.serializeLog("/Users/shawneerizqa/Desktop/CSC207/rizqatsa/Log.txt");
		} 
		catch (IOException e){
			e.printStackTrace();
		}
		try{
			File saveTagManager = new File(curDir, "TagManager");
			newTagManager.serializeTagList(saveTagManager.getPath());
			//newTagManager.serializeTagList("C:\\Users\\Julian2\\Documents\\TagManager");
			//newTagManager.serializeTagList("/Users/shawneerizqa/Desktop/CSC207/rizqatsa/TagManager.txt");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Obtains the file extension of a given file and checks if 
	 * it is a common photo filetype. 
	 * Based on Pankaj's code for checking file extensions.: 
	 * http://www.journaldev.com/842/how-to-get-file-extension-in-java.  
	 * Returns true if the file contains is a common photo file,
	 * and false otherwise.
	 * 
	 * @param file
	 * 		the file to check the extension of.
	 * @return true if the given file has the extensions .jpg or .png,
	 * 		false otherwise.
	 */
	// http://www.journaldev.com/842/how-to-get-file-extension-in-java
	public static Boolean CheckExtension(File file){
		String name = file.getName();
		if(name.lastIndexOf(".") != (name.length() - 1) && name.lastIndexOf(".") != 0){
			String extension = name.substring(name.lastIndexOf(".") + 1);
			if(extension.equals("jpg") || extension.equals("png")){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}	
	}

}