module se233.chapter6_ch5_p1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens se233.chapter6_ch5_p1 to javafx.fxml;
    exports se233.chapter6_ch5_p1;
}