module se233.chapter6_ch4 {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens se233.chapter6_ch4 to javafx.fxml;
    exports se233.chapter6_ch4;
}