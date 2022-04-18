package com.avmoga.dpixel.actors.mobs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Fire;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.items.Egg;
import com.avmoga.dpixel.items.potions.Potion;
import com.avmoga.dpixel.items.potions.PotionOfHealing;
import com.avmoga.dpixel.levels.features.Chasm;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.TorchHustmanSprites;
import com.watabou.utils.Random;

public class TorchHustman extends Mob {
    private static final String COMBO = "combo";
    private String[] attackCurse = {"烧，快烧起来！", "为ZOT大人清除杂碎",
            "扬了你的骨灰！为ZOT大人上贡！"};
    private int combo = 0;
    private String[] deathCurse = {"ZOT是唯一真神","没有人能打败ZOT","停下，你不能再前进了！"};

    public TorchHustman() {
        name = Messages.get(this, "name");
        this.spriteClass = TorchHustmanSprites.class;
        HP = HT = 75+(adj(0)*Random.NormalIntRange(24, 36));
        defenseSkill =8;
        this.defenseSkill = 7;
        this.EXP = 10;
        this.state = this.SLEEPING;
        this.loot = new PotionOfHealing();
        this.lootChance = 0.01f;
    }

    @Override
    public String description() {
        return Messages.get(this, "desc");
    }

    public int attackSkill(Char target) {
        return 16;
    }

    public int damageRoll() {
        return Random.NormalIntRange(11, 12)*(adj(0)*Random.NormalIntRange(1, 2));
    }

    public int attackProc(Char enemy, int damage) {
        if (1==1) {
            this.sprite.showStatus(0x00ff00, this.attackCurse[Random.Int(this.attackCurse.length)], new Object[0]);
        }
        int damage2 = TorchHustman.super.attackProc(enemy, this.combo + damage);
        this.combo++;
        if (Dungeon.level.flamable[enemy.pos]) {
            GameScene.add(Blob.seed(enemy.pos, 4, Fire.class));
        }
        if (enemy.buff(Burning.class) == null) {
            Buff.affect(enemy, Burning.class).reignite(enemy);
        }
        if (this.combo > 5) {
            this.combo = 1;
        }
        return damage2;
    }

    public void die(Object cause) {
        TorchHustman.super.die(cause);
        if (cause != Chasm.class) {
            this.sprite.showStatus(0xff000, this.deathCurse[Random.Int(this.deathCurse.length)], new Object[0]);

        }
    }}

