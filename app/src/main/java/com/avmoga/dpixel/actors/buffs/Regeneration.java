package com.avmoga.dpixel.actors.buffs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.items.artifacts.ChaliceOfBlood;

public class Regeneration extends Buff {

    public static final float REGENERATION_DELAY = 10;

    @Override
    public boolean act() {
        if (target.isAlive()) {

            if (target.HP < target.HT && !((Hero) target).isStarving()) {
                target.HP += 1;
            }

            ChaliceOfBlood.chaliceRegen regenBuff = Dungeon.hero
                    .buff(ChaliceOfBlood.chaliceRegen.class);

            if (regenBuff != null)
                if (regenBuff.isCursed())
                    spend(REGENERATION_DELAY * 1.5f);
                else
                    spend(Math
                            .max(REGENERATION_DELAY - regenBuff.level(), 0.5f));
            else
                spend(REGENERATION_DELAY);

        } else {

            diactivate();

        }

        return true;
    }
}
