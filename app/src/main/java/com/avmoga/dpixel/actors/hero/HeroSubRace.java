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
			"warlock",
			"A _Warlock_ has an affinity to magic, increasing the duration of additional effects on wands."), MONK(
			"monk",
			"The _Monk_ gains damage penetration when using his fists to deal damage."),

	DEMOLITIONIST("demolitionist",
			"The _demolitionist_ gains access to a wide variety of new bombs."), MERCENARY(
			"mercenary",
			"The _mercenary_ is smart about reading his enemies' attacks, and counterattacks at twice the speed."),

	RED(
			"red wraith",
			"_Red_ _Wraiths_ are surprisingly lucky."), BLUE(
			"blue wraith",
			"_Blue_ _Wraiths_ gain vision of all enemies that they have hit."),

	SHAMAN("shaman",
			"With an affinity to nature, the _Shaman_ gains natural healing when traveling through tall grass."), BRUTE(
			"brute",
			"The warriors of their species, the _Brute_ gains a power buff for all enemies around him.");

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
