package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.mobs.Wraith;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.plants.BombFruit;
import com.avmoga.dpixel.scenes.CellSelector;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MysticBranch extends Artifact {
	public static final String TXT_YOURSELF = "You can't target yourself.";
	{
		name = "木质雕像";
		image = ItemSpriteSheet.ARTIFACT_ROOT;
		
		level = 0;
		levelCap = 4;
		
		exp = 0;
		charge = 0;
	}
	private static final String TXT_MONSTER = "不能在非高草区域的种植";
	private static final String UPGRADETXT = "一个灵魂已被派去地牢复仇。";
	private static final String AC_PLANT = "放置高爆花";
	
	protected static CellSelector.Listener listener = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {

			if (target != null && Actor.findChar(target) == null && Level.flamable[target]) {
					Dungeon.level.plant(new BombFruit.Seed(), target);
					return;
			} else if (target != null) {
				GLog.i(TXT_MONSTER);
			}
		}

		@Override
		public String prompt() {
			return "Choose direction to zap";
		}
	};
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 0 && !cursed && level >= 2 && Dungeon.hero.heroRace() == HeroRace.GNOLL)
			actions.add(AC_PLANT);
		return actions;
	}
	
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_PLANT) && Dungeon.hero.heroRace == HeroRace.GNOLL && charge == 0) {
			GameScene.selectCell(listener);
		}
	}
	
	@Override
	protected ArtifactBuff passiveBuff() {
			return new Affinity();
	}
	
	public class Affinity extends ArtifactBuff {
		@Override
		public boolean act(){
			if(charge > 0){
				charge--;
			}
			if(cursed){
					if(Random.Int(30)==0){
						new Wraith().pos = Dungeon.level.randomRespawnCell();
						GLog.w("那声音在你耳边不停的咯咯怪叫，它在召唤邪恶的灵魂来骚扰你！");
					}
			}
			updateQuickslot();
			spend(TICK);
			return true;
		}
		@Override
		public String toString() {
			return "Affinity";
		}
		public void gainEXP(){
			exp += 10000;
			if(exp > ((level + 1) * 100)){
				exp = 0;
				if(level < levelCap){
					upgrade();
				}
			}
		}
	}

@Override
public String desc() {
	String desc = "你有一个古怪的发现：从这个被雕刻成小丑样貌的木质雕像里，你发现有纯净的能量从中流出。\n\n";
	if(Dungeon.hero.heroRace() == HeroRace.HUMAN){
		desc += "你听见雕像向你轻声诉说，尽可能的告诉你这层地牢的人物的位置";
	} else if(Dungeon.hero.heroRace() == HeroRace.GNOLL){
		desc += "你认出你发现的木质雕像是一个SpiritHead，它能够让你与这里的自然的灵魂交流。虽然它们的活动领域有限，但也许能为你提供一些信息，甚至以后还能给你提供一些攻击能力。\n\n";
				if(level < 2){
					 desc += "也许你该试试在草地里穿梭来与更多灵魂达成联系，这也许能提升你的雕像的力量。";
				}
	}
	else{
		desc += "一阵不知道是哪个人的声音从雕像里向你吼叫；你吓了一跳，把雕像摔向墙壁。也许你该把它转化为别的更有用的东西。";
	}
	
	if(isEquipped(Dungeon.hero) && (Dungeon.hero.heroRace == HeroRace.GNOLL || Dungeon.hero.heroRace == HeroRace.HUMAN || cursed)){
		desc += "\n\n";
		if(cursed){
			desc += "那声音在你耳边不停的咯咯怪叫，它在召唤邪恶的灵魂来骚扰你！";
		} else if(level < 5 || Dungeon.hero.heroRace() == HeroRace.HUMAN){
			desc += "你手中的雕像发出声音，告诉你这块区域里灵魂的位置。你发现它们并未全部失死去。";
		} else if (level < 10 && Dungeon.hero.heroRace() == HeroRace.GNOLL){
			desc += "看起来如果有必要的话，你可以联系灵魂们在战斗中帮助你。";
		} else {
			desc += "你完全掌控了这块图腾，它非常适应你的意志，你也练习过怎么好好地使用它。";
		}
	}
	
	return desc;
}
	/* Wooden Effigy
	 * A charge-based ranged artillery mechanism used best by a Gnoll.
	 * Upgrades by trampling grass
	 * Basic: See all mobs that are standing on grass.
	 * Advanced: Spawn a seed bomb at a given (grassed) location. The bomb explodes after 
	 * two turns, spreads grass seeds, and damages mobs.
	 */
}
