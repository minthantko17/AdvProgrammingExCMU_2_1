module se233.chapter4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se233.chapter4 to javafx.fxml;
    exports se233.chapter4;
}