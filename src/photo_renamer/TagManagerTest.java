package photo_renamer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagManagerTest {
	
	private TagManager tm1;
	private ArrayList<String> tags;
	private File file;
	private Photo photo;
	
	/**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Set these values in this method so that every test case can
        // begin with them in this state, even if previous test cases
        // have changed them.
        tm1 = new TagManager();
        tags = new ArrayList<String>();
        file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/image.jpg");
        photo = new Photo(file);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    
    /**
     * Test method for {@link photo_renamer.TagManager#getAvailableTags(java.util.ArrayList<String>)}.
     * Test case for an empty list of tags.
     */
    @Test
    public void testGetAvailableTagsEmpty() {
        ArrayList<String> actual = tm1.getAvailableTags();
        ArrayList<String> expected = tags;
    	assertEquals("getAvailableTagsFailed", expected, actual);	
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#getAvailableTags()}.
     * Test case for an empty list of tags.
     */
    @Test
    public void testGetAvailableTagsNonEmpty() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
			tm1.addAvailableTags(tags);
		} catch (DuplicateTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<String> actual = tm1.getAvailableTags();
        ArrayList<String> expected = tags;
    	assertEquals("getAvailableTagsFailed", expected, actual);	
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#toString()}.
     * Test case for an empty list of availableTags.
     */
    @Test
    public void testToStringEmpty() {
    	String actual = tm1.toString();
    	String expected = "";
    	assertEquals("toStringFailed", expected, actual);
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#toString()}.
     * Test case for a non-empty list of availableTags.
     */
    @Test
    public void testToStringNonEmpty() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
			tm1.addAvailableTags(tags);
		} catch (DuplicateTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = tm1.toString();
    	String expected = "@nameA" + "\n" + "@nameB" + "\n" + "@nameC" + "\n";
    	assertEquals("toStringFailed", expected, actual);
    }
    
    /**
     * Test method for DuplicateTagsException in
     * {@link photo_renamer.TagManager#addAvailableTags(java.util.ArrayList<String>)}.
     * Test case for when the list of tags already contains
     * some of the tags in availableTags.
     */
    @Test
    public void testDuplicateTagsExceptionAddTags() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	ArrayList<String> tags2 = new ArrayList<>();
    	tags2.add("nameA");
    	tags2.add("nameB");
    	try {
    		tm1.addAvailableTags(tags);
    		tm1.addAvailableTags(tags2);
    		fail("DuplicateTagsException not thrown: some tags are already in the list!");
    	} catch (DuplicateTagsException e) {
    		
    	}
    }
    
    
    /**
     * Test method for {@link photo_renamer.TagManager#addAvailableTags(java.util.ArrayList<String>)}
     * Test case for when the user tries to add an empty
     * list of tags to the list of availableTags.
     */
    @Test
    public void testAddAvailableTagsEmpty() {
    	try {
			tm1.addAvailableTags(tags);
		} catch (DuplicateTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ArrayList<String> actual = tm1.getAvailableTags();
    	ArrayList<String> expected = tags;
    	assertEquals("addAvailableTagsFailed", expected, actual);
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#addAvailableTags(java.util.ArrayList<String>)}
     * Test case for when multiple tags that aren't
     * already in availableTags are added to availableTags.
     */
    @Test
    public void testAddAvailableTagsMultipleTags() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
			tm1.addAvailableTags(tags);
		} catch (DuplicateTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ArrayList<String> actual = tm1.getAvailableTags();
    	ArrayList<String> expected = tags;
    	assertEquals("addAvailableTagsFailed", expected, actual);
    }
    
    /**
     * Test method for NonExistentTagsException in
     * {@link photo_renamer.TagManager#removeAvailableTags(java.util.ArrayList<String>)}.
     * Test case for when the list of availableTags doesn't contain
     * the tags in the list of tags that are going to be removed.
     */
    @Test
    public void testNonExistentTagsExceptionRemoveAvailableTags() {
    	tags.add("nameB");
    	tags.add("nameC");
    	ArrayList<String> tags2 = new ArrayList<>();
    	tags2.add("nameB");
    	tags2.add("nameA");
    	try {
    		try {
				tm1.addAvailableTags(tags);
			} catch (DuplicateTagsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		tm1.removeAvailableTags(tags2);
    		fail("NonExistentTagsException not thrown: some tags are NOT in the list!");
    	} catch (NonExistentTagsException e) {
    		
    	}
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#removeAvailableTags(java.util.ArrayList<String>)}
     * Test case for when the user tries to remove an
     * empty list of tags from availableTags.
     */
    @Test
    public void testRemoveAvailableTagsEmpty() {
    	try {
			tm1.removeAvailableTags(tags);
		} catch (NonExistentTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ArrayList<String> actual = tm1.getAvailableTags();
    	ArrayList<String> expected = tags;
    	assertEquals("removeAvailableTagsFailed", expected, actual);
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#removeAvailableTags(java.util.ArrayList<String>)}
     * Test case for when the user removes multiple
     * tags from the list of availableTags.
     */
    @Test
    public void testRemoveAvailableTagsNonEmpty() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	ArrayList<String> tags2 = new ArrayList<>();
    	tags2.add("nameA");
    	tags2.add("nameB");
    	try {
    		try {
				tm1.addAvailableTags(tags);
			} catch (DuplicateTagsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tm1.removeAvailableTags(tags2);
			tags.remove("nameA");
			tags.remove("nameB");
		} catch (NonExistentTagsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ArrayList<String> actual = tm1.getAvailableTags();
    	ArrayList<String> expected = tags;
    	assertEquals("removeAvailableTagsFailed", expected, actual);
    }
    
    /**
     * Test method for DuplicateTagsException in
     * {@link photo_renamer.TagManager#addPhotoTags(java.util.ArrayList<String>, Photo)}.
     * Test case for when the photo already contains some 
     * of the tags that are going to be added.
     * 
     * @throws FileNotRenamedException 
     * 		if renaming is unsuccessful.
     */
    @Test
    public void testDuplicateTagsExceptionAddPhotoTags() throws FileNotRenamedException {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/image@nameA@nameB.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	try {
    		tm1.addPhotoTags(tags, photo);
    		fail("DuplicateTagsException not thrown: some tags are already in the photo!");
    	} catch (DuplicateTagsException e) {
    		
    	}
    }
    
    /**
     * Test method for FileNotRenamedException in
     * {@link photo_renamer.TagManager#addPhotoTags(java.util.ArrayList<String>, Photo)}.
     * Test case for when renameTo in addPhotoTags returns false, which
     * indicates that the renaming is unsuccessful.
     * 
     * @throws DuplicateTagsException 
     * 		if a tag that is going to be removed is not in the photo.
     */
    @Test
    public void testFileNotRenamedExceptionAddPhotoTags() throws DuplicateTagsException {
    	// initiate a non-existent pathname. 
    	// addTags should then throw FileNotRenamedException.
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/random.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	try {
    		tm1.addPhotoTags(tags, photo);
    		fail("FileNotRenamedException not thrown: the pathname is not valid.");
    	} catch (FileNotRenamedException e) {
    		
    	}
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#addPhotoTags(java.util.ArrayList<String>, Photo)}
     * Test case for when the user tries to add an
     * empty list of tags to a photo.
     */
    @Test
    public void testAddPhotoTagsEmpty() {
    	try {
			tm1.addPhotoTags(tags, photo);
		} catch (DuplicateTagsException | FileNotRenamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = photo.getCurName();
    	String expected = "image.jpg";
    	assertEquals("addPhotoTagsFailed", expected, actual);
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#addPhotoTags(java.util.ArrayList<String>, Photo)}
     * Test case for when multiple tags that aren't
     * already in a photo are added to the photo.
     */
    @Test
    public void testAddPhotoTagsNonEmpty() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
			tm1.addPhotoTags(tags, photo);
		} catch (DuplicateTagsException | FileNotRenamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = photo.getCurName();
    	String expected = "image@nameA@nameB@nameC.jpg";
    	assertEquals("addPhotoTagsFailed", expected, actual);
    }
    
    /**
     * Test method for NonExistentTagsException in
     * {@link photo_renamer.TagManager#removeTags(java.util.ArrayList<String>, Photo)}.
     * Test case for when the tags that are going to be
     * removed are not in the photo.
     * 
     * @throws FileNotRenamedException
     */
    @Test
    public void testNonExistentTagsExceptionRemoveTags() throws FileNotRenamedException {
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
    		tm1.removeTags(tags, photo);
    		fail("NonExistentTagsException not thrown: some tags are NOT in the photo!");
    	} catch (NonExistentTagsException e) {
    		
    	}
    }
    
    /**
     * Test method for FileNotRenamedException in
     * {@link photo_renamer.TagManager#removeTags(java.util.ArrayList<String>, Photo)}.
     * Test case for when renameTo in removeTags returns false, which
     * indicates that the renaming is unsuccessful.
     * 
     * @throws NonExistentTagsException 
     * 		if a tag that is going to be removed is not in the photo.
     */
    @Test
    public void testFileNotRenamedExceptionRemoveTags() throws NonExistentTagsException {
    	// initiate a non-existent pathname. 
    	// addTags should then throw FileNotRenamedException.
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/random@nameA@nameB.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	try {
    		tm1.removeTags(tags, photo);
    		fail("FileNotRenamedException not thrown: the pathname is not valid.");
    	} catch (FileNotRenamedException e) {
    		
    	}
    }
 
    /**
     * Test method for {@link photo_renamer.TagManager#removeTags(java.util.ArrayList<String>, Photo)}
     * Test case for when the user tries to rename a photo with 
     * an empty list of tags.
     */
    @Test
    public void testRemoveTagsEmpty() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/image@nameA.jpg");
    	photo = new Photo(file);
    	try {
			tm1.removeTags(tags, photo);
		} catch (NonExistentTagsException | FileNotRenamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = photo.getCurName();
    	String expected = "image@nameA.jpg";
    	assertEquals("removeTagsFailed", expected, actual);
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#removeTags(java.util.ArrayList<String>, Photo)}
     * Test case for when multiple tags that are in the 
     * photo are removed the photo.
     */
    @Test
    public void testRemoveTagsMultipleTags() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/image@nameA@nameB@nameC.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	try {
			tm1.removeTags(tags, photo);
		} catch (NonExistentTagsException | FileNotRenamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = photo.getCurName();
    	String expected = "image.jpg";
    	assertEquals("removeTagsFailed", expected, actual);
    }
    
    /**
     * Test method for FileNotRenamedException in
     * {@link photo_renamer.TagManager#revertName(Photo, java.lang.String)}.
     * Test case for when renameTo in revertName returns false, which
     * indicates that the renaming is unsuccessful.
     */
    @Test
    public void testFileNotRenamedExceptionRevert() {
    	// initiate a non-existent pathname. 
    	// addTags should then throw FileNotRenamedException.
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/random@nameA@nameB.jpg");
    	photo = new Photo(file);
    	try {
    		tm1.revertName(photo, "random.jpg");
    		fail("FileNotRenamedException not thrown: the pathname is not valid.");
    	} catch (FileNotRenamedException e) {
    		
    	}
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#revertName(Photo, java.lang.String)}.
     * Test case for the user reverts a photo's name
     * to one of its previous names.
     */
    @Test
    public void testRevertName() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/image@nameA@nameB.jpg");
    	photo = new Photo(file);
    	try {
			tm1.revertName(photo, "image.jpg");
		} catch (FileNotRenamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String actual = photo.getCurName();
    	String expected = "image.jpg";
    	assertEquals("revertNameFailed", expected, actual);
    }
   
    /**
     * Test method for {@link photo_renamer.TagManager#getTagsInNameList(Photo)}.
     * Test case for when there  are no tags
     * in the photo.
     */
    @Test
    public void testGetTagsInNameEmpty() {
        ArrayList<String> actual = tm1.getTagsInNameList(photo);
        ArrayList<String> expected = tags;
    	assertEquals("getTagsInNameFailed", expected, actual);	
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#getTagsInNameList(Photo)}.
     * Test case for when there are multiple tags in the photo.
     */
    @Test
    public void testGetTagsInNameNonEmpty() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/photo@nameA@nameB@nameC.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
        ArrayList<String> actual = tm1.getTagsInNameList(photo);
        ArrayList<String> expected = tags;
    	assertEquals("getTagsInNameFailed", expected, actual);	
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when list of tags is empty and photo
     * does not contain any tags.
     */
    @Test
    public void testNameContainsTagNoTags() {
    	assertFalse(tm1.nameContainsTag(tags, photo));
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when list of tags is empty but photo
     * contains some tags.
     */
    @Test
    public void testNameContainsTagEmptyTagList() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/photo@nameA@nameB.jpg");
    	photo = new Photo(file);
    	assertFalse(tm1.nameContainsTag(tags, photo));
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when photo does not contain any tags but
     * list of tags is not empty.
     */
    @Test
    public void testNameContainsTagEmptyPhoto() {
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	assertFalse(tm1.nameContainsTag(tags, photo));
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when photo contains all tags in tags.
     */
    @Test
    public void testNameContainsTagAllEqual() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/photo@nameA@nameB@nameC.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameB");
    	tags.add("nameC");
    	assertTrue(tm1.nameContainsTag(tags, photo));
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when photo does not contain any of the tags in tags.
     */
    @Test
    public void testNameContainsTagAllDifferent() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/photo@nameA@nameB@nameC.jpg");
    	photo = new Photo(file);
    	tags.add("nameD");
    	tags.add("nameE");
    	tags.add("nameF");
    	assertFalse(tm1.nameContainsTag(tags, photo));
    }
    
    /**
     * Test method for {@link photo_renamer.TagManager#nameContainsTag(java.util.ArrayList<String>, Photo)}.
     * Test case for when photo contains only some of the tags in tags.
     */
    @Test
    public void testNameContainsTagSomeEqual() {
    	file = new File("/Users/shawneerizqa/Desktop/SHAWNEE/Unsorted/photo@nameA@nameB@nameC.jpg");
    	photo = new Photo(file);
    	tags.add("nameA");
    	tags.add("nameC");
    	tags.add("nameD");
    	assertTrue(tm1.nameContainsTag(tags, photo));
    }

	

}
