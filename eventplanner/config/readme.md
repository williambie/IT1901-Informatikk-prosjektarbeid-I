# Configuration files

The config files are used to filter certain methods or classes within checkstyle and spotbugs that should not be considered bugs.

## Checkstyle

In addition to Jacoco, we use the static analytics tools checkstyle and spotbugs. Checkstyle tells us whether the code follows a coding standard.

## Spotbugs

 Spotbugs quite literally spots bugs for us and visually gives us a breakdown of bugs, bug patterns and bad practice.
 The exclude file makes sure it does not compare any fields with itself.

 To run spotbugs use the following command:

    mvn spotbugs:gui
