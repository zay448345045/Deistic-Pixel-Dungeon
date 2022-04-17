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
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.buffs.Awareness;
import com.avmoga.dpixel.actors.buffs.BerryRegeneration;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Hunger;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Blueberry extends Food {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.SEED_BLUEBERRY;
		energy = (Hunger.STARVING - Hunger.HUNGRY)/10;
		message = Messages.get(Blackberry.class, "eat");
		hornValue = 1;
		bones = false;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			if (Random.Float() < 0.75f) {

				int length = Dungeon.level.getLength();
				int[] map = Dungeon.level.map;
				boolean[] mapped = Dungeon.level.mapped;
				boolean[] discoverable = Level.discoverable;

				boolean noticed = false;

				for (int i = 0; i < length; i++) {

					int terr = map[i];

					if (discoverable[i]) {

						mapped[i] = true;
						if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

							Level.set(i, Terrain.discover(terr));
							GameScene.updateMap(i);

							if (Dungeon.visible[i]) {
								GameScene.discoverTile(i, terr);
								discover(i);

								noticed = true;
							}
						}
					}
				}
				//GameScene.updateFog();

				if (noticed) {
					Sample.INSTANCE.play(Assets.SND_SECRET);
				}

				GLog.p(Messages.get(this, "eat"));

				Buff.affect(hero, Awareness.class, 10f);
				Dungeon.observe();


			} else {

				GLog.p(Messages.get(Blackberry.class, "eat3"));
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT + hero.HT);

				int length = Dungeon.level.getLength();
				int[] map = Dungeon.level.map;
				boolean[] mapped = Dungeon.level.mapped;
				boolean[] discoverable = Level.discoverable;

				boolean noticed = false;

				for (int i = 0; i < length; i++) {

					int terr = map[i];

					if (discoverable[i]) {

						mapped[i] = true;
						if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

							Level.set(i, Terrain.discover(terr));
							GameScene.updateMap(i);

							if (Dungeon.visible[i]) {
								GameScene.discoverTile(i, terr);
								discover(i);

								noticed = true;
							}
						}
					}
				}
				Dungeon.observe();

				if (noticed) {
					Sample.INSTANCE.play(Assets.SND_SECRET);
				}

				GLog.p(Messages.get(this, "eat"));

				Buff.affect(hero, Awareness.class, 10f);
				Dungeon.observe();


			}
		}
	}	
	
	public static void discover(int cell) {
		CellEmitter.get(cell).start(Speck.factory(Speck.DISCOVER), 0.1f, 4);
	}

	@Override
	public String info() {
		return Messages.get(this, "desc");
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
	
	public Blueberry() {
		this(1);
	}

	public Blueberry(int value) {
		this.quantity = value;
	}
	
}
