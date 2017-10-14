package photo_renamer;

import java.io.File;

/** 
 * A representation of a photo file to be renamed. 
 * @author group_0653
 */
public class Photo {
	
	/**A given photo file that this instance of Photo will represent.*/
	private File photoFile;
	
	/** The current name of this Photo.*/
	private String curName;
	
	/** The original (untagged) name of the Photo.*/
	private String origName;
	
	/**
	 * A new Photo instance representing a photo file. 
	 * 
	 * @param photoFile
	 * 		a file for Photo to represent.
	 */
	public Photo(File photoFile){
		this.photoFile = photoFile;
		this.curName = photoFile.getName();
		this.origName = removeAllTags(photoFile);
	}
	
	/**
	 * Sets the file represented by this photo instance. Used for renaming purposes. 
	 * 
	 * @param photoFile
	 * 		the file to set Photo's photoFile to.
	 */
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	
	/** 
	 * Returns the current name of this photo.
	 * 
	 * @return current name of this photo.
	 */
	public String getCurName(){
		return curName;
	}
	
	/**
	 * Sets the current name of this photo to a new name. This is only 
	 * to be used by the Renamer class in the programand should not 
	 * be accessible by the user.
	 * 
	 * @param newName
	 * 		the new name to set Photo's curName to.
	 */
	void setCurName(String newName){
		curName = newName;
	}
	
	/**
	 * Returns the original (untagged) name of this photo.
	 * 
	 * @return the original (untagged) name of this photo.
	 */
	public String getOrigName(){
		return origName;
	}
	
	/** 
	 * Returns the parent directory for this photo. Allows the parent directory 
	 * for this photo to be opened in an OS. 
	 * 
	 * @return the parent directory file of this photo. 
	 */
	public File getParentDirectory(){
		return photoFile.getParentFile();
	}
	
	/**
	 * Returns the file represented by this photo.
	 * 
	 * @return the file being represented by this photo.
	 */
	public File getPhotoFile(){
		return photoFile;
	}

	/**
	 * Removes all tags from a photo and returns the original
	 * (untagged) name of the photo. 
	 * 
	 * @param photofile
	 * 		the photo file whose tags are going to be removed.
	 * 
	 * @return the untagged filename of a photo file.
	 */
	public static String removeAllTags(File photofile){
		String filename = photofile.getName();
		if (filename.indexOf("@") > 0){
			int lastIndex = filename.indexOf("@");
			String newName = filename.substring(0, lastIndex);
			newName += filename.substring(filename.length() - 4);
			return newName;
		}
		else{
			return filename;
		}
	}

}
