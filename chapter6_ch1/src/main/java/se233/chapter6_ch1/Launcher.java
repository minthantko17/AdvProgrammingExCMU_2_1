package se233.chapter6_ch1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.chapter6_ch1.controller.GenCharacter;
import se233.chapter6_ch1.controller.GenItemList;
import se233.chapter6_ch1.model.character.BasedCharacter;
import se233.chapter6_ch1.model.item.Armor;
import se233.chapter6_ch1.model.item.BasedEquipment;
import se233.chapter6_ch1.model.item.Weapon;
import se233.chapter6_ch1.view.CharacterPane;
import se233.chapter6_ch1.view.EquipPane;
import se233.chapter6_ch1.view.InventoryPane;

import java.util.ArrayList;

public class Launcher extends Application {
    private static Scene mainScene;
    private static BasedCharacter mainCharacter=null;
    private static ArrayList<BasedEquipment> allEquipments=null;
    private static Weapon equippedWeapon=null;
    private static Armor equippedArmor=null;
    private static CharacterPane characterPane=null;
    private static EquipPane equipPane=null;
    private static InventoryPane inventoryPane=null;

    public static Weapon getEquippedWeapon() { return equippedWeapon; }
    public static void setEquippedWeapon(Weapon equippedWeapon) {Launcher.equippedWeapon = equippedWeapon;}
    public static Armor getEquippedArmor() { return equippedArmor; }
    public static void setEquippedArmor(Armor equippedArmor) { Launcher.equippedArmor = equippedArmor; }
    public static ArrayList<BasedEquipment> getAllEquipments() { return allEquipments; }
    public static void setAllEquipments(ArrayList<BasedEquipment> allEquipments) { Launcher.allEquipments = allEquipments; }
    public static BasedCharacter getMainCharacter(){return mainCharacter;}
    public static void setMainCharacter(BasedCharacter mainCharacter){Launcher.mainCharacter=mainCharacter;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chapter1");
        primaryStage.setResizable(false);
        primaryStage.show();

        mainCharacter= GenCharacter.setUpCharacter();   //build random character
        allEquipments= GenItemList.setUpItemList();     //add item lists

        Pane mainPane=getMainPane();
        mainScene=new Scene(mainPane);
        primaryStage.setScene(mainScene);
    }

    public Pane getMainPane(){              //to combine all subPanes into one
        BorderPane mainPane=new BorderPane();
        characterPane=new CharacterPane();
        equipPane=new EquipPane();
        inventoryPane=new InventoryPane();
        refreshPane();  //recalculate sub-components

        mainPane.setCenter(characterPane);
        mainPane.setLeft(equipPane);
        mainPane.setBottom(inventoryPane);
        return mainPane;
    }

    public static void refreshPane(){       //refresh by re-drawing Pane from respective View class
        characterPane.drawPane(mainCharacter);
        equipPane.drawPane(equippedWeapon, equippedArmor);
        inventoryPane.drawPane(allEquipments);
    }

    public static void main(String[] args) {launch(args);}
}
