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
package com.avmoga.dpixel.windows;

import static com.avmoga.dpixel.Dungeon.hero;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Hunger;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.actors.mobs.pets.PET;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.gods.God;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.food.Blackberry;
import com.avmoga.dpixel.items.food.Blueberry;
import com.avmoga.dpixel.items.food.ChargrilledMeat;
import com.avmoga.dpixel.items.food.Cloudberry;
import com.avmoga.dpixel.items.food.FrozenCarpaccio;
import com.avmoga.dpixel.items.food.FullMoonberry;
import com.avmoga.dpixel.items.food.Meat;
import com.avmoga.dpixel.items.food.Moonberry;
import com.avmoga.dpixel.items.food.MysteryMeat;
import com.avmoga.dpixel.items.food.Nut;
import com.avmoga.dpixel.items.food.ToastedNut;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.plants.Plant;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.CharSprite;
import com.avmoga.dpixel.sprites.HeroSprite;
import com.avmoga.dpixel.ui.BuffIndicator;
import com.avmoga.dpixel.ui.HealthBar;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.ui.Button;

import java.util.Locale;

public class WndHero extends WndTabbed {
	private static final String TXT_STATS = Messages.get(WndHero.class, "stats");
	private static final String TXT_LEVELSTATS = Messages.get(WndHero.class, "levelstats");
	private static final String TXT_BUFFS = Messages.get(WndHero.class, "buffs");
	private static final String TXT_PET = Messages.get(WndHero.class, "pet");

	private static final String TXT_HEALS = Messages.get(WndHero.class, "heals");

	private static final String TXT_EXP = Messages.get(WndHero.class, "exp");
	private static final String TXT_STR = Messages.get(WndHero.class, "str");
	private static final String TXT_BREATH = Messages.get(WndHero.class, "breath");
	private static final String TXT_SPIN = Messages.get(WndHero.class, "spin");
	private static final String TXT_STING = Messages.get(WndHero.class, "sting");
	private static final String TXT_FEATHERS = Messages.get(WndHero.class, "feathers");
	private static final String TXT_SPARKLE = Messages.get(WndHero.class, "sparkle");
	private static final String TXT_FANGS = Messages.get(WndHero.class, "fangs");
	private static final String TXT_ATTACK = Messages.get(WndHero.class, "attack");
	private static final String TXT_HEALTH = Messages.get(WndHero.class, "health");
	private static final String TXT_MOVES2 = Messages.get(WndHero.class, "moves2");
	private static final String TXT_MOVES3 = Messages.get(WndHero.class, "moves3");
	private static final String TXT_MOVES4 = Messages.get(WndHero.class, "moves4");
	private static final String TXT_HUNGER = Messages.get(WndHero.class, "hunger");

	private static final String TXT_KILLS = "Kills";

	private static final String TXT_GOLD = "总计金币";
	private static final String TXT_DEPTH = "最大深度";
	private static final String TXT_MOVES = "总回合数";


	private static final int WIDTH = 100;
	private static final int TAB_WIDTH = 40;

	private StatsTab stats;
	private PetTab pet;
	private BuffsTab buffs;
	private GodsTab gods;

