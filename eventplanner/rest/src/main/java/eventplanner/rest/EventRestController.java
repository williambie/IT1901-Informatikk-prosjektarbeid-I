package eventplanner.rest;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for sending requests to the web.
 */
@RestController
public class EventRestController {

    @Autowired
    EventRestService restservice;

    @GetMapping(value = "/login/{username}")
    public String logIn(@PathVariable String username) {
        return restservice.logInUser(username);
    }

    @GetMapping(value = "/userinfo/{username}")
    public ArrayList<ArrayList<String>> getUserInfo(@PathVariable String username) {
        return restservice.getUserInfoArrayList(username);
    }


    @RequestMapping(value = "/addevent", method = RequestMethod.POST) 
    public String addEvent(@RequestBody String body) {
        return restservice.addEvent(body);
    }

    /**
     * Deletes events.
     *
     * @param username name of the user
     * @param eventname name of the event
     * @return HTTP Response
     */
    @RequestMapping(value = "/{username}/delete/{eventname}", method = RequestMethod.DELETE) 
    public String deleteEvent(@PathVariable String username, @PathVariable String eventname) {
        return restservice.deleteEvent(username, eventname);
    } 

    @RequestMapping(value = "/addparticipant", method = RequestMethod.POST)
    public String addParticipant(@RequestBody String body) {
        return restservice.addParticipant(body);
    }

    @RequestMapping(value = "/{username}/{eventname}/deleteparticipant/{name}", method 
        = RequestMethod.DELETE)
    public String deleteParticipant(@PathVariable String username, 
        @PathVariable String eventname, @PathVariable String name) {
        return restservice.deleteParticipant(username, eventname, name);
    }

    @RequestMapping(value = "/{username}/{eventname}/participantlist", method = RequestMethod.GET)
    public HashMap<String, Boolean> getParticipList(@PathVariable String username, 
        @PathVariable String eventname) {
        return restservice.getParticipList(username, eventname);
    }

    @RequestMapping(value = 
        "/updateevent/{eventname}", method = RequestMethod.POST) 
    public String updateEventInfo(@RequestBody String body, @PathVariable String eventname) {
        return restservice.updateEventInfo(body, eventname);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkParticipant(@RequestBody String body) {
        return restservice.checkOrUncheck(body);
    }

    @RequestMapping(value = "/signup/{username}", method = RequestMethod.POST)
    public String signUp(@PathVariable String username) {
        return restservice.signUp(username);
    }
}
