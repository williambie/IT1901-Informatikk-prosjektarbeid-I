package eventplanner.ui;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Class for the AppController.
 */
public class AppController {

    private String userName;
    private WebHandler webHandler = new WebHandler();
    private String currEvent;
    private int validNum;

    @FXML
    public ListView<String> eventList;

    @FXML
    public ListView<String> participantsView;

    @FXML
    public TextField eventNameInput;
    public TextField eventParticipantsInput;
    public TextField eventLocationInput;
    public TextField eventDateInput;
    public TextField eventInfoDate;
    public TextField participantName;
    public TextField eventMaxParticipantInfo;
    public TextField eventInfoLocation;
    public TextField eventInfoName;

    @FXML
    public Button createEventButton;
    public Button deleteEventButton;
    public Button logOut; 
    public Button logInButton;
    public Button addParticipant;
    public Button removeParticipant;
    public Button updateEventInfo;
    public Button closeParticipantPane;
    public Button signUpButton;

    @FXML
    public Pane loginScreen;

    @FXML
    public AnchorPane participantPane;

    @FXML
    public TextField attendedBox;
    public TextField insertUsername;

    @FXML
    public Text feedbackField;

    @FXML
    public TextFlow signInField;

    public void initialize() {
        deleteEventButton.setOpacity(0);
        participantPane.setOpacity(0);
    }

