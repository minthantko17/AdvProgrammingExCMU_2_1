package se233.chapter4.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;

public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    private Image gameCharacterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    int yVelocity = 0;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    int xAcceleration;
    int yAcceleration;
    int xMaxVelocity;
    int yMaxVelocity;

    public GameCharacter(ImageView imgView, int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey,
                         int xAcceleration, int xMaxVelocity,int yAcceleration, int yMaxVelocity) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.imageView = (AnimatedSprite) imgView;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.xAcceleration=xAcceleration;
        this.xMaxVelocity=xMaxVelocity;
        this.yAcceleration=yAcceleration;
        this.yMaxVelocity=yMaxVelocity;
        this.getChildren().addAll(this.imageView);
    }

    public void stop() {
        isMoveLeft = false;
        isMoveRight= false;
    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x - xVelocity;
        }
        if(isMoveRight) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x + xVelocity;
        }
    }

    public void moveY() {
        setTranslateY(y);
        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity+yAcceleration;
            y = y + yVelocity;
        }else if(isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity-yAcceleration;
            y = y - yVelocity;
        }
    }

    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft=true;
        isMoveRight=false;
        if(x<=0){
            logger.debug("Reached left wall.");
        }
    }

    public void moveRight() {
        setScaleX(1);
        isMoveLeft=false;
        isMoveRight=true;
        if( x+getWidth() >=GameStage.WIDTH){
            logger.debug("Reached right wall.");
        }
    }

    public void checkReachHighest() {
        if(isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + getWidth() >= GameStage.WIDTH) {
            x = GameStage.WIDTH - (int) getWidth();
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - (int)imageView.getFitHeight()) {
            y = GameStage.GROUND - (int)imageView.getFitHeight();
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public KeyCode getLeftKey() { return leftKey; }
    public KeyCode getRightKey() { return rightKey; }
    public KeyCode getUpKey() { return upKey; }
    public AnimatedSprite getImageView() { return imageView; }

    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}",x,y,xVelocity,yVelocity);
    }

}

