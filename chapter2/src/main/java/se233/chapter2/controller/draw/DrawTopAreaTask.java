package se233.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.model.Currency;

import java.util.concurrent.Callable;

public class DrawTopAreaTask implements Callable<HBox> {
    Currency currency;
    private Button watch;
    private Button unWatch;
    private Button delete;

    public DrawTopAreaTask(Currency currency) {
        this.currency = currency;

        this.watch = new Button("Watch");
        this.unWatch = new Button("Unwatch");
        this.delete= new Button("Delete");

        this.watch.setOnAction(e -> {AllEventHandlers.onWatch(currency.getShortCode());});
        this.unWatch.setOnAction(e ->{AllEventHandlers.unWatch(currency.getShortCode());});
        this.delete.setOnAction(e -> {AllEventHandlers.onDelete(currency.getShortCode());});

    }

    @Override
    public HBox call() throws Exception {
        HBox topArea=new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch, unWatch, delete);
        topArea.setAlignment(Pos.CENTER_RIGHT);
        return topArea;
    }
}
