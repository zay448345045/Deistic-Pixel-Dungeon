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
package com.avmoga.dpixel.actors.mobs.pets;

import java.util.HashSet;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.ResultDescriptions;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Fire;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.actors.buffs.Weakness;
import com.avmoga.dpixel.actors.mobs.Bee;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.CharSprite;
import com.avmoga.dpixel.sprites.MirrorSprite;
import com.avmoga.dpixel.sprites.RedDragonSprite;
import com.avmoga.dpixel.sprites.SteelBeeSprite;
import com.avmoga.dpixel.sprites.VioletDragonSprite;
import com.avmoga.dpixel.sprites.WarlockSprite;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class VioletDragon extends PET implements Callback{
	
	{
		name = "violet dragon";
		spriteClass = VioletDragonSprite.class;       
		flying=true;
		state = HUNTING;
		level = 1;
		type = 6;
		cooldown=1000;

	}
	private static final float TIME_TO_ZAP = 1f;

	//Frames 1-4 are idle, 5-8 are moving, 9-12 are attack and the last are for death 

	//flame on!
	//spits fire
	//feed meat
			
	protected int regen = 1;	
	protected float regenChance = 0.2f;	
	
	@Override
	protected float attackDelay() {
		return 0.8f;
	}
	
		

	@Override
	public void adjustStats(int level) {
		this.level = level;
		HT = (level) * 10;
		defenseSkill = 5 + level*level;
	}
	
	

	@Override
	public int attackSkill(Char target) {
		return defenseSkill;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(HT / 5, HT / 2);
	}

	@Override
	protected boolean act() {
		
		if (cooldown>0){
			cooldown=Math.max(cooldown-(level*level),0);
			if (cooldown==0) {GLog.w("Your violet dragon is dripping poison!");}
		}
		
		if (Random.Float()<regenChance && HP<HT){HP+=regen;}

		return super.act();
	}
	
	
	@Override
	protected boolean canAttack(Char enemy) {
		if (cooldown>0){
		  return Level.adjacent(pos, enemy.pos);
		} else {
		  return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
		}
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((VioletDragonSprite) sprite).zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	
	private void zap() {
		spend(TIME_TO_ZAP);

		cooldown=1000;
		yell("Roaaar!");
		
		if (hit(this, enemy, true)) {			

			int dmg = damageRoll()*2;
			enemy.damage(dmg, this);
			
			Buff.affect(enemy,Poison.class).set(Poison.durationFactor(enemy) * (level + 1));
			
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
		
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}
	
	
	
	@Override
	public void interact() {

		int curPos = pos;

		moveSprite(pos, Dungeon.hero.pos);
		move(Dungeon.hero.pos);

		Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);

		Dungeon.hero.spend(1 / Dungeon.hero.speed());
		Dungeon.hero.busy();
	}


@Override
public String description() {
	return "A feshly hatched violet dragon. Super fierce and super cute!";
}


}