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
package com.avmoga.dpixel.items.armor;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Burning;
import com.avmoga.dpixel.actors.buffs.Roots;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroClass;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class MageArmor extends ClassArmor {

	private static final String AC_SPECIAL = Messages.get(MageArmor.class, "ac_special");

	private static final String TXT_NOT_MAGE = Messages.get(MageArmor.class, "not_mage");

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.ARMOR_MAGE;
	}

	@Override
	public String special() {
		return AC_SPECIAL;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Burning.class).reignite(mob);
				Buff.prolong(mob, Roots.class, 3);
			}
		}

		curUser.HP -= (curUser.HP / 3);

		curUser.spend(Actor.TICK);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public boolean doEquip(Hero hero) {
		if (hero.heroClass == HeroClass.MAGE) {
			return super.doEquip(hero);
		} else {
			GLog.w(TXT_NOT_MAGE);
			return false;
		}
	}
}