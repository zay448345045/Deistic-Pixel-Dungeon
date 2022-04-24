package com.avmoga.dpixel.actors.buffs;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.ui.BuffIndicator;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;

public class Unkonwn extends Buff implements Hero.Doom {

    protected float left;

    private static final String LEFT = "left";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LEFT, left);

    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        left = bundle.getFloat(LEFT);
    }

    public void set(float duration) {
        this.left = duration;
    }

    ;

    @Override
    public int icon() {
        return BuffIndicator.BOMB;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {

            target.damage((int) (left / 2) + 1, this);
            spend(TICK);

            if ((left -= TICK) <= 0) {
                detach();
            }

        } else {

            detach();

        }

        return true;
    }


    @Override
    public void onDeath() {
        GLog.n("你被炸的渣都不剩了……");
    }
}