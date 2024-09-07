module se233.chapter5_part2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se233.chapter5_part2 to javafx.fxml;
    exports se233.chapter5_part2;
}