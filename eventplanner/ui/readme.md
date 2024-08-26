# eventplanner.ui

This module contains everything related to the user interface of the application. The project utilizes [JavaFX](https://openjfx.io/) and FXML to display the application.

The first interface that the user meets is a login screen. This screen dsplays the applications logo and name, as well as a field for inserting a username. After logging in or signing up the application then displays the main page. The main page consists of an event list as well as a form for addning new events. Additionally the main page contains a top bar with the application logo and name, as well as showing which user is logged in and a logout button. Clicking on events in the event list will display a popup which allows for updating events details, as well as a list with participants for the current event. The participants list also have functionality for adding participants, removing participants and changing their attendance status by doubleclicking the names. Overall the application has a comfortable blue color, complementet by simple black and white. The style is minimalistic and contains no advanced imagery.

## Classes

- **AppController** - Connects the user interface with the logic from the domain layer  
- **EventApp** - Application launcher
- **WebHandler** - Connects the UI with the REST-setup

## Dependencies

- **eventplanner.data**
- **JavaFX**
- **TestFX**
- **Jupiter**
- **Fontawesome5 icon pack**

## Plugins

- **JavaFX**
- **Jpackage**
- **Maven Compiler**
- **Maven Surefire**
- **Maven Site**
- **Maven Install**
- **Maven Deploy**
- **JaCoCo**
- **Checkstyle**
- **Spotbugs**

Read more about checkstyle and spotbugs where they are [configured](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/eventplanner/config).