	private SmartTexture icons;
	private TextureFilm film;
	
	
	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}

	public WndHero() {

		super();

		icons = TextureCache.get(Assets.BUFFS_LARGE);
		film = new TextureFilm(icons, 16, 16);

		stats = new StatsTab();
		add(stats);
		
		PET heropet = checkpet();
		
		if (heropet!=null){
		  pet = new PetTab(heropet);
		  add(pet);
		}
		
		buffs = new BuffsTab();
		add(buffs);
		
		
		add(new LabeledTab(TXT_STATS) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				stats.visible = stats.active = selected;
			};
		});
		

		if (heropet!=null){
		add(new LabeledTab(TXT_PET) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				pet.visible = pet.active = selected;
			};
		});
		}

		add(new LabeledTab(TXT_BUFFS) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				buffs.visible = buffs.active = selected;
			};
		});
		/*add(new LabeledTab(TXT_DEITY) {
		*	@Override
		*	protected void select(boolean value) {
		*		super.select(value);
		*		gods.visible = gods.active = selected;
		*	};
		});*/
		
		resize(WIDTH, (int) Math.max(stats.height(), buffs.height()));

		layoutTabs();

		select(0);
	}

	private class StatsTab extends Group {

		private final String TXT_TITLE = Messages.get(WndHero.class, "title")+hero.lvl+hero.className()+hero.heroRace.title();
		private final String TXT_CATALOGUS = Messages.get(WndHero.class, "catalogus");
		private final String TXT_JOURNAL = Messages.get(WndHero.class, "journal");

		private static final int GAP = 5;

		private float pos;

		public StatsTab() {

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();
			title.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
			title.label(Utils.format(TXT_TITLE, hero.lvl, hero.className())
					.toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnCatalogus = new RedButton(TXT_CATALOGUS) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndCatalogus());
				}

				@Override
				protected boolean onLongClick() {
					Hero heroToBuff = Dungeon.hero;
					if (Level.water[heroToBuff.pos] && heroToBuff.belongings.armor == null) {
						//heroToBuff.heroClass.playtest(heroToBuff);
					}
					return true;
				}
			};
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnJournal = new RedButton(TXT_JOURNAL) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndJournal());
				}
			};
			btnJournal.setRect(btnCatalogus.right() + 1, btnCatalogus.top(),
					btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2);
			add(btnJournal);

			pos = btnJournal.bottom() + GAP;

			statSlot(TXT_STR, hero.STR());
			statSlot(TXT_HEALTH, (hero.HP) + "/" + hero.HT);
			statSlot(TXT_EXP, hero.exp + "/" + hero.maxExp());

			if (Dungeon.hero.buff(Hunger.class) != null) {
				statSlot(TXT_HUNGER,
						(100 - Math.round(((float) Dungeon.hero.buff(Hunger.class).hungerLevel()) / 7f)) + "%");
			}


			pos += GAP;


		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	
	private class GodsTab extends Group {
		
		private static final int GAP = 2;
		
		private float pos;
		
		public GodsTab() {
			for (God god : Dungeon.discoveredGods) {
				godSlot(god);
			}
		}

		private void godSlot(God god) {

			int index = god.icon();


				Image icon = new Image(icons);
				icon.frame(film.get(index));
				icon.y = pos;
				add(icon);

				RenderedText txt = PixelScene.renderText(god.name(), 8);
				txt.x = icon.width + GAP;
				txt.y = pos + (int) (icon.height - txt.baseLine()) / 2;
				add(txt);

				pos += GAP + icon.height;
		}

		public float height() {
			return pos;
		}
		
	}

	private class BuffsTab extends Group {

		private static final int GAP = 2;

		private float pos;

		public BuffsTab() {
			for (Buff buff : hero.buffs()) {
				if (buff.icon() != BuffIndicator.NONE) {
					BuffSlot slot = new BuffSlot(buff);
					slot.setRect(0, pos, WIDTH, slot.icon.height());
					add(slot);
					pos += GAP + slot.height();
				}
			}
		}

		public float height() {
			return pos;
		}

		private class BuffSlot extends Button {
			private Buff buff;
			Image icon;
			RenderedText txt;

			public BuffSlot(Buff buff) {
				super();
				this.buff = buff;
				int index = buff.icon();

				icon = new Image(icons);
				icon.frame(film.get(index));
				icon.y = this.y;
				add(icon);

				txt = PixelScene.renderText(buff.toString(), 8);
				txt.x = icon.width + GAP;
				txt.y = this.y + (int) (icon.height - txt.baseLine()) / 2;
				add(txt);
			}

			@Override
			protected void layout() {
				super.layout();
				icon.y = this.y;
				txt.x = icon.width + GAP;
				txt.y = pos + (int) (icon.height - txt.baseLine()) / 2;
			}

			@Override
			protected void onClick() {
				GameScene.show(new WndInfoBuff(buff));
			}
		}


	}
	
	private class PetTab extends Group {

		private final String TXT_TITLE = Messages.get(WndHero.class, "p_title");
		private final String TXT_FEED = Messages.get(WndHero.class, "p_feed");
		private final String TXT_SELECT = Messages.get(WndHero.class, "p_select");
		
		private CharSprite image;
		private RenderedText name;
		private HealthBar health;
		private BuffIndicator buffs;

		private static final int GAP = 5;

		private float pos;
		
				
		public PetTab(PET heropet) {

			name = PixelScene.renderText(Utils.capitalize(heropet.name), 9);
			name.hardlight(TITLE_COLOR);
			//add(name);

			image = heropet.sprite();
			add(image);

			health = new HealthBar();
			health.level((float) heropet.HP / heropet.HT);
			add(health);

			buffs = new BuffIndicator(heropet);
			add(buffs);
		
			
			
			IconTitle title = new IconTitle();
			title.icon(image);
			title.label(Utils.format(TXT_TITLE, heropet.level, heropet.name).toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnFeed = new RedButton(TXT_FEED) {
				@Override
				protected void onClick() {
					hide();
					GameScene.selectItem(itemSelector, WndBag.Mode.ALL, TXT_SELECT);
				}
			};
			btnFeed.setRect(0, title.height(),
					btnFeed.reqWidth() + 2, btnFeed.reqHeight() + 2);
			add(btnFeed);


			pos = btnFeed.bottom() + GAP;

			statSlot(TXT_ATTACK, heropet.attackSkill(null));
			statSlot(TXT_HEALTH, heropet.HP + "/" + heropet.HT);
			statSlot(TXT_KILLS, heropet.kills);
			statSlot(TXT_EXP, heropet.level<10 ?
					heropet.experience + "/" + (heropet.level*heropet.level*heropet.level) : "顶级");
			if (heropet.type == 4 || heropet.type == 5 || heropet.type == 6 || heropet.type == 7 || heropet.type == 12) {
				statSlot(TXT_BREATH, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			} else if (heropet.type == 1) {
				statSlot(TXT_SPIN, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			} else if (heropet.type == 3) {
				statSlot(TXT_FEATHERS, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			} else if (heropet.type == 8) {
				statSlot(TXT_STING, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			} else if (heropet.type == 10 || heropet.type == 11) {
				statSlot(TXT_SPARKLE, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			} else if (heropet.type == 9) {
				statSlot(TXT_FANGS, heropet.cooldown == 0 ? Messages.get(WndHero.class, "p_ready") : (Math.round((1000 - heropet.cooldown) / 10) + "%"));
			}
			
			pos += GAP;

			
		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				feed(item);
			}
		}
	};
	
	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	private void feed(Item item) {
						
		PET heropet = checkpet();
		boolean nomnom = checkFood(heropet.type, item);
		boolean nearby = checkpetNear();
	
		if (nomnom && nearby){
		  int effect = heropet.HT-heropet.HP;
		  if (effect > 0){
		    heropet.HP=heropet.HT;
		    heropet.sprite.emitter().burst(Speck.factory(Speck.HEALING),2);
		    heropet.sprite.showStatus(CharSprite.POSITIVE, TXT_HEALS, effect);
		  }
	      heropet.cooldown=1;  
		  item.detach(hero.belongings.backpack);
		  GLog.n("Your pet eats the %s.",item.name());
		}else if (!nearby){
			GLog.n("Your pet is too far away!");
		} else {
		  GLog.n("Your pet rejects the %s.",item.name());
		  
		}		
	}

	private boolean checkFood(Integer petType, Item item){
		boolean nomnom = false;
		
		if (petType==1){ //Spider
			if (item instanceof Nut){				
				nomnom=true;
			}
		} 
		
		if (petType==2){ //steel bee
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		} 
		if (petType==3){//Velocirooster 
			if (item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof ToastedNut
				){				
				nomnom=true;
			}
		}			
		if (petType==4){//red dragon - fire
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		}
		
		if (petType==5){//green dragon - lit
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof ToastedNut
				|| item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
		
		if (petType==6){//violet dragon - poison
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				|| item instanceof Nut
				|| item instanceof ToastedNut
				){				
				nomnom=true;
			}
		}
		if (petType==7){//blue dragon - ice
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof Plant.Seed
				){				
				nomnom=true;
			}
		}
		
		if (petType==8){ //scorpion
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		} 
		
		if (petType==9){//Vorpal Bunny 
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		}
		if (petType==10){//Fairy
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
		if (petType==11){//Sugarplum Fairy
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
	return nomnom;		
	}
	
}
