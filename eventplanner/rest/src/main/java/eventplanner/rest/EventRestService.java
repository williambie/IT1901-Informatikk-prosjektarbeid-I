package eventplanner.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import eventplanner.core.Event;
import eventplanner.core.Event.Participant;
import eventplanner.core.EventHandler;
import eventplanner.data.AppStorage;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Service;

/**
 * Service class for REST.
 */
@Service
public class EventRestService {
        
    private AppStorage apphandler = new AppStorage();

    /**
     * Logs in the user.
     *
     * @param username name of the user
     * @return eventList
     */
    public String logInUser(String username) {
        EventHandler handler = findEventHandler(username);
        if (handler != null) {
            return "200:OK";
        } 
        return "400: Person not found";
    }

    /**
     * Gets a user info ArrayList.
     *
     * @param username name of the user
     * @return result
     */
    public ArrayList<ArrayList<String>> getUserInfoArrayList(String username) {
        try {
            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
            EventHandler user = apphandler.read(username);
            for (Event event : user.getEventList()) {
                ArrayList<String> info = new ArrayList<>();
                info.add(event.getEventName()); 
                info.add(Integer.toString(event.getParticipants())); 
                info.add(event.getLocation()); 
                info.add(event.getDate());
                result.add(info);
            }   
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void write(EventHandler eventHandler) {
        String username = eventHandler.getUserName();
        if (username != null) {
            apphandler.writeApp(eventHandler, username);
        }
    }

    private EventHandler findEventHandler(String username) {
        EventHandler handler = apphandler.read(username);
        if (handler == null) {
            return null;
        }
        handler.setUserName(username);
        return handler;
    }

    private Event findEvent(EventHandler eventHandler, String eventName) {
        for (Event e : eventHandler.getEventList()) {
            if (e.getEventName().equals(eventName)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Add an event to an eventhandler.
     *
     * @return HTTP response
     */
    public String addEvent(String body) {
        Gson gson = new Gson();
        Event event = gson.fromJson(body, new TypeToken<Event>() {}.getType());
        try {
            Event validEvent = new Event(event.getCreatorName(), event.getEventName(), event.getParticipants(), event.getLocation(), event.getDate());
            EventHandler handler = findEventHandler(event.getCreatorName());
            handler.addEvent(validEvent);
            apphandler.writeApp(handler, event.getCreatorName());
            return "200 OK, added";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case "nameError":
                    return "400";
                case "participantsError":
                    return "401";
                case "locationError":
                    return "402";
                case "dateError":
                    return "403";
                default:
                    return "404";
            }
        }
    }

    /**
     * Deletes an event from the eventhandler.
     *
     * @param username name of the user
     * @param eventname name of the event
     */
    public String deleteEvent(String username, String eventname) {
        try {
            EventHandler handler = findEventHandler(username);
            Event event = findEvent(handler, eventname);

            if (event != null) {
                handler.deleteEvent(event);
                write(handler);
                return "200";
            }
            return "400";
        } catch (Exception e) {
            return "404";
        }
    }

    /**
     * Signs up the user.
     *
     * @param username name of the user
     * @return Http response
     */
    public String signUp(String username) {
        try {
            EventHandler hand = apphandler.read(username);
            if (hand == null) {
                EventHandler handler = new EventHandler();
                handler.setUserName(username);
                write(handler);
                return "200 OK, signed up";
            }
            return "400, user exists";
        } catch (Exception e) {
            return "404 unexpected error";
        }
    }

    /**
     * Adds a participant.
     *
     * @return Http response
     */
    public String addParticipant(String body) {
        String resp = "";
        try {
            Participant party = new Gson().fromJson(body, Participant.class);
            EventHandler handler = findEventHandler(party.getCreatorName());
            Event event = findEvent(handler, party.getEventName());
            event.addParticipant(party.getName(), false);
            write(handler);
            resp = "200";
        } catch (Exception e) {
            if (e.getClass().equals(IllegalStateException.class)) {
                resp = "401";
            }
            if (e.getClass().equals(IllegalArgumentException.class)) {
                resp = "402";
            } else {
                resp = "400";
            }
        }
        return resp;
    }

    /**
     * Deletes the participant.
     *
     * @param username name of the user
     * @param eventName name of the event
     * @param participantName name of the particpant
     * @return Http response
     */
    public String deleteParticipant(String username, String eventName, String participantName) {
        try {
            EventHandler handler = findEventHandler(username);
            Event event = findEvent(handler, eventName);
            for (Participant participant : event.getParticipantList()) {
                if (participant.getName().equals(participantName)) {
                    event.removeParticipant(participant);
                    write(handler);
                    return "200 OK, deleted";
                }
            }

            return "400 participant not found";
        } catch (Exception e) {
            System.out.println(e);
            return "404 unknown error";
        }
    }

    /**
     * Gets the participant list.
     *
     * @param username name of the user
     * @param eventName name of the event
     * @return participantlist
     */
    public HashMap<String, Boolean> getParticipList(String username, String eventName) {
        try {
            HashMap<String, Boolean> tmp = new HashMap<>();
            for (Event event : apphandler.read(username).getEventList()) {
                if (event.getEventName().equals(eventName)) {
                    for (Participant participant : event.getParticipantList()) {
                        tmp.put(participant.getName(), participant.getChecked());
                    }
                }
            }
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Updates the event info.
     *
     * @param eventname name of the event
     * @return Http response
     */
    public String updateEventInfo(String body, String eventname) {
        try {
            Gson gson = new Gson();
            Event event = gson.fromJson(body, Event.class);
            Event validEvent = new Event(event.getCreatorName(), event.getEventName(), event.getParticipants(), event.getLocation(), event.getDate());
            EventHandler handler = apphandler.read(event.getCreatorName());
            if (handler != null) {
                ArrayList<Event> newList = handler.getEventList();
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i).getEventName().equals(eventname)) {
                        newList.set(i, validEvent);
                    }
                }
                handler = new EventHandler(newList);
                handler.setUserName(event.getCreatorName());
                write(handler);
                return "200 OK, updated event";
            }
            return "405 cant find event";
        } catch (Exception e) {
            // System.out.println(e);
            // switch (e.getMessage()) {
            //     case "nameError":
            //         return "400";
            //     case "participantsError":
            //         return "401";
            //     case "locationError":
            //         return "402";
            //     case "dateError":
            //         return "403";
            //     default:
            //         return "404";
            // }
            return "500; Error";
        }
    }

    /**
     * Changes boolean attending status of a participant.
     *
     * @param username name of the user
     * @param eventname name of the event
     * @param participantname name of the participants
     * @return HTTP Response
     */
    public String checkOrUncheck(String body) {
        try {
            JsonObject jason = new Gson().fromJson(body, JsonObject.class);
            String username = jason.get("userName").getAsString();
            System.out.println(username);
            String eventname = jason.get("eventName").getAsString();
            String participantname = jason.get("participantName").getAsString();
            EventHandler handler = findEventHandler(username);
            boolean checked = false;
            if (handler != null) {
                ArrayList<Event> newList = handler.getEventList();
                for (Event e : newList) {
                    if (e.getEventName().equals(eventname)) {
                        checked = e.checkOrUncheck(participantname);
                    }
                }
                EventHandler newHandler = new EventHandler(newList);
                newHandler.setUserName(username);
                write(newHandler);
                return String.valueOf(checked);
            }
            return "400 cant find event";
        } catch (Exception e) {
            return "404 unexpected Error";
        }
    }
}
