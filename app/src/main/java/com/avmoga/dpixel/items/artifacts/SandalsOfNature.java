package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Roots;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.EarthParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.plants.Earthroot;
import com.avmoga.dpixel.plants.Plant;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndBag;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by debenhame on 08/09/2014.
 */
public class SandalsOfNature extends Artifact {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.ARTIFACT_SANDALS;

		level = 0;
		levelCap = 3;

		charge = 0;

		defaultAction = AC_ROOT;
	}

	public static final String[] NAMES = {Messages.get(SandalsOfNature.class, "name"),
			Messages.get(SandalsOfNature.class, "name_1"), Messages.get(SandalsOfNature.class, "name_2"), Messages.get(SandalsOfNature.class, "name_3")};
	public static final String AC_FEED = Messages.get(SandalsOfNature.class, "ac_feed");
	public static final String AC_ROOT = Messages.get(SandalsOfNature.class, "ac_root");
	public static final String AC_GROW = Messages.get(SandalsOfNature.class, "ac_grow");

	protected String inventoryTitle = Messages.get(SandalsOfNature.class, "prompt");

	protected WndBag.Mode mode = WndBag.Mode.SEED;

	public ArrayList<String> seeds = new ArrayList<String>();

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < 3 && !cursed)
			actions.add(AC_FEED);
		if (isEquipped(hero) && charge > 0)
			actions.add(AC_ROOT);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_FEED)) {
			GameScene.selectItem(itemSelector, mode, inventoryTitle);
		} else if (action.equals(AC_ROOT) && level > 0) {

			if (!isEquipped(hero))
				GLog.i(Messages.get(SandalsOfNature.class, "equip"));
			else if (charge <= 0)
				GLog.i(Messages.get(SandalsOfNature.class, "no_charge"));
			else {
				Buff.prolong(hero, Roots.class, 5);
				Buff.affect(hero, Earthroot.Armor.class).level(charge);
				CellEmitter.bottom(hero.pos).start(EarthParticle.FACTORY,
						0.05f, 8);
				Camera.main.shake(1, 0.4f);
				charge = 0;
				updateQuickslot();
			}
		}
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Naturalism();
	}

	@Override
	public String desc() {
		String desc = "";
		if (level == 0)
			desc += Messages.get(SandalsOfNature.class, "desc_1");
		else if (level == 1)
			desc += Messages.get(SandalsOfNature.class, "desc_2");
		else if (level == 2)
			desc += Messages.get(SandalsOfNature.class, "desc_3");
		else
			desc += Messages.get(SandalsOfNature.class, "desc_4");

		if (isEquipped(Dungeon.hero)) {
			desc += "\n\n";
			if (level == 0) {
				if (!cursed)
					desc += Messages.get(SandalsOfNature.class, "desc_5");
				else
					desc += Messages.get(SandalsOfNature.class, "desc_6");
			} else if (level == 1)
				desc += Messages.get(SandalsOfNature.class, "desc_7");
			else if (level == 2)
				desc += Messages.get(SandalsOfNature.class, "desc_8");
			else
				desc += Messages.get(SandalsOfNature.class, "desc_9");

			if (!cursed)
				desc += Messages.get(SandalsOfNature.class, "desc_hint");
			else
				desc += Messages.get(SandalsOfNature.class, "desc_cursed");

			if (level > 0)
				desc += "\n\n" + Messages.get(SandalsOfNature.class, "desc_ability");
		}

		if (!seeds.isEmpty()) {
			desc += "\n\n" + Messages.get(SandalsOfNature.class, "desc_10", seeds.size());
		}

		return desc;
	}

	@Override
	public Item upgrade() {
		if (level < 0)
			image = ItemSpriteSheet.ARTIFACT_SANDALS;
		else if (level == 0)
			image = ItemSpriteSheet.ARTIFACT_SHOES;
		else if (level == 1)
			image = ItemSpriteSheet.ARTIFACT_BOOTS;
		else if (level >= 2)
			image = ItemSpriteSheet.ARTIFACT_GREAVES;
		name = NAMES[level + 1];
		return super.upgrade();
	}

	private static final String SEEDS = "seeds";
	private static final String NAME = "name";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(NAME, name);
		bundle.put(SEEDS, seeds.toArray(new String[seeds.size()]));
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		name = bundle.getString(NAME);
		if (bundle.contains(SEEDS))
			Collections.addAll(seeds, bundle.getStringArray(SEEDS));
	}

	public class Naturalism extends ArtifactBuff {
		public void charge() {
			if (charge < target.HT) {
				// gain 1+(1*level)% of the difference between current charge
				// and max HP.
				charge += (Math.round((target.HT - charge)
						* (.01 + level * 0.01)));
				updateQuickslot();
			}
		}
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Plant.Seed) {
				if (seeds.contains(item.name())) {
					GLog.w(Messages.get(SandalsOfNature.class, "already_fed"));
				} else {
					seeds.add(item.name());

					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					Sample.INSTANCE.play(Assets.SND_PLANT);
					hero.busy();
					hero.spend(2f);
					if (seeds.size() >= 5 + (level * 2)) {
						seeds.clear();
						upgrade();
						if (level >= 1 && level <= 3) {
							GLog.p(Messages.get(SandalsOfNature.class, "levelup"));
						}

					} else {
						GLog.i(Messages.get(SandalsOfNature.class, "absorb_seed"));
					}
					item.detach(hero.belongings.backpack);
				}
			}
		}
	};

}
