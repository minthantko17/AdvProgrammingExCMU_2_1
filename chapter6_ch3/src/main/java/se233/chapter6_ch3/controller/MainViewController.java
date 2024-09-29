package se233.chapter6_ch3.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter6_ch3.Launcher;
import se233.chapter6_ch3.model.FileFreq;
import se233.chapter6_ch3.model.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MainViewController {
    LinkedHashMap<String, List<FileFreq>> uniqueSets;
    @FXML
    private ListView<String> inputListView;
    @FXML
    private Button startButton;
    @FXML
    private ListView listView;
    @FXML
    private MenuItem closeBtn;

    List<String> inputFilePath=new ArrayList<>();

    private static final Logger logger = LogManager.getLogger(MainViewController.class);

    @FXML
    public void initialize(){
        inputListView.setOnDragOver(event->{
            Dragboard db=event.getDragboard();
            final boolean isAccepted=db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf");
            if (db.hasFiles()&& isAccepted){
                event.acceptTransferModes(TransferMode.COPY);
            }else{
                event.consume();
            }
        });

        inputListView.setOnDragDropped(event->{
            Dragboard db=event.getDragboard();
            System.out.println("file: "+db.getFiles().get(0));
            boolean success=false;
            if(db.hasFiles()){
                success=true;
                String filePath;
                String fileName;
                int totalFiles=db.getFiles().size();
                for(int i=0;i<totalFiles;i++){
                    File file = db.getFiles().get(i);
                    filePath = file.getAbsolutePath();
                    fileName = Paths.get(filePath).getFileName().toString();
                    inputFilePath.add(filePath);
                    inputListView.getItems().add(fileName);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        startButton.setOnAction(event->{
            Parent bgRoot = Launcher.primaryStage.getScene().getRoot();
            Task<Void> processTask= new Task<Void>() {
                @Override
                protected Void call() throws IOException {
                    ProgressIndicator pi = new ProgressIndicator();
                    VBox box = new VBox(pi);
                    box.setAlignment(Pos.CENTER);
                    Launcher.primaryStage.getScene().setRoot(box);

                    ExecutorService executor = Executors.newFixedThreadPool(4);
                    final ExecutorCompletionService<Map<String, FileFreq>> completionService = new ExecutorCompletionService<>(executor);
                    List<String> inputListViewItems = inputFilePath;
                    int totalFiles = inputListViewItems.size();
                    Map<String, FileFreq>[] wordMap = new Map[totalFiles];
                    for (int i = 0; i < totalFiles; i++) {
                        try{
                            String filePath = inputListViewItems.get(i);
                            PdfDocument p = new PdfDocument(filePath);
                            completionService.submit(new WordCountMapTask(p));
                        }catch (IOException e){e.printStackTrace();}
                    }
                    for(int i=0; i<totalFiles; i++){
                        try{
                            Future<Map<String, FileFreq>> future= completionService.take();
                            wordMap[i] = future.get();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    try{
                        WordCountReduceTask merger = new WordCountReduceTask(wordMap);
                        Future<LinkedHashMap<String, List<FileFreq>>> future = executor.submit(merger);
                        uniqueSets = future.get();
                        listView.getItems().addAll(uniqueSets.entrySet().stream()
                                .map(m->m.getKey()+"("
                                        +m.getValue().stream()
                                        .map(num-> (num.getFreq()))
                                        .collect(Collectors.toList()).toString().replaceAll("[\\[\\]]","")
                                        +")")
                                .collect(Collectors.toList())
                        );
                        logger.info("Imported Files: {}",inputListView.getItems());
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        executor.shutdown();
                    }
                    return null;
                }
            };

            processTask.setOnSucceeded(e-> {
                Launcher.primaryStage.getScene().setRoot(bgRoot);
            });

            Thread thread = new Thread(processTask);
            thread.setDaemon(true);
            thread.start();
        });

        listView.setOnMouseClicked(event->{
            List<FileFreq> listOfLinks = uniqueSets.get(
                    listView.getSelectionModel().getSelectedItem().toString()
                            .replaceAll("\\s*\\([^)]*\\)", ""));
            ListView<FileFreq> popupListView = new ListView<>();
            LinkedHashMap<FileFreq,String> lookupTable = new LinkedHashMap<>();
            for (int i = 0; i < listOfLinks.size(); i++) {
                lookupTable.put(listOfLinks.get(i), listOfLinks.get(i).getPath());
                popupListView.getItems().add(listOfLinks.get(i));
            }

            popupListView.setPrefHeight(popupListView.getItems().size() * 50);
            popupListView.setOnMouseClicked(innerEvent->{
                Launcher.hs.showDocument("file:///"+lookupTable.get(popupListView.getSelectionModel().getSelectedItem()));
                popupListView.getScene().getWindow().hide();
            });
            Popup popup=new Popup();
            popup.getContent().add(popupListView);
            popup.show(Launcher.primaryStage);

            popupListView.setOnKeyPressed(e->{
                if(e.getCode()== KeyCode.ESCAPE){
                    popup.hide();
                }
            });

        });

        closeBtn.setOnAction(event->System.exit(1));
    }
}
