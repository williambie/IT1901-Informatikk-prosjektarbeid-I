package eventplanner.ui;

import eventplanner.data.AppStorage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

@TestInstance(Lifecycle.PER_CLASS)
public class AppControllerTest extends ApplicationTest {
    AppController controller = new AppController();
    AppStorage storage;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("EventApp.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void tests() {
        setup();
        testSignup();
        testCreateEvent();
        openEvent();
        testAddParticipant();
        testUpdateEventInfo();
        testLogIn();
        testCheckandRemoveAttendence();
    }

    @BeforeEach
    public void setup() {
        storage = new AppStorage();
    }

    public void testSignup() {
        String text = "William";
        clickOn("#insertUsername").write(text);
        clickOn("#signUpButton");
        FxAssert.verifyThat("#insertUsername", (TextField field) -> {
            String p = field.getText();
            return p.equals("William");
        });

    }

    public void testCreateEvent() {
        String eventName = "Test";
        String participants = "10";
        String location = "Mars";
        String date = "10.09.2022";
        clickOn("#eventNameInput").write(eventName);
        FxAssert.verifyThat("#eventNameInput", (TextField field) -> {
            String p = field.getText();
            return p.equals("Test");
        });
        clickOn("#eventParticipantsInput").write(participants);
        FxAssert.verifyThat("#eventParticipantsInput", (TextField field) -> {
            String p = field.getText();
            return p.equals("10");
        });
        clickOn("#eventLocationInput").write(location);
        FxAssert.verifyThat("#eventLocationInput", (TextField field) -> {
            String p = field.getText();
            return p.equals("Mars");
        });
        clickOn("#eventDateInput").write(date);
        FxAssert.verifyThat("#eventDateInput", (TextField field) -> {
            String p = field.getText();
            return p.equals("10.09.2022");
        });
        clickOn("#createEventButton");
    }

    public void openEvent() {
        doubleClickOn(controller.eventList.getItems().get(0));
        sleep(500);
    }

    public void testAddParticipant() {
        String name = "Johannes";
        String name2 = "August";
        clickOn("#participantName").write(name);
        FxAssert.verifyThat("#participantName", (TextField field) -> {
            String p = field.getText();
            return p.equals("Johannes");
        });
        clickOn("#addParticipant");
        clickOn("#participantName").write(name2);
        FxAssert.verifyThat("#participantName", (TextField field) -> {
            String p = field.getText();
            return p.equals("August");
        });
        clickOn("#addParticipant");
    }

    public void testUpdateEventInfo() {
        clickOn("#eventInfoName").eraseText(4);
        clickOn("#eventMaxParticipantInfo").eraseText(2);
        clickOn("#eventInfoLocation").eraseText(4);
        clickOn("#eventInfoDate").eraseText(10);
        String eventName = "Låvefest";
        String participants = "20";
        String location = "Oslo";
        String date = "10.04.2025";
        clickOn("#eventInfoName").write(eventName);
        FxAssert.verifyThat("#eventInfoName", (TextField field) -> {
            String p = field.getText();
            return p.equals("Låvefest");
        });
        clickOn("#eventMaxParticipantInfo").write(participants);
        FxAssert.verifyThat("#eventMaxParticipantInfo", (TextField field) -> {
            String p = field.getText();
            return p.equals("20");
        });
        clickOn("#eventInfoLocation").write(location);
        FxAssert.verifyThat("#eventInfoLocation", (TextField field) -> {
            String p = field.getText();
            return p.equals("Oslo");
        });
        clickOn("#eventInfoDate").write(date);
        FxAssert.verifyThat("#eventInfoDate", (TextField field) -> {
            String p = field.getText();
            return p.equals("10.04.2025");
        });
        // clickOn("#updateEventInfo");
    }

    public void testLogIn() {
        clickOn("#closeParticipantPane");
        clickOn("#logOut");
        clickOn("#insertUsername").write("William");
        FxAssert.verifyThat("#insertUsername", (TextField field) -> {
            String p = field.getText();
            return p.equals("William");
        });
        clickOn("#logInButton");
        sleep(1000);
    }

    public void testCheckandRemoveAttendence() {
        doubleClickOn(controller.eventList.getItems().get(0));
        clickOn(controller.participantsView.getItems().get(0));
        doubleClickOn(controller.participantsView.getItems().get(0));
        sleep(1000);
        clickOn(controller.participantsView.getItems().get(1));
        clickOn("#removeParticipant");
    }

    @AfterAll
    public void tearDown() {
        storage.getPath2("William").toFile().delete();
    }
}
