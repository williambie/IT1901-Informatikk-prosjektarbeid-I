# Eventplanner

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221)

This application is an event planner. The application allows the user to create an account. Users can then create events they are planning to host. Each event has a name, a date, a location and a participating number. Additionally each event has a partipation list that tracks attendance. Events may be update after creation, as well as deleted.

This application is currently on its third release. Documentation on spesific releases can be found within the [documentation](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/docs). User stories for each release are also available within the docs folder.  

About the repository: You will find the project in /eventplanner where you will find the four modules, along with their documentation within their respective directories.

## Modules

- core
- data
- ui
- rest

## GitPod

The project is runnable in GitPod. To run in GitPod use the button at the top of this file.

## Authors

August Skarsfjord Nyheim - [augustsn (gitlab)](https://gitlab.stud.idi.ntnu.no/augustsn)  
Johannes  Landmark Meldal - [johanlm  (gitlab)](https://gitlab.stud.idi.ntnu.no/johanlm)  
Torunn Torunn Mikkelsen Bårdstu - [torunnmb  (gitlab)](https://gitlab.stud.idi.ntnu.no/torunnmb)  
William  Bie Andreassen - [williba  (gitlab)](https://gitlab.stud.idi.ntnu.no/williba)  

## Prerequisites

### Java

This project is made in Java and therefore [Java](https://www.java.com/en/) needs to be installed to run the application. The current project version uses Java 17.

### Maven

The application is built in [Maven](https://maven.apache.org/). The project is muti modular and the root-project´s pom.xml contains an overview of all the sub-modules and their configuration. Also located in the main pom.xml-file is an overview of dependencies and plugins for the different modules. Maven is required to run the project.

## Installation and launching

### Application launch

To run the application you first have to install it:  

Step 1. Open a terminal and navigate to the "eventplanner" directory. Run the following command:

        mvn clean install

Step 2. Navigate in the terminal to the "rest" directory and run the following command to initialize the server:

        mvn spring-boot:run

Step 3. Navigate to the "ui" directory in another terminal and run the following command:

        mvn javafx:run

### Tests

Test coverage for the modules core, ui and rest are available after initial installation using JaCoCo and Maven site. The reports are located in:

> eventplanner/{modulename}/target/site/jacoco/index.html

UI tests are disabled by default, as they require the REST Server to be running in order to work. In order to run UI tests and get access to JaCoCo test coverage site. Due to trouble implementing MockMvc, we found this as the best solution.

Step 1. toggle "skipUITests" in the parent-pom.xml to "false"

Step 2. Initiate the spring-boot-server by running the following command in the "rest" directory

        mvn spring-boot:run

Step 3. Run the following command in the "ui" directory

        mvn clean install  

Step 4 (Optional). Toggle "skipUITests" in the parent-pom.xml back to "true"

### Download the application

To download and run the application locally, use Jlink and Jpackage commands.
This creates the zip-file in target and prepares the files needed:

        mvn javafx:jlink -f ./eventplanner/ui/pom.xml

Then run Jpackage:

        mvn jpackage:jpackage -f ./eventplanner/ui/pom.xml

Depending on which operating system you are using, this will open an installer locally and you can use the app without any other program needed.
