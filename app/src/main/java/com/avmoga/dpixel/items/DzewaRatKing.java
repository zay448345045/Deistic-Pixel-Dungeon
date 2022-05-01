package com.avmoga.dpixel.items;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.items.artifacts.DriedRose;
import com.avmoga.dpixel.items.artifacts.TimekeepersHourglass;
import com.avmoga.dpixel.scenes.InterlevelScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class DzewaRatKing extends Item {

    private static final String TXT_PREVENTING = "强大的魔力流阻止你使用诡异的巨鼠头骨!";
    private static final String TXT_PREVENTING2 = "你需要先击败老鼠国王才能离开这里……";


    public static final float TIME_TO_USE = 1;

    public static final String AC_PORT = "传送";

    private int specialLevel = 666;
    private int returnDepth = -1;
    private int returnPos;

    {
        name = "诡异的巨鼠头骨";
        image = ItemSpriteSheet.SKULL;

        stackable = false;
        unique = true;
    }

    private static final String DEPTH = "depth";
    private static final String POS = "pos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DEPTH, returnDepth);
        if (returnDepth != -1) {
            bundle.put(POS, returnPos);
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        returnDepth = bundle.getInt(DEPTH);
        returnPos = bundle.getInt(POS);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_PORT);

        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        if (action == AC_PORT) {

            if (Dungeon.bossLevel()) {
                hero.spend(TIME_TO_USE);
                GLog.w(TXT_PREVENTING);
                return;
            }

            if (Dungeon.depth>25 && Dungeon.depth!=specialLevel) {
                hero.spend(TIME_TO_USE);
                GLog.w(TXT_PREVENTING);
                return;
            }

            if (Dungeon.depth==specialLevel && !Dungeon.banditRATkilled && !Dungeon.level.reset) {
                hero.spend(TIME_TO_USE);
                GLog.w(TXT_PREVENTING2);
                return;
            }

            if (Dungeon.depth==1) {
                hero.spend(TIME_TO_USE);
                GLog.w(TXT_PREVENTING);
                return;
            }


        }

        if (action == AC_PORT) {

            Buff buff = Dungeon.hero
                    .buff(TimekeepersHourglass.timeFreeze.class);
            if (buff != null)
                buff.detach();

            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
                if (mob instanceof DriedRose.GhostHero)
                    mob.destroy();
            if (Dungeon.depth<25 && !Dungeon.bossLevel()){

                returnDepth = Dungeon.depth;
                returnPos = hero.pos;
                InterlevelScene.mode = InterlevelScene.Mode.RATKING;
            } else {

                this.doDrop(hero);

                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
            }
            InterlevelScene.returnDepth = returnDepth;
            InterlevelScene.returnPos = returnPos;
            Game.switchScene(InterlevelScene.class);

        } else {

            super.execute(hero, action);

        }
    }

    public void reset() {
        returnDepth = -1;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }


    private static final ItemSprite.Glowing BLACK = new ItemSprite.Glowing(0xbb0000);

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

    @Override
    public String info() {
        return "此物品会使你传送到鼠王的英灵殿。";
    }
}

