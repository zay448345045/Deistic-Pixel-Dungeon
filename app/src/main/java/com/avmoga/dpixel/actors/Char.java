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
package com.avmoga.dpixel.actors;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ResultDescriptions;
import com.avmoga.dpixel.actors.buffs.Amok;
import com.avmoga.dpixel.actors.buffs.Bleeding;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Charm;
import com.avmoga.dpixel.actors.buffs.Cripple;
import com.avmoga.dpixel.actors.buffs.EarthImbue;
import com.avmoga.dpixel.actors.buffs.FireImbue;
import com.avmoga.dpixel.actors.buffs.Frost;
import com.avmoga.dpixel.actors.buffs.Haste;
import com.avmoga.dpixel.actors.buffs.Invisibility;
import com.avmoga.dpixel.actors.buffs.Levitation;
import com.avmoga.dpixel.actors.buffs.Light;
import com.avmoga.dpixel.actors.buffs.MagicalSleep;
import com.avmoga.dpixel.actors.buffs.MindVision;
import com.avmoga.dpixel.actors.buffs.Paralysis;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.actors.buffs.Roots;
import com.avmoga.dpixel.actors.buffs.Shadows;
import com.avmoga.dpixel.actors.buffs.ShieldBuff;
import com.avmoga.dpixel.actors.buffs.Silence;
import com.avmoga.dpixel.actors.buffs.Sleep;
import com.avmoga.dpixel.actors.buffs.Slow;
import com.avmoga.dpixel.actors.buffs.Speed;
import com.avmoga.dpixel.actors.buffs.Terror;
import com.avmoga.dpixel.actors.buffs.Vertigo;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroSubClass;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.PoisonParticle;
import com.avmoga.dpixel.items.artifacts.CloakOfShadows;
import com.avmoga.dpixel.items.artifacts.RingOfDisintegration;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.levels.features.Door;
import com.avmoga.dpixel.sprites.CharSprite;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.HashSet;

public abstract class Char extends Actor {



	protected static final String TXT_HIT = "%s攻击了%s";
	protected static final String TXT_KILL = "%s杀死了你...";
	protected static final String TXT_DEFEAT = "%s 击败了 %s。";

	//private static final String TXT_YOU_MISSED = "%s %s your attack";
	//private static final String TXT_SMB_MISSED = "%s %s %s's attack";
//
	//private static final String TXT_OUT_OF_PARALYSIS = "The pain snapped %s out of paralysis";
	public Char enemy;
	public int pos = 0;

	public CharSprite sprite;

	public String name = "mob";
	public Alignment alignment;
	public enum Alignment{
		ENEMY,
		NEUTRAL,
		ALLY
	}
	public int HT;
	public int HP;

	protected float baseSpeed = 1;

	public boolean paralysed = false;
	public boolean rooted = false;
	public boolean flying = false;
	public int invisible = 0;

	public int viewDistance = 8;

	private HashSet<Buff> buffs = new HashSet<Buff>();

	@Override
	protected boolean act() {
		Dungeon.level.updateFieldOfView(this);
		return false;
	}

