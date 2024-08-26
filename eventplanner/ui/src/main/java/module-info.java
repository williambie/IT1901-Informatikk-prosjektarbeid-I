module eventplanner.ui {

    requires java.net.http;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires transitive eventplanner.data;

    opens eventplanner.ui to javafx.graphics, javafx.fxml;
}
