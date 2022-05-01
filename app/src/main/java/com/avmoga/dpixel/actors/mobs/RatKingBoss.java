package com.avmoga.dpixel.actors.mobs;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.ToxicGas;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.items.ActiveMrDestructo2;
import com.avmoga.dpixel.items.Ankh;
import com.avmoga.dpixel.items.Generator;
import com.avmoga.dpixel.items.Gold;
import com.avmoga.dpixel.items.potions.PotionOfExperience;
import com.avmoga.dpixel.items.potions.PotionOfHealing;
import com.avmoga.dpixel.items.scrolls.ScrollOfMagicalInfusion;
import com.avmoga.dpixel.items.scrolls.ScrollOfPsionicBlast;
import com.avmoga.dpixel.items.scrolls.ScrollOfRemoveCurse;
import com.avmoga.dpixel.items.weapon.enchantments.Death;
import com.avmoga.dpixel.items.weapon.melee.RoyalSpork;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.RatKingSprite;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.HashSet;

public class RatKingBoss extends Mob {

    private static final int JUMP_DELAY = 5;

    {
        name = Messages.get(this, "name");
        spriteClass = RatKingSprite.class;
        baseSpeed = 2f;

        HP = HT = 800;
        EXP = 20;
        defenseSkill = 20;
    }

    private int timeToJump = JUMP_DELAY;


    @Override
    public int damageRoll() {
        return Random.NormalIntRange(30, 80);
    }

    @Override
    public int attackSkill(Char target) {
        return 35;
    }

    @Override
    public int dr() {
        return 10;
    }

    @Override
    public void move(int step) {
        super.move(step);

        if (Dungeon.level.map[step] == Terrain.PEDESTAL && HP < HT) {

            HP += Random.Int(5, HT - HP);
            sprite.emitter().burst(ElmoParticle.FACTORY, 5);

            if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
                GLog.n(Messages.get(RatKingBoss.class, "heal"));
            }
        }
    }



    @Override
    public void die(Object cause) {

        for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
            if ( mob instanceof GreyRat||mob instanceof HermitCrab||mob instanceof Shell) {
                mob.die(cause);
            }
        }

        GameScene.bossSlain();

        Dungeon.level.drop(new Gold(Random.Int(1000, 3000)), pos).sprite.drop();

        if (Random.Float() < 0.6f) {
            Dungeon.level.drop(new RoyalSpork(), pos).sprite.drop();
        }

        if (Random.Float() < 0.2f) {
            Dungeon.level.drop(new ActiveMrDestructo2(), pos).sprite.drop();
        }

        Dungeon.level.drop(new Ankh(), pos).sprite.drop();
        Dungeon.level.drop(new ScrollOfRemoveCurse(), pos).sprite.drop();

        Dungeon.level.drop(new PotionOfHealing(), pos).sprite.drop();
        Dungeon.level.drop(new PotionOfHealing(), pos).sprite.drop();

        Dungeon.level.drop(new PotionOfExperience(), pos).sprite.drop();
        Dungeon.level.drop(new PotionOfExperience(), pos).sprite.drop();

        Dungeon.level.drop(new ScrollOfMagicalInfusion(), pos).sprite.drop();

        Dungeon.level.drop(Generator.random(Generator.Category.ARTIFACT), pos).sprite.drop();

        Dungeon.banditRATkilled=true;


        super.die(cause);
        Dungeon.sporkAvail = true;
        yell(Messages.get(RatKingBoss.class, "die"));

    }

    @Override
    protected boolean getCloser(int target) {
        if (Level.fieldOfView[target]) {
            return super.getCloser(target);
        } else {
            return super.getCloser(target);
        }
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
    }

    @Override
    protected boolean doAttack(Char enemy) {
        timeToJump--;
        if (timeToJump <= 0 && Level.adjacent(pos, enemy.pos)) {
            jump();
            return true;
        } else {
            return super.doAttack(enemy);
        }
    }

    private void jump() {
        timeToJump = JUMP_DELAY;

        int newPos;
        do {
            newPos = Random.Int(Level.getLength());
        } while (!Level.fieldOfView[newPos] || !Level.passable[newPos]
                || Level.adjacent(newPos, enemy.pos));

        sprite.move(pos, newPos);
        move(newPos);

        if (Dungeon.visible[newPos]) {
            CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
            Sample.INSTANCE.play(Assets.SND_PUFF);
        }

        spend(1 / speed());
    }

    @Override
    public void notice() {
        super.notice();
        yell(Messages.get(RatKingBoss.class, "notice", Dungeon.hero.givenName()));
    }

    @Override
    public String description() {
        return Messages.get(this, "desc");
    }

    private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
    static {
        RESISTANCES.add(ToxicGas.class);
        RESISTANCES.add(Poison.class);
        RESISTANCES.add(Death.class);
        RESISTANCES.add(ScrollOfPsionicBlast.class);
    }

    @Override
    public HashSet<Class<?>> resistances() {
        return RESISTANCES;
    }
}

