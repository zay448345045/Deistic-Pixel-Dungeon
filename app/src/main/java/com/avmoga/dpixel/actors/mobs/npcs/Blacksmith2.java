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

import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.items.AdamantArmor;
import com.avmoga.dpixel.items.AdamantRing;
import com.avmoga.dpixel.items.AdamantWand;
import com.avmoga.dpixel.items.AdamantWeapon;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.armor.Armor;
import com.avmoga.dpixel.items.quest.DarkGold;
import com.avmoga.dpixel.items.rings.Ring;
import com.avmoga.dpixel.items.wands.Wand;
import com.avmoga.dpixel.items.weapon.melee.MeleeWeapon;
import com.avmoga.dpixel.items.weapon.missiles.Boomerang;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.BlacksmithSprite;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndBlacksmith2;
import com.avmoga.dpixel.windows.WndQuest;

public class Blacksmith2 extends NPC {


	private static final String TXT_LOOKS_BETTER = "你的 %s 被注入了魔法能量。";
	private static final String TXT2 = "我和我的哥哥制做了这个地牢里所有的优质物品。他可以通过熔炼两个武器来增强其中的一个，而我的专长是用精金突破一个物品的升级限制。当你有_50_块暗金矿和一些精金时再回到我这来，我会为你锻造它们。" ;

	private static final String TXT3 = "看起来你这儿有一些精金，我可以使用这些精金帮你移除某些物品的等级限制。但是，我需要大量的暗金矿来工作。当你有_50_块暗金矿时再回到我这儿来。" ;



	{
		name = Messages.get(Blacksmith2.class, "name");
		spriteClass = BlacksmithSprite.class;
	}


	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public void interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);


		DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
		if (!checkAdamant()) {
			tell(TXT2);
		} else if (gold == null || gold.quantity() < 50) {
			tell(TXT3);
		} else if (checkAdamant() && gold != null && gold.quantity() > 49){
		GameScene.show(new WndBlacksmith2(this, Dungeon.hero));
		} else {
			tell(TXT2);
		}

	}

	public static String verify(Item item1, Item item2) {

		if (item1 == item2) {
			return "选择两个不同的物品，不是两次一样的！";
		}

		if (!item1.isIdentified()) {
			return "我需要知道我在拿什么干活，先鉴定它们！";
		}

		if (item1.cursed) {
			return "我可不碰被诅咒的东西！";
		}

		if (item1.reinforced) {
			return "这个东西已经被界限突破过了！";
		}

		if (item1.level < 0) {
			return "这简直就是个垃圾，质量太烂了！";
		}

		if (!item1.isUpgradable()) {
			return "我不能锻造这些东西！";
		}

		if(item1 instanceof Armor && item2 instanceof AdamantArmor){
			return null;
		}

		if(item1 instanceof MeleeWeapon && item2 instanceof AdamantWeapon){
			return null;
		}

		if(item1 instanceof Boomerang && item2 instanceof AdamantWeapon){
			return null;
		}

		if(item1 instanceof Wand && item2 instanceof AdamantWand){
			return null;
		}

		if(item1 instanceof Ring && item2 instanceof AdamantRing){
			return null;
		}

		return "这没用。选择一个物品和一个与之匹配的精金物品。";

	}

	public static void upgrade(Item item1, Item item2) {

		item1.reinforced=true;
		item2.detach(Dungeon.hero.belongings.backpack);
		DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
		if (gold == null || gold.quantity() > 49) {
			gold.detach(Dungeon.hero.belongings.backpack,50);
			if(!(Dungeon.hero.belongings.getItem(DarkGold.class).quantity() > 0)){
				gold.detachAll(Dungeon.hero.belongings.backpack);
			}
		}

		GLog.p(TXT_LOOKS_BETTER, item1.name());
		Dungeon.hero.spendAndNext(2f);
		Badges.validateItemLevelAquired(item1);

	}


	private void tell(String text) {
		GameScene.show(new WndQuest(this, text));
	}


	public static boolean checkAdamant() {
		AdamantArmor armor1 = Dungeon.hero.belongings.getItem(AdamantArmor.class);
		AdamantWeapon weapon1 = Dungeon.hero.belongings.getItem(AdamantWeapon.class);
		AdamantRing ring1 = Dungeon.hero.belongings.getItem(AdamantRing.class);
		AdamantWand wand1 = Dungeon.hero.belongings.getItem(AdamantWand.class);

		if(armor1!=null ||  weapon1!=null || ring1!=null || wand1!=null) {
			return true;
		}
		   return false;
	}




	@Override
	public int defenseSkill(Char enemy) {
		return 1000;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public String description() {
		return "This troll blacksmith looks like all trolls look: he is tall and lean, and his skin resembles stone "
				+ "in both color and texture. The troll blacksmith is tinkering with unproportionally small tools.";
	}


}
