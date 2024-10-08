package se233.chapter6_ch4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter6_ch4.Launcher;
import se233.chapter6_ch4.model.AnimatedSprite;
import se233.chapter6_ch4.model.GameCharacter;
import se233.chapter6_ch4.model.Keys;


public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private Image gameStageImg;
    private Image char1Img;
    private Image char2Img;
    private ImageView char1ImgView;
    private ImageView char2ImgView;


    private GameCharacter gameCharacter1;
    private GameCharacter gameCharacter2;
    private Keys keys;

    public GameStage() {
        keys = new Keys();
        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        char1Img = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
        char2Img = new Image(Launcher.class.getResourceAsStream("assets/Rockman.png"));

        char1ImgView =new AnimatedSprite(char1Img, 32, 64, 4, 4, 1, 0, 0, 16, 32);
        char2ImgView =new AnimatedSprite(char2Img, 64, 64,10, 5, 2, 0, 0, 410, 347);


        gameCharacter1 = new GameCharacter(char1ImgView,30, 30,0,0, KeyCode.A,KeyCode.D,KeyCode.W,1,7, 1,17);
        gameCharacter2 = new GameCharacter(char2ImgView,70, 30,0,0, KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,3,15,2,25);
        getChildren().addAll(backgroundImg, gameCharacter1, gameCharacter2);
    }

    public GameCharacter getGameCharacter1() { return gameCharacter1; }
    public GameCharacter getGameCharacter2() { return gameCharacter2; }

    public Keys getKeys() { return keys; }
}
