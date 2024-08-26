package eventplanner.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import eventplanner.core.Event;
import eventplanner.core.EventHandler;
import eventplanner.core.Event.Participant;

public class AppStorageTest {
    private static AppStorage appStorage;
    private Event event;
    private EventHandler eventHandler;

    @BeforeEach
    public void setup() {
        appStorage = new AppStorage();
        event = new Event("TestUser", "Fest", 10, "Gløshaugen", "10.10.2022");
        eventHandler = new EventHandler();
        eventHandler.addEvent(event);
        event.addParticipant("Johannes", false);
    }

    @Test
    public void testWriteApp() {
        try {
            appStorage.writeApp(eventHandler, "testFile");
        } catch (Exception e) {
            fail("Klarte ikke lese fra fil");
        }

        try {
            appStorage.writeApp(eventHandler, "testFile2");
        } catch (Exception e) {
            fail("Klarte ikke lese fra fil");
        }

        byte[] testWrite = null;
        byte[] testWrite2 = null;

        try {
            testWrite = Files.readAllBytes(appStorage.getPath("testFile"));
        } catch (IOException e) {
            fail("Kan ikke laste fil");
        }

        try {
            testWrite2 = Files.readAllBytes(appStorage.getPath("testFile2"));
        } catch (IOException e) {
            fail("Kan ikke laste fil");
        }
        assertNotNull(testWrite);
        assertTrue(Arrays.equals(testWrite, testWrite2));
    }

    @Test
    public void testReadApp() {
        try {
            eventHandler = appStorage.read("testFile");
            eventHandler = appStorage.read("testFile2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        assertEquals(eventHandler.toString(), eventHandler.toString());
    }

    private ArrayList<ArrayList<String>> toArrayList(EventHandler handler) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for (Event event : handler.getEventList()) {
            ArrayList<String> info = new ArrayList<>();
            info.add(event.getEventName());
            info.add(Integer.toString(event.getParticipants()));
            info.add(event.getLocation());
            info.add(event.getDate());
            result.add(info);
        }
        return result;
    }

    @Test
    public void testInfoDeserializer() {
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> result = toArrayList(eventHandler);

        ArrayList<ArrayList<String>> resultEqual = appStorage.infoDeserializer(gson.toJson(result));
        Objects.equals(result, resultEqual);
    }

    private HashMap<String, Boolean> participantHash(Event event) {
        HashMap<String, Boolean> result = new HashMap<>();
        ArrayList<Participant> participants = event.getParticipantList();
        for (Participant participant : participants) {
            result.put(participant.getName(), participant.getChecked());
        }
        return result;
    }

    @Test
    public void testParticipantHashDeserializer() {
        Gson gson = new Gson();

        HashMap<String, Boolean> result = participantHash(event);

        HashMap<String, Boolean> resultEqual = appStorage.participantHashDeserializer(gson.toJson(result));

        Objects.equals(result, resultEqual);
    }

    private String EventToString(Event event) {
        String stringEvent = appStorage.eventToString(event.getCreatorName(), event.getEventName(),
                event.getParticipants(), event.getLocation(), event.getDate());

        return stringEvent;
    }

    @Test
    public void testEventToString() {
        Gson gson = new Gson();

        String result = EventToString(event);
        Event checkEvent = gson.fromJson(result, Event.class);

        assertEquals(event.getCreatorName(), checkEvent.getCreatorName());
        assertEquals(event.getParticipants(), checkEvent.getParticipants());
        assertEquals(event.getEventName(), checkEvent.getEventName());
        assertEquals(event.getLocation(), checkEvent.getLocation());
        assertEquals(event.getDate(), checkEvent.getDate());
    }

    @AfterAll
    public static void tearDown() {
        appStorage.getPath("testFile").toFile().delete();
        appStorage.getPath("testFile2").toFile().delete();
    }
}
