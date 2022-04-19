package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.ToxicGas;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.actors.mobs.RedWraith;
import com.avmoga.dpixel.actors.mobs.npcs.NPC;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.particles.ShaftParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.scrolls.ScrollOfPsionicBlast;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.GhostSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndQuest;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by debenhame on 21/11/2014.
 */
public class DriedRose extends Artifact {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.ARTIFACT_ROSE1;

		level = 0;
		levelCap = 10;

		charge = 100;
		chargeCap = 100;

		defaultAction = AC_SUMMON;
	}

	protected static boolean talkedTo = false;
	protected static boolean firstSummon = false;
	protected static boolean spawned = false;

	public int droppedPetals = 0;

	public static final String AC_SUMMON = Messages.get(DriedRose.class, "ac_summon");

	public DriedRose() {
		super();
		talkedTo = firstSummon = spawned = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == chargeCap && !cursed)
			actions.add(AC_SUMMON);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_SUMMON)) {

			if (spawned)
				GLog.n(Messages.get(this, "spawned"));
			else if (!isEquipped(hero))
				GLog.i(Messages.get(this, "equip"));
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else if (cursed)
				GLog.i(Messages.get(this, "cursed"));
			else {
				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
				for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
					int p = hero.pos + Level.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null
							&& (Level.passable[p] || Level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0) {
					GhostHero ghost = new GhostHero(level);
					ghost.pos = Random.element(spawnPoints);

					GameScene.add(ghost, 1f);
					CellEmitter.get(ghost.pos).start(ShaftParticle.FACTORY,
							0.3f, 4);
					CellEmitter.get(ghost.pos).start(
							Speck.factory(Speck.LIGHT), 0.2f, 3);

					hero.spend(1f);
					hero.busy();
					hero.sprite.operate(hero.pos);

					if (!firstSummon) {
						ghost.yell(Messages.get(DriedRose.class, "hello", Dungeon.hero.givenName()));
						Sample.INSTANCE.play(Assets.SND_GHOST);
						firstSummon = true;
					} else
						ghost.saySpawned();

					spawned = true;
					charge = 0;
					updateQuickslot();

				} else
					GLog.i(Messages.get(this, "no_space"));
			}

		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");

		if (isEquipped(Dungeon.hero)) {
			if (!cursed) {
				desc += Messages.get(this, "warm");
				desc += Messages.get(this, "desc_hint");
			} else
				desc += Messages.get(this, "desc_cursed");
		}

		return desc;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new roseRecharge();
	}

	@Override
	public Item upgrade() {
		if (level >= 9)
			image = ItemSpriteSheet.ARTIFACT_ROSE3;
		else if (level >= 4)
			image = ItemSpriteSheet.ARTIFACT_ROSE2;

		// For upgrade transferring via well of transmutation
		droppedPetals = Math.max(level, droppedPetals);

		return super.upgrade();
	}

	private static final String TALKEDTO = "talkedto";
	private static final String FIRSTSUMMON = "firstsummon";
	private static final String SPAWNED = "spawned";
	private static final String PETALS = "petals";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		bundle.put(TALKEDTO, talkedTo);
		bundle.put(FIRSTSUMMON, firstSummon);
		bundle.put(SPAWNED, spawned);
		bundle.put(PETALS, droppedPetals);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		talkedTo = bundle.getBoolean(TALKEDTO);
		firstSummon = bundle.getBoolean(FIRSTSUMMON);
		spawned = bundle.getBoolean(SPAWNED);
		droppedPetals = bundle.getInt(PETALS);
	}

	public class roseRecharge extends ArtifactBuff {

		@Override
		public boolean act() {

			if (charge < chargeCap && !cursed) {
				partialCharge += 10 / 75f;
				if (partialCharge > 1) {
					charge++;
					partialCharge--;
					if (charge == chargeCap) {
						partialCharge = 0f;
						GLog.p(Messages.get(DriedRose.class, "charged"));
					}
				}
			} else if (cursed && Random.Int(100) == 0) {

				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

				for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
					int p = target.pos + Level.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null
							&& (Level.passable[p] || Level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0) {
					RedWraith.spawnAt(Random.element(spawnPoints));
					Sample.INSTANCE.play(Assets.SND_CURSED);
				}

			}

			updateQuickslot();

			spend(TICK);

			return true;
		}
	}

	public static class Petal extends Item {

		{
			name = Messages.get(this, "name");
			stackable = true;
			image = ItemSpriteSheet.PETAL;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			DriedRose rose = hero.belongings.getItem(DriedRose.class);

			if (rose == null) {
				GLog.w(Messages.get(this, "no_rose"));
				return false;
			}
			if (rose.level >= rose.levelCap) {
				GLog.i(Messages.get(this, "no_room"));
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;
			} else {

				rose.upgrade();
				if (rose.level == rose.levelCap) {
					GLog.p(Messages.get(this, "maxlevel"));
					Sample.INSTANCE.play(Assets.SND_GHOST);
					GLog.n(Messages.get(DriedRose.class, "ghost"));
				} else
					GLog.i(Messages.get(this, "levelup"));

				Sample.INSTANCE.play(Assets.SND_DEWDROP);
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;

			}
		}

		@Override
		public String info() {
			return Messages.get(this, "desc");
		}

	}

	public static class GhostHero extends NPC {

		{
			name = Messages.get(this, "name");
			spriteClass = GhostSprite.class;

			flying = true;

			state = WANDERING;
			enemy = null;

			ally = true;
		}

		public GhostHero() {
			super();

			// double heroes defence skill
			defenseSkill = (Dungeon.hero.lvl + 4) * 2;
		}

		public GhostHero(int roseLevel) {
			this();
			HP = HT = 10 + roseLevel * 3;
		}

		public void saySpawned() {
			int i = (Dungeon.depth - 1) / 5;
			if (chooseEnemy() == null)
				yell(Random.element(VOICE_AMBIENT[i]));
			else
				yell(Random.element(VOICE_ENEMIES[i][Dungeon.bossLevel() ? 1
						: 0]));
			Sample.INSTANCE.play(Assets.SND_GHOST);
		}

		public void sayAnhk() {
			yell(Random.element(VOICE_BLESSEDANKH));
			Sample.INSTANCE.play(Assets.SND_GHOST);
		}

		public void sayDefeated() {
			yell(Random.element(VOICE_DEFEATED[Dungeon.bossLevel() ? 1 : 0]));
			Sample.INSTANCE.play(Assets.SND_GHOST);
		}

		public void sayHeroKilled() {
			yell(Random.element(VOICE_HEROKILLED));
			Sample.INSTANCE.play(Assets.SND_GHOST);
		}

		public void sayBossBeaten() {
			yell(Random.element(VOICE_BOSSBEATEN[Dungeon.depth == 25 ? 1 : 0]));
			Sample.INSTANCE.play(Assets.SND_GHOST);
		}

		@Override
		public String defenseVerb() {
			return Messages.get(this, "def_verb");
		}

		@Override
		protected boolean act() {
			if (Random.Int(10) == 0)
				damage(1, this);
			if (!isAlive())
				return true;
			if (!Dungeon.hero.isAlive()) {
				sayHeroKilled();
				sprite.die();
				destroy();
				return true;
			}
			return super.act();
		}

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Level.distance(target, Dungeon.hero.pos) > 6)
				this.target = target = Dungeon.hero.pos;
			return super.getCloser(target);
		}

		@Override
		protected Char chooseEnemy() {
			if (enemy == null || !enemy.isAlive() || state == WANDERING) {

				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}
			return enemy;
		}

		@Override
		public int attackSkill(Char target) {
			// same accuracy as the hero.
			return (defenseSkill / 2) + 5;
		}

		@Override
		public int damageRoll() {
			// equivalent to N/2 to 5+N, where N is rose level.
			int lvl = (HT - 10) / 3;
			return Random.NormalIntRange(lvl / 2, 5 + lvl);
		}

		@Override
		public int dr() {
			// defence is equal to the level of rose.
			return (HT - 10) / 3;
		}

		@Override
		public void add(Buff buff) {
			// in other words, can't be directly affected by buffs/debuffs.
		}

		@Override
		public void interact() {
			if (!DriedRose.talkedTo) {
				DriedRose.talkedTo = true;
				GameScene.show(new WndQuest(this, VOICE_INTRODUCE));
			} else {
				int curPos = pos;

				moveSprite(pos, Dungeon.hero.pos);
				move(Dungeon.hero.pos);

				Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
				Dungeon.hero.move(curPos);

				Dungeon.hero.spend(1 / Dungeon.hero.speed());
				Dungeon.hero.busy();
			}
		}

		@Override
		public void die(Object cause) {
			sayDefeated();
			super.die(cause);
		}

		@Override
		public void destroy() {
			DriedRose.spawned = false;
			super.destroy();
		}

		@Override
		public String description() {
			return Messages.get(this, "desc");
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(ToxicGas.class);
			IMMUNITIES.add(ScrollOfPsionicBlast.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		// ************************************************************************************
		// This is a bunch strings & string arrays, used in all of the sad
		// ghost's voice lines.
		// ************************************************************************************

		public static final String VOICE_HELLO = "Hello again ";

		private static final String VOICE_INTRODUCE = Messages.get(DriedRose.class, "1");

		public static final String[][] VOICE_AMBIENT = {
				{Messages.get(DriedRose.class, "2")},
				{Messages.get(DriedRose.class, "3")},
				{Messages.get(DriedRose.class, "4")},
				{Messages.get(DriedRose.class, "5")},
				{Messages.get(DriedRose.class, "6")},
				{Messages.get(DriedRose.class, "7")}};

		// 1st index - depth type, 2nd index - boss or not, 3rd index - specific
		// line.
		public static final String[][][] VOICE_ENEMIES = {
				{{Messages.get(DriedRose.class, "8")}, {Messages.get(DriedRose.class, "9")}},
				{{Messages.get(DriedRose.class, "10")}, {Messages.get(DriedRose.class, "11")}},
				{{Messages.get(DriedRose.class, "12")}, {Messages.get(DriedRose.class, "13")}},
				{{Messages.get(DriedRose.class, "14")}, {Messages.get(DriedRose.class, "15")}},
				{{Messages.get(DriedRose.class, "16")}, {Messages.get(DriedRose.class, "17")}},
				{{Messages.get(DriedRose.class, "18")},
						{"Hello source viewer, I'm writing this here as this line should never trigger. Have a nice day!"}}};

		// 1st index - Yog or not, 2nd index - specific line.
		public static final String[][] VOICE_BOSSBEATEN = {
				{Messages.get(DriedRose.class, "19")},
				{Messages.get(DriedRose.class, "20")}};


		// 1st index - boss or not, 2nd index - specific line.
		public static final String[][] VOICE_DEFEATED = {
				{Messages.get(DriedRose.class, "21"), Messages.get(DriedRose.class, "22"),
						Messages.get(DriedRose.class, "23")},
				{Messages.get(DriedRose.class, "24"), Messages.get(DriedRose.class, "25"),
						Messages.get(DriedRose.class, "26")}};

		public static final String[] VOICE_HEROKILLED = {Messages.get(DriedRose.class, "27"),
				Messages.get(DriedRose.class, "28"), Messages.get(DriedRose.class, "29")};

		public static final String[] VOICE_BLESSEDANKH = {Messages.get(DriedRose.class, "30"),
				Messages.get(DriedRose.class, "31"), Messages.get(DriedRose.class, "32")};
	}
}
