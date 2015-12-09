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

import java.util.HashSet;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Statistics;
import com.avmoga.dpixel.Badges.Badge;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.ToxicGas;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Invisibility;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.effects.particles.ShadowParticle;
import com.avmoga.dpixel.items.AdamantRing;
import com.avmoga.dpixel.items.ConchShell;
import com.avmoga.dpixel.items.Gold;
import com.avmoga.dpixel.items.TenguKey;
import com.avmoga.dpixel.items.TomeOfMastery;
import com.avmoga.dpixel.items.keys.SkeletonKey;
import com.avmoga.dpixel.items.scrolls.ScrollOfMagicMapping;
import com.avmoga.dpixel.items.scrolls.ScrollOfPsionicBlast;
import com.avmoga.dpixel.items.weapon.enchantments.Death;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.TenguSprite;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class TenguDen extends Mob {

	private static final int JUMP_DELAY = 5;
	protected static final float SPAWN_DELAY = 2f;
	
	{
		name = "Tengu";
		spriteClass = TenguSprite.class;
		baseSpeed = 2f;

		HP = HT = 400;
		EXP = 20;
		defenseSkill = 30;
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25);
	}

	@Override
	public int attackSkill(Char target) {
		return 28;
	}

	@Override
	public int dr() {
		return 20;
	}

	
	
	
	@Override
	public void die(Object cause) {

		
		    GameScene.bossSlain();
	        Dungeon.tengudenkilled=true;	
	    	yell("Ugh...!");
	   	
	    	Dungeon.level.drop(new AdamantRing(), pos).sprite.drop();
			Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
	    	
	    	super.die(cause); 	
					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		if (!this.isSilenced()){
			return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
		}
		else {
			return false;
		}
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 ) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;

		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.passable[newPos]
				|| Level.adjacent(newPos, Dungeon.hero.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
		
		if (Dungeon.level.mobs.size()<7){
		 Assassin.spawnAt(pos);
		}
		
		
	}

	public static TenguDen spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			TenguDen w = new TenguDen();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			//w.sprite.alpha(0);
			//w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

			return w;
  			
		} else {
			return null;
		}
	}
	
	@Override
	public void notice() {
		super.notice();
		yell("How did you find me!?");
	}

	@Override
	public String description() {
		return "Tengu are members of the ancient assassins clan, which is also called Tengu. "
				+ "These assassins are noted for extensive use of shuriken and traps.";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
