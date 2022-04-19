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
package com.avmoga.dpixel.items.scrolls;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Fire;
import com.avmoga.dpixel.actors.buffs.Invisibility;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.wands.WandOfBlink;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfTeleportation extends Scroll {

	public static final String TXT_TELEPORTED = Messages.get(ScrollOfTeleportation.class, "tele");

	public static final String TXT_NO_TELEPORT = Messages.get(ScrollOfTeleportation.class, "no_tele");

	public static final String TXT_DET = Messages.get(ScrollOfTeleportation.class, "deact");

	{
		name = Messages.get(this, "name");
		consumedValue = 10;
	}

	@Override
	protected void doRead() {

		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		teleportHero(curUser);
		setKnown();

		curUser.spendAndNext(TIME_TO_READ);
	}
	@Override
	public void detonate(Heap heap){
		int spawn = Dungeon.level.randomRespawnCell();
		while(!Level.flamable[spawn]){
			spawn = Dungeon.level.randomRespawnCell();
		}
		Blob.seed(spawn, 1, Fire.class);
	}
	@Override
	public void detonateIn(Hero hero){
		Item item = hero.belongings.randomUnequipped();
		int pos;
		int count = 10;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if(count-- <= 0){
				break;
			}
		} while (pos == -1);
		//Dungeon.level.drop(item, pos);
		item.detach(hero.belongings.backpack);
		GLog.w(TXT_DET);
	}

	public static void teleportHero(Hero hero) {

		int count = 10;
		int pos;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if (count-- <= 0) {
				break;
			}
		} while (pos == -1);

		if (pos == -1) {

			GLog.w(TXT_NO_TELEPORT);

		} else {

			WandOfBlink.appear(hero, pos);
			Dungeon.level.press(pos, hero);
			Dungeon.observe();

			GLog.i(TXT_TELEPORTED);

		}
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}


	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
