package se233.chapter3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import se233.chapter3.model.FileFreq;
import se233.chapter3.model.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainViewController {
    LinkedHashMap<String, List<FileFreq>> uniqueSets;
    @FXML
    private ListView<String> inputListView;
    @FXML
    private Button startButton;
    @FXML
    private ListView listView;

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
            boolean success=false;
            if(db.hasFiles()){
                success=true;
                String filePath;
                int totalFiles=db.getFiles().size();
                for(int i=0;i<totalFiles;i++){
                    File file = db.getFiles().get(i);
                    filePath = file.getAbsolutePath();
                    inputListView.getItems().add(filePath);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        startButton.setOnAction(event->{
            List<String> inputListViewItems = inputListView.getItems();
            int totalFiles = inputListViewItems.size();
            WordCountMapTask[] wordCountMapTaskArray = new WordCountMapTask[totalFiles];
            Map<String, FileFreq>[] wordMap = new Map[totalFiles];
            for (int i = 0; i < totalFiles; i++) {
                try{
                    String filePath = inputListViewItems.get(i);
                    PdfDocument p = new PdfDocument(filePath);
                    wordCountMapTaskArray[i] = new WordCountMapTask(p);
                    wordMap[i] = wordCountMapTaskArray[i].getWordCount();
                }catch (IOException e){e.printStackTrace();}
            }
            WordCountReduceTask merger = new WordCountReduceTask(wordMap);
            uniqueSets = merger.getUniqueSets();
            listView.getItems().addAll(uniqueSets.keySet());
        });
    }
}
