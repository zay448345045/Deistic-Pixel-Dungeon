package com.avmoga.dpixel.actors.mobs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Fire;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.BlastParticle;
import com.avmoga.dpixel.items.food.Food;
import com.avmoga.dpixel.items.food.Pasty;
import com.avmoga.dpixel.items.potions.PotionOfHealing;
import com.avmoga.dpixel.levels.features.Chasm;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.MolotovHuntsmanSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class MolotovHuntsman extends Mob {

    @Override
    public String description() {
        return Messages.get(this, "desc");
    }
    private static final String COMBO = "combo";
    private String[] attackCurse;
    private int combo;
    private String[] deathCurse;
    public float lifespan;
    public MolotovHuntsman() {
        this.spriteClass = MolotovHuntsmanSprite.class;
        HP = HT = 240+(adj(0)*Random.NormalIntRange(2, 5));
        this.defenseSkill = 10;
        name = Messages.get(this, "name");
        this.EXP = 15;
        this.state = this.SLEEPING;
        this.baseSpeed = 0.8F;
        this.loot = new PotionOfHealing();
        this.lootChance = 0.5F;
        this.deathCurse = new String[]{"ZOT大人!","你居然……让我解脱了……","拯救ZOT大人!!!"};
        this.attackCurse = new String[]{"我说，为什么要让我承担？", "扬了你的骨灰！", "烧死你","离开这里！"};
        this.combo = 0;
    }

    public int attackProc(Char var1, int var2) {
        byte var3 = 0;
        int var4;
        if (Random.Int(0, 10) > 7) {
            var4 = Random.Int(this.attackCurse.length);
            this.sprite.showStatus(0xff0000, this.attackCurse[var4], new Object[0]);
        }

        int var5 = super.attackProc(var1, var2);
        var4 = var1.pos;
        CellEmitter.center(var4).burst(BlastParticle.FACTORY, 30);
        GameScene.add(Blob.seed(var4, 2, Fire.class));
        int[] var7 = PathFinder.NEIGHBOURS9;
        int var6 = var7.length;

        for(var2 = var3; var2 < var6; ++var2) {
            int var8 = var7[var2];
            if (!Dungeon.level.solid[var4 + var8]) {
                GameScene.add(Blob.seed(var4 + var8, 2, Fire.class));
            }
        }

        ++this.combo;
        return var5;
    }

    public int attackSkill(Char var1) {
        return 56;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if(!this.isSilenced()){
            return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
        } else {
            return false;
        }
    }

    public int damageRoll() {
        return Random.NormalIntRange(7, 20)+(adj(0)+Random.NormalIntRange(1, 15));
    }

    public void die(Object var1) {
        super.die(var1);
        if (var1 != Chasm.class) {
            int var2 = Random.Int(this.deathCurse.length);
            this.sprite.showStatus(0x00ff00, this.deathCurse[var2], new Object[0]);
        }

    }

    protected boolean getCloser(int var1) {
        boolean var2 = false;
        this.combo = 0;
        if (this.state == this.HUNTING) {
            boolean var3 = var2;
            if (this.enemySeen) {
                var3 = var2;
                if (this.getFurther(var1)) {
                    var3 = true;
                }
            }

            return var3;
        } else {
            return super.getCloser(var1);
        }
    }

    public void restoreFromBundle(Bundle var1) {
        super.restoreFromBundle(var1);
        this.combo = var1.getInt("combo");
    }

    public void storeInBundle(Bundle var1) {
        super.storeInBundle(var1);
        var1.put("combo", this.combo);
    }
}

