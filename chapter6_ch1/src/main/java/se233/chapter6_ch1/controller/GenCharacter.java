package se233.chapter6_ch1.controller;

import se233.chapter6_ch1.model.character.BasedCharacter;
import se233.chapter6_ch1.model.character.BattleMageCharacter;
import se233.chapter6_ch1.model.character.MagicalCharacter;
import se233.chapter6_ch1.model.character.PhysicalCharacter;

import java.util.Random;

public class GenCharacter {
    public static BasedCharacter setUpCharacter(){  //create random character and stats
        BasedCharacter character;
        Random rand=new Random();
        int type=rand.nextInt(3)+1;
        int basedDef=rand.nextInt(50)+1;
        int basedRes=rand.nextInt(50)+1;
        if(type==1){
            character=new MagicalCharacter("MagicChar", "assets/wizard.png", basedDef,basedRes);
        }
        else if(type==2) {
            character=new BattleMageCharacter("BattleMage", "assets/battlemage.png", basedDef,basedRes);
        }else{
            character=new PhysicalCharacter("PhysicalChar", "assets/knight.png", basedDef, basedRes);
        }
        return character;
    }
}
