package eventplanner.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import eventplanner.data.AppStorage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for handling Web Requests.
 */
public class WebHandler {
    
    private AppStorage dataAccess = new AppStorage();
    private int port = 8080;

    /**
     * Web handler for logging in.
     *
     * @param username name of the user
     * @return HTTP response
     */
    public boolean logIn(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/login/" + username))
                .GET()
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().contains("200");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Gets the user info.
     */
    public ArrayList<ArrayList<String>> getUserInfo(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/userinfo/" + username))
                .GET()
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return dataAccess.infoDeserializer(response.body());
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Gets the participant list.
     */
    public HashMap<String, Boolean> getParticipantList(String username, String eventName) { 
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/" + username + "/" + eventName + "/participantlist"))
                .GET()
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            HashMap<String, Boolean> tmp = new HashMap<>();
            tmp = dataAccess.participantHashDeserializer((response.body()));
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds an event.
     */
    public String addEvent(String username, String eventname, 
                           int participants, String location, String date) {
        String resp = "";
        JsonObject json = new JsonObject();
        json.addProperty("date", date);
        json.addProperty("participantsNumber", participants);
        json.addProperty("creatorName", username);
        json.addProperty("eventName", eventname);
        json.addProperty("location", location);
        System.out.println(json.toString());
        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:" + port + "/addevent"))
            .POST(BodyPublishers.ofString(json.toString()))
            .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            resp = response.body();
        } catch (Exception e) {
            resp = "420";
        }
        System.out.println(resp);
        return resp;
    }

    /**
     * Deletes an event.
     */
    public String deleteEvent(String username, String eventname) { 
        String resp = "";
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:" + port + "/" + username + "/delete/" + eventname))
                .DELETE()
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            resp = response.body();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    /**
     * Adds a participant.
     */
    public String addParticipant(String username, String eventname, String participantname) { //todo
        try {
            JsonObject json = new JsonObject();
            json.addProperty("name", participantname);
            json.addProperty("checked", false);
            json.addProperty("creatorName", username);
            json.addProperty("eventName", eventname);
            HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:" + port + "/" + "addparticipant"))
                .POST(BodyPublishers.ofString(new Gson().toJson(json)))
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deletes a participant.
     */
    public String deleteParticipant(String username, String eventname, String participantname) { 
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:" + port + "/" + username + "/" + eventname + "/" + "deleteparticipant" + "/" + participantname))
                .DELETE()
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Updates the event info.
     */
    public String updateEventInfo(String username, String eventname, String neweventname, int participants, String location, String date) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("date", date);
            json.addProperty("participantsNumber", participants);
            json.addProperty("creatorName", username);
            json.addProperty("eventName", neweventname);
            json.addProperty("location", location);
            HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:" + port + "/updateevent/" + eventname))
                .POST(BodyPublishers.ofString(json.toString()))
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "420";
        }
    }

    /**
     * Changes attendance boolean on participant.
     */
    public String checkOrUncheck(String username, String eventname, String participantname) { //todo
        try {
            JsonObject json = new JsonObject();
            json.addProperty("participantName", participantname);
            json.addProperty("userName", username);
            json.addProperty("eventName", eventname);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/check"))
                .POST(BodyPublishers.ofString(json.toString()))
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Signs the user up.
     */
    public String signUp(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:" + port + "/signup/" + username))
                .POST(BodyPublishers.ofString(""))
                .build();
            final HttpResponse<String> response 
            = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "404";
        }
    }
}
