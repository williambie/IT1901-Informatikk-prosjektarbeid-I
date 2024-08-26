package eventplanner.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * ({@summary} Class containing an event.)
 *
 * @param eventName              name of the event
 * @param participantsNumber     max number of participants
 * @param location               location of the event
 * @param date                   date of the event
 * @param ArrayList<Participant> list containing event participants
 */
public class Event {
    private String creatorName;
    private String eventName;
    private int participantsNumber;
    private String date;
    private String location;
    private ArrayList<Participant> participantsList = new ArrayList<>();

    /**
     * ({@summary} Sets the parameters of an event.)
     *
     * @param eventName          name of the event
     * @param participantsNumber max number of participants
     * @param location           location of the event
     * @param date               date of the event
     */
    public Event(String username, String eventName, int participantsNumber, String location, String date) {
        setCreatorName(username);
        setEventName(eventName);
        setParticipants(participantsNumber);
        setLocation(location);
        setDate(date);
    }

    /**
     * Nested Object Participant that includes two fields; string name and boolean.
     * checked/unchecked
     */
    public static class Participant {
        String name;
        Boolean checked;
        String creatorName;
        String eventName;

        /**
         * constructor that creates an new Participant instance.
         *
         * @param name  name of the participant
         * @param check boolean attending status of participant
         */
        public Participant(String name, Boolean check, String creatorName, String eventName) {
            validateParticipants(name, check);
            this.name = name;
            this.checked = check;
            this.creatorName = creatorName;
            this.eventName = eventName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        /**
         * validates the constructor.
         *
         * @param name  of the participant
         * @param check boolean attending status of participant
         */
        private void validateParticipants(String name, Boolean check) {
            if (name.length() < 2 || name.length() > 20) {
                throw new IllegalArgumentException("The name has to be between 1-21 characters.");
            }
            if (check == null) {
                throw new IllegalArgumentException("Participant needs to be checked or unchecked.");
            }
        }

        /**
         * ({@summary} returns the participant name.)
         *
         * @return Participant.name
         */
        public String getName() {
            return name;
        }

        public String getCreatorName() {
            return creatorName;
        }

        /**
         * ({@summary} returns the boolean attending status of the participant.)
         *
         * @return Participant.checked
         */
        public boolean getChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    /**
     * Function that adds a new Participant to participantList.
     *
     * @param name  name of the participant
     * @param check boolean attending status of the participant
     */
    public void addParticipant(String name, Boolean check) {

        if (participantsList.size() >= participantsNumber) {
            throw new IllegalStateException("This event is full.");
        }
        if (validParticipant(name)) {
            Participant tmp = new Participant(name, check, this.creatorName, this.eventName);
            participantsList.add(tmp);
        } else {
            throw new IllegalArgumentException("Invalid name format.");
        }
    }

    private boolean validParticipant(String name) {
        // if (name != null && name.matches("^[a-zA-Z]*{1,25}$")) {
        if (name != "" && name.matches("^[a-zA-Z](\s?[a-zA-Z]){2,25}$")) {
            return true;
        }
        return false;
    }

    /**
     * ({@summary} returns a list of participants.)
     *
     * @return participantsList
     */
    public ArrayList<Participant> getParticipantList() {
        return this.participantsList;
    }

    /**
     * Removes participant from participantsList.
     *
     * @param name name of the participant
     */
    public void removeParticipant(Participant name) {
        boolean found = false;
        for (Participant participant : getParticipantList()) {
            if (participant.equals(name)) {
                getParticipantList().remove(participant);
                found = true;
                break;
            }
        }
        if (found == false) {
            throw new IllegalArgumentException(name + " is not in the list.");
        }
    }

    /**
     * ({@summary} validates the name of the event.)
     *
     * @param eventName name of the event
     * @return true or falses
     */
    private boolean validEventName(String eventName) {
        if (eventName == "") {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ({@summary} sets the event name.)
     *
     * @param eventName name of the event
     */
    public void setEventName(String eventName) {
        if (validEventName(eventName) == true) {
            this.eventName = eventName;
        } else {
            System.out.println("namerror");
            throw new IllegalArgumentException("nameError");
        }
    }

    /**
     * ({@summary} gets the name of the event.)
     *
     * @return the event name
     */
    public String getEventName() {
        return this.eventName;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String name) {
        this.creatorName = name;
    }

    /**
     * ({@summary} valiadates the number of participants.)
     *
     * @param participants number of participants
     * @return true or false
     */
    private boolean validParticipants(int participants) {
        if (participants <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ({@summary} Sets the number of participants.)
     *
     * @param participants number of participants
     */
    public void setParticipants(int participants) {
        if (validParticipants(participants)) {
            this.participantsNumber = participants;
        } else {
            throw new IllegalArgumentException("participantsError");
        }
    }

    /**
     * ({@summary} Gets the participant numbers.)
     *
     * @return participantNumber
     */
    public int getParticipants() {
        return participantsNumber;
    }

    /**
     * ({@summary} Validates the date.)
     *
     * @param date date of the event
     * @return true or false
     */
    private boolean validDate(String date) {
        String[] dateArray = date.split("\\.");
        int year = Integer.parseInt(dateArray[2]);
        if (year > 3000 || year < 1) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.setLenient(false);
        try {
            formatter.parse(date);
            return true;

        } catch (ParseException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * ({@summary} Sets the event date.)
     *
     * @param date date of the event
     */
    public void setDate(String date) {
        if (validDate(date)) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("dateError");
        }
    }

    /**
     * ({@summary} Gets the event date.)
     *
     * @return the date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * ({@summary} Valiadates the event location.)
     *
     * @param location location of the event
     * @return true or false
     */
    private boolean validLocation(String location) {
        if (location.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ({@summary} Sets the location of the event.)
     *
     * @param location location of the event
     */
    public void setLocation(String location) {
        if (validLocation(location)) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("locationError");
        }
    }

    /**
     * ({@summary} Gets the location of the event.)
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * ({@summary} Changes the boolean attending status of the participants.)
     *
     * @param participantName name of the participant
     * @return true or false
     */
    public boolean checkOrUncheck(String participantName) {
        for (Participant p : participantsList) {
            if (p.getName().equals(participantName)) {
                p.setChecked(!p.getChecked());
                return !p.getChecked();
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Event [eventName= " + eventName + ", participantsNumber= " + participantsNumber
                + ", eventLocation= " + location + ", eventDate= " + date + "]";
    }
}