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

import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.ToxicGas;
import com.avmoga.dpixel.actors.blobs.Web;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.actors.buffs.Roots;
import com.avmoga.dpixel.actors.buffs.Terror;
import com.avmoga.dpixel.effects.Pushing;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.items.Gold;
import com.avmoga.dpixel.items.keys.SkeletonKey;
import com.avmoga.dpixel.items.potions.PotionOfMending;
import com.avmoga.dpixel.items.scrolls.ScrollOfPsionicBlast;
import com.avmoga.dpixel.items.weapon.enchantments.Death;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.SewerBossLevel;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.levels.features.Door;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.PoisonGooSprite;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class PoisonGoo extends Mob {
	
protected static final float SPAWN_DELAY = 2f;

private boolean gooSplit = false;

private int gooGeneration = 0;
private int goosAlive = 0;

private static final String GOOGENERATION = "gooGeneration";

	{
		name = Messages.get(this, "name");
		HP = HT = 120;
		EXP = 10;
		defenseSkill = 20;
		spriteClass = PoisonGooSprite.class;
		baseSpeed = 2.5f;

		loot = new PotionOfMending();
		lootChance = 1f;
		FLEEING = new Fleeing();
	}

	private static final float SPLIT_DELAY = 1f;	
	
	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Poison.class) == null) {
			state = HUNTING;
		}
		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP++;
		} else if(Level.water[pos] && HP == HT && HT < 100){
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HT=HT+5;
			HP=HT;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(1) == 0) {
			Buff.affect(enemy, Poison.class).set(
					Random.Int(7, 10) * Poison.durationFactor(enemy));
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public void move(int step) {
		if (state == FLEEING) {
			GameScene.add(Blob.seed(pos, Random.Int(7, 10), Web.class));
		}
		super.move(step);
	}
	
	@Override
	public int damageRoll() {
			return Random.NormalIntRange(1, 10);
	}

	@Override
	public int attackSkill(Char target) {
		return 5;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GOOGENERATION, gooGeneration);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		gooGeneration = bundle.getInt(GOOGENERATION);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		gooSplit = false;   
		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Goo) {
				gooSplit = true;
			}
		}
		if (HP >= damage + 2 && gooSplit) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			int[] neighbours = { pos + 1, pos - 1, pos + Level.getWidth(),
					pos - Level.getWidth() };
			for (int n : neighbours) {
				if (passable[n] && Actor.findChar(n) == null) {
					candidates.add(n);
				}
			}

			if (candidates.size() > 0) {
				GLog.n(Messages.get(this, "divide"));
				PoisonGoo clone = split();
				clone.HP = (HP - damage) / 2;
				clone.pos = Random.element(candidates);
				clone.state = clone.HUNTING;

				if (Dungeon.level.map[clone.pos] == Terrain.DOOR) {
					Door.enter(clone.pos);
				}

				GameScene.add(clone, SPLIT_DELAY);
				Actor.addDelayed(new Pushing(clone, pos, clone.pos), -1);

				HP -= clone.HP;
			}
		}

		return damage;
	}


	private PoisonGoo split() {
		PoisonGoo clone = new PoisonGoo();
		clone.gooGeneration = gooGeneration + 1;
		if (buff(Burning.class) != null) {
			Buff.affect(clone, Burning.class).reignite(clone);
		}
		if (buff(Poison.class) != null) {
			Buff.affect(clone, Poison.class).set(2);
		}
		return clone;
	}
	
	
	

	@Override
	public void die(Object cause) {
		
		if (gooGeneration > 0){
		lootChance = 0;
		}

		super.die(cause);
		   
		for (Mob mob : Dungeon.level.mobs) {
		
		if (mob instanceof Goo || mob instanceof PoisonGoo){
			   goosAlive++;
			 }
		
		}
		
		 if(goosAlive==0){
		   ((SewerBossLevel) Dungeon.level).unseal();

			GameScene.bossSlain();
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();

			Dungeon.level.drop(new Gold(Random.Int(900, 2000)), pos).sprite.drop();

			Badges.validateBossSlain();
		} else {

		Dungeon.level.drop(new Gold(Random.Int(100, 200)), pos).sprite.drop();
		}

		yell(Messages.get(this, "die"));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}

	@Override
	public String description() {
		return Messages.get(this, "desc");
	}

	

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
	
	
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			GLog.n(Messages.get(PoisonGoo.class, "squeeze"));
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
				GLog.n(Messages.get(PoisonGoo.class, "create"));
			}
		}
	}
	
	public static PoisonGoo spawnAt(int pos) {
		
        PoisonGoo b = new PoisonGoo();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	
}
