package com.avmoga.dpixel.items;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Statistics;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Music;

import java.util.ArrayList;

public class NeverGiveUpAmulet extends Item {
    private static final String AC_END = "焯！！！";

    {
            name = "Yendor护身符";

            image = ItemSpriteSheet.AMULET;

        unique = true;
    }

    public String name() {
        if (Statistics.amuletObtainednever) {
            return name = "RickRoll自拍照";
        }
        return name;
    }

    public int image() {
        if (Statistics.amuletObtainednever) {
            return ItemSpriteSheet.PLZP;
        }
        return image;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_END);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        if (action == AC_END) {

            detach(hero.belongings.backpack);
            GLog.w("你恼羞成怒的撕烂了照片");

        } else {

            super.execute(hero, action);
        }
    }

    @Override
    public boolean doPickUp(Hero hero) {
        if (super.doPickUp(hero)) {

            if (!Statistics.amuletObtainednever) {
                Statistics.amuletObtainednever = true;
                Statistics.amuletObtained = true;
                GLog.w("这是假的护身符！！！古神骗了你！！！");
                Music.INSTANCE.play(Assets.NEVERGIVE, true);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public String info() {
        String desc = "";
        if (!Statistics.amuletObtainednever) {
            desc += "Yendor护身符是来自未知领域中最强大的著名神器。据说它能够实现持有者的一切愿望，只要持有者具备足够的力量来\"说服\"它去做。";
        } else {
            desc += "很明显，Yog和RickRoll联手骗了你！这不是真正的护身符，而你的旅程才刚刚开始！_去找小恶魔吧，他会告诉你后续行动！！！_";
        }
        return desc;
    }
}
