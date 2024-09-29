package se233.chapter6_ch2.view;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import se233.chapter6_ch2.model.Currency;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyParentPane extends ScrollPane {
    VBox vBox = new VBox();

    public CurrencyParentPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.setPadding(new Insets(0));
        refreshPane(currencyList);
        this.setFitToWidth(true);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setMaxHeight(600);
        this.setContent(vBox);
    }
    public void refreshPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        vBox.getChildren().clear();
        for(int i=0; i<currencyList.size(); i++) {
            CurrencyPane cp = new CurrencyPane(currencyList.get(i));
            vBox.getChildren().add(cp);
        }
    }

}
