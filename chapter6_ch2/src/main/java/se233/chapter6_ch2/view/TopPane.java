package se233.chapter6_ch2.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se233.chapter6_ch2.controller.AllEventHandlers;

import java.time.LocalDateTime;

public class TopPane extends FlowPane {
    private Button refresh;
    private Button add;
    private Button changeBase;
    private Label update;
    public TopPane(){
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setPrefSize(640,20);
        add= new Button("Add");
        refresh= new Button("Refresh");
        changeBase= new Button("Base Currency");

        refresh.setOnAction(e->{AllEventHandlers.onRefresh();});
        add.setOnAction(e->{AllEventHandlers.onAdd();});
        changeBase.setOnAction(e ->{AllEventHandlers.changeBaseCurrency();});

        update=new Label();
        refreshPane();
        this.getChildren().addAll(refresh, add, changeBase, update);
    }
    public void refreshPane(){
        update.setText(String.format("Last update: %s", LocalDateTime.now().toString()));
    }
}
