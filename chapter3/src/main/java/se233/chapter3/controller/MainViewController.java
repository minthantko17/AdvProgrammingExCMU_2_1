package se233.chapter3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;

import java.io.File;

public class MainViewController {
    @FXML
    private Region dropRegion;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView listView;

    @FXML
    public void initialize(){
        dropRegion.setOnDragOver(event->{
            Dragboard db=event.getDragboard();
            final boolean isAccepted=db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf");
            if (db.hasFiles()&& isAccepted){
                event.acceptTransferModes(TransferMode.COPY);
            }else{
                event.consume();
            }
        });

        dropRegion.setOnDragDropped(event->{
            Dragboard db=event.getDragboard();
            boolean success=false;
            if(db.hasFiles()){
                success=true;
                String filePath;
                int totalFiles=db.getFiles().size();
                for(int i=0;i<totalFiles;i++){
                    File file=db.getFiles().get(i);
                    filePath=file.getAbsolutePath();
                    System.out.println(filePath);
                }
                progressBar.setProgress(100);
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
