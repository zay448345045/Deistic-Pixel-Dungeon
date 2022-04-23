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
package com.avmoga.dpixel.actors.mobs.npcs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.Statistics;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.sprites.ImpSprite;
import com.avmoga.dpixel.utils.Utils;

public class ImpShopkeeper extends Shopkeeper {

	private static final String TXT_GREETINGS = "你好，%s！ 一旦你清除了那恼人的古神，我会给你一个关于书籍的交易。";
	private static final String TXT_GREETINGS2 = "你做到了....现在你需要这些书获得三相碎片，来追逐Yog在地牢中的隐藏位置……";
	public static final String TXT_THIEF = "我以为我可以信任你！";

	{
		name = Messages.get(Imp.class, "name");
		spriteClass = ImpSprite.class;
	}

	private boolean seenBefore = false;
	private boolean killedYog = false;

	@Override
	protected boolean act() {

		if (!seenBefore && Dungeon.visible[pos]) {
			yell(Utils.format(TXT_GREETINGS, Dungeon.hero.givenName()));
			seenBefore = true;
		}
		
		if (Statistics.amuletObtained && !killedYog && Dungeon.visible[pos]) {
			yell(Utils.format(TXT_GREETINGS2, Dungeon.hero.givenName()));
			killedYog = true;
		}

		return super.act();
	}

	@Override
	public void flee() {
		for (Heap heap : Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
				heap.destroy();
			}
		}

		destroy();

		sprite.emitter().burst(Speck.factory(Speck.WOOL), 15);
		sprite.killAndErase();
	}

	@Override
	public String description() {
		return Messages.get(Imp.class, "desc");
	}
}
