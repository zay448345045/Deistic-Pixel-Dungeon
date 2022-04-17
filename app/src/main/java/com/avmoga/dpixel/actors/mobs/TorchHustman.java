package com.avmoga.dpixel.actors.mobs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Fire;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.items.Egg;
import com.avmoga.dpixel.levels.features.Chasm;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.TorchHustmanSprites;
import com.watabou.utils.Random;

public class TorchHustman extends Mob {
    private static final String COMBO = "combo";
    private String[] attackCurse = {"烧，快烧起来！", "扭曲的世界",
            "滚开！", "冒险家，肮脏的冒险家！",
            "去死，你这肮脏的冒险者！"};
    private int combo = 0;
    private String[] deathCurse = {"快停下...", "你这怪物", "神啊...帮帮我吧...",
            "四周好冷", "诅咒你这怪物", "相信我的同伴今晚会为我复仇"};

    public TorchHustman() {
        name = Messages.get(this, "name");
        this.spriteClass = TorchHustmanSprites.class;
        HP = HT = 75+(adj(0)*Random.NormalIntRange(4, 7));
        defenseSkill =8;
        this.defenseSkill = 7;
        this.EXP = 10;
        this.state = this.SLEEPING;
        this.loot = new Egg();
        this.lootChance = 0.05f;
    }

    @Override
    public String description() {
        return Messages.get(this, "desc");
    }

    public int attackSkill(Char target) {
        return 16;
    }

    public int damageRoll() {
        return Random.NormalIntRange(11, 12);
    }

    public int attackProc(Char enemy, int damage) {
        if (Random.Int(0, 10) > 7) {
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

