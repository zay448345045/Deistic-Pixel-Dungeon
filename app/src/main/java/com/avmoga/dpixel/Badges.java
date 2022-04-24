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
package com.avmoga.dpixel;

import android.content.Context;

import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.mobs.Acidic;
import com.avmoga.dpixel.actors.mobs.Albino;
import com.avmoga.dpixel.actors.mobs.Bandit;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.actors.mobs.Senior;
import com.avmoga.dpixel.actors.mobs.Shielded;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.artifacts.Artifact;
import com.avmoga.dpixel.items.bags.PotionBandolier;
import com.avmoga.dpixel.items.bags.ScrollHolder;
import com.avmoga.dpixel.items.bags.SeedPouch;
import com.avmoga.dpixel.items.bags.WandHolster;
import com.avmoga.dpixel.items.potions.Potion;
import com.avmoga.dpixel.items.rings.Ring;
import com.avmoga.dpixel.items.scrolls.Scroll;
import com.avmoga.dpixel.items.wands.Wand;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Badges {

	public static enum Badge {
		MONSTERS_SLAIN_1("击杀 10 个敌人", 0), MONSTERS_SLAIN_2(
				"击杀 50 个敌人", 1), MONSTERS_SLAIN_3("击杀 150 个敌人", 2), MONSTERS_SLAIN_4(
				"击杀 250 个敌人", 3), GOLD_COLLECTED_1("收集 100 金币",
				4), GOLD_COLLECTED_2("收集 500 金币", 5), GOLD_COLLECTED_3(
				"收集 2500 金币", 6), GOLD_COLLECTED_4(
				"收集 7500 金币", 7), LEVEL_REACHED_1("升级 到 6级", 8), LEVEL_REACHED_2(
				"升级 到 12级", 9), LEVEL_REACHED_3("升级 到 18级", 10), LEVEL_REACHED_4(
				"升级 到 24级", 11), ALL_POTIONS_IDENTIFIED(
				"全部药水鉴定", 16), ALL_SCROLLS_IDENTIFIED(
				"全部卷轴鉴定", 17), ALL_RINGS_IDENTIFIED(
				"全部戒指鉴定", 18), ALL_WANDS_IDENTIFIED(
				"全部法杖鉴定", 19), ALL_ITEMS_IDENTIFIED(
				"全物品鉴定", 35, true), BAG_BOUGHT_SEED_POUCH, BAG_BOUGHT_SCROLL_HOLDER, BAG_BOUGHT_POTION_BANDOLIER,
		BAG_BOUGHT_WAND_HOLSTER, ALL_BAGS_BOUGHT(
				"获得所有包裹", 23), DEATH_FROM_FIRE("死于火焰", 24), DEATH_FROM_POISON(
				"死于中毒", 25), DEATH_FROM_GAS(
				"死于毒气", 26), DEATH_FROM_HUNGER(
				"饥饿至死", 27), DEATH_FROM_GLYPH(
				"死于延缓伤害", 57), DEATH_FROM_FALLING(
				"死于坠落楼层", 59), DEATH_FROM_DECAY
				("腐烂致死", 29), YASD(
				"死于火焰，毒，毒气，饥饿，护甲刻印，腐烂，和坠落", 34, true), BOSS_SLAIN_1_WARRIOR, BOSS_SLAIN_1_MAGE, BOSS_SLAIN_1_ROGUE,
		BOSS_SLAIN_1_HUNTRESS, BOSS_SLAIN_1(
				"斩杀第1个Boss", 12), BOSS_SLAIN_2("斩杀第2个Boss", 13), BOSS_SLAIN_3(
				"斩杀第3个Boss", 14), BOSS_SLAIN_4("斩杀第4个Boss", 15), BOSS_SLAIN_1_ALL_CLASSES(
				"分别使用战士、法师、盗贼和女猎手斩杀第1个Boss", 32, true), BOSS_SLAIN_3_GLADIATOR, BOSS_SLAIN_3_BERSERKER, BOSS_SLAIN_3_WARLOCK, BOSS_SLAIN_3_BATTLEMAGE, BOSS_SLAIN_3_FREERUNNER, BOSS_SLAIN_3_ASSASSIN, BOSS_SLAIN_3_SNIPER, BOSS_SLAIN_3_WARDEN, BOSS_SLAIN_3_ALL_SUBCLASSES(
				"分别使用战士、法师、盗贼和女猎手斩杀第3个Boss", 33, true), THIEVES_ARMBAND(
				"获得神偷袖章", 20), CLOAK_OF_THORNS(
				"获得荆棘斗篷", 21), STRENGTH_ATTAINED_1(
				"达到13点力量", 40), STRENGTH_ATTAINED_2(
				"达到15点力量", 41), STRENGTH_ATTAINED_3(
				"达到17点力量", 42), STRENGTH_ATTAINED_4(
				"达到19点力量", 43), FOOD_EATEN_1(
				"吃掉10个食物", 44), FOOD_EATEN_2(
				"吃掉20个食物", 45), FOOD_EATEN_3(
				"吃掉30个食物", 46), FOOD_EATEN_4(
				"吃掉40个食物", 47), MASTERY_WARRIOR, MASTERY_MAGE, MASTERY_ROGUE, MASTERY_HUNTRESS, ITEM_LEVEL_1(
				"获得3级物品", 48), ITEM_LEVEL_2(
				"获得6级物品", 49), ITEM_LEVEL_3(
				"获得9级物品", 50), ITEM_LEVEL_4(
				"获得12级物品", 51), RARE_ALBINO, RARE_BANDIT, RARE_SHIELDED, RARE_SENIOR, RARE_ACIDIC, RARE(
				"杀死所有稀有怪物", 37, true), VICTORY_WARRIOR, VICTORY_MAGE, VICTORY_ROGUE, VICTORY_HUNTRESS, VICTORY(
				"取得Yendor护符", 22), VICTORY_ALL_CLASSES(
				"分别使用战士，法师，盗贼和女猎手取得Yendor护符",
				36, true), MASTERY_COMBO("达成7连击", 56), POTIONS_COOKED_1(
				"一场游戏中酿造3瓶药水", 52), POTIONS_COOKED_2("一场游戏中酿造6瓶药水",
				53), POTIONS_COOKED_3("一场游戏中酿造9瓶药水", 54), POTIONS_COOKED_4(
				"一场游戏中酿造12瓶药水", 55), NO_MONSTERS_SLAIN(
				"在不击杀怪物的情况下通过楼层", 28), GRIM_WEAPON(
				"通过\"残酷\"武器附魔秒杀一个怪物", 29), PIRANHAS(
				"=消灭6只食人鱼", 30), NIGHT_HUNTER(
				"在夜间杀死 15 只怪物", 58), GAMES_PLAYED_1(
				"进行10场游戏", 60, true), GAMES_PLAYED_2(
				"进行50场游戏", 61, true), GAMES_PLAYED_3(
				"进行500场游戏", 62, true), GAMES_PLAYED_4(
				"进行2000场游戏", 63, true), HAPPY_END("幸福结局", 38), CHAMPION(
				"开启挑战通关", 39, true), SUPPORTER(
				"感谢游玩", 31, true),
				 ORB("获得邪神之球！", 68), MASTERY_HUMAN, MASTERY_GNOLL,
				 MASTERY_DWARF, MASTERY_WRAITH 
				 ;

		public boolean meta;

		public String description;
		public int image;

		private Badge(String description, int image) {
			this(description, image, false);
		}

		private Badge(String description, int image, boolean meta) {
			this.description = description;
			this.image = image;
			this.meta = meta;
		}

		private Badge() {
			this("", -1);
		}
	}

	private static HashSet<Badge> global;
	private static HashSet<Badge> local = new HashSet<Badges.Badge>();

	private static boolean saveNeeded = false;

	public static Callback loadingListener = null;

	public static void reset() {
		local.clear();
		loadGlobal();
	}

	private static final String BADGES_FILE = "badges.dat";
	private static final String BADGES = "badges";

	private static HashSet<Badge> restore(Bundle bundle) {
		HashSet<Badge> badges = new HashSet<Badge>();

		String[] names = bundle.getStringArray(BADGES);
		for (int i = 0; i < names.length; i++) {
			try {
				badges.add(Badge.valueOf(names[i]));
			} catch (Exception e) {
			}
		}

		return badges;
	}

	private static void store(Bundle bundle, HashSet<Badge> badges) {
		int count = 0;
		String names[] = new String[badges.size()];

		for (Badge badge : badges) {
			names[count++] = badge.toString();
		}
		bundle.put(BADGES, names);
	}

	public static void loadLocal(Bundle bundle) {
		local = restore(bundle);
	}

	public static void saveLocal(Bundle bundle) {
		store(bundle, local);
	}

	public static void loadGlobal() {
		if (global == null) {
			try {
				InputStream input = Game.instance.openFileInput(BADGES_FILE);
				Bundle bundle = Bundle.read(input);
				input.close();

				global = restore(bundle);

			} catch (Exception e) {
				global = new HashSet<Badge>();
			}
		}
	}

	public static void saveGlobal() {
		if (saveNeeded) {

			Bundle bundle = new Bundle();
			store(bundle, global);

			try {
				OutputStream output = Game.instance.openFileOutput(BADGES_FILE,
						Context.MODE_PRIVATE);
				Bundle.write(bundle, output);
				output.close();
				saveNeeded = false;
			} catch (IOException e) {

			}
		}
	}

	public static void validateMonstersSlain() {
		Badge badge = null;

		if (!local.contains(Badge.MONSTERS_SLAIN_1)
				&& Statistics.enemiesSlain >= 10) {
			badge = Badge.MONSTERS_SLAIN_1;
			local.add(badge);
		}
		if (!local.contains(Badge.MONSTERS_SLAIN_2)
				&& Statistics.enemiesSlain >= 50) {
			badge = Badge.MONSTERS_SLAIN_2;
			local.add(badge);
		}
		if (!local.contains(Badge.MONSTERS_SLAIN_3)
				&& Statistics.enemiesSlain >= 150) {
			badge = Badge.MONSTERS_SLAIN_3;
			local.add(badge);
		}
		if (!local.contains(Badge.MONSTERS_SLAIN_4)
				&& Statistics.enemiesSlain >= 250) {
			badge = Badge.MONSTERS_SLAIN_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateGoldCollected() {
		Badge badge = null;

		if (!local.contains(Badge.GOLD_COLLECTED_1)
				&& Statistics.goldCollected >= 100) {
			badge = Badge.GOLD_COLLECTED_1;
			local.add(badge);
		}
		if (!local.contains(Badge.GOLD_COLLECTED_2)
				&& Statistics.goldCollected >= 500) {
			badge = Badge.GOLD_COLLECTED_2;
			local.add(badge);
		}
		if (!local.contains(Badge.GOLD_COLLECTED_3)
				&& Statistics.goldCollected >= 2500) {
			badge = Badge.GOLD_COLLECTED_3;
			local.add(badge);
		}
		if (!local.contains(Badge.GOLD_COLLECTED_4)
				&& Statistics.goldCollected >= 7500) {
			badge = Badge.GOLD_COLLECTED_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateLevelReached() {
		Badge badge = null;

		if (!local.contains(Badge.LEVEL_REACHED_1) && Dungeon.hero.lvl >= 6) {
			badge = Badge.LEVEL_REACHED_1;
			local.add(badge);
		}
		if (!local.contains(Badge.LEVEL_REACHED_2) && Dungeon.hero.lvl >= 12) {
			badge = Badge.LEVEL_REACHED_2;
			local.add(badge);
		}
		if (!local.contains(Badge.LEVEL_REACHED_3) && Dungeon.hero.lvl >= 18) {
			badge = Badge.LEVEL_REACHED_3;
			local.add(badge);
		}
		if (!local.contains(Badge.LEVEL_REACHED_4) && Dungeon.hero.lvl >= 24) {
			badge = Badge.LEVEL_REACHED_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateStrengthAttained() {
		Badge badge = null;

		if (!local.contains(Badge.STRENGTH_ATTAINED_1)
				&& Dungeon.hero.STR >= 13) {
			badge = Badge.STRENGTH_ATTAINED_1;
			local.add(badge);
		}
		if (!local.contains(Badge.STRENGTH_ATTAINED_2)
				&& Dungeon.hero.STR >= 15) {
			badge = Badge.STRENGTH_ATTAINED_2;
			local.add(badge);
		}
		if (!local.contains(Badge.STRENGTH_ATTAINED_3)
				&& Dungeon.hero.STR >= 17) {
			badge = Badge.STRENGTH_ATTAINED_3;
			local.add(badge);
		}
		if (!local.contains(Badge.STRENGTH_ATTAINED_4)
				&& Dungeon.hero.STR >= 19) {
			badge = Badge.STRENGTH_ATTAINED_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateFoodEaten() {
		Badge badge = null;

		if (!local.contains(Badge.FOOD_EATEN_1) && Statistics.foodEaten >= 10) {
			badge = Badge.FOOD_EATEN_1;
			local.add(badge);
		}
		if (!local.contains(Badge.FOOD_EATEN_2) && Statistics.foodEaten >= 20) {
			badge = Badge.FOOD_EATEN_2;
			local.add(badge);
		}
		if (!local.contains(Badge.FOOD_EATEN_3) && Statistics.foodEaten >= 30) {
			badge = Badge.FOOD_EATEN_3;
			local.add(badge);
		}
		if (!local.contains(Badge.FOOD_EATEN_4) && Statistics.foodEaten >= 40) {
			badge = Badge.FOOD_EATEN_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validatePotionsCooked() {
		Badge badge = null;

		if (!local.contains(Badge.POTIONS_COOKED_1)
				&& Statistics.potionsCooked >= 3) {
			badge = Badge.POTIONS_COOKED_1;
			local.add(badge);
		}
		if (!local.contains(Badge.POTIONS_COOKED_2)
				&& Statistics.potionsCooked >= 6) {
			badge = Badge.POTIONS_COOKED_2;
			local.add(badge);
		}
		if (!local.contains(Badge.POTIONS_COOKED_3)
				&& Statistics.potionsCooked >= 9) {
			badge = Badge.POTIONS_COOKED_3;
			local.add(badge);
		}
		if (!local.contains(Badge.POTIONS_COOKED_4)
				&& Statistics.potionsCooked >= 12) {
			badge = Badge.POTIONS_COOKED_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validatePiranhasKilled() {
		Badge badge = null;

		if (!local.contains(Badge.PIRANHAS) && Statistics.piranhasKilled >= 6) {
			badge = Badge.PIRANHAS;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateItemLevelAquired(Item item) {

		// This method should be called:
		// 1) When an item is obtained (Item.collect)
		// 2) When an item is upgraded (ScrollOfUpgrade, ScrollOfWeaponUpgrade,
		// ShortSword, WandOfMagicMissile)
		// 3) When an item is identified

		// Note that artifacts should never trigger this badge as they are
		// alternatively upgraded
		if (!item.levelKnown || item instanceof Artifact) {
			return;
		}

		Badge badge = null;
		if (!local.contains(Badge.ITEM_LEVEL_1) && item.level >= 3) {
			badge = Badge.ITEM_LEVEL_1;
			local.add(badge);
		}
		if (!local.contains(Badge.ITEM_LEVEL_2) && item.level >= 6) {
			badge = Badge.ITEM_LEVEL_2;
			local.add(badge);
		}
		if (!local.contains(Badge.ITEM_LEVEL_3) && item.level >= 9) {
			badge = Badge.ITEM_LEVEL_3;
			local.add(badge);
		}
		if (!local.contains(Badge.ITEM_LEVEL_4) && item.level >= 12) {
			badge = Badge.ITEM_LEVEL_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validateAllPotionsIdentified() {
		if (Dungeon.hero != null && Dungeon.hero.isAlive()
				&& !local.contains(Badge.ALL_POTIONS_IDENTIFIED)
				&& Potion.allKnown()) {

			Badge badge = Badge.ALL_POTIONS_IDENTIFIED;
			local.add(badge);
			displayBadge(badge);

			validateAllItemsIdentified();
		}
	}

	public static void validateAllScrollsIdentified() {
		if (Dungeon.hero != null && Dungeon.hero.isAlive()
				&& !local.contains(Badge.ALL_SCROLLS_IDENTIFIED)
				&& Scroll.allKnown()) {

			Badge badge = Badge.ALL_SCROLLS_IDENTIFIED;
			local.add(badge);
			displayBadge(badge);

			validateAllItemsIdentified();
		}
	}

	public static void validateAllRingsIdentified() {
		if (Dungeon.hero != null && Dungeon.hero.isAlive()
				&& !local.contains(Badge.ALL_RINGS_IDENTIFIED)
				&& Ring.allKnown()) {

			Badge badge = Badge.ALL_RINGS_IDENTIFIED;
			local.add(badge);
			displayBadge(badge);

			validateAllItemsIdentified();
		}
	}

	public static void validateAllWandsIdentified() {
		if (Dungeon.hero != null && Dungeon.hero.isAlive()
				&& !local.contains(Badge.ALL_WANDS_IDENTIFIED)
				&& Wand.allKnown()) {

			Badge badge = Badge.ALL_WANDS_IDENTIFIED;
			local.add(badge);
			displayBadge(badge);

			validateAllItemsIdentified();
		}
	}

	public static void validateAllBagsBought(Item bag) {

		Badge badge = null;
		if (bag instanceof SeedPouch) {
			badge = Badge.BAG_BOUGHT_SEED_POUCH;
		} else if (bag instanceof ScrollHolder) {
			badge = Badge.BAG_BOUGHT_SCROLL_HOLDER;
		} else if (bag instanceof PotionBandolier) {
			badge = Badge.BAG_BOUGHT_POTION_BANDOLIER;
		} else if (bag instanceof WandHolster) {
			badge = Badge.BAG_BOUGHT_WAND_HOLSTER;
		}

		if (badge != null) {

			local.add(badge);

			if (!local.contains(Badge.ALL_BAGS_BOUGHT)
					&& local.contains(Badge.BAG_BOUGHT_SEED_POUCH)
					&& local.contains(Badge.BAG_BOUGHT_SCROLL_HOLDER)
					&& local.contains(Badge.BAG_BOUGHT_POTION_BANDOLIER)
					&& local.contains(Badge.BAG_BOUGHT_WAND_HOLSTER)) {

				badge = Badge.ALL_BAGS_BOUGHT;
				local.add(badge);
				displayBadge(badge);
			}
		}
	}

	public static void validateAllItemsIdentified() {
		if (!global.contains(Badge.ALL_ITEMS_IDENTIFIED)
				&& global.contains(Badge.ALL_POTIONS_IDENTIFIED)
				&& global.contains(Badge.ALL_SCROLLS_IDENTIFIED)
				&& global.contains(Badge.ALL_RINGS_IDENTIFIED)
				&& global.contains(Badge.ALL_WANDS_IDENTIFIED)) {

			Badge badge = Badge.ALL_ITEMS_IDENTIFIED;
			displayBadge(badge);
		}
	}

	public static void validateDeathFromFire() {
		Badge badge = Badge.DEATH_FROM_FIRE;
		local.add(badge);
		displayBadge(badge);

		validateYASD();
	}
	public static void validateDeathFromDecay() {
		Badge badge = Badge.DEATH_FROM_DECAY;
		local.add(badge);
		displayBadge(badge);
		
		validateYASD();
	}
	public static void validateDeathFromPoison() {
		Badge badge = Badge.DEATH_FROM_POISON;
		local.add(badge);
		displayBadge(badge);

		validateYASD();
	}

	public static void validateDeathFromGas() {
		Badge badge = Badge.DEATH_FROM_GAS;
		local.add(badge);
		displayBadge(badge);

		validateYASD();
	}

	public static void validateDeathFromHunger() {
		Badge badge = Badge.DEATH_FROM_HUNGER;
		local.add(badge);
		displayBadge(badge);

		validateYASD();
	}

	public static void validateDeathFromGlyph() {
		Badge badge = Badge.DEATH_FROM_GLYPH;
		local.add(badge);
		displayBadge(badge);
	}

	public static void validateDeathFromFalling() {
		Badge badge = Badge.DEATH_FROM_FALLING;
		local.add(badge);
		displayBadge(badge);
	}

	private static void validateYASD() {
		if (global.contains(Badge.DEATH_FROM_FIRE)
				&& global.contains(Badge.DEATH_FROM_POISON)
				&& global.contains(Badge.DEATH_FROM_GAS)
				&& global.contains(Badge.DEATH_FROM_HUNGER) && global.contains(Badge.DEATH_FROM_DECAY)) {

			Badge badge = Badge.YASD;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateBossSlain() {
		Badge badge = null;
		switch (Dungeon.depth) {
		case 5:
			badge = Badge.BOSS_SLAIN_1;
			break;
		case 10:
			badge = Badge.BOSS_SLAIN_2;
			break;
		case 15:
			badge = Badge.BOSS_SLAIN_3;
			break;
		case 20:
			badge = Badge.BOSS_SLAIN_4;
			break;
		}

		if (badge != null) {
			local.add(badge);
			displayBadge(badge);

			if (badge == Badge.BOSS_SLAIN_1) {
				switch (Dungeon.hero.heroClass) {
				case WARRIOR:
					badge = Badge.BOSS_SLAIN_1_WARRIOR;
					break;
				case MAGE:
					badge = Badge.BOSS_SLAIN_1_MAGE;
					break;
				case ROGUE:
					badge = Badge.BOSS_SLAIN_1_ROGUE;
					break;
				case HUNTRESS:
					badge = Badge.BOSS_SLAIN_1_HUNTRESS;
					break;
				}
				local.add(badge);
				if (!global.contains(badge)) {
					global.add(badge);
					saveNeeded = true;
				}

				if (global.contains(Badge.BOSS_SLAIN_1_WARRIOR)
						&& global.contains(Badge.BOSS_SLAIN_1_MAGE)
						&& global.contains(Badge.BOSS_SLAIN_1_ROGUE)
						&& global.contains(Badge.BOSS_SLAIN_1_HUNTRESS)) {

					badge = Badge.BOSS_SLAIN_1_ALL_CLASSES;
					if (!global.contains(badge)) {
						displayBadge(badge);
						global.add(badge);
						saveNeeded = true;
					}
				}
			} else if (badge == Badge.BOSS_SLAIN_3) {
				switch (Dungeon.hero.subClass) {
				case GLADIATOR:
					badge = Badge.BOSS_SLAIN_3_GLADIATOR;
					break;
				case BERSERKER:
					badge = Badge.BOSS_SLAIN_3_BERSERKER;
					break;
				case WARLOCK:
					badge = Badge.BOSS_SLAIN_3_WARLOCK;
					break;
				case BATTLEMAGE:
					badge = Badge.BOSS_SLAIN_3_BATTLEMAGE;
					break;
				case FREERUNNER:
					badge = Badge.BOSS_SLAIN_3_FREERUNNER;
					break;
				case ASSASSIN:
					badge = Badge.BOSS_SLAIN_3_ASSASSIN;
					break;
				case SNIPER:
					badge = Badge.BOSS_SLAIN_3_SNIPER;
					break;
				case WARDEN:
					badge = Badge.BOSS_SLAIN_3_WARDEN;
					break;
				default:
					return;
				}
				local.add(badge);
				if (!global.contains(badge)) {
					global.add(badge);
					saveNeeded = true;
				}

				if (global.contains(Badge.BOSS_SLAIN_3_GLADIATOR)
						&& global.contains(Badge.BOSS_SLAIN_3_BERSERKER)
						&& global.contains(Badge.BOSS_SLAIN_3_WARLOCK)
						&& global.contains(Badge.BOSS_SLAIN_3_BATTLEMAGE)
						&& global.contains(Badge.BOSS_SLAIN_3_FREERUNNER)
						&& global.contains(Badge.BOSS_SLAIN_3_ASSASSIN)
						&& global.contains(Badge.BOSS_SLAIN_3_SNIPER)
						&& global.contains(Badge.BOSS_SLAIN_3_WARDEN)) {

					badge = Badge.BOSS_SLAIN_3_ALL_SUBCLASSES;
					if (!global.contains(badge)) {
						displayBadge(badge);
						global.add(badge);
						saveNeeded = true;
					}
				}
			}
		}
	}
	
	public static void validateOrbObtained()  {
			if (!local.contains(Badge.ORB)) {
				Badge badge = Badge.ORB;
				local.add(badge);
				displayBadge(badge);
			}
		}



	public static void validateMastery() {

		Badge badge = null;
		switch (Dungeon.hero.heroClass) {
		case WARRIOR:
			badge = Badge.MASTERY_WARRIOR;
			break;
		case MAGE:
			badge = Badge.MASTERY_MAGE;
			break;
		case ROGUE:
			badge = Badge.MASTERY_ROGUE;
			break;
		case HUNTRESS:
			badge = Badge.MASTERY_HUNTRESS;
			break;
		}

		if (!global.contains(badge)) {
			global.add(badge);
			saveNeeded = true;
		}
	}

	public static void validateMasteryCombo(int n) {
		if (!local.contains(Badge.MASTERY_COMBO) && n == 7) {
			Badge badge = Badge.MASTERY_COMBO;
			local.add(badge);
			displayBadge(badge);
		}
	}

	// TODO: Replace this badge, delayed until an eventual badge rework
	public static void validateThievesArmband() {
		if (!local.contains(Badge.THIEVES_ARMBAND)/*
												 * && new
												 * RingOfThorns().isKnown()
												 */) {
			Badge badge = Badge.THIEVES_ARMBAND;
			local.add(badge);
			displayBadge(badge);
		}
	}

	// TODO: Replace this badge, delayed until an eventual badge rework
	public static void validateCloakOfThorns() {
		if (!local.contains(Badge.CLOAK_OF_THORNS)/*
												 * && new
												 * RingOfThorns().isKnown()
												 */) {
			Badge badge = Badge.CLOAK_OF_THORNS;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateRare(Mob mob) {

		Badge badge = null;
		if (mob instanceof Albino) {
			badge = Badge.RARE_ALBINO;
		} else if (mob instanceof Bandit) {
			badge = Badge.RARE_BANDIT;
		} else if (mob instanceof Shielded) {
			badge = Badge.RARE_SHIELDED;
		} else if (mob instanceof Senior) {
			badge = Badge.RARE_SENIOR;
		} else if (mob instanceof Acidic) {
			badge = Badge.RARE_ACIDIC;
		}
		if (!global.contains(badge)) {
			global.add(badge);
			saveNeeded = true;
		}

		if (global.contains(Badge.RARE_ALBINO)
				&& global.contains(Badge.RARE_BANDIT)
				&& global.contains(Badge.RARE_SHIELDED)
				&& global.contains(Badge.RARE_SENIOR)
				&& global.contains(Badge.RARE_ACIDIC)) {

			badge = Badge.RARE;
			displayBadge(badge);
		}
	}

	public static void validateVictory() {

		Badge badge = Badge.VICTORY;
		displayBadge(badge);

		switch (Dungeon.hero.heroClass) {
		case WARRIOR:
			badge = Badge.VICTORY_WARRIOR;
			break;
		case MAGE:
			badge = Badge.VICTORY_MAGE;
			break;
		case ROGUE:
			badge = Badge.VICTORY_ROGUE;
			break;
		case HUNTRESS:
			badge = Badge.VICTORY_HUNTRESS;
			break;
		}
		local.add(badge);
		if (!global.contains(badge)) {
			global.add(badge);
			saveNeeded = true;
		}

		if (global.contains(Badge.VICTORY_WARRIOR)
				&& global.contains(Badge.VICTORY_MAGE)
				&& global.contains(Badge.VICTORY_ROGUE)
				&& global.contains(Badge.VICTORY_HUNTRESS)) {

			badge = Badge.VICTORY_ALL_CLASSES;
			displayBadge(badge);
		}
	}

	public static void validateNoKilling() {
		if (!local.contains(Badge.NO_MONSTERS_SLAIN)
				&& Statistics.completedWithNoKilling) {
			Badge badge = Badge.NO_MONSTERS_SLAIN;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateGrimWeapon() {
		if (!local.contains(Badge.GRIM_WEAPON)) {
			Badge badge = Badge.GRIM_WEAPON;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateNightHunter() {
		if (!local.contains(Badge.NIGHT_HUNTER) && Statistics.nightHunt >= 15) {
			Badge badge = Badge.NIGHT_HUNTER;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateSupporter() {

		global.add(Badge.SUPPORTER);
		saveNeeded = true;

		PixelScene.showBadge(Badge.SUPPORTER);
	}

	public static void validateGamesPlayed() {
		Badge badge = null;
		if (Rankings.INSTANCE.totalNumber >= 10) {
			badge = Badge.GAMES_PLAYED_1;
		}
		if (Rankings.INSTANCE.totalNumber >= 100) {
			badge = Badge.GAMES_PLAYED_2;
		}
		if (Rankings.INSTANCE.totalNumber >= 500) {
			badge = Badge.GAMES_PLAYED_3;
		}
		if (Rankings.INSTANCE.totalNumber >= 2000) {
			badge = Badge.GAMES_PLAYED_4;
		}

		displayBadge(badge);
	}

	public static void validateHappyEnd() {
		displayBadge(Badge.HAPPY_END);
	}

	public static void validateChampion() {
		displayBadge(Badge.CHAMPION);
	}

	private static void displayBadge(Badge badge) {

		if (badge == null) {
			return;
		}

		if (global.contains(badge)) {

			if (!badge.meta) {
				GLog.h("获得徽章: %s", badge.description);
			}

		} else {

			global.add(badge);
			saveNeeded = true;

			if (badge.meta) {
				GLog.h("新的超级徽章: %s", badge.description);
			} else {
				GLog.h("新的徽章: %s", badge.description);
			}
			PixelScene.showBadge(badge);
		}
	}

	public static boolean isUnlocked(Badge badge) {
		return global.contains(badge);
	}
	public static void validateRace(HeroRace race) {
		if((race == HeroRace.DWARF) && !(isUnlocked(Badge.MASTERY_DWARF))){
			global.add(Badge.MASTERY_DWARF);
		}
		else if((race == HeroRace.HUMAN) && !(isUnlocked(Badge.MASTERY_HUMAN))){
			global.add(Badge.MASTERY_HUMAN);
		}
		else if((race == HeroRace.WRAITH) && !(isUnlocked(Badge.MASTERY_WRAITH))){
			global.add(Badge.MASTERY_WRAITH);
		}
		else if((race == HeroRace.GNOLL) && !(isUnlocked(Badge.MASTERY_GNOLL))){
			global.add(Badge.MASTERY_GNOLL);
		}
	}
	public static void disown(Badge badge) {
		loadGlobal();
		global.remove(badge);
		saveNeeded = true;
	}

	public static List<Badge> filtered(boolean global) {

		HashSet<Badge> filtered = new HashSet<Badge>(global ? Badges.global
				: Badges.local);

		if (!global) {
			Iterator<Badge> iterator = filtered.iterator();
			while (iterator.hasNext()) {
				Badge badge = iterator.next();
				if (badge.meta) {
					iterator.remove();
				}
			}
		}

		leaveBest(filtered, Badge.MONSTERS_SLAIN_1, Badge.MONSTERS_SLAIN_2,
				Badge.MONSTERS_SLAIN_3, Badge.MONSTERS_SLAIN_4);
		leaveBest(filtered, Badge.GOLD_COLLECTED_1, Badge.GOLD_COLLECTED_2,
				Badge.GOLD_COLLECTED_3, Badge.GOLD_COLLECTED_4);
		leaveBest(filtered, Badge.BOSS_SLAIN_1, Badge.BOSS_SLAIN_2,
				Badge.BOSS_SLAIN_3, Badge.BOSS_SLAIN_4);
		leaveBest(filtered, Badge.LEVEL_REACHED_1, Badge.LEVEL_REACHED_2,
				Badge.LEVEL_REACHED_3, Badge.LEVEL_REACHED_4);
		leaveBest(filtered, Badge.STRENGTH_ATTAINED_1,
				Badge.STRENGTH_ATTAINED_2, Badge.STRENGTH_ATTAINED_3,
				Badge.STRENGTH_ATTAINED_4);
		leaveBest(filtered, Badge.FOOD_EATEN_1, Badge.FOOD_EATEN_2,
				Badge.FOOD_EATEN_3, Badge.FOOD_EATEN_4);
		leaveBest(filtered, Badge.ITEM_LEVEL_1, Badge.ITEM_LEVEL_2,
				Badge.ITEM_LEVEL_3, Badge.ITEM_LEVEL_4);
		leaveBest(filtered, Badge.POTIONS_COOKED_1, Badge.POTIONS_COOKED_2,
				Badge.POTIONS_COOKED_3, Badge.POTIONS_COOKED_4);
		leaveBest(filtered, Badge.BOSS_SLAIN_1_ALL_CLASSES,
				Badge.BOSS_SLAIN_3_ALL_SUBCLASSES);
		leaveBest(filtered, Badge.DEATH_FROM_FIRE, Badge.YASD);
		leaveBest(filtered, Badge.DEATH_FROM_GAS, Badge.YASD);
		leaveBest(filtered, Badge.DEATH_FROM_HUNGER, Badge.YASD);
		leaveBest(filtered, Badge.DEATH_FROM_POISON, Badge.YASD);
		leaveBest(filtered, Badge.VICTORY, Badge.VICTORY_ALL_CLASSES);
		leaveBest(filtered, Badge.GAMES_PLAYED_1, Badge.GAMES_PLAYED_2,
				Badge.GAMES_PLAYED_3, Badge.GAMES_PLAYED_4);

		ArrayList<Badge> list = new ArrayList<Badge>(filtered);
		Collections.sort(list);

		return list;
	}

	private static void leaveBest(HashSet<Badge> list, Badge... badges) {
		for (int i = badges.length - 1; i > 0; i--) {
			if (list.contains(badges[i])) {
				for (int j = 0; j < i; j++) {
					list.remove(badges[j]);
				}
				break;
			}
		}
	}
}
