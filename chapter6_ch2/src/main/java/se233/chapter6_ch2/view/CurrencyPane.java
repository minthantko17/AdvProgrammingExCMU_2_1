package se233.chapter6_ch2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.chapter6_ch2.controller.draw.DrawCurrencyInfoTask;
import se233.chapter6_ch2.controller.draw.DrawGraphtask;
import se233.chapter6_ch2.controller.draw.DrawTopAreaTask;
import se233.chapter6_ch2.model.Currency;

import java.util.concurrent.*;

public class CurrencyPane extends BorderPane {
    private Currency currency;

    public CurrencyPane(Currency currency) {

        this.setPadding(new Insets(0));
        this.setPrefSize(850, 300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        }catch (ExecutionException e){
            System.out.println("Encountered an execution exception.");
        }catch (InterruptedException e){
            System.out.println("Encountered an interrupted exception.");
        }
    }

    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;
        FutureTask<VBox> fTaskDrawGraph=new FutureTask<VBox>(new DrawGraphtask(currency));
        FutureTask<VBox> fTaskCurrencyPane= new FutureTask<>(new DrawCurrencyInfoTask(currency));
        FutureTask<HBox> fTaskTopArea= new FutureTask<>(new DrawTopAreaTask(currency));

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.execute(fTaskDrawGraph);
            executor.execute(fTaskCurrencyPane);
            executor.execute(fTaskTopArea);
            VBox currencyGraph = fTaskDrawGraph.get();
            VBox currencyInfo= fTaskCurrencyPane.get();
            HBox topArea= fTaskTopArea.get();
            this.setTop(topArea);
            this.setLeft(currencyInfo);
            this.setCenter(currencyGraph);
//            executor.shutdown();
        }
    }

}
