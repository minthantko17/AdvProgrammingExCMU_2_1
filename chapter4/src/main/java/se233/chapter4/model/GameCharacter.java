package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;

public class GameCharacter extends Pane {
    public static final int CHARACTER_WIDTH = 64;
    public static final int CHARACTER_HEIGHT = 64;
    private Image gameCharacterImg;
    private ImageView imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int yVelocity = 3;
    boolean isFalling = true;

    public GameCharacter(int x, int y, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.gameCharacterImg = new Image(Launcher.class.getResourceAsStream("assets/StillMario.png"));
        this.imageView = new ImageView(this.gameCharacterImg);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }

    public void moveY() {
        if (isFalling) {
            y = y + yVelocity;
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
        }
    }

    public void repaint() {
        setTranslateX(x);
        setTranslateY(y);
    }

}

