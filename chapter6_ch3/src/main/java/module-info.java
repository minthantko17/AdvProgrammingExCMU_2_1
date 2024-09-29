module se233.chapter6_ch3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox;
    requires org.apache.logging.log4j;

    opens se233.chapter6_ch3 to javafx.fxml;
    opens se233.chapter6_ch3.controller to javafx.fxml;
    exports se233.chapter6_ch3;
    exports se233.chapter6_ch3.controller;
}