	private static final String POS = "pos";
	private static final String TAG_HP = "HP";
	private static final String TAG_HT = "HT";
	private static final String BUFFS = "buffs";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(POS, pos);
		bundle.put(TAG_HP, HP);
		bundle.put(TAG_HT, HT);
		bundle.put(BUFFS, buffs);
	}
	//used so that buffs(Shieldbuff.class) isn't called every time unnecessarily
	private int cachedShield = 0;
	public boolean needsShieldUpdate = true;
	public int shielding(){
		if (!needsShieldUpdate){
			return cachedShield;
		}

		cachedShield = 0;
		for (ShieldBuff s : buffs(ShieldBuff.class)){
			cachedShield += s.shielding();
		}
		needsShieldUpdate = false;
		return cachedShield;
	}


	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pos = bundle.getInt(POS);
		HP = bundle.getInt(TAG_HP);
		HT = bundle.getInt(TAG_HT);

		for (Bundlable b : bundle.getCollection(BUFFS)) {
			if (b != null) {
				((Buff) b).attachTo(this);
			}
		}
	}

	public boolean attack(Char enemy) {

		boolean visibleFight = Dungeon.visible[pos]
				|| Dungeon.visible[enemy.pos];

		if (hit(this, enemy, false)) {

			int dr = this instanceof Hero && ((Hero) this).rangedWeapon != null
					&& ((Hero) this).subClass == HeroSubClass.SNIPER ? 0
					: Random.IntRange(0, enemy.dr());

			int dmg = damageRoll();
			int effectiveDamage = Math.max(dmg - dr, 0);

			effectiveDamage = attackProc(enemy, effectiveDamage);
			effectiveDamage = enemy.defenseProc(this, effectiveDamage);

			if (visibleFight) {
				Sample.INSTANCE.play(Assets.SND_HIT, 1, 1,
						Random.Float(0.8f, 1.25f));
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage,
			// such as armor glyphs.
			if (!enemy.isAlive()) {
				return true;
			}

			float shake = 0f;
			if (enemy == Dungeon.hero)
				shake = effectiveDamage / (enemy.HT / 4);

			if (shake > 1f)
				Camera.main.shake(GameMath.gate(1, shake, 5), 0.3f);

			enemy.damage(effectiveDamage, this);

			if (buff(FireImbue.class) != null)
				buff(FireImbue.class).proc(enemy);
			if (buff(EarthImbue.class) != null)
				buff(EarthImbue.class).proc(enemy);

			if (buff(RingOfDisintegration.ringRecharge.class) != null && enemy.isAlive()) {
				if (buff(RingOfDisintegration.ringRecharge.class).level() > 10) {
				}
			}

			enemy.sprite.bloodBurstA(sprite.center(), effectiveDamage);
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {
					Dungeon.fail(Utils.format(ResultDescriptions.MOB, Utils.indefinite(name)));
					GLog.n(Messages.get(this, "kill"), name);
				} else {
					GLog.i(Messages.get(this, "defeat"), name, enemy.name);
				}
			}

			return true;

		} else {

			if (visibleFight) {
				String defense = enemy.defenseVerb();
				enemy.sprite.showStatus(CharSprite.NEUTRAL, defense);
				Sample.INSTANCE.play(Assets.SND_MISS);
			}

			return false;

		}
	}

	public static boolean hit(Char attacker, Char defender, boolean magic) {
		float acuRoll = Random.Float(attacker.attackSkill(defender));
		float defRoll = Random.Float(defender.defenseSkill(attacker));
		return (magic ? acuRoll * 2 : acuRoll) >= defRoll;
	}

	public int attackSkill(Char target) {
		return 0;
	}

	public int defenseSkill(Char enemy) {
		return 0;
	}

	public String defenseVerb() {
		return Messages.get(this, "dodged");
	}

	public int dr() {
		return 0;
	}

	public int damageRoll() {
		return 1;
	}

	public int attackProc(Char enemy, int damage) {
		return damage;
	}

	public int defenseProc(Char enemy, int damage) {
		return damage;
	}

	public float speed() {
		if (buff(Cripple.class) != null){
			return baseSpeed * 0.5f;
		} else if (buff(Haste.class) != null){
			return baseSpeed * 2f;
		} else {
			return baseSpeed;
		}
		
		
	}

	public void damage(int dmg, Object src) {

		if (HP <= 0) {
			return;
		}
		if (this.buff(Frost.class) != null) {
			Buff.detach(this, Frost.class);
			if (Level.water[this.pos]) {
				Buff.prolong(this, Paralysis.class, 1f);
			}
		}
		if (this.buff(MagicalSleep.class) != null) {
			Buff.detach(this, MagicalSleep.class);
		}

		Class<?> srcClass = src.getClass();
		if (immunities().contains(srcClass)) {
			dmg = 0;
		} else if (resistances().contains(srcClass)) {
			dmg = Random.IntRange(0, dmg);
		}

		if (buff(Paralysis.class) != null) {
			if (Random.Int(dmg) >= Random.Int(HP)) {
				Buff.detach(this, Paralysis.class);
				if (Dungeon.visible[pos]) {
					GLog.i(Messages.get(this, "np"), name);
				}
			}
		}

		HP -= dmg;
		if (dmg > 0 || src instanceof Char) {
			sprite.showStatus(HP > HT / 2 ? CharSprite.WARNING
					: CharSprite.NEGATIVE, Integer.toString(dmg));
		}
		if (HP <= 0) {
			die(src);
		}
	}

	public void destroy() {
		HP = 0;
		Actor.remove(this);
		Actor.freeCell(pos);
	}

	public void die(Object src) {
		destroy();
		sprite.die();
	}

	public boolean isAlive() {
		return HP > 0;
	}

	@Override
	protected void spend(float time) {

		float timeScale = 1f;
		if (buff(Slow.class) != null) {
			timeScale *= 0.5f;
		}
		if (buff(Speed.class) != null) {
			timeScale *= 2.0f;
		}
		if (buff(Haste.class) != null) {
			timeScale *= 2.0f;
		}

		super.spend(time / timeScale);
	}

	public HashSet<Buff> buffs() {
		return buffs;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> HashSet<T> buffs(Class<T> c) {
		HashSet<T> filtered = new HashSet<T>();
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				filtered.add((T) b);
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> T buff(Class<T> c) {
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				return (T) b;
			}
		}
		return null;
	}
	public boolean isSilenced() {
		for (Buff b : buffs) {
			if (b instanceof Silence){
				return true;
			}
		}
		return false;
	}

	public boolean isCharmedBy(Char ch) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm) b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public void add(Buff buff) {

		buffs.add(buff);
		Actor.add(buff);

		if (sprite != null) {
			if (buff instanceof Poison) {

				CellEmitter.center(pos).burst(PoisonParticle.SPLASH, 5);
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Poison.class, "name"));

			} else if (buff instanceof Amok) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Amok.class, "name"));

			} else if (buff instanceof Slow) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Slow.class, "name"));

			} else if (buff instanceof MindVision) {

				sprite.showStatus(CharSprite.POSITIVE, Messages.get(MindVision.class, "name"));

			} else if (buff instanceof Paralysis) {

				sprite.add(CharSprite.State.PARALYSED);
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Paralysis.class, "name"));

			} else if (buff instanceof Terror) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Terror.class, "name"));

			} else if (buff instanceof Roots) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Roots.class, "name"));

			} else if (buff instanceof Cripple) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Cripple.class, "name"));

			} else if (buff instanceof Bleeding) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Bleeding.class, "name"));

			} else if (buff instanceof Vertigo) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Vertigo.class, "name"));

			} else if (buff instanceof Haste) {

				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Haste.class, "name"));

			} else if (buff instanceof Sleep) {
				sprite.idle();
			} else if (buff instanceof Burning) {
				sprite.add(CharSprite.State.BURNING);
			} else if (buff instanceof Levitation) {
				sprite.add(CharSprite.State.LEVITATING);
			} else if (buff instanceof Frost) {
				sprite.add(CharSprite.State.FROZEN);
			} else if (buff instanceof Invisibility
					|| buff instanceof CloakOfShadows.cloakStealth) {
				if (!(buff instanceof Shadows)) {
					sprite.showStatus(CharSprite.POSITIVE, Messages.get(Invisibility.class, "name"));
				}
				sprite.add(CharSprite.State.INVISIBLE);
			}
		}
	}

	public void remove(Buff buff) {

		buffs.remove(buff);
		Actor.remove(buff);

		if (buff instanceof Burning) {
			sprite.remove(CharSprite.State.BURNING);
		} else if (buff instanceof Levitation) {
			sprite.remove(CharSprite.State.LEVITATING);
		} else if ((buff instanceof Invisibility || buff instanceof CloakOfShadows.cloakStealth)
				&& invisible <= 0) {
			sprite.remove(CharSprite.State.INVISIBLE);
		} else if (buff instanceof Paralysis) {
			sprite.remove(CharSprite.State.PARALYSED);
		} else if (buff instanceof Frost) {
			sprite.remove(CharSprite.State.FROZEN);
		}
	}

	public void remove(Class<? extends Buff> buffClass) {
		for (Buff buff : buffs(buffClass)) {
			remove(buff);
		}
	}

	@Override
	protected void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[0])) {
			buff.detach();
		}
	}

	public void updateSpriteState() {
		for (Buff buff : buffs) {
			if (buff instanceof Burning) {
				sprite.add(CharSprite.State.BURNING);
			} else if (buff instanceof Levitation) {
				sprite.add(CharSprite.State.LEVITATING);
			} else if (buff instanceof Invisibility
					|| buff instanceof CloakOfShadows.cloakStealth) {
				sprite.add(CharSprite.State.INVISIBLE);
			} else if (buff instanceof Paralysis) {
				sprite.add(CharSprite.State.PARALYSED);
			} else if (buff instanceof Frost) {
				sprite.add(CharSprite.State.FROZEN);
			} else if (buff instanceof Light) {
				sprite.add(CharSprite.State.ILLUMINATED);
			}
		}
	}

	public int stealth() {
		return 0;
	}

	public void move(int step) {

		if (Level.adjacent(step, pos) && buff(Vertigo.class) != null) {
			step = pos + Level.NEIGHBOURS8[Random.Int(8)];
			if (!(Level.passable[step] || Level.avoid[step])
					|| Actor.findChar(step) != null)
				return;
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave(pos);
		}

		pos = step;

		if (flying && Dungeon.level.map[pos] == Terrain.DOOR) {
			Door.enter(pos);
		}

		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.visible[pos];
		}
	}

	public int distance(Char other) {
		return Level.distance(pos, other.pos);
	}

	public void onMotionComplete() {
		next();
	}

	public void onAttackComplete() {
		next();
	}

	public void onOperateComplete() {
		next();
	}

	private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();

	public HashSet<Class<?>> resistances() {
		return EMPTY;
	}

	public HashSet<Class<?>> immunities() {
		return EMPTY;
	}
}
