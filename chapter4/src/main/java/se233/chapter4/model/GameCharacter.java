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
    int xVelocity = 5;
    int yVelocity = 3;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    int highestJump = 100;

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

    public void stop() {
        isMoveLeft = false;
        isMoveRight= false;
    }
    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) { x = x - xVelocity; }
        if(isMoveRight) { x = x + xVelocity; }
    }

    public void jump() {
        if (canJump) {
            yVelocity = 5;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void checkReachHighest() {
        if (isJumping && y <= GameStage.GROUND - CHARACTER_HEIGHT - highestJump) {
            isJumping = false;
            isFalling = true;
        }
    }

    public void moveY() {
        setTranslateY(y);
        if (isFalling) {
            y = y + yVelocity;
        }else if(isJumping) {
            y = y - yVelocity;
        }
    }

    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft=true;
        isMoveRight=false;
    }

    public void moveRight() {
        setScaleX(1);
        isMoveLeft=false;
        isMoveRight=true;
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + getWidth() >= GameStage.WIDTH) {
            x = GameStage.WIDTH - (int) getWidth();
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public KeyCode getLeftKey() { return leftKey; }
    public KeyCode getRightKey() { return rightKey; }
    public KeyCode getUpKey() { return upKey; }
}

