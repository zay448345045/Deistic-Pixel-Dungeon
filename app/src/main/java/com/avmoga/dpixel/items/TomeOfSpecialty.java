/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.avmoga.dpixel.items;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.buffs.Blindness;
import com.avmoga.dpixel.actors.buffs.Madness;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroSubRace;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.SpellSprite;
import com.avmoga.dpixel.items.rings.RingOfWealth;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.avmoga.dpixel.windows.WndChooseWay;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class TomeOfSpecialty extends Item {

	private static final String TXT_BLINDED = Messages.get(TomeOfMastery.class, "blinded");

	public static final float TIME_TO_READ = 10;

	public static final String AC_READ = Messages.get(TomeOfMastery.class, "ac_read");

	{
		stackable = false;
		name = "种族之书";
		image = ItemSpriteSheet.SPECIALTY;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_READ)) {

			if (hero.buff(Blindness.class) != null) {
				GLog.w(TXT_BLINDED);
				return;
			}

			curUser = hero;

			HeroSubRace way1 = null;
			HeroSubRace way2 = null;
			switch (hero.heroRace) {
			case HUMAN:
				way1 = HeroSubRace.DEMOLITIONIST;
				way2 = HeroSubRace.MERCENARY;
				break;
			case GNOLL:
				way1 = HeroSubRace.SHAMAN;
				way2 = HeroSubRace.BRUTE;
				break;
			case DWARF:
				way1 = HeroSubRace.WARLOCK;
				way2 = HeroSubRace.MONK;
				break;
			case WRAITH:
				way1 = HeroSubRace.RED;
				way2 = HeroSubRace.BLUE;
				break;
			}
			GameScene.show(new WndChooseWay(this, way1, way2));

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean doPickUp(Hero hero) {
		Badges.validateRace(hero.heroRace);
		return super.doPickUp(hero);
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public String info() {
		return "这本皮封典籍没多厚, 但是你隐约感觉到自己能从中学到不少东西.\n\n不过记住: 阅读这本典籍需要一些时间。";
	}

	public void choose(HeroSubRace way) {

		detach(curUser.belongings.backpack);

		curUser.subRace = way;
		
		curUser.sprite.operate(curUser.pos);
		Sample.INSTANCE.play(Assets.SND_MASTERY);

		SpellSprite.show(curUser, SpellSprite.MASTERY);
		curUser.sprite.emitter().burst(Speck.factory(Speck.MASTERY), 12);
		GLog.w("你选择了专精了%s的冒险！",
				Utils.capitalize(way.title()));
		
		
		Dungeon.hero.sprite.texture(Dungeon.hero.heroClass.spritesheet());
		
		curUser.spend(TomeOfSpecialty.TIME_TO_READ);
		curUser.busy();

		if (way == HeroSubRace.RED) {
			RingOfWealth ring = new RingOfWealth(); 
			ring.degrade(); ring.degrade(); ring.degrade(); ring.collect();
			//If you want to use this, you gotta work for it.
		} else if (way == HeroSubRace.BRUTE) {
			Dungeon.hero.buff(Madness.class);
		}
	}
}
