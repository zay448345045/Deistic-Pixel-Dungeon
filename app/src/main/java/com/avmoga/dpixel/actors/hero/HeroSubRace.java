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

import com.avmoga.dpixel.Assets;
import com.watabou.utils.Bundle;

public enum HeroSubRace {

	NONE(null, null),

	WARLOCK(
			"术士",
			"_术士_与魔力的紧密联系提升他们法杖额外效果的持续时间。" ), MONK(
			"武僧",
			"_武僧_在空手造成伤害时获得伤害穿透。"),

	DEMOLITIONIST("毁灭者",
			"_毁灭者_会使用非常多种不同的新型炸弹"), MERCENARY(
			"雇佣兵",
			"_雇佣兵_可以解读敌方的攻击，并且以双倍速度反击。"),

	RED(
			"红色幽灵",
			"_红色幽灵_非常的幸运"), BLUE(
			"蓝色幽灵",
			"_蓝色幽灵_可以获得他们击中的所有敌人的视野"),

	SHAMAN("豺狼萨满",
			"由于对自然的亲近，_豺狼萨满_在高草中行走时可以获得自然疗愈"), BRUTE(
			"豺狼暴徒",
			"_豺狼暴徒_作为种族中的勇士，在周围有任何敌人时获得能力加成");

	private String title;
	private String desc;

	private HeroSubRace(String title, String desc) {
		this.title = title;
		this.desc = desc;
	}

	public String title() {
		return title;
	}

	public String desc() {
		return desc;
	}

	private static final String SUBRACE = "subRace";

	public void storeInBundle(Bundle bundle) {
		bundle.put(SUBRACE, toString());
	}

	public static HeroSubRace restoreInBundle(Bundle bundle) {
		String value = bundle.getString(SUBRACE);
		return valueOf(value);

	}

	public String warriorSprite() {
		switch(this){
		case WARLOCK:
			return Assets.WARLOCKWARRIOR;
		case MONK:
			return Assets.MONKWARRIOR;
		case RED:
			return Assets.SPECTERWARRIOR;
		case BLUE:
			return Assets.SERAPHWARRIOR;
		case SHAMAN:
			return Assets.SHAMANWARRIOR;
		case BRUTE:
			return Assets.BRUTEWARRIOR;
		default:
			return null;
		}
	}
	public String mageSprite() {
		switch(this){
		case WARLOCK:
			return Assets.WARLOCKMAGE;
		case MONK:
			return Assets.MONKMAGE;
		case RED:
			return Assets.SPECTERMAGE;
		case BLUE:
			return Assets.SERAPHMAGE;
		case SHAMAN:
			return Assets.SHAMANMAGE;
		case BRUTE:
			return Assets.BRUTEMAGE;
		default:
			return null;
		}
	}
	public String rogueSprite() {
		switch(this){
		case WARLOCK:
			return Assets.WARLOCKROGUE;
		case MONK:
			return Assets.MONKROGUE;
		case RED:
			return Assets.SPECTERROGUE;
		case BLUE:
			return Assets.SERAPHROGUE;
		case SHAMAN:
			return Assets.SHAMANROGUE;
		case BRUTE:
			return Assets.BRUTEROGUE;
		default:
			return null;
		}
	}
	public String huntressSprite() {
		switch(this){
		case WARLOCK:
			return Assets.WARLOCKHUNTRESS;
		case MONK:
			return Assets.MONKHUNTRESS;
		case RED:
			return Assets.SPECTERHUNTRESS;
		case BLUE:
			return Assets.SERAPHHUNTRESS;
		case SHAMAN:
			return Assets.SHAMANHUNTRESS;
		case BRUTE:
			return Assets.BRUTEHUNTRESS;
		default:
			return null;
		}
	}

}
