package com.avmoga.dpixel.actors.hero;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Badges.Badge;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.blobs.Freezing;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Ooze;
import com.avmoga.dpixel.actors.buffs.Poison;
import com.avmoga.dpixel.items.TomeOfSpecialty;
import com.avmoga.dpixel.items.scrolls.ScrollOfRegrowth;
import com.watabou.utils.Bundle;


public enum HeroRace {
	
	DWARF("dwarf"), HUMAN("human"), WRAITH("wraith"), GNOLL("gnoll");
	
	private String title;
	public static HeroRace race;

	public String title() {
		return Messages.get(HeroRace.class, title);
	}
	
	private HeroRace(String race){
		this.title = race;
	}
	public static final String[] DWF_PERKS = {
			"一个机智狡猾又残忍野蛮的种族。",
			"矮人获得随额外力量增加而增加的伤害加成。",
			"矮人坚韧的皮肤为他们提供针对所有元素的伤害吸收。",
			"矮人易受腐败影响，他们会承受更多恶魔的伤害。",
			"矮人是唯一知道如何发挥战神之链全部力量的种族。"};
	
	public static final String[] HUM_PERKS = {
			"社会地位与智力弥补了这个种族在天生防御中的缺陷。",
			"人类是难以置信的宗教生物，并且在与神打交道中获得加成（暂未实装）",//Will be added with gods
			"人类相当狡猾，并且能发挥其他种族特殊物品的基本功能。",
			"人类有点虚弱，开局生命上限降低两点。",
			"人类是唯一知道如何发挥通信中继器全部力量的种族。"
	};
	
	public static final String[] WRA_PERKS = {
			"纯粹的阴影构成的生物，没人知道幽灵如何出现在地牢中，只知道他们补习一切离开这。",
			"暗淡的的幽灵会获得一小点被动闪避加成。",
			"非实体而又模糊的幽灵使它们更难被冻结或点燃。",
			"他们的黑暗倾向使他们会受到闪光卷轴、诅咒移除卷轴和祝福的露水瓶的轻微伤害。",
			"幽灵是唯一知道如何发挥幽灵金属护符全部力量的种族。"
	};
	
	public static final String[] GNO_PERKS = {
			"豺狼人曾经是一个爱好和平的种族，但其他种族的发展与豺狼人家乡的毁灭迫使他们拿起武器。",
			"豺狼人强大的精神力量使他们对魅惑有抵抗力。",
			"豺狼人开局携带他们家乡的遗产-再生卷轴。",
			"豺狼人的小型身体需要更多食物，也会因食物缺乏收到更多伤害。",
			"豺狼人是唯一知道如何发挥木质神像全部力量的种族。",
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
