package com.avmoga.dpixel.actors.hero;

import com.avmoga.dpixel.Badges.Badge;
import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.blobs.Freezing;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Ooze;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.items.TomeOfSpecialty;
import com.avmoga.dpixel.items.scrolls.ScrollOfRegrowth;
import com.watabou.utils.Bundle;


public enum HeroRace {
	
	DWARF("dwarf"), HUMAN("human"), WRAITH("wraith"), GNOLL("gnoll");
	
	private String title;
	public static HeroRace race;
	
	public String title() {
		return title;
	}
	
	private HeroRace(String race){
		this.title = race;
	}
	public static final String[] DWF_PERKS = {
			"A species that is just as wise and cunning as it is cruel and brutal.",
			"Dwarves gain an increased damage boost from excess strength.",
			"Dwarves' tough skin gives them a minor reduction in damage from all elemental sources.",
			"Dwarves are susceptible to corruption; they will take additional damage from evils.",
			"Dwarves are the only race who know how to use the Chains of Ares to their maximum potential."};
	
	public static final String[] HUM_PERKS = {
			"What this species lacks in natural defenses, it makes up for in social standing and intelligence.",
			"Humans are incredibly religion creatures, and gain a bonus in dealing with Gods (Not yet implemented).",//Will be added with gods
			"Humans are fairly cunning, and able to use the basic functions of other races' special items.",
			"Humans are a tad frail, and start with 2 less health.",
			"Humans are the only race who know how to use Communication Relays to their maximum potential."
	};
	
	public static final String[] WRA_PERKS = {
			"Beings of pure shadow, nobody knows how Wraiths manifested in the dungeon; only that they will stop at nothing to leave.",
			"Wraiths are dimming creatures, and recieve a small passive increase in evasion chance.",
			"The insubstantial, shadowy nature of Wraiths make them harder to freeze or ignite.",
			"Their dark disposition means that they are hurt slightly by the light of Scrolls of Remove Curse and dew bottle Blessings.",
			"Wraiths are the only race who know how to use Wraithmetal Amulets to their maximum potential."
	};
	
	public static final String[] GNO_PERKS = {
			"Once a peaceful race, the advances of other races and the destruction of their homelands "
			+ "has forced them to take up arms.",
			"Gnolls have a strong mental power that gives them resistance to being Charmed.",//Added
			"Gnolls start with a scroll of regrowth, as a legacy from their homeland.", //Added
			"The Gnoll's small bodies require extra nutrition, and they are hurt more by lack of food.",//Added 
			"Gnolls are the only race who know how to use Wooden Effigies to their maximum potential.", 
	};
	
	public void raceHero(Hero hero){
		
		hero.heroRace = this;
		
		switch(this){
		case DWARF:
			initDwarf(hero);
			break;
		case HUMAN:
			initHuman(hero);
			break;
		case WRAITH:
			initWraith(hero);
			break;
		case GNOLL:
			initGnoll(hero);
			break;
		}
		
	}
	
	
	private static void initDwarf(Hero hero){
		hero.heroRace = DWARF;
		hero.resistances().add(Burning.class);
		hero.resistances().add(Freezing.class);
		hero.resistances().add(Ooze.class);
		hero.resistances().add(Burning.class);
		hero.resistances().add(Poison.class);
		hero.resistances().add(Burning.class);
		if(Badges.isUnlocked(Badges.Badge.MASTERY_DWARF)){
			TomeOfSpecialty special = new TomeOfSpecialty(); special.collect();
		}
	}
	private static void initHuman(Hero hero){
		hero.heroRace = HUMAN;
		hero.HP = (hero.HT -= 2);
		if(Badges.isUnlocked(Badges.Badge.MASTERY_HUMAN)){
			TomeOfSpecialty special = new TomeOfSpecialty(); special.collect();
		}
	}
	private static void initWraith(Hero hero){
		hero.heroRace = WRAITH;
		hero.resistances().add(Burning.class);
		hero.resistances().add(Freezing.class);
		if(Badges.isUnlocked(Badges.Badge.MASTERY_WRAITH)){
			TomeOfSpecialty special = new TomeOfSpecialty(); special.collect();
		}
	}
	private static void initGnoll(Hero hero){
		hero.heroRace = GNOLL;
		ScrollOfRegrowth regrow = new ScrollOfRegrowth();
		regrow.setKnown();
		regrow.collect();
		if(Badges.isUnlocked(Badges.Badge.MASTERY_GNOLL)){
			TomeOfSpecialty special = new TomeOfSpecialty(); special.collect();
		}
	}
	public String[] perks(){
		switch(this){
		case DWARF:
			return DWF_PERKS;
		case HUMAN:
			return HUM_PERKS;
		case WRAITH:
			return WRA_PERKS;
		case GNOLL:
			return GNO_PERKS;
		}
		
		return null;
	}
	private static final String RACE = "race";
	
	public void storeIntoBundle(Bundle bundle){
		bundle.put(RACE, toString());
	}
	public static HeroRace restoreInBundle(Bundle bundle){
		String value = bundle.getString(RACE);
		return value.length() > 0 ? valueOf(value) : HUMAN;
	}

	public Badge masteryBadge() {
		switch (this) {
		case HUMAN:
			return Badges.Badge.MASTERY_HUMAN;
		case DWARF:
			return Badges.Badge.MASTERY_DWARF;
		case WRAITH:
			return Badges.Badge.MASTERY_WRAITH;
		case GNOLL:
			return Badges.Badge.MASTERY_GNOLL;
		}
		return null;
	}

	public String warriorSprite() {
		if(Dungeon.hero.subRace == HeroSubRace.NONE){
			switch(this){
			case DWARF:
				return Assets.DWARFWARRIOR;
			case HUMAN:
				return Assets.HUMANWARRIOR;
			case WRAITH:
				return Assets.WRAITHWARRIOR;
			case GNOLL:
				return Assets.GNOLLWARRIOR;
			}
			return null;
		} else {
			return Dungeon.hero.subRace.warriorSprite();
		}
	}
	public String mageSprite() {
		if(Dungeon.hero.subRace == HeroSubRace.NONE){
			switch(this){
			case DWARF:
				return Assets.DWARFMAGE;
			case HUMAN:
				return Assets.HUMANMAGE;
			case WRAITH:
				return Assets.WRAITHMAGE;
			case GNOLL:
				return Assets.GNOLLMAGE;
			}
			return null;
		} else {
			return Dungeon.hero.subRace.mageSprite();
		}
	}
	public String rogueSprite() {
		if(Dungeon.hero.subRace == HeroSubRace.NONE){
			switch(this){
			case DWARF:
				return Assets.DWARFROGUE;
			case HUMAN:
				return Assets.HUMANROGUE;
			case WRAITH:
				return Assets.WRAITHROGUE;
			case GNOLL:
				return Assets.GNOLLROGUE;
			}
			return null;
		} else {
			return Dungeon.hero.subRace.rogueSprite();
		}
	}
	public String huntressSprite() {
		if(Dungeon.hero.subRace == HeroSubRace.NONE){
			switch(this){
			case DWARF:
				return Assets.DWARFHUNTRESS;
			case HUMAN:
				return Assets.HUMANHUNTRESS;
			case WRAITH:
				return Assets.WRAITHHUNTRESS;
			case GNOLL:
				return Assets.GNOLLHUNTRESS;
			}
			return null;
		} else {
			return Dungeon.hero.subRace.huntressSprite();
		}
	}
}
