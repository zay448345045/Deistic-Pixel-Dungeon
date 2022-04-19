package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ResultDescriptions;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.particles.ShadowParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.plants.Earthroot;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.avmoga.dpixel.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 27/08/2014.
 */
public class ChaliceOfBlood extends Artifact {

	private static final String TXT_CHALICE = Messages.get(ChaliceOfBlood.class, "name");
	private static final String TXT_YES = Messages.get(ChaliceOfBlood.class, "yes");
	private static final String TXT_NO = Messages.get(ChaliceOfBlood.class, "no");
	private static final String TXT_PRICK = Messages.get(ChaliceOfBlood.class, "prick_warn");

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.ARTIFACT_CHALICE1;

		level = 0;
		levelCap = 10;
	}

	public static final String AC_PRICK = Messages.get(ChaliceOfBlood.class, "ac_prick");

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_PRICK);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_PRICK)) {

			int damage = 3 * (level * level);

			if (damage > hero.HP * 0.75) {

				GameScene.show(new WndOptions(TXT_CHALICE, TXT_PRICK, TXT_YES,
						TXT_NO) {
					@Override
					protected void onSelect(int index) {
						if (index == 0)
							prick(Dungeon.hero);
					};
				});

			} else {
				prick(hero);
			}
		}
	}

	private void prick(Hero hero) {
		int damage = 3 * (level * level);

		Earthroot.Armor armor = hero.buff(Earthroot.Armor.class);
		if (armor != null) {
			damage = armor.absorb(damage);
		}

		damage -= Random.IntRange(0, hero.dr());

		hero.sprite.operate(hero.pos);
		hero.busy();
		hero.spend(3f);
		if (damage <= 0) {
			GLog.i(Messages.get(this, "onprick2"));
		} else if (damage < 25) {
			GLog.w(Messages.get(this, "onprick3"));
			Sample.INSTANCE.play(Assets.SND_CURSED);
			hero.sprite.emitter().burst(ShadowParticle.CURSE, 6);
		} else if (damage < 100) {
			GLog.w(Messages.get(this, "onprick"));
			Sample.INSTANCE.play(Assets.SND_CURSED);
			hero.sprite.emitter().burst(ShadowParticle.CURSE, 12);
		} else {
			GLog.w(Messages.get(this, "onprick4"));
			Sample.INSTANCE.play(Assets.SND_CURSED);
			hero.sprite.emitter().burst(ShadowParticle.CURSE, 18);
		}

		if (damage > 0)
			hero.damage(damage, this);

		if (!hero.isAlive()) {
			Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
			GLog.n(Messages.get(this, "ondeath"));
		} else {
			upgrade();
		}
	}

	@Override
	public Item upgrade() {
		if (level >= 6)
			image = ItemSpriteSheet.ARTIFACT_CHALICE3;
		else if (level >= 2)
			image = ItemSpriteSheet.ARTIFACT_CHALICE2;
		return super.upgrade();
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new chaliceRegen();
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");
		if (level < levelCap)
			desc += Messages.get(this, "desc_5");
		else
			desc += Messages.get(this, "desc_4");

		if (isEquipped(Dungeon.hero)) {
			desc += "\n\n";
			if (cursed)
				desc += Messages.get(this, "desc_cursed");
			else if (level == 0)
				desc += Messages.get(this, "desc_1");
			else if (level < levelCap)
				desc += Messages.get(this, "desc_2");
			else
				desc += Messages.get(this, "desc_3");
		}

		return desc;
	}

	public class chaliceRegen extends ArtifactBuff {

	}

}
