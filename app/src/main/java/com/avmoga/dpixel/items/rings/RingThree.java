package com.avmoga.dpixel.items.rings;

import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroClass;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.ItemStatusHandler;
import com.avmoga.dpixel.items.KindofMisc;
import com.avmoga.dpixel.items.weapon.melee.BattleAxe;
import com.avmoga.dpixel.items.weapon.melee.Dagger;
import com.avmoga.dpixel.items.weapon.melee.Glaive;
import com.avmoga.dpixel.items.weapon.melee.Knuckles;
import com.avmoga.dpixel.items.weapon.melee.Longsword;
import com.avmoga.dpixel.items.weapon.melee.Mace;
import com.avmoga.dpixel.items.weapon.melee.Quarterstaff;
import com.avmoga.dpixel.items.weapon.melee.RoyalSpork;
import com.avmoga.dpixel.items.weapon.melee.ShortSword;
import com.avmoga.dpixel.items.weapon.melee.Spear;
import com.avmoga.dpixel.items.weapon.melee.Spork;
import com.avmoga.dpixel.items.weapon.melee.Sword;
import com.avmoga.dpixel.items.weapon.melee.WarHammer;
import com.avmoga.dpixel.items.weapon.missiles.Boomerang;
import com.avmoga.dpixel.items.weapon.missiles.CurareDart;
import com.avmoga.dpixel.items.weapon.missiles.CurareShuriken;
import com.avmoga.dpixel.items.weapon.missiles.Dart;
import com.avmoga.dpixel.items.weapon.missiles.ForestDart;
import com.avmoga.dpixel.items.weapon.missiles.IncendiaryDart;
import com.avmoga.dpixel.items.weapon.missiles.IncendiaryShuriken;
import com.avmoga.dpixel.items.weapon.missiles.Javelin;
import com.avmoga.dpixel.items.weapon.missiles.RiceBall;
import com.avmoga.dpixel.items.weapon.missiles.Shuriken;
import com.avmoga.dpixel.items.weapon.missiles.Tamahawk;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class RingThree extends KindofMisc {

    private static final int TICKS_TO_KNOW = 200;

    private static final float TIME_TO_EQUIP = 1f;

    public static HashSet<Class<? extends RingThree>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends RingThree>> getUnknown() {
        return handler.unknown();
    }

    private static final String TXT_IDENTIFY = Messages.get(Ring.class, "identify");

    protected Buff buff;

    //Wepaon
    private static final Class<?>[] rings = {
            BattleAxe.class,
            Dagger.class,
            Glaive.class,
            Knuckles.class,
            Longsword.class,
            Mace.class,
            Quarterstaff.class,
            RoyalSpork.class,
            ShortSword.class,
            Spear.class,
            Spork.class,
            Sword.class,
            WarHammer.class,
            Boomerang.class,
            CurareDart.class,
            CurareShuriken.class,
            Dart.class,
            ForestDart.class,
            IncendiaryDart.class,
            IncendiaryShuriken.class,
            Javelin.class,
            RiceBall.class,
            Shuriken.class,
            Tamahawk.class,
    };
    private static final String[] gems = {
            Messages.get(Ring.class, "diamond"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "diamond"),
            Messages.get(Ring.class, "opal"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "diamond"),
            Messages.get(Ring.class, "opal"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "diamond"),
            Messages.get(Ring.class, "opal"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
    };
    private static final Integer[] images = {
            ItemSpriteSheet.RING_DIAMOND,
            ItemSpriteSheet.RING_GARNET,
            ItemSpriteSheet.RING_RUBY, ItemSpriteSheet.RING_AMETHYST,
            ItemSpriteSheet.RING_TOPAZ, ItemSpriteSheet.RING_ONYX,
            ItemSpriteSheet.RING_TOURMALINE, ItemSpriteSheet.RING_EMERALD,
            ItemSpriteSheet.RING_SAPPHIRE, ItemSpriteSheet.RING_QUARTZ,
            ItemSpriteSheet.RING_AGATE, ItemSpriteSheet.RING_DIAMOND,
            ItemSpriteSheet.RING_OPAL, ItemSpriteSheet.RING_GARNET,
            ItemSpriteSheet.RING_RUBY, ItemSpriteSheet.RING_AMETHYST,
            ItemSpriteSheet.RING_TOPAZ, ItemSpriteSheet.RING_ONYX,
            ItemSpriteSheet.RING_TOURMALINE, ItemSpriteSheet.RING_EMERALD,
            ItemSpriteSheet.RING_SAPPHIRE,ItemSpriteSheet.RING_SAPPHIRE,ItemSpriteSheet.RING_EMERALD,
            ItemSpriteSheet.RING_SAPPHIRE
    };

    private static ItemStatusHandler<RingThree> handler;

    private String gem;

    private int ticksToKnow = TICKS_TO_KNOW;

    @SuppressWarnings("unchecked")
    public static void initGems() {
        handler = new ItemStatusHandler<RingThree>((Class<? extends RingThree>[]) rings,
                gems, images);
    }

    public static void save(Bundle bundle) {
        handler.save(bundle);
    }

    @SuppressWarnings("unchecked")
    public static void restore(Bundle bundle) {
        handler = new ItemStatusHandler<RingThree>((Class<? extends RingThree>[]) rings,
                gems, images, bundle);
    }

    public RingThree() {
        super();
        syncVisuals();
    }

    @Override
    public void syncVisuals() {
        image = handler.image(this);
        gem = handler.label(this);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
        return actions;
    }

    @Override
    public boolean doEquip(Hero hero) {

        if (hero.belongings.misc1 != null && hero.belongings.misc2 != null) {

            GLog.w("you can only wear 2 misc items at a time");
            return false;

        } else {

            if (hero.belongings.misc1 == null) {
                hero.belongings.misc1 = this;
            } else {
                hero.belongings.misc2 = this;
            }

            detach(hero.belongings.backpack);

            activate(hero);

            cursedKnown = true;
            if (cursed) {
                equipCursed(hero);
                GLog.n("your " + this
                        + " tightens around your finger painfully");
            }

            hero.spendAndNext(TIME_TO_EQUIP);
            return true;

        }

    }

    @Override
    public void activate(Char ch) {
        buff = buff();
        buff.attachTo(ch);
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        if (super.doUnequip(hero, collect, single)) {

            if (hero.belongings.misc1 == this) {
                hero.belongings.misc1 = null;
            } else {
                hero.belongings.misc2 = null;
            }

            hero.remove(buff);
            buff = null;

            return true;

        } else {

            return false;

        }
    }

    @Override
    public boolean isEquipped(Hero hero) {
        return hero.belongings.misc1 == this || hero.belongings.misc2 == this;
    }

    @Override
    public Item upgrade() {

        super.upgrade();

        if (buff != null) {

            Char owner = buff.target;
            buff.detach();
            if ((buff = buff()) != null) {
                buff.attachTo(owner);
            }
        }

        return this;
    }

    public boolean isKnown() {
        return handler.isKnown(this);
    }

    protected void setKnown() {
        if (!isKnown()) {
            handler.know(this);
        }

        Badges.validateAllRingsIdentified();
    }

    @Override
    public String name() {
        return isKnown() ? super.name() : Messages.get(this, "unknown_name", gem);
    }

    @Override
    public String desc() {
        return Messages.get(this, "unknown_desc", gem);
    }

    @Override
    public String info() {
        if (isEquipped(Dungeon.hero)) {

            return desc()
                    + "\n\n" + Messages.get(this, "on_finger", name())
                    + (cursed ? Messages.get(this, "cursed_worn") : "")
                    + (reinforced ? Messages.get(this, "reinforced") : "");

        } else if (cursed && cursedKnown) {

            return desc()
                    + "\n\n" + Messages.get(this, "curse_known", name());

        } else {

            return desc() + (reinforced ? Messages.get(this, "reinforced") : "");

        }
    }

    @Override
    public boolean isIdentified() {
        return super.isIdentified() && isKnown();
    }

    @Override
    public Item identify() {
        setKnown();
        return super.identify();
    }

    @Override
    public Item random() {
        if (Random.Float() < 0.3f) {
            level = -Random.Int(1, 3);
            cursed = true;
        } else
            level = Random.Int(1, 2);
        return this;
    }

    public static boolean allKnown() {
        return handler.known().size() == rings.length - 2;
    }

    @Override
    public int price() {
        int price = 75;
        if (cursed && cursedKnown) {
            price /= 2;
        }
        if (levelKnown) {
            if (level > 0) {
                price *= (level + 1);
            } else if (level < 0) {
                price /= (1 - level);
            }
        }
        if (price < 1) {
            price = 1;
        }
        return price;
    }

    protected Ring.RingBuff buff() {
        return null;
    }

    private static final String UNFAMILIRIARITY = "unfamiliarity";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(UNFAMILIRIARITY, ticksToKnow);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if ((ticksToKnow = bundle.getInt(UNFAMILIRIARITY)) == 0) {
            ticksToKnow = TICKS_TO_KNOW;
        }
    }

    public class RingBuff extends Buff {

        private final String TXT_KNOWN = Messages.get(Ring.class, "known");

        public int level;

        public RingBuff() {
            level = RingThree.this.level;
        }

        @Override
        public boolean attachTo(Char target) {

            if (target instanceof Hero
                    && ((Hero) target).heroClass == HeroClass.ROGUE
                    && !isKnown()) {
                setKnown();
                GLog.i(TXT_KNOWN, name());
                Badges.validateItemLevelAquired(RingThree.this);
            }

            return super.attachTo(target);
        }

        @Override
        public boolean act() {

            if (!isIdentified() && --ticksToKnow <= 0) {
                String gemName = name();
                identify();
                GLog.w(TXT_IDENTIFY, gemName, RingThree.this.toString());
                Badges.validateItemLevelAquired(RingThree.this);
            }

            spend(TICK);

            return true;
        }
    }
}

