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

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Blindness;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.items.Generator;
import com.avmoga.dpixel.items.food.Meat;
import com.avmoga.dpixel.sprites.BrownBatSprite;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class BrownBat extends Mob {

	{
		name = Messages.get(this, "name");
		spriteClass = BrownBatSprite.class;

		HP = HT = 4;
		defenseSkill = 1;
		baseSpeed = 2f;

		EXP = 1;
		maxLvl = 15;

		flying = true;

		loot = new Meat();
		lootChance = 0.5f; // by default, see die()
		
		lootOther = Generator.Category.BERRY;
		lootChanceOther = 0.05f; // by default, see die()
		
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 4);
	}

	@Override
	public int attackSkill(Char target) {
		return 5+Dungeon.depth;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.prolong(enemy, Blindness.class, Random.Int(3, 10));
			GLog.n("棕色蝙蝠刺伤了你的眼睛!");
			Dungeon.observe();
			state = FLEEING;
		}
		
		return damage;
	}
	@Override
	public int dr() {
		return 1;
	}

	@Override
	public String defenseVerb() {
		return "躲避";
	}
	
	@Override
	public void die(Object cause) {
		
		if (Random.Int(5) == 0) {
			  for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				  if (Random.Int(2) == 0 && enemy!=null){mob.beckon(enemy.pos);}
			      }
			  GLog.n("棕色蝙蝠的发出的尖叫把附近的敌人都惊醒了！");
				Sample.INSTANCE.play(Assets.SND_ALERT);
			}

		super.die(cause);

	}


	@Override
	public String description() {
		return Messages.get(this, "desc");
	}

	
}
