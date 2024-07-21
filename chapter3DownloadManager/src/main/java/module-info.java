module se233.chapter3downloadmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens se233.chapter3downloadmanager to javafx.fxml;
    opens se233.chapter3downloadmanager.controller to javafx.fxml;
    exports se233.chapter3downloadmanager;
    exports se233.chapter3downloadmanager.controller;
}