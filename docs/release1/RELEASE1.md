# Release 1

## Event-planner

The application in this project is an event planning application. The user of the application will be able to create and host events with the application. The application runs locally on the users system. The user will be able to create an event with a name and a number of participants. The event details are written to an external txt-file that stores the data. This way the application will remember which events the user created the next time the application is opened.

## GUI

The application has a simple GUI created with JavaFX. The left side of the GUI consists of a simple list, containing all the stored events and their attached attributes. The right side consists of a simple form where the user can create an event. The form consists of two input fields, for name and number of participants, and a button to create the event.

## Architecture

The architecture of the application follows the model-view-controller prinsipal. In the class Event.java, we have the simple logic for checking if an event is valid. The EventHandler.java class, contains a hashmap that stores the name and number of participants of an event. The AppStorage.java class takes care of wrtiting/reading from file, for now, just a .txt file. The controller, links the logic in the previous mentioned classes, with the fxml.
