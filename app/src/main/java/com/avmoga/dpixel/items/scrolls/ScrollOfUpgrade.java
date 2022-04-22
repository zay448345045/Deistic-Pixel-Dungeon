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
package com.avmoga.dpixel.items.scrolls;

import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.windows.WndBag;

public class ScrollOfUpgrade extends InventoryScroll {

	//private static final String TXT_LOOKS_BETTER = "your %s certainly looks better now";

	{
		name = Messages.get(this, "name");
		inventoryTitle = Messages.get(this, "inv_title");
		mode = WndBag.Mode.UPGRADEABLE;
		consumedValue = 15;
		bones = true;
	}
	@Override
	public void detonate(Heap heap){
		for(Item item : heap.items){
			if (item.isUpgradable()){
				item.upgrade();
				break;
			}
		}
	}
	@Override
	public void detonateIn(Hero hero){
		Item item = hero.belongings.randomUnequipped();
		if(item.isUpgradable()){
			item.upgrade();
		}
		else{
			hero.earnExp(200);
		}
	}
	@Override
	protected void onItemSelected(Item item) {

		ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
		item.upgrade();

		upgrade(curUser);
		//GLog.p(TXT_LOOKS_BETTER, item.name());

		Badges.validateItemLevelAquired(item);
	}

	public static void upgrade(Hero hero) {
		hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}
}
