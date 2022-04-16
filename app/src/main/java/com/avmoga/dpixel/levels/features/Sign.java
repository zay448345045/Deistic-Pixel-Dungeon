/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.avmoga.dpixel.levels.features;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.ElmoParticle;
import com.avmoga.dpixel.levels.DeadEndLevel;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.windows.WndMessage;
import com.watabou.noosa.audio.Sample;

public class Sign {

	private static final String TXT_DEAD_END = "你为什么在这里?!";

	private static final String[] TIPS = {
			"几乎所有的装备都有力量需求。 \n" +
					"不要无视自己的力量来用使用你不能用的装备，这样做会带来巨大的惩罚！\n" +
					"提高你的力量不是使用更好的装备的唯一途径，你也可以用升级卷轴降低装备的力量要求。\n" +
					"地牢里面遇见的物品大多数都是未鉴定的。\n" +
					"有些物品会带有未知的效果，可能是升级，可能是降级，甚至是诅咒！\n" +
					"未鉴定物品的效果是不可以被预测，所以千万小心！",
			"不管不顾地向前冲非常容易被杀死。\n" +
					"放慢一点，小心查看敌人，充分利用好环境和你的物品，可以使游戏产生很大的不同\n" +
					"这座地牢充满了陷阱和隐藏的通道，睁大眼睛仔细看看！",
			"升级很重要!\n" +
					"建议让你的等级和你的当前楼层保持相同。 \n" +
					"饥饿会让你需要大量地进食，但是不要害怕因此而放慢速度备战。\n" +
					"饥饿和健康都是你手上有用的资源，你可以为了节省食物来让自己多挨一会饿。",
			"盗贼不是唯一一个能够利用潜伏的角色。你可以退到到门后边来奇袭敌人，这样你会必定命中。\n" +
					"对于没有察觉到你的怪物的攻击也会是必定命中的。",

			"给所有下水道维护和清洁人员的注意事项：立即返回。某种粘性怪物在这里安家，有几名工作人员在试图处理时失踪了。\n" +
					"已批准封锁下水道，该区域已被废弃，请立即离开。",

			"像素商店 - 成功冒险所需的一切！\n" +
					"尽快鉴定你的药水和卷轴。不要等到你需要他们的时候再去追悔莫及。\n" +
					"饥饿不会受伤，极度饥饿才会。\n" +
					"奇袭能必定命中。例如，当你知道敌人正在接近你时，你可以在关闭的门后面伏击你的敌人。",

			"别让天狗跑了!",

			"像素商店 - 充钱使你变强\n" +
					"当你同时被几个怪物攻击时，尽量退到门后。\n" +
					"如果你正在燃烧，你不能在飞行的同时在水中把火扑灭。\n" +
					"同时拥有多个未受祝福的十字架是没有意义的，因为你将在复活后失去他们。",

			"危险！此重型机械可能导致受伤、残疾或者死亡，我们对您的遭遇不负有任何责任",

			"像素商店 - 为你的地牢探险保驾护航\n" +
					"当你升级一个附了魔的武器时，有几率抹消掉这个附魔。\n" +
					"\n" +
					"在转化之井中，你可以得到一个其他地方无法得到的物品。\n" +
					"\n" +
					"给武器附魔的唯一方法是用“武器升级卷轴”来升级它。",

			"在陛下面前不允许携带武器！",

			"像素商店 - 猎魔人专属特惠！",

			// hmm.. I wonder what this is?
			//TODO: update with quotes to tease
			"fetch DensesT GOod", "eNTreNCh Pews", "yo fisH vAlid so End GoO" };
	
	
	private static final String PIT = "Note to self: Always leave a teleport scroll in the vault.";
	//private static final String BOOKLVL = "Note to self: Always leave a teleport scroll in the vault.";


	private static final String TXT_BURN = "As you try to read the sign it bursts into greenish flames.";

	public static void read(int pos) {

		if (Dungeon.level instanceof DeadEndLevel) {

			GameScene.show(new WndMessage(TXT_DEAD_END));

		} else {

			int index = Dungeon.depth - 1;

			if (index < TIPS.length) {
				GameScene.show(new WndMessage(TIPS[index]));

				if (index >= 21) {

					Level.set(pos, Terrain.EMBERS);
					GameScene.updateMap(pos);
					GameScene.discoverTile(pos, Terrain.SIGN);

					GLog.w(TXT_BURN);

					CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
					Sample.INSTANCE.play(Assets.SND_BURNING);
				}

			}
		}
	}
	
	public static void readPit(int pos) {
				GameScene.show(new WndMessage(PIT));			
			}
			
	
}
