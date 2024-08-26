package eventplanner.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import eventplanner.core.Event;
import eventplanner.core.Event.Participant;
import eventplanner.data.AppStorage;

@SpringBootTest(classes = EventRestController.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {EventRestApplication.class, EventRestService.class, EventRestController.class})
public class RestTest {
    private static String userName1 = "test";
    private static AppStorage storageHandler = new AppStorage();
    
    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:8080" + "/signup/" + userName1))
                .POST(BodyPublishers.ofString(""))
                .build();
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @AfterAll
    public static void breakDown(){
        storageHandler.getPath(userName1).toFile().delete();
        System.out.println("FERDI");
    } 
    
    @Test
    public void basicTest() throws URISyntaxException, IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder().uri(
                new URI("http://localhost:8080" + "/login/" + userName1))
                .GET()
                .build();
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Test
    public void testEventTest() throws URISyntaxException, IOException, InterruptedException {
        String eventName1 = "Party";
        String eventName2 = "Christmaseve";
        String event1 = addEvent(userName1, eventName1, 15, "Partystreet", "20.11.2023");
        System.out.println(event1 + "EVENT1");
        String event2 = addEvent(userName1, eventName2, 6, "Christmashouse", "25.12.2022");
        System.out.println(event2 + "EVENT1");
        assertTrue(event1.contains("200"));
        assertTrue(event2.contains("200"));
        assertTrue(getUserInfo(userName1).size() == 2);
        System.out.println(getUserInfo(userName1));

        String delEvent1 = deleteEvent(userName1, eventName1);
        assertTrue(delEvent1.contains("200"));
        assertTrue(getUserInfo(userName1).size() == 1);

        String update2 = updateEventInfo(userName1, eventName1 , "Easter", 12, "Bunnyhole", "01.04.2023");
        assertTrue(update2.contains("200"));
    }

    @Test
    public void participantTest() throws URISyntaxException, IOException, InterruptedException {
        String n = "Tommy";
        String eventName3 = "Openhouse";
        addEvent(userName1, eventName3, 15, "My house", "20.11.2023");
        String addParty = addParticipant(userName1, eventName3, "Tommy");
        assertTrue(addParty.contains("200"));
        assertTrue(getParticipantList(userName1, eventName3).size() == 1);
        System.out.println();

        assertFalse(getParticipantList(userName1, eventName3).get(n));

        String check3 = checkOrUncheck(userName1, eventName3, n);
        System.out.println(check3);

        assertTrue(deleteParticipant(userName1, eventName3, n).contains("200"));
        assertTrue(getParticipantList(userName1, eventName3).size() == 0);

    }


    private static String addEvent(String username, String eventname, int participants, String location, String date) throws URISyntaxException, IOException, InterruptedException {
        JsonObject json = new JsonObject();

        json.addProperty("date", date);
        json.addProperty("participantsNumber", participants);
        json.addProperty("creatorName", username);
        json.addProperty("eventName", eventname);
        json.addProperty("location", location);

        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://localhost:8080" + "/addevent"))
        .POST(BodyPublishers.ofString(json.toString()))
        .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
        
    }

    private static String deleteEvent(String username, String eventname) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(
            new URI("http://localhost:8080/" + username + "/delete/" + eventname))
            .DELETE()
            .build();
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static ArrayList<ArrayList<String>> getUserInfo(String username) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://localhost:8080" + "/userinfo/" + username))
        .GET()
        .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return storageHandler.infoDeserializer(response.body());
    }

    private static HashMap<String, Boolean> getParticipantList(String username, String eventname) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080/" + username + "/" + eventname + "/participantlist"))
            .GET()
            .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String, Boolean> tmp = new HashMap<>();
        tmp = storageHandler.participantHashDeserializer((response.body()));
        return tmp;
    }

    private static String addParticipant(String username, String eventname, String participantname) throws URISyntaxException, IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("name", participantname);
        json.addProperty("checked", false);
        json.addProperty("creatorName", username);
        json.addProperty("eventName", eventname);
        HttpRequest request = HttpRequest.newBuilder().uri(
            new URI("http://localhost:8080" + "/" + "addparticipant"))
            .POST(BodyPublishers.ofString(new Gson().toJson(json)))
            .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String deleteParticipant(String username, String eventname, String participantname) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(
            new URI("http://localhost:8080/" + username + "/" + eventname + "/" + "deleteparticipant" + "/" + participantname))
            .DELETE()
            .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String updateEventInfo(String username, String eventname, String neweventname, int participants, String location, String date) throws URISyntaxException, IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("date", date);
        json.addProperty("participantsNumber", participants);
        json.addProperty("creatorName", username);
        json.addProperty("eventName", neweventname);
        json.addProperty("location", location);
        HttpRequest request = HttpRequest.newBuilder().uri(
            new URI("http://localhost:8080" + "/updateevent/" + eventname))
            .POST(BodyPublishers.ofString(json.toString()))
            .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    
    }

    private static String checkOrUncheck(String username, String eventname, String participantname) throws URISyntaxException, IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("participantName", participantname);
        json.addProperty("userName", username);
        json.addProperty("eventName", eventname);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + "/check"))
            .POST(BodyPublishers.ofString(json.toString()))
            .build();
        final HttpResponse<String> response 
        = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
        
    }
}
