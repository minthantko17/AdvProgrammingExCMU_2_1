package se233.chapter6_ch1.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter6_ch1.Launcher;
import se233.chapter6_ch1.controller.AllCustomHandler;
import se233.chapter6_ch1.model.character.BasedCharacter;

public class CharacterPane extends ScrollPane {
    private BasedCharacter character;
    public CharacterPane(){ }

    private Pane getDetailsPane(){
        Pane characterInfoPane= new VBox(10);
        characterInfoPane.setBorder(null);
        characterInfoPane.setPadding(new Insets(25, 25, 25, 25));

        Label name, type, hp, atk, def, res;
        ImageView mainImage=new ImageView();

        if(this.character != null){
            name=new Label("Name: "+character.getName());
            type=new Label("Type: "+character.getType());
            atk=new Label("ATK: "+character.getPower());
            def=new Label("DEF: "+character.getDefense());
            res=new Label("RES: "+character.getResistance());
            mainImage.setImage(new Image(Launcher.class.getResource(character.getImgpath()).toString()));
            hp=new Label("HP: "+character.getHp().toString()+"/"+character.getFullHp().toString());
        }else{
            name=new Label("Name: ");
            type=new Label("Type: ");
            atk=new Label("ATK: ");
            def=new Label("DEF: ");
            res=new Label("RES: ");
            mainImage.setImage(new Image(Launcher.class.getResource("assets/unknown.png").toString()));
            hp=new Label("HP: ");
        }

        //Button setting
        Button genCharacter=new Button();
        genCharacter.setText("Generate Character");
        genCharacter.setOnAction(event -> {
            new AllCustomHandler.UnequipItemHandler().handle(event);
            new AllCustomHandler.GenCharacterHandler().handle(event);
        });

        characterInfoPane.getChildren().addAll(name, mainImage, type, hp, atk, def, res, genCharacter);
        return characterInfoPane;
    }

    public void drawPane(BasedCharacter character){
        this.character=character;
        Pane characterInfo=getDetailsPane();
        this.setStyle("-fx-background-color: Red;");
        this.setContent(characterInfo);
    }
}
