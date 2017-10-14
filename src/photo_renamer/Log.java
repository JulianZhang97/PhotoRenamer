package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A log of events.
 * This class is involved in the Iterator Design Pattern.
 * It acts as a container for Event objects.
 * @author group_0653
 *
 */
public class Log implements Iterable<Event> {
	
	/** A list of events that has occurred in this application. */
	private ArrayList<Event> eventList;
	
	/**
	 * A new instance of Log with an empty list of events.
	 */
	public Log(){
		this.eventList = new ArrayList<Event>();
	}
	
	/**
	 * Returns the list of events representing the renaming occurrences in this application.
	 *  
	 * @return the ArrayList of Event objects representing all renaming events. 
	 */
	public ArrayList<Event> getEventList() {
		return eventList;
	}

	/**
	 * Adds an Event to this Log's list of events.
	 * 
	 * @param event
	 * 		an Event representing a renaming occurrence.  
	 */
	public void addEvent(Event event) {
		eventList.add(event);
	}
	
	/**
	 * Returns all events recorded by this Log in a user-friendly format.
	 * 
	 * @return the string representation of all events in this Log. 
	 */
	public String displayLog() {
		String log = "";
		for (Event event: eventList) {
			log += "Original name: " + event.getOldName() + "\n" + "New name: " + event.getNewName() + "\n" + "Changes made at: " + event.getTimestamp() + "\n" + "\n";
		}
		return log;	
	}
	
	/**
	 * Sets the list of events of the log to events. This is only to be used to restore 
	 * a serialized log and should not be accessible outside the class. 
	 * 
	 * @param events
	 * 		the ArrayList of Event objects to set Log's eventList to. 
	 */
	private void setEventList(ArrayList<Event> events){
		this.eventList = events;
	}
	
	/**
     * Returns an iterator for this Log.
     * 
     * @return an iterator for this Log.
     */
	@Override
    public Iterator<Event> iterator() {
        return new LogIterator();
    }

    /**
     * An Iterator class for Log Events.
     * This class is also a part of the Iterator Design Pattern.
     * It iterates over the Event objects in Log.
     */
    private class LogIterator implements Iterator<Event> {

        /** The index of the next Event to return. */
        private int current = 0;

        /**
         * Returns whether there is another Event to return.
         * 
         * @return true if there is another Event to return, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return current < eventList.size();
        }

        /**
         * Returns the next Event.
         * 
         * @return the next Event object.
         */
        @Override
        public Event next() {
            Event event;

            // List.get(i) throws an IndexOutBoundsException if
            // we call it with i >= eventList.size().
            // But Iterator's next() needs to throw a 
            // NoSuchElementException if there are no more elements.
            try {
                event = eventList.get(current);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return event;
        }

        /**
         * Removes the Event just returned. Unsupported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
	
	/**
	 * Serializes the ArrayList of Events of this Log to a file 
	 * for access by the program if it is closed and reopened.
	 * 
	 * @param fileName
	 * 		the file to serialize the ArrayList of Event objects eventList in Log to. 
	 *
	 * @throws IOException if the file trying to serialize to is 
	 * 		disturbed during the write process. 
	 * 
	 */
	public void serializeLog(String fileName)throws IOException {
		FileOutputStream file = new FileOutputStream(fileName);
		BufferedOutputStream buffer = new BufferedOutputStream(file);
		ObjectOutputStream output = new ObjectOutputStream(buffer);
		output.writeObject(eventList);
		output.close();
		}
	
	/**
	 * Deserializes a serialized ArrayList of Events and sets the eventList 
	 * of this Log to this ArrayList of Events. 
	 * Precondition: The file fileName represents a serialized Object 
	 * of type ArrayList of Event objects. 
	 * 
	 * @param fileName
	 * 		the file to deserialize from.
	 * 
	 * @throws IOException if the file to be deserialized from is disturbed during the read process. 
	 * 
	 * @throws ClassNotFoundException if the Log and Event classes are not on the classpath. 
	 */
	@SuppressWarnings("unchecked")
	public void loadLog(String fileName) throws IOException, ClassNotFoundException{
		FileInputStream file = new FileInputStream(fileName);
		BufferedInputStream buffer = new BufferedInputStream(file);
		ObjectInputStream input = new ObjectInputStream(buffer);
		Object events = input.readObject();
		input.close();
		setEventList((ArrayList<Event>) events);
	}

}