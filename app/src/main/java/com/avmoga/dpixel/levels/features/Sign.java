/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.avmoga.dpixel.levels.features;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.levels.DeadEndLevel;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndMessage;
import com.watabou.noosa.audio.Sample;

public class Sign {

	private static final String PIT = "警告：请冒险者始终在保险库中留下传送卷轴以方便离开。";
	//private static final String BOOKLVL = "Note to self: Always leave a teleport scroll in the vault.";

	public static void read( int pos ) {

		if (Dungeon.level instanceof DeadEndLevel) {

			GameScene.show( new WndMessage( Messages.get(Sign.class, "dead_end") ) );

		} else {

			if (Dungeon.depth <= 21) {
				GameScene.show( new WndMessage( Messages.get(Sign.class, "tip_"+Dungeon.depth) ) );
			} else {

				Level.set(pos, Terrain.EMBERS);
				GameScene.updateMap( pos );
				GameScene.discoverTile( pos, Terrain.SIGN );

				GLog.w( Messages.get(Sign.class, "burn") );

				CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
				Sample.INSTANCE.play( Assets.SND_BURNING );
			}

		}
	}

	public static void readPit(int pos) {
				GameScene.show(new WndMessage(PIT));
			}


}
