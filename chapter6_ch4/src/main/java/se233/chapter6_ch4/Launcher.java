package se233.chapter6_ch4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import se233.chapter6_ch4.controller.DrawingLoop;
import se233.chapter6_ch4.controller.GameLoop;
import se233.chapter6_ch4.view.GameStage;

import java.io.File;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameStage gameStage = new GameStage();
//        String soundFile="src/main/resources/se233/chapter4/assets/SuperMarioBros.mp3";
//        Media sound=new Media(new File(soundFile).toURI().toString());
//        MediaPlayer mediaPlayer=new MediaPlayer(sound);
//        mediaPlayer.play();

        GameLoop gameLoop = new GameLoop(gameStage);
        DrawingLoop drawingLoop = new DrawingLoop(gameStage);

        Scene scene = new Scene(gameStage, gameStage.WIDTH, gameStage.HEIGHT);
        scene.setOnKeyPressed(event-> gameStage.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event -> gameStage.getKeys().remove(event.getCode()));

        stage.setTitle("Mario");
        stage.setScene(scene);
        stage.show();

        Thread gameLoopThread= new Thread(gameLoop);
        gameLoopThread.setDaemon(true);
        gameLoopThread.start();


        Thread drawingLoopThread= new Thread(drawingLoop);
        drawingLoopThread.setDaemon(true);
        drawingLoopThread.start();
    }
}

