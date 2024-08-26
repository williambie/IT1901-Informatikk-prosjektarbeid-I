package eventplanner.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventplanner.core.Event.Participant;

public class EventTest {

    private Event event;
    private Participant participant;

    @BeforeEach
    public void setup() {
        event = new Event("TestUser", "Test", 3, "Mars", "21.09.2023");
        participant = new Participant("William", false, "TestCreator", "Test");

    }

    @Test
    public void testGetName() {
        assertEquals("William", participant.getName());
    }

    @Test
    public void testSetGetChecked() {
        assertEquals(false, participant.getChecked());
        participant.setChecked(true);
        assertEquals(true, participant.getChecked());
    }

    @Test
    public void testAddParticipant() {
        assertEquals(0, event.getParticipantList().size());
        event.addParticipant("Johannes", false);
        assertEquals(1, event.getParticipantList().size());
    }

    @Test
    public void testRemoveParticipant() {
        String name = "Johannes";
        event.addParticipant(name, false);
        Participant p = event.getParticipantList().stream().filter(q -> q.getName().equals(name)).findFirst().get();
        event.removeParticipant(p);
    }

    @Test
    public void testSetEventName() {
        event.setEventName("Test");
        assertEquals("Test", event.getEventName());
    }

    @Test
    public void testSetEventNameFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            event.setEventName("");
        });
    }

    @Test
    public void testGetEventName() {
        assertEquals("Test", event.getEventName());
    }

    @Test
    public void testSetParticipants() {
        event.setParticipants(100);
        assertEquals(100, event.getParticipants());
    }

    @Test
    public void testGetParticipants() {
        assertEquals(3, event.getParticipants());
    }

    @Test
    public void testSetParticipantFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            event.setParticipants(0);
        });
    }

    @Test
    public void testSetLocation() {
        event.setLocation("Gløshaugen");
        assertEquals("Gløshaugen", event.getLocation());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Mars", event.getLocation());
    }

    @Test
    public void testSetLocationFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            event.setLocation("");
        });
    }

    @Test
    public void testSetGetDate() {
        event.setDate("10.10.2022");
        assertEquals("10.10.2022", event.getDate());
        event.setDate("09.01.2023");
        assertEquals("09.01.2023", event.getDate());
        event.setDate("29.02.2024");
        assertEquals("29.02.2024", event.getDate());
    }

    @Test
    public void testValidDateFail() {
        assertThrows(IllegalArgumentException.class, () -> event.setDate("40.02.2022"));
        event.setDate("10.10.2022");
        assertThrows(IllegalArgumentException.class, () -> event.setDate("29.40.2022"));
        event.setDate("10.10.2022");
        assertThrows(IllegalArgumentException.class, () -> event.setDate("29.02.2022"));
    }

    @Test
    public void testCheckOrUncheck() {
        String name = "Johannes";
        event.addParticipant(name, false);
        event.checkOrUncheck(name);
        boolean check = event.getParticipantList().stream().filter(p -> p.getName().equals(name)).findFirst().get()
                .getChecked();
        assertEquals(true, check);
    }

    @Test
    public void testCheckOrUncheckFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            event.addParticipant("Tor", null);
        });
    }

    @Test
    public void testAddParticipantsFail() {
        event.addParticipant("Batman", false);
        event.addParticipant("Superman", false);
        event.addParticipant("Catwoman", false);
        Assertions.assertThrows(IllegalStateException.class, () -> event.addParticipant("Wonderwoman", false),
                "Cannot add more participants than the maximum participantCount.");
    }

    @Test
    public void testRemoveParticipantsFail() {
        Participant fakeParty = new Participant("John Doe", false, event.getCreatorName(), event.getEventName());
        event.addParticipant("John Doe", false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> event.removeParticipant(fakeParty),
                "Cannot remove something that does not exist in the list.");
    }

    @Test
    public void testValidateParticipants() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> event.addParticipant("e", false),
                "Name needs to be longer than 1 ch.");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> event.addParticipant("eeeeeeeeeeeeeeeeeeeee", false), "name needs to be less than 21 ch.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> event.addParticipant("John", null),
                "Checked needs to be checked or unchecked.");
    }
}
