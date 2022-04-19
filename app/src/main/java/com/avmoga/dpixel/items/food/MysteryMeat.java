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

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Hunger;
import com.avmoga.dpixel.actors.buffs.Paralysis;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.actors.buffs.Roots;
import com.avmoga.dpixel.actors.buffs.Slow;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Random;

public class MysteryMeat extends Food {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.MYSTERYMEAT;
		energy = Hunger.STARVING - Hunger.HUNGRY;
		message = Messages.get(this, "eat_msg");
		hornValue = 1;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(5)) {
				case 0:
					GLog.w(Messages.get(this, "hot"));
					Buff.affect(hero, Burning.class).reignite(hero);
					break;
				case 1:
					GLog.w(Messages.get(this, "legs"));
					Buff.prolong(hero, Roots.class, Paralysis.duration(hero));
					break;
				case 2:
					GLog.w(Messages.get(this, "not_well"));
					Buff.affect(hero, Poison.class).set(
							Poison.durationFactor(hero) * hero.HT / 5);
					break;
				case 3:
					GLog.w(Messages.get(this, "stuffed"));
					Buff.prolong(hero, Slow.class, Slow.duration(hero));
					break;
			}
		}
	}

	@Override
	public String info() {
		return Messages.get(this, "desc");
	}

	@Override
	public int price() {
		return 5 * quantity;
	};
}
