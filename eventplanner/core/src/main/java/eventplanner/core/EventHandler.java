package eventplanner.core;

import java.util.ArrayList;

/**
 * Class that contains events.
 *
 * @param ArrayList list containing all events
 * @param userName name of the user hosting the events
 */
public class EventHandler {

    private ArrayList<Event> eventList = new ArrayList<>();
    private String userName;

    public EventHandler() {
    }

    /**
     * ({@summary} Puts a list into the eventhandler.)
     *
     * @param eventList list of events
     */
    public EventHandler(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * ({@summary} returns the eventList.)
     *
     * @return the eventList
     */
    public ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * ({@summary} Adds an event to an eventList.)
     *
     * @param event an event object
     */
    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    /**
     * ({@summary} returns the size of the eventList.)
     *
     * @return the event count
     */
    public int getEventCount() {
        return eventList.size();
    }

    /**
     * ({@summary} Sets the username for the eventhandler.)
     *
     * @param userName Username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ({@summary} returns the username.)
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    public void deleteEvent(Event event) {
        this.eventList.remove(event);
    }

    @Override
    public String toString() {
        return "EventHandler [eventListCount=" + getEventCount() + ", userName=" + userName + "]";
    }
}
