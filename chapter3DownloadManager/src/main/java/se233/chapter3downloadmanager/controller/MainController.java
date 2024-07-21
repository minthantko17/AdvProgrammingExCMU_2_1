package se233.chapter3downloadmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import se233.chapter3downloadmanager.model.Downloader;
import se233.chapter3downloadmanager.model.Merger;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    @FXML
    private TextField urlField;
    @FXML
    private TextField fileField;
    @FXML
    private ProgressBar thread1, thread2, merge_bar;

    long totalSizeOfFile;
    public static ExecutorService executor;
    public void handleDownloadAction(){
        CountDownLatch countDownLatch = new CountDownLatch(2);
        try{
            executor= Executors.newFixedThreadPool(2);

            URL url=new URL(urlField.getText());
            String fileName=fileField.getText();
            URLConnection openConnection=url.openConnection();

            totalSizeOfFile=openConnection.getContentLength();
            long baseLength=totalSizeOfFile/2;

            Downloader downloader1=new Downloader(url, fileName+"-part1", 0, baseLength,countDownLatch);
            Downloader downloader2=new Downloader(url, fileName+"-part2", 0, baseLength,countDownLatch);
            Merger merger=new Merger(fileName, 2, countDownLatch);

            thread1.progressProperty().bind(downloader1.progressProperty());
            thread2.progressProperty().bind(downloader2.progressProperty());
            merge_bar.progressProperty().bind(merger.progressProperty());

            executor.submit(downloader1);
            executor.submit(downloader2);
            executor.submit(merger);
            executor.shutdown();


        }catch (Exception e){e.printStackTrace();}
    }

}

