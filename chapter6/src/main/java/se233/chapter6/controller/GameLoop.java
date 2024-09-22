package se233.chapter6.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;
import se233.chapter6.Launcher;
import se233.chapter6.model.Direction;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private Snake snake;
    private Food food;
    private Food specialFood;
    private float interval = 1000.0f / 10;
    private boolean running;
    private  int score;

    public GameLoop(GameStage gameStage, Snake snake, Food food, Food specialFood) {
        this.snake = snake;
        this.gameStage = gameStage;
        this.food = food;
        this.specialFood = specialFood;
        running = true;
    }

    private void keyProcess() {
        KeyCode curKey = gameStage.getKey();
        Direction curDirection = snake.getDirection();
        if (curKey == KeyCode.UP && curDirection != Direction.DOWN) {
            snake.setDirection(Direction.UP);
        } else if (curKey == KeyCode.DOWN && curDirection != Direction.UP) {
            snake.setDirection(Direction.DOWN);
        } else if (curKey == KeyCode.LEFT && curDirection != Direction.RIGHT) {
            snake.setDirection(Direction.LEFT);
        } else if (curKey == KeyCode.RIGHT && curDirection != Direction.LEFT) {
            snake.setDirection(Direction.RIGHT);
        }
        snake.move();
    }

    private void checkCollision() throws InterruptedException {
        if (snake.collided(food)) {
            snake.grow();
            score++;
            food.respawn();
        }
        if (snake.collided(specialFood)) {
            snake.grow();
            score=score+5;
            specialFood.respawn();
        }
        if (snake.checkDead()) {
            running = false;

            Platform.runLater(()-> {
                Label label=new Label(" Game Over \n Score: "+score);
                label.setStyle("-fx-text-fill: white; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: DARKGREY");
                Popup popup=new Popup();
                popup.getContent().add(label);
                popup.show(Launcher.primaryStage);
            });
            Thread.sleep(5000);
            Platform.exit();
        }
    }

    private void redraw() {
        gameStage.render(snake, food, specialFood);
    }

    @Override
    public void run() {
        while (running) {
            keyProcess();
            try { checkCollision();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            redraw();
            try {
                Thread.sleep((long) interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

