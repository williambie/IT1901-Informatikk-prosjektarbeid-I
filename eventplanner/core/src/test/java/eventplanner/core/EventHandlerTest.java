package eventplanner.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventHandlerTest {
    private EventHandler eventHandler;
    private Event event;
    private Event event1;
    private Event event2;

    @BeforeEach
    public void setup() {
        eventHandler = new EventHandler();
        eventHandler.setUserName("William");
        event = new Event("TestUser", "Fest", 10, "Gløshaugen", "10.10.2022");
        event1 = new Event("TestUser", "Skitur", 20, "Lian", "06.01.2023");
        event2 = new Event("TestUser", "Løpetur", 15, "Sentrum", "25.07.2025");
        eventHandler.addEvent(event);
        eventHandler.addEvent(event1);
    }

    @Test
    public void testGetEventList() {
        ArrayList<Event> testList = new ArrayList<>();
        testList.add(event);
        testList.add(event1);
        assertEquals(testList, eventHandler.getEventList());
    }

    @Test
    public void testAddEvent() {
        eventHandler.addEvent(event2);
        assertEquals(3, eventHandler.getEventCount());
    }

    @Test
    public void testGetEventCount() {
        assertEquals(2, eventHandler.getEventCount());
    }

    @Test
    public void testSetGetUsername() {
        assertEquals("William", eventHandler.getUserName());
        eventHandler.setUserName("Johannes");
        assertEquals("Johannes", eventHandler.getUserName());
    }

    @Test
    public void testDeleteEvent() {
        assertEquals(2, eventHandler.getEventCount());
        eventHandler.deleteEvent(event);
        assertEquals(1, eventHandler.getEventCount());

    }

    @Test
    public void testEventConstructor() {
        ArrayList<Event> list = new ArrayList<>();
        Event event = new Event("TestUser", "Test", 1, "Månen", "10.02.2023");
        list.add(event);
        EventHandler handler = new EventHandler(list);
        assertEquals(list, handler.getEventList());
    }
}
