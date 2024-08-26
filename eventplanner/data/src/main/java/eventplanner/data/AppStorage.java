package eventplanner.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eventplanner.core.Event;
import eventplanner.core.EventHandler;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to store and retrieve data.
 */
public class AppStorage {

    /**
     * Location to store the application data.
     */
    private String storeDirectory = System.getProperty("user.dir").replace("rest", "data")
            + "/src/main/java/eventplanner/json/";

    /**
     * Directory for ui.
     */
    private String storeDirectory2 = System.getProperty("user.dir").replace("ui", "data")
            + "/src/main/java/eventplanner/json/";

    /**
     * Function that writes to a .json file using gson.
     *
     * @param eventHandler List of the events
     * @param user         username
     * @throws exeption in case of error
     */
    public void writeApp(EventHandler eventHandler, String user) {
        try (Writer writer = new FileWriter(getUserOrientedDirectory(user))) {
            Gson gson = new Gson();
            writer.write(gson.toJson(eventHandler));
            writer.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * method that deserializes string from a json string.
     *
     * @param jsonString string in json format
     */
    public ArrayList<ArrayList<String>> infoDeserializer(String jsonString) {
        Gson gson = new Gson();
        try {
            ArrayList<ArrayList<String>> infoList = gson.fromJson(jsonString,
                    new TypeToken<ArrayList<ArrayList<String>>>() {
                    }.getType());
            return infoList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * method that deserializes string from a json string.
     *
     * @param jsonString string in json format
     */
    public HashMap<String, Boolean> participantHashDeserializer(String jsonString) {
        Gson gson = new Gson();
        try {
            HashMap<String, Boolean> list = gson.fromJson(jsonString,
                    new TypeToken<HashMap<String, Boolean>>() {
                    }.getType());
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Reads file and converts to an eventhandler. 
     *
     * @param filename name of the file to read
     * @return handler
     */
    public EventHandler read(String filename) {
        try (Reader reader = new FileReader(getUserOrientedDirectory(filename))) {
            Gson gson = new Gson();
            EventHandler handler = gson.fromJson(reader, EventHandler.class);
            if (handler.getUserName() == null) {
                handler.setUserName(filename);
            }
            return handler;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * Gets the user directory.
     *
     * @param userName name of the user
     * @return the path to the user's json file
     */
    public String getUserOrientedDirectory(String userName) {
        return this.storeDirectory + userName + ".json";
    }

    /**
     * Converts an event to string.
     *
     * @param username name of the user
     * @param eventname name of the event
     * @param participants number of participants
     * @param location location of the event
     * @param date date of the event
     * @return handler in json format
     */
    public String eventToString(String username, String eventname, 
                               int participants, String location, String date) {
        Event temp = new Event(username, eventname, participants, location, date);
        Gson handler = new Gson();
        return handler.toJson(temp);
    }

    /**
     * gets the path for a file.
     *
     * @param filename name of the file
     * @return Path of filename
     */
    public Path getPath(String filename) {
        return Path.of(this.storeDirectory + filename + ".json");
    }

    /**
     * Gets the path of storage directory2.
     *
     * @param filename name of the file
     * @return Path of filename
     */
    public Path getPath2(String filename) {
        return Path.of(this.storeDirectory2 + filename + ".json");
    }
}