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

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.buffs.BerryRegeneration;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Hunger;
import com.avmoga.dpixel.actors.buffs.MindVision;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Random;

public class Blackberry extends Food {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.SEED_BLACKBERRY;
		energy = (Hunger.STARVING - Hunger.HUNGRY)/10;
		message = Messages.get(this, "eat");
		hornValue = 1;
		bones = false;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(10)) {
			case 1:
				GLog.w("多汁美味！");
				Buff.affect(hero, MindVision.class, MindVision.DURATION);
				Dungeon.observe();

				if (Dungeon.level.mobs.size() > 0) {
					GLog.i("你可以感受到其他生物的存在！");
				} else {
					GLog.i("你能判定现在本层内就只有你一个人。");
				}
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT+hero.HT);
				GLog.w(Messages.get(this, "eat3"));
				break;
			case 0: case 2: case 3: case 4: case 5: 
			case 6: case 7: case 8: case 9: case 10:
				GLog.w("莓果将能量注入了你的体内！");
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT/2);
				break;
			}
		}
	}	
	
	@Override
	public String info() {
		return "一种能够在地牢内发现的莓果。它们通过吸取魔法露珠来获得能量并茁壮成长。";
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
	
	public Blackberry() {
		this(1);
	}

	public Blackberry(int value) {
		this.quantity = value;
	}
}
