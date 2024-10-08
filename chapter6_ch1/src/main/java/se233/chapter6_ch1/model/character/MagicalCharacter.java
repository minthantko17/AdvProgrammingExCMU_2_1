package se233.chapter6_ch1.model.character;

import se233.chapter6_ch1.model.DamageType;

public class MagicalCharacter extends BasedCharacter{
    public MagicalCharacter(String name, String imgpath, int basedDef, int basedRes){
        this.name=name;
        this.imgpath=imgpath;
        this.type= DamageType.MAGICAL;
        this.fullHp=30;
        this.basedPow=50;
        this.basedDef=basedDef;
        this.basedRes=basedRes;
        this.hp=this.fullHp;
        this.power=this.basedPow;
        this.defense=basedDef;
        this.resistance=basedRes;
    }
}
