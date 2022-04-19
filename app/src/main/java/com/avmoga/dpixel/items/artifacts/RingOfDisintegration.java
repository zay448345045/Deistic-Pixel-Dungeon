package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.DungeonTilemap;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Strength;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.DeathRay;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.effects.particles.PurpleParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.scrolls.Scroll;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by dachhack on 10/15/2015.
 */
public class RingOfDisintegration extends Artifact {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.RING_DISINTEGRATION;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_BLAST;
	}

	protected String inventoryTitle = Messages.get(RingOfDisintegration.class, "invtitle");
	protected WndBag.Mode mode = WndBag.Mode.SCROLL;
	
	public static int consumedpts = 0;

	public static final String AC_BLAST = Messages.get(RingOfDisintegration.class, "ac_blast");
	public static final String AC_ADD = Messages.get(RingOfDisintegration.class, "ac_add");


	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed)
			actions.add(AC_BLAST);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_ADD);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_BLAST)) {

			if (!isEquipped(hero))
				GLog.i(Messages.get(this, "equip"));
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {
				
				blast(hero.pos);
				charge = 0;
				updateQuickslot();
				GLog.p("爆破！");
				
			}
			
		} else if (action.equals(AC_ADD)) {
			GameScene.selectItem(itemSelector, mode, inventoryTitle);
		}
	}
	
	private int distance() {
		return level() + 2;
	}
	
	public int level(){
		return level;
	}
	
	public void blast(int cell) {
		
		if (level()<5){
		for (int n : Level.NEIGHBOURS4) {
			int c = cell + n;			
			    discharge(c);		
		     }
		
	   } else if (level()<10){
	 		for (int n : Level.NEIGHBOURS8) {
				int c = cell + n;			
				    discharge(c);		
			     }
			
	   } else {
		  for (int n : Level.NEIGHBOURS8DIST2) {
				int c = cell + n;			
				    discharge(c);		
			     }
			}
	  }
	
	protected void  discharge(int cell){
				
		final int hitcell = Ballistica.cast(curUser.pos, cell, true, false);
		 fx(cell, new Callback() {	@Override
		public void call() {zap(hitcell);	ringUsed();	}});

	}
	
	protected void zap(int cell){
		
		boolean terrainAffected = false;

		int level = level();

		int maxDistance = distance();
		Ballistica.distance = Math.min(Ballistica.distance, maxDistance);

		ArrayList<Char> chars = new ArrayList<Char>();

		for (int i = 1; i < Ballistica.distance; i++) {

			int c = Ballistica.trace[i];

			Char ch;
			if ((ch = Actor.findChar(c)) != null) {
				chars.add(ch);
			}

			int terr = Dungeon.level.map[c];
			if (terr == Terrain.DOOR || terr == Terrain.BARRICADE) {

				Level.set(c, Terrain.EMBERS);
				GameScene.updateMap(c);
				terrainAffected = true;

			} else if (terr == Terrain.HIGH_GRASS) {

				Level.set(c, Terrain.GRASS);
				GameScene.updateMap(c);
				terrainAffected = true;

			}

			CellEmitter.center(c).burst(PurpleParticle.BURST,
					Random.IntRange(1, 2));
		}

		if (terrainAffected) {
			Dungeon.observe();
		}

		int lvl = level + chars.size();
		int dmgMin = lvl*lvl;
		int dmgMax = 10 + lvl*lvl*3;
		if (Dungeon.hero.buff(Strength.class) != null){ dmgMin *= (int) 4f; dmgMax *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
		for (Char ch : chars) {
			ch.damage(Random.NormalIntRange(dmgMin, dmgMax), this);
			ch.sprite.centerEmitter().burst(PurpleParticle.BURST,
					Random.IntRange(1, 2));
			ch.sprite.flash();
	
		}
	}
	
	protected void ringUsed() {
		
		updateQuickslot();

	}
	
	protected void fx(int cell, Callback callback) {

		cell = Ballistica.trace[Math.min(Ballistica.distance, distance()) - 1];
		curUser.sprite.parent.add(new DeathRay(curUser.sprite.center(),
				DungeonTilemap.tileCenterToWorld(cell)));
		callback.call();
	}
	
	
	@Override
	protected ArtifactBuff passiveBuff() {
		return new ringRecharge();
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc1");
		if (isEquipped(Dungeon.hero)) {
			desc += "\n\n";
			if (charge < 100)
				desc += Messages.get(this, "desc2");
			else
				desc += Messages.get(this, "desc3");
		}

		return desc;
	}

	public class ringRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap) {
				
					partialCharge += 1+(level*level);

				if (partialCharge >= 10) {
					charge++;
					partialCharge = 0;
					if (charge == chargeCap) {
						partialCharge = 0;
					}

				}
			} else
				partialCharge = 0;

			
			updateQuickslot();

			spend(TICK);

			return true;
		}

	}
	
	private static final String PARTIALCHARGE = "partialCharge";
	private static final String CHARGE = "charge";
	private static final String CONSUMED = "consumedpts";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(PARTIALCHARGE, partialCharge);
		bundle.put(CHARGE, charge);
		bundle.put(CONSUMED, consumedpts);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		partialCharge = bundle.getInt(PARTIALCHARGE);
		charge = bundle.getInt(CHARGE);
		consumedpts = bundle.getInt(CONSUMED);
	}
	
	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Scroll && item.isIdentified()) {
				Hero hero = Dungeon.hero;
				
				int scrollWorth = ((Scroll)item).consumedValue;
				consumedpts += scrollWorth;
			
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(2f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);

				item.detach(hero.belongings.backpack);
				GLog.i(Messages.get(RingOfDisintegration.class, "points", consumedpts));
				
				int levelChk = ((level()*level()/2)+1)*10;
								
				if (consumedpts > levelChk && level()<10) {
					upgrade();
					GLog.p(Messages.get(RingOfDisintegration.class, "better"));
					}


			} else if (item instanceof Scroll && !item.isIdentified()) {
				GLog.w(Messages.get(RingOfDisintegration.class, "notsure"));
			} else if (item != null) {
				GLog.w(Messages.get(RingOfDisintegration.class, "unable"));
			}
	 }
	};
	
}
