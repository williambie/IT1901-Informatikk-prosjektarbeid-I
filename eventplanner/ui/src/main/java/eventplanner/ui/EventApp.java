package eventplanner.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class for starting the EventApp.
 */
public class EventApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EventApp.fxml"));
        final Parent parent = fxmlLoader.load();
        final Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Eventplanner");
        stage.show();
    }

    public static void main(String[] args) {
        launch(EventApp.class, args);
    }
}