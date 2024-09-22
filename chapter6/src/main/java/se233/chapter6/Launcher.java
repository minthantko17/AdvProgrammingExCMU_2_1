package se233.chapter6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import se233.chapter6.controller.GameLoop;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

public class Launcher extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        GameStage gameStage = new GameStage();
        Snake snake = new Snake(new Point2D(gameStage.WIDTH / 2, gameStage.HEIGHT / 2));
        Food food = new Food();
        Food specialFood = new Food();
        GameLoop gameLoop = new GameLoop(gameStage, snake, food, specialFood);

        Scene scene = new Scene(gameStage, gameStage.WIDTH * gameStage.TILE_SIZE, gameStage.HEIGHT * gameStage.TILE_SIZE);
        scene.setOnKeyPressed(event -> gameStage.setKey(event.getCode()));
        scene.setOnKeyReleased(event -> gameStage.setKey(null));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.setResizable(false);
        primaryStage.show();

        new Thread(gameLoop).start();

    }
}

