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
package com.avmoga.dpixel.items.food;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.Statistics;
import com.avmoga.dpixel.actors.buffs.Hunger;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.SpellSprite;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.scrolls.ScrollOfRecharging;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Food extends Item {

	public int hornValue = 3;

	private static final float TIME_TO_EAT = 3f;

	public static final String AC_EAT = Messages.get(Food.class, "ac_eat");

	public float energy = Hunger.HUNGRY;
	public String message = Messages.get(this, "eat_msg");

	{
		stackable = true;
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.RATION;

		bones = true;

		defaultAction = AC_EAT;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_EAT);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EAT)) {

			detach(hero.belongings.backpack);

			hero.buff(Hunger.class).satisfy(energy);
			GLog.i(message);
            int healEnergy = Math.max(7, Math.round(energy/40));
			switch (hero.heroClass) {
			case WARRIOR:
				if (hero.HP < hero.HT) {
					hero.HP = Math.min(hero.HP + Random.Int(3, healEnergy), hero.HT);
					hero.sprite.emitter()
							.burst(Speck.factory(Speck.HEALING), 1);
				}
				break;
			case MAGE:
				hero.belongings.charge(false);
				ScrollOfRecharging.charge(hero);
				if (hero.HP < hero.HT) {
					hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.HT);
					hero.sprite.emitter()
							.burst(Speck.factory(Speck.HEALING), 1);
				}
				break;
			case ROGUE:
				if (hero.HP < hero.HT) {
					hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.HT);
					hero.sprite.emitter()
							.burst(Speck.factory(Speck.HEALING), 1);
				}
			case HUNTRESS:
				if (hero.HP < hero.HT) {
					hero.HP = Math.min((hero.HP + Random.Int(1, 3)), hero.HT);
					hero.sprite.emitter()
							.burst(Speck.factory(Speck.HEALING), 1);
				}
				break;
			}

			hero.sprite.operate(hero.pos);
			hero.busy();
			SpellSprite.show(hero, SpellSprite.FOOD);
			Sample.INSTANCE.play(Assets.SND_EAT);

			hero.spend(TIME_TO_EAT);

			Statistics.foodEaten++;
			Badges.validateFoodEaten();

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public String info() {
		return Messages.get(this, "desc");
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
	public int price() {
		return 10 * quantity;
	}
}
