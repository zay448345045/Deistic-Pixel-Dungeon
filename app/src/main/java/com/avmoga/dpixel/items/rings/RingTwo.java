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
import com.avmoga.dpixel.items.food.Blackberry;
import com.avmoga.dpixel.items.food.Blandfruit;
import com.avmoga.dpixel.items.food.BlueMilk;
import com.avmoga.dpixel.items.food.Blueberry;
import com.avmoga.dpixel.items.food.ChargrilledMeat;
import com.avmoga.dpixel.items.food.Cloudberry;
import com.avmoga.dpixel.items.food.DeathCap;
import com.avmoga.dpixel.items.food.Earthstar;
import com.avmoga.dpixel.items.food.Food;
import com.avmoga.dpixel.items.food.FrozenCarpaccio;
import com.avmoga.dpixel.items.food.FullMoonberry;
import com.avmoga.dpixel.items.food.GoldenJelly;
import com.avmoga.dpixel.items.food.GoldenNut;
import com.avmoga.dpixel.items.food.JackOLantern;
import com.avmoga.dpixel.items.food.Meat;
import com.avmoga.dpixel.items.food.Moonberry;
import com.avmoga.dpixel.items.food.MysteryMeat;
import com.avmoga.dpixel.items.food.Nut;
import com.avmoga.dpixel.items.food.OverpricedRation;
import com.avmoga.dpixel.items.food.Pasty;
import com.avmoga.dpixel.items.food.PixieParasol;
import com.avmoga.dpixel.items.food.PotionOfConstitution;
import com.avmoga.dpixel.items.food.ToastedNut;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class RingTwo extends KindofMisc {

    private static final int TICKS_TO_KNOW = 200;

    private static final float TIME_TO_EQUIP = 1f;

    public static HashSet<Class<? extends RingTwo>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends RingTwo>> getUnknown() {
        return handler.unknown();
    }

    private static final String TXT_IDENTIFY = Messages.get(Ring.class, "identify");

    protected Buff buff;

    //Food
    private static final Class<?>[] rings = {
            Blackberry.class,
            Blandfruit.class,
            Blueberry.class,
            BlueMilk.class,
            ChargrilledMeat.class,
            Cloudberry.class,
            DeathCap.class,
            Earthstar.class,
            Food.class,
            FrozenCarpaccio.class,
            FullMoonberry.class,
            GoldenJelly.class,
            GoldenNut.class,
            JackOLantern.class,
            Meat.class,
            Moonberry.class,
            MysteryMeat.class,
            Nut.class,
            OverpricedRation.class,
            Pasty.class,
            PixieParasol.class,
            PotionOfConstitution.class,
            ToastedNut.class
    };
    private static final String[] gems = {
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
            Messages.get(Ring.class, "diamond"),
            Messages.get(Ring.class, "opal"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
            Messages.get(Ring.class, "garnet"),
            Messages.get(Ring.class, "ruby"),
            Messages.get(Ring.class, "amethyst"),
    };
    private static final Integer[] images = {
            ItemSpriteSheet.RING_DIAMOND,
            ItemSpriteSheet.RING_OPAL, ItemSpriteSheet.RING_GARNET,
            ItemSpriteSheet.RING_RUBY, ItemSpriteSheet.RING_AMETHYST,
            ItemSpriteSheet.RING_TOPAZ, ItemSpriteSheet.RING_ONYX,
            ItemSpriteSheet.RING_TOURMALINE, ItemSpriteSheet.RING_EMERALD,
            ItemSpriteSheet.RING_SAPPHIRE, ItemSpriteSheet.RING_QUARTZ,
            ItemSpriteSheet.RING_AGATE, ItemSpriteSheet.RING_DIAMOND,
            ItemSpriteSheet.RING_OPAL, ItemSpriteSheet.RING_GARNET,
            ItemSpriteSheet.RING_RUBY, ItemSpriteSheet.RING_AMETHYST,
            ItemSpriteSheet.RING_TOPAZ, ItemSpriteSheet.RING_ONYX,
            ItemSpriteSheet.RING_TOURMALINE, ItemSpriteSheet.RING_EMERALD,
            ItemSpriteSheet.RING_SAPPHIRE,ItemSpriteSheet.RING_SAPPHIRE
    };

    private static ItemStatusHandler<RingTwo> handler;

    private String gem;

    private int ticksToKnow = TICKS_TO_KNOW;

    @SuppressWarnings("unchecked")
    public static void initGems() {
        handler = new ItemStatusHandler<RingTwo>((Class<? extends RingTwo>[]) rings,
                gems, images);
    }

    public static void save(Bundle bundle) {
        handler.save(bundle);
    }

    @SuppressWarnings("unchecked")
    public static void restore(Bundle bundle) {
        handler = new ItemStatusHandler<RingTwo>((Class<? extends RingTwo>[]) rings,
                gems, images, bundle);
    }

    public RingTwo() {
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
            level = RingTwo.this.level;
        }

        @Override
        public boolean attachTo(Char target) {

            if (target instanceof Hero
                    && ((Hero) target).heroClass == HeroClass.ROGUE
                    && !isKnown()) {
                setKnown();
                GLog.i(TXT_KNOWN, name());
                Badges.validateItemLevelAquired(RingTwo.this);
            }

            return super.attachTo(target);
        }

        @Override
        public boolean act() {

            if (!isIdentified() && --ticksToKnow <= 0) {
                String gemName = name();
                identify();
                GLog.w(TXT_IDENTIFY, gemName, RingTwo.this.toString());
                Badges.validateItemLevelAquired(RingTwo.this);
            }

            spend(TICK);

            return true;
        }
    }
}

