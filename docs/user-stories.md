# User Stories

This file contains user stories that describes the application's uses. The purpose of this file is to provide an overview of the some of the requirements the application is supposed to fulfill. As the project develops, new user stories will be added to provide background on future functionality.  

## User Story (1 - Release 1)

"As an event host, I wish to be able to create an event and access an overview of all the events that I have created. This way I will have an overview over upcoming events and their respective sizes."

### Solution

The event host requires that events can be made well in advance to plan and that an overview is easily available. The overview should show all upcoming events. It will also be pratical for the user to provide the estimated number of participants on the events to get a sense of the events' size.

### Important to be able to see

- At application launch: an overview over upcoming events
- When new events are added: updated overview.

### Important to be able to do

- Add new events
- Provide names for new events
- Provide number of participants for new events

## User story (2 - Release 2)

"As an event host, I wish to be able to add more attributes to the events, in order to separate similar ones."

### Solution

Implementation of location and date solves this and provides the user with a comprihensive overview.

### Important to be able to see

- At application launch: an overview over upcoming events
- Have the overview include number of participants, location and date
- When new events are added: updated overview.

### Important to be able to do

- Add date of event
- Add location of the event

## User story (3 - Release 3)

"I want to be able to recall events i have previously made when i close and reopen the application.

### Solution

Implementation of a login/logout system solves this problem. This allows the application to store events tied to usernames and makes it possible to remember application states on exit.

### Important to be able to see

- A login in window at application launch
- Username of currently logged in user
- A logout button

### Important to be able to do

- Create new user
- Login
- Logout

## User story (4 - Release 3)

"I want to create an attendance list for my events. In these lists i want to be able to toggle attendance on each participant to keep track of who showed up at my event."

### Solution

Implementation of an attendance system tied to each event. This system is accessed by clicking into events in the UI.

### Important to be able to see

- A list of all participating members in each event.
- Attendance status of each member.

### Important to be able to do

- Add and remove members from events
- Change the attendance status of each member.

## User story (5 - Release 3)

"I want to be able to update existing events. This includes changing the event name, date, location or participant number."

### Solution

Implementation of a system that allows existing events to be updated. This system is accessed by clicking into events in the UI.

## Important to be able to see

- Current event attributes
- User input fields to update/change event attributes

## Important to be able to do

- Change event name
- Change event location
- Change event date
- Change event partipation  