    /**
     * Reads the eventinfo.
     */
    public void read() {
        try { 
            for (ArrayList<String> eventInfo : webHandler.getUserInfo(this.userName)) {
                String event = 
                    "Name: " + eventInfo.get(0) 
                    + ", Participants: " + eventInfo.get(1) 
                    + ", Location: " + eventInfo.get(2) 
                    + ", Date: " + eventInfo.get(3);
                eventList.getItems().add(event);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * method that adds input to the list in the UI + saves the event in Grandstorage.
     */
    public void addToList() {
        this.validNum = 0;
        String name = null;
        String participantString = null;
        int participantInt = 0;
        String location = null;
        String date = null;
        try {
            name = eventNameInput.getText();

            participantString = eventParticipantsInput.getText();
            participantInt = Integer.valueOf(participantString);

            location = eventLocationInput.getText();

            date = eventDateInput.getText();
        
        } catch (Exception e) {
            System.out.println("feil innputt på herren");
            eventInputvalidator();
            this.validNum = 6;
            exceptionColor();
            return;
        }
        if (eventDateInput.getText() == "" || eventLocationInput.getText() 
                                     == "" || eventNameInput.getText() == "") {
            eventInputvalidator();
            this.validNum = 6;
            exceptionColor();
            return;
        }
        String response = webHandler.addEvent(this.userName, name, participantInt, location, date);
        if (response.equals("400")) {
            this.validNum = 1;
        }
        if (response.equals("401")) {
            this.validNum = 2;
        }
        if (response.equals("402")) {
            this.validNum = 3;
        }
        if (response.equals("403")) {
            this.validNum = 4;
        }
        if (response.equals("404")) {
            this.validNum = 5;
        }
        if (this.validNum == 0) {
            String eventString = "Name: " + name + ", Participants: " 
                                  + participantString + ", Location: " + location
                + ", Date: " + date;
            eventList.getItems().add(eventString);
            resetEventInput();
            setEventPageDefault();
            return;
        }
        eventInputvalidator();
    }

    
    private void exceptionColor() {
        boolean isInt = true;
        try {
            Integer.parseInt(eventParticipantsInput.getText());
        } catch (Exception e) {
            isInt = false;
        }
        if (eventNameInput.getText() == "") {
            eventNameInput.setStyle(getInputColor(6));
        }
        if (eventParticipantsInput.getText() == "" || !isInt) {
            eventParticipantsInput.setStyle(getInputColor(6));
        }
        if (eventLocationInput.getText() == "") {
            eventLocationInput.setStyle(getInputColor(6));
        }
        if (eventDateInput.getText() == "") {
            eventDateInput.setStyle(getInputColor(6));
        }
    }

    private void exceptionInfoColor() {
        boolean isInt = true;
        try {
            Integer.parseInt(eventMaxParticipantInfo.getText());
        } catch (Exception e) {
            isInt = false;
        }
        if (eventInfoName.getText() == "") {
            eventInfoName.setStyle(getInputColor(6));
        }
        if (eventMaxParticipantInfo.getText() == "" || !isInt) {
            eventMaxParticipantInfo.setStyle(getInputColor(6));
        }
        if (eventInfoLocation.getText() == "") {
            eventInfoLocation.setStyle(getInputColor(6));
        }
        if (eventInfoDate.getText() == "") {
            eventInfoDate.setStyle(getInputColor(6));
        }
    }

    private void eventInputvalidator() {
        eventNameInput.setStyle(getInputColor(1));
        eventParticipantsInput.setStyle(getInputColor(2));
        eventLocationInput.setStyle(getInputColor(3));
        eventDateInput.setStyle(getInputColor(4));
    }

    private void eventInfoInputvalidator() {
        System.out.println(validNum);
        eventInfoName.setStyle(getInputColor(1));
        eventMaxParticipantInfo.setStyle(getInputColor(2));
        eventInfoLocation.setStyle(getInputColor(3));
        eventInfoDate.setStyle(getInputColor(4));
    }

    private String getInputColor(int num) {
        if (num == 6 || num == this.validNum) {
            return "-fx-background-color: rgba(255, 202, 191); -fx-border-color: red";
        }
        if (num < this.validNum) {
            return "-fx-background-color: rgba(200, 240, 175); -fx-border-color: green";
        } else {
            return "-fx-background-color: white; -fx-border-color: black";
        }
    }

    /**
     * Resets the input fields on the event page.
     */
    private void setEventPageDefault() {
        eventNameInput.setText("");
        eventParticipantsInput.setText("");
        eventLocationInput.setText("");
        eventDateInput.setText("");
        eventNameInput.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventParticipantsInput.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventLocationInput.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventDateInput.setStyle("-fx-background-color: white; -fx-border-color: black");
    }

    /**
     * Resets the input fields on the event page.
     */
    private void setEventInfoPageDefault() {    
        eventInfoName.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventMaxParticipantInfo.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventInfoLocation.setStyle("-fx-background-color: white; -fx-border-color: black");
        eventInfoDate.setStyle("-fx-background-color: white; -fx-border-color: black");
    }

    /**
     * When logIn is clicked. Sets loginScreen invisible and logOut visible.
     * insertUsername is saved as username.
     * Checks for blank username.
     * Checks if user is registered.
     */
    public void logInButton() {
        if (insertUsername.getText() == "") {
            feedbackField.setText("Username cannot be blank!");
        } else {
            if (webHandler.logIn(insertUsername.getText())) {
                Text text1 = new Text("Signed in as user ");
                text1.setFont(Font.font("Verdana", 18));
                Text text2 = new Text(insertUsername.getText());
                text2.setFont(Font.font("Verdana", 18));
                text2.setFill(Color.CHARTREUSE);
                signInField.getChildren().add(text1);
                signInField.getChildren().add(text2);
                loginScreen.setVisible(false);
                deleteEventButton.setOpacity(1);
                this.userName = insertUsername.getText();
                read();
            } else {
                feedbackField.setText("User \"" + insertUsername.getText() 
                    + "\" not found, please sign up instead!");
                insertUsername.setStyle("-fx-border-color: red;" + "-fx-border-width: 0 0 2 0;" 
                    + "-fx-background-color: transparent;");
            }
        }
    }
    

    /**
     * Button to sign up user.
     */
    public void signUpButton() {
        
        if (insertUsername.getText() == null || insertUsername.getText().equals("")) {
            feedbackField.setText("Username cannot be blank!");
            return;
        }
        String check = webHandler.signUp(insertUsername.getText());
        System.out.println(check);
        if (!check.contains("200")) {
            feedbackField.setText("User \"" + insertUsername.getText() 
                + "\" already exists, please log in instead!");
            insertUsername.setStyle("-fx-border-color: red;" 
                + "-fx-border-width: 0 0 2 0;" + "-fx-background-color: transparent;");
            return;
        }
        Text text1 = new Text("Signed in as user ");
        text1.setFont(Font.font("Verdana", 18));
        Text text2 = new Text(insertUsername.getText());
        text2.setFont(Font.font("Verdana", 18));
        text2.setFill(Color.CHARTREUSE);
        signInField.getChildren().add(text1);
        signInField.getChildren().add(text2);
        loginScreen.setVisible(false);
        deleteEventButton.setOpacity(1);
        this.userName = insertUsername.getText();
        webHandler.signUp(this.userName);
    }

    /**
     * When logOut is clicked. App is initialized
     */
    public void logOutButton() {
        setEventPageDefault();
        eventList.getItems().clear();
        deleteEventButton.setOpacity(0);
        loginScreen.setVisible(true);
        feedbackField.setText("");
        signInField.getChildren().clear();
        insertUsername.setText("");
        insertUsername.setStyle("-fx-border-color: #0598ff;" 
            + "-fx-border-width: 0 0 2 0;" + "-fx-background-color: transparent;");
        participantPane.setOpacity(0);
        participantPane.toBack();
        resetEventInput();
        setEventInfoPageDefault();
    }


    private void resetEventInput() {
        eventNameInput.setText("");
        eventParticipantsInput.setText("");
        eventLocationInput.setText("");
        eventDateInput.setText("");
    }

    private void resetInfoInput() {
        eventInfoName.setText("");
        eventMaxParticipantInfo.setText("");
        eventInfoLocation.setText("");
        eventInfoDate.setText("");
    }

    /**
     * Deletes event.
     */
    public void deleteEvent() {
        String line = eventList.getSelectionModel().getSelectedItem();
        line = line.substring(line.indexOf(" ") + 1);
        line = line.substring(0, line.indexOf(","));
        webHandler.deleteEvent(this.userName, line);
        int index = eventList.getSelectionModel().getSelectedIndex();
        eventList.getItems().remove(index);
    }

    /**
     * Method to handle when events are clicked.
     */
    public void handleEventClicked(MouseEvent e) {
        if (eventList.getSelectionModel().isEmpty()) {
            return;
        }
        if (e.getClickCount() == 2) {
            setEventPageDefault();
            participantPane.setOpacity(1);
            participantPane.toFront();

            String line = eventList.getSelectionModel().getSelectedItem();
            line = line.substring(line.indexOf(" ") + 1);
            line = line.substring(0, line.indexOf(","));
            this.currEvent = line;
            System.out.println(line);

            
            ArrayList<String> tmp = new ArrayList<>();
            for (ArrayList<String> eventInfo : webHandler.getUserInfo(this.userName)) {
                if (eventInfo.get(0).equals(line)) {
                    tmp.addAll(eventInfo);
                }
            }

            eventInfoName.setText(tmp.get(0));
            eventMaxParticipantInfo.setText(tmp.get(1));
            eventInfoLocation.setText(tmp.get(2));
            eventInfoDate.setText(tmp.get(3));

            participantsView.getItems().clear();
            HashMap<String, Boolean> retrievedHash = 
            webHandler.getParticipantList(this.userName, line);
            
            if (retrievedHash != null) {
                for (java.util.Map.Entry<String, Boolean> entry : retrievedHash.entrySet()) {
                    String participant = entry.getKey();
                    participantsView.getItems().add(participant);
                }
            }
        }
    }

    /**
     * Adds participants to the participantsview.
     */
    public void addParticipant() {
        String partName = participantName.getText();
        System.out.println("method= addparticipant" + " username = " 
            + this.userName + " eventname = " + this.currEvent + "participant = " + partName);
        String resp = webHandler.addParticipant(this.userName, this.currEvent, partName);
        if (resp.equals("401")) {
            alert("401 error", "Event is full.");
            return;
        }
        if (resp.equals("402")) {
            alert("402 error", "Invalid name format.");
            return;
        }
        if (resp.equals("400")) {
            alert("400", "File not found.");
            return;
        }
        participantName.setText("");
        participantsView.getItems().add(partName);
    }

    /**
     * Sets the alert box to the correct values. 
     *
     * @param title title of the fxml doc
     * @param context lol
     */
    private void alert(String title, String context) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * Removes et participant.
     */
    public void removeParticipant() {
        String line = participantsView.getSelectionModel().getSelectedItem();
        int index = participantsView.getSelectionModel().getSelectedIndex();
        webHandler.deleteParticipant(this.userName, this.currEvent, line);
        participantsView.getItems().remove(index);
        attendedBox.setText("");
    }

    /**
     * Updates the event info.
     */
    public void updateEventInfo() {
        this.validNum = 0;
        String newName = null;
        String newParticipantString = null;
        int newParticipantInt = 0;
        String newLocation = null;
        String newDate = null;
        try {
            newName = eventInfoName.getText();
            System.out.println(newName);

            newParticipantString = eventMaxParticipantInfo.getText();
            newParticipantInt = Integer.valueOf(newParticipantString);
            System.out.println(newParticipantInt);
    
            newLocation = eventInfoLocation.getText();
            System.out.println(newLocation);

            newDate = eventInfoDate.getText();
            System.out.println(newDate);

        } catch (Exception e) {
            eventInfoInputvalidator();
            this.validNum = 6;
            exceptionInfoColor();
            return;
        }
        if (eventInfoDate.getText() == "" || eventInfoLocation.getText() 
            == "" || eventInfoName.getText() == "") {
            eventInfoInputvalidator();
            this.validNum = 6;
            exceptionInfoColor();
            return;
        }
        String response = webHandler.updateEventInfo(this.userName, this.currEvent, 
        newName, newParticipantInt, newLocation, newDate);    
        System.out.println(response);
        if (response.equals("400")) {
            this.validNum = 1;
        }
        if (response.equals("401")) {
            this.validNum = 2;
        }
        if (response.equals("402")) {
            this.validNum = 3;
        }
        if (response.equals("403")) {
            this.validNum = 4;
        }
        if (response.equals("404")) {
            this.validNum = 5;
        }

        if (this.validNum == 0) {
            this.currEvent = newName;
            eventList.getItems().clear();
            setEventInfoPageDefault();
            read();
            return;
        }
        eventInfoInputvalidator();
    }

    /**
     * Closes the participantspane.
     */
    public void closeParticipantPane() {    
        currEvent = null;
        participantName.setText("");
        attendedBox.setText("");
        participantPane.setOpacity(0);
        participantPane.toBack();
        resetInfoInput();
        setEventInfoPageDefault();
    }

    /**
     * Handles clicks on the participationpane.
     */
    public void handleAttendence(MouseEvent e) {
    
        if (participantsView.getSelectionModel().isEmpty()) {
            return;
        }
        if (e.getClickCount() == 1) {
            check();
        }
        if (e.getClickCount() == 2) {
            String partic = participantsView.getSelectionModel().getSelectedItem();
            webHandler.checkOrUncheck(this.userName, this.currEvent, partic);
            check();
        }
    }

    /**
     * Handles doubleclicks to toggle participation.
     */
    public void check() {
        String partic = participantsView.getSelectionModel().getSelectedItem();
        HashMap<String, Boolean> retrievedHash 
            = webHandler.getParticipantList(this.userName, this.currEvent);
        if (retrievedHash != null) {
            for (java.util.Map.Entry<String, Boolean> entry : retrievedHash.entrySet()) {
                if (entry.getKey().equals(partic)) {
                    if (entry.getValue() == true) {
                        attendedBox.setText("Attending");
                    } else {
                        attendedBox.setText("Not attending");
                    }
                }
            }
        }
    }
}