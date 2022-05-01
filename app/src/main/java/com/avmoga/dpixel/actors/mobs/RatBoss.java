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
package com.avmoga.dpixel.actors.mobs;


import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.items.quest.RatSkull;
import com.avmoga.dpixel.sprites.RatBossSprite;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Random;

public class RatBoss extends Rat {

	{
		name = "古老鼠王";
		spriteClass = RatBossSprite.class;
		if (Dungeon.depth == 666) {
			HP = HT = 90;
		} else {
			HP = HT = 12 + (Dungeon.depth * Random.NormalIntRange(2, 5));
		}
		defenseSkill = 5+(Dungeon.depth/4);

		if (Dungeon.depth == 666) {
			loot = new RatSkull();
			lootChance = 0f;
		} else {
			loot = new RatSkull();
			lootChance = 0.7f;
		}
	}


	private boolean spawnedRats = false;
			
	@Override
	public int damageRoll() {
		if (Dungeon.depth == 666) {
			return Random.NormalIntRange(20,40);
		} else {
			return Random.NormalIntRange(2 + Dungeon.depth / 2, 8 + (Dungeon.depth));
		}
	}

	@Override
	public int attackSkill(Char target) {
		if (Dungeon.depth == 666) {
			return 20;
		} else {
			return 11 + Dungeon.depth;
		}
	}

	@Override
	public int dr() {
		return Dungeon.depth/2;
	}

	@Override
	public void notice() {
		//super.notice();
		yell(Messages.get(this, "notice"));
		if (Dungeon.depth == 666) {
			if (!spawnedRats) {
				GreyRat.spawnAround(pos);
				GLog.n(Messages.get(this, "spawn"));
				spawnedRats = true;
			}
		} else {
			if (!spawnedRats) {
				Rat.spawnAround(pos);
				GLog.n(Messages.get(this, "spawn"));
				spawnedRats = true;
			}
		}
	}


	@Override
	public String description() {
		String desc = "";
		if (Dungeon.depth == 666) {
			desc += "曾经的鼠王，现在只会保卫现任老鼠王，使用它动用一切的力量来保证能顺利处决入侵者";
		} else {
			desc += Messages.get(this, "desc");
		}
		return desc;
	}
}
