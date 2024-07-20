package se233.chapter1.model.character;

import javafx.scene.image.ImageView;
import se233.chapter1.model.DamageType;

public class BattleMageCharacter extends BasedCharacter{
    public BattleMageCharacter(String name, String imgpath, int basedDef, int basedRes){
        this.name=name;
        this.imgpath=imgpath;
        this.type= DamageType.PURE_DAMAGE;
        this.fullHp=40;
        this.basedPow=40;
        this.basedDef=basedDef;
        this.basedRes=basedRes;
        this.hp=this.fullHp;
        this.power=this.basedPow;
        this.defense=this.basedDef;
        this.resistance=this.basedRes;

    }
}
