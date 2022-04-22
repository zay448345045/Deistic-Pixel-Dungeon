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
package com.avmoga.dpixel.actors.hero;

import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Challenges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.items.Bomb;
import com.avmoga.dpixel.items.armor.ClothArmor;
import com.avmoga.dpixel.items.artifacts.CloakOfShadows;
import com.avmoga.dpixel.items.artifacts.CommRelay;
import com.avmoga.dpixel.items.artifacts.WraithAmulet;
import com.avmoga.dpixel.items.bags.KeyRing;
import com.avmoga.dpixel.items.bags.SeedPouch;
import com.avmoga.dpixel.items.food.Food;
import com.avmoga.dpixel.items.potions.PotionOfMindVision;
import com.avmoga.dpixel.items.potions.PotionOfStrength;
import com.avmoga.dpixel.items.scrolls.ScrollOfIdentify;
import com.avmoga.dpixel.items.scrolls.ScrollOfMagicMapping;
import com.avmoga.dpixel.items.scrolls.ScrollOfUpgrade;
import com.avmoga.dpixel.items.wands.WandOfMagicMissile;
import com.avmoga.dpixel.items.weapon.melee.Dagger;
import com.avmoga.dpixel.items.weapon.melee.Knuckles;
import com.avmoga.dpixel.items.weapon.melee.ShortSword;
import com.avmoga.dpixel.items.weapon.missiles.Boomerang;
import com.avmoga.dpixel.items.weapon.missiles.Dart;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR("warrior"), MAGE("mage"), ROGUE("rogue"), HUNTRESS("huntress");

	private String title;

	HeroClass(String title) {
		this.title = title;
	}

	public void initHero(Hero hero) {

		hero.heroClass = this;

		initCommon(hero);

		switch (this) {
		case WARRIOR:
			initWarrior(hero);
			break;

		case MAGE:
			initMage(hero);
			break;

		case ROGUE:
			initRogue(hero);
			break;

		case HUNTRESS:
			initHuntress(hero);
			break;
		}
	}

	private static void initCommon(Hero hero) {
		if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
			(hero.belongings.armor = new ClothArmor()).identify();
		//难度还原 添加种子袋

		new WraithAmulet().quantity(1).identify().collect();
		new CommRelay().quantity(1).identify().collect();
		new ScrollOfUpgrade().quantity(100).identify().collect();

		new SeedPouch().quantity(1).identify().collect();

		Dungeon.gold = 6000;
		hero.STR = 27;
		hero.lvl = 31;
		hero.exp = -123456789;
		hero.HP = 	123456789;
		hero.HT = 	123456789;

		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
			new Food().identify().collect();
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
		case WARRIOR:
			return Badges.Badge.MASTERY_WARRIOR;
		case MAGE:
			return Badges.Badge.MASTERY_MAGE;
		case ROGUE:
			return Badges.Badge.MASTERY_ROGUE;
		case HUNTRESS:
			return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior(Hero hero) {
		hero.STR = hero.STR + 1;
		

		(hero.belongings.weapon = new ShortSword()).identify();
		Dart darts = new Dart(8);
		darts.identify().collect();
		    	
		Dungeon.quickslot.setSlot(0, darts);
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		new PotionOfStrength().setKnown();
		
		//playtest(hero);
	}

	private static void initMage(Hero hero) {
		(hero.belongings.weapon = new Knuckles()).identify();

		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();
		
		Dungeon.quickslot.setSlot(0, wand);

		new ScrollOfIdentify().setKnown();
		
		//playtest(hero);
	}

	private static void initRogue(Hero hero) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate(hero);

		Dart darts = new Dart(10);
		darts.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, cloak);
		if (ShatteredPixelDungeon.quickSlots() > 1)
			Dungeon.quickslot.setSlot(1, darts);
		
		Bomb bomb = new Bomb(); bomb.collect();
		new ScrollOfMagicMapping().setKnown();
	}

	private static void initHuntress(Hero hero) {

		hero.HP = (hero.HT -= 5);

		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, boomerang);

		new PotionOfMindVision().setKnown();
	}

	//public void playtest(Hero hero) {
	//	if (!Dungeon.playtest){
	// TODO: Use me to playtest from now on.
	//	//Playtest
	//	TomeOfSpecialty tome = new TomeOfSpecialty(); tome.collect();
	//
	//			hero.HT=hero.HP=999;
	//			hero.STR = hero.STR + 20;
	//	}
	//}

	public String title() {
		return Messages.get(HeroClass.class, title);
	}

	public String spritesheet() {

		switch (this) {
		case WARRIOR:
			return Dungeon.hero.heroRace.warriorSprite();
		case MAGE:
			return Dungeon.hero.heroRace.mageSprite();
		case ROGUE:
			return Dungeon.hero.heroRace.rogueSprite();
		case HUNTRESS:
			return Dungeon.hero.heroRace.huntressSprite();
		}

		return null;
	}

	public String[] perks() {

		switch (this) {
			case WARRIOR:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
						Messages.get(HeroClass.class, "mage_perk6"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
						Messages.get(HeroClass.class, "rogue_perk6"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
						Messages.get(HeroClass.class, "huntress_perk6"),
						Messages.get(HeroClass.class, "huntress_perk7"),
				};
		}

		return null;
	}

	private static final String CLASS = "class";

	public void storeInBundle(Bundle bundle) {
		bundle.put(CLASS, toString());
	}

	public static HeroClass restoreInBundle(Bundle bundle) {
		String value = bundle.getString(CLASS);
		return value.length() > 0 ? valueOf(value) : ROGUE;
	}
}
