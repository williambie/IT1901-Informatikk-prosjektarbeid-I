# Release 3

## REST

For the rest API for our application, we have used springboot. The rest API works like a contract between the information provider and the information user—establishing the content required from the consumer (the call) and the content required by the producer (the response). The rest module is separated from the core of the application, and does not directly use any instances of the core logic. The EventRestService uses AppStorage (to serialize and deserialize) from the data module, which then again uses the core. The rest API uses POST, GET and DELETE to perform tasks given by the user.  

## Additional modules

Two new modules has been added to the project in this release. The data module now contains everything related to persistence and storage of the json files. The reasoning for the moving this out of the core module is to achieve better structure on the files and easier dependecies between modules. The other module that has been added is the rest module. This module contains the restserver and restapi. These changes also include the change from "fxui" to the module "ui"
The modules are now:

- core
- data
- rest
- ui

You can find an overview of the current architecture along with a package diagram in [eventplanner](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/eventplanner/)

## GUI Makeover

The user interface has gone through a major makeover. The old gray design has been replaced by vibrant blue colors. The different part of the user interface feels visually more connected and functionality has been improved. Additionally feedback has been significantly improved to make for a better user experience when using the application. Read more about the GUI in the [ui module](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/eventplanner/ui).

## New functionality

The application now allows the user to add participants to their events. These participants also has a boolean status "attending" or "not attending" which can be toggled by doubleclicking their names. This allows the user of the application full control of who's attending their events. Events can now also be re-opened and updated after creation to alter their details.

## Shippable product

The application is now shippable, meaning a user can download as zip and install locally without java or any developer enviroment. This is possible through the implementation of jlink, which produces a package with javafx, and made user friendly through the jpackage plugin. Find instructions on how to run in [root readme](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/eventplanner/)
