# Release 2

## Event-planner

The application in this project is an event planning application. The user of the application will be able to create and host events with the application. The application runs locally on the users system. The user will be able to create an event with a name, location and a number of participants. The event details are written to an external txt-file that stores the data. This way the application will remember which events the user created the next time the application is opened.

## GUI

The application has a simple GUI created with JavaFX. The left side of the GUI consists of a simple list, containing all the stored events and their attached attributes. The right side consists of a simple form where the user can create an event. The form consists of multiple input fields, for event name, number of participants, location, date and a button to create the event.

## Changes to Project Architecture

The application has been divided into two modules. This is to give the architecture a better overview and make it easier to see what each files does. One of the modules is called “core” and contains everything related to persistence and domain logic. The other module is called “fxui” and contains everything related to the user interface of the application.

## Gson

Gson is the serialization/deserialization library that we use when writing and reading to and from a json file. This library provides functions that makes it easy for us to convert an hashmap to an readable json file  by using toJson(), and easy to convert the text from this file back into an hashmap when loading data.

## JavaDocs

Comments in the code is made using JavaDocs. This extention makes for a consistent and clean comment structure throughout the code.

## Code quality

Our code quality has been improved through pair programming by using the tools jacoco, spotbugs and checkstyle. This was implemented in this release and will be used throughout the projects coming releases. More about these can be read in the main readme.
