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
package com.avmoga.dpixel.items.weapon.melee;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Chainsaw extends MeleeWeapon {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.CHAINSAW;
		reinforced = true;
		cursed = true;
	}
	
	public Boolean turnedOn = false;
	public static final String AC_ON = Messages.get(Chainsaw.class, "ac_on");
	public static final String AC_OFF = Messages.get(Chainsaw.class, "ac_off");
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if(turnedOn){actions.add(AC_OFF);}else{actions.add(AC_ON);}
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_ON)) {
			turnedOn = true;
			GLog.i(Messages.get(this, "on"));
			hero.next();

		} else if (action.equals(AC_OFF)) {
			turnedOn = false;
			GLog.i(Messages.get(this, "off"));
			hero.next();
		} else {
			super.execute(hero, action);
		}
	}

	public Chainsaw() {
		super(1, 1.2f, .75f);
	}

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}
	
	private static final String TURNEDON = "turnedOn";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TURNEDON, turnedOn);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		turnedOn = bundle.getBoolean(TURNEDON);
	}
	
}
