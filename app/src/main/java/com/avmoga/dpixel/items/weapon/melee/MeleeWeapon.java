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

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.weapon.Weapon;
import com.watabou.utils.Random;

public class MeleeWeapon extends Weapon {

	private int tier;

	public MeleeWeapon(int tier, float acu, float dly) {
		super();

		this.tier = tier;

		ACU = acu;
		DLY = dly;

		STR = typicalSTR();

		MIN = min();
		MAX = max();
	}

	private int min() {
		return tier;
	}

	private int max() {
		return (int) ((tier * tier - tier + 10) / ACU * DLY);
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	@Override
	public Item upgrade(boolean enchant) {
		STR--;
		MIN++;
		MAX += tier;

		return super.upgrade(enchant);
	}

	public Item safeUpgrade() {
		return upgrade(enchantment != null);
	}

	@Override
	public Item degrade() {
		STR++;
		MIN--;
		MAX -= tier;
		return super.degrade();
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}

	@Override
	public String info() {

		String name = name();
		final String p = "\n\n";

		StringBuilder info = new StringBuilder(desc());

		String quality = levelKnown && level != 0 ? (level > 0 ? Messages.get(MeleeWeapon.class, "upgraded")
				: Messages.get(this, "degraded")) : "";
		info.append(p);
		info.append(Messages.get(MeleeWeapon.class, "this", name, quality, tier));

		if (levelKnown) {
			info.append(Messages.get(MeleeWeapon.class, "avgdmg",
					Math.round((MIN) * (imbue == Imbue.LIGHT ? 0.75f : (imbue == Imbue.HEAVY ? 1.5f : 1))), Math.round((MAX) * (imbue == Imbue.LIGHT ? 0.75f : (imbue == Imbue.HEAVY ? 1.5f : 1)))));

		} else {
			info.append(Messages.get(MeleeWeapon.class, "typicaldmg", min(), max(), typicalSTR()));
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append(Messages.get(MeleeWeapon.class, "pheavy"));
			}
		}

		if (DLY != 1f) {
			info.append(Messages.get(MeleeWeapon.class, "rather") + (DLY < 1f ? Messages.get(MeleeWeapon.class, "fast") : Messages.get(MeleeWeapon.class, "slow")));
			if (ACU != 1f) {
				if ((ACU > 1f) == (DLY < 1f)) {
					info.append(Messages.get(MeleeWeapon.class, "and"));
				} else {
					info.append(Messages.get(MeleeWeapon.class, "but"));
				}
				info.append(ACU > 1f ? Messages.get(MeleeWeapon.class, "ac") : Messages.get(MeleeWeapon.class, "inac"));
			}
			info.append(Messages.get(MeleeWeapon.class, "weapon"));
		} else if (ACU != 1f) {
			info.append(Messages.get(MeleeWeapon.class, "rather")
					+ (ACU > 1f ? Messages.get(MeleeWeapon.class, "ac") : Messages.get(MeleeWeapon.class, "inac")) + Messages.get(MeleeWeapon.class, "weapon"));
		}
		switch (imbue) {
			case LIGHT:
				info.append(Messages.get(MeleeWeapon.class, "lighter"));
				break;
			case HEAVY:
				info.append(Messages.get(MeleeWeapon.class, "heavier"));
				break;
			case NONE:
		}

		if (enchantment != null) {
			info.append(Messages.get(MeleeWeapon.class, "enchanted"));
		}

		if (reinforced) {
			info.append(Messages.get(MeleeWeapon.class, "reinforced"));
		}

		if (levelKnown
			//&& Dungeon.hero.belongings.backpack.items.contains(this)
		) {
			if (STR > Dungeon.hero.STR()) {
				info.append(p);
				info.append(Messages.get(MeleeWeapon.class, "decreased", name));
			}
			if (STR < Dungeon.hero.STR()) {
				info.append(p);
				info.append(Messages.get(MeleeWeapon.class, "increased", name));
			}
		}

		if (isEquipped(Dungeon.hero)) {
			info.append(p);
			info.append(Messages.get(MeleeWeapon.class, "atready", name)
					+ (cursed ? Messages.get(MeleeWeapon.class, "equipcursed") : ""));
		} else {
			if (cursedKnown && cursed) {
				info.append(p);
				info.append(Messages.get(MeleeWeapon.class, "knowncursed", name));
			}
		}

		return info.toString();
	}

	@Override
	public int price() {
		int price = 20 * (1 << (tier - 1));
		if (enchantment != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	@Override
	public Item random() {
		super.random();

		if (Random.Int(10 + level) == 0) {
			enchant();
		}

		return this;
	}
}
