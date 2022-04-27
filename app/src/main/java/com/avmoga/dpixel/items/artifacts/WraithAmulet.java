package com.avmoga.dpixel.items.artifacts;
//TODO Rewrite me! Replace both effects with something more useful.

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Invisibility;
import com.avmoga.dpixel.actors.buffs.Vertigo;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.effects.particles.ShadowParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.wands.WandOfBlink;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.scenes.CellSelector;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.ui.QuickSlotButton;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class WraithAmulet extends Artifact {
	
	{
		name = "幽灵金属护身符";
		image = ItemSpriteSheet.ARTIFACT_WAMULET;
		cooldown = 0;
		charge = ((level + 2 / 2) +3 );
		partialCharge = 0;
		chargeCap = (((level + 2) / 2) + 3);
		level = 0;
		levelCap = 10;
		
		defaultAction = AC_GHOST;
	}
	
	private static final String TXT_SELFSELECT = "你不能以自己为目标";
	private static final String TXT_NOCHARGE = "充能不足，无法使用！";
	private static final String TXT_NOEQUIP = "该物品必须装备才能使用。";
	private static final String AC_GHOST = "虚无化";
	private static final String AC_ASSASSINATE = "暗杀";
	private static final String TXT_FAR = "暗杀位置必须和暗杀对象间距一格。";
	private static final String TXT_GHOST = "你的本源开始消散";
	private static final String TXT_NOTHING_THERE = "这里没有东西可以杀死。";
	
	@Override
	public Item upgrade(){
		chargeCap++;
		return super.upgrade();
	}


	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if(action.equals(AC_GHOST)){
		if (cooldown > 0) {
			GLog.i("幽灵护身符正在冷却中……");
		} else if(useableBasic()) {
				if(this.isEquipped(Dungeon.hero)){
					if(this.charge > 0) {
						exp += 5;
						Buff.affect(Dungeon.hero, Invisibility.class, Invisibility.DURATION);
						GLog.i(TXT_GHOST);
						cooldown = 12 - (level / 2);
						charge--;
					} else {
						GLog.i(TXT_NOCHARGE);
					}
				} else {
					GLog.i(TXT_NOEQUIP);
				} 
			} else {
				GLog.i("你在干什么？");
			}
		} else if (action.equals(AC_ASSASSINATE)) {
			if(this.charge >= 5){
				GameScene.selectCell(porter);
				charge-=5;
			} else {
				GLog.i(TXT_NOCHARGE);
			}
		}
	}
	
	protected boolean useableBasic(){
		if(Dungeon.hero.heroRace() == HeroRace.HUMAN || Dungeon.hero.heroRace() == HeroRace.WRAITH){
			return true;
		}
		return false;
	}
	
	protected boolean useable(){
		if(Dungeon.hero.heroRace() == HeroRace.WRAITH){
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && Dungeon.hero.heroRace == HeroRace.WRAITH)
			actions.add(AC_GHOST);
		if (isEquipped(hero) && charge >= 7 && Dungeon.hero.heroRace == HeroRace.WRAITH)
			actions.add(AC_ASSASSINATE);
		return actions;
	}
	
	@Override
	protected ArtifactBuff passiveBuff() {
		return new WraithRecharge();
	}
	
	public class WraithRecharge extends ArtifactBuff{
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed && useableBasic()) {
				partialCharge += 1 / (150f - (chargeCap - charge) * 15f);

				if (partialCharge >= 1) {
					partialCharge--;
					charge++;

					if (charge == chargeCap) {
						partialCharge = 0;
					}
				}
			} else if(cursed){
				if(Random.Int(40) == 0){
					GLog.i("你被奥术征服了！");
					Buff.affect(curUser, Vertigo.class, Vertigo.duration(curUser));

				}
			}

			if(exp > level * 50){
				exp = 0;
				if(level < levelCap){
					//I must add Complete WraithAmulet
					//Ok,Ling will Complete WraithAmulet
					upgrade();
					exp += level * 50;
					GLog.p("你的幽灵金属护身符变得更加强大了");
				}
			}

			if (cooldown > 0)
				cooldown--;

			updateQuickslot();
			spend(TICK);
			return true;
		}
	}


	@Override
	public String desc() {
		String desc = "你发现了一个十分有趣的物品：一块镶嵌着紫色宝石的银色透明金属护身符\n\n";
		if(Dungeon.hero.heroRace() == HeroRace.HUMAN){
			desc += "当你触摸它的时候，你发现你的手已经开始变得透明，并且现在已经失去形体。\n" +
					"也许幽灵才能把它发挥更好的用处，但你也能搞明白出一些它的基础功能。\n" +
					"你只是希望不会有一些伴随而来的负面效果。";
		} else if(Dungeon.hero.heroRace() == HeroRace.WRAITH){
			desc += "你大吃一惊，这是暗影钢的护身符。自从你离开家园后就没见过这种东西了。当你用手指摩擦他的时候，一股寒意窜上你的后背。这东西将会非常有用";
		}
		else{
			desc += "你一摸上它就感到一丝寒意窜上你的后背。不管这是什么，这里面都流淌着一股强大而邪恶的力量。不经了解就使用它可能很危险。所以你决定你该把它转化为一些更有用的东西。";
		}
		
		if(isEquipped(Dungeon.hero) && (Dungeon.hero.heroRace() == HeroRace.WRAITH ||  cursed)){
			desc += "\n\n";
			if(cursed){
				desc += "护符正在进入你的脑海，它让你知道超出你理解力的事情。";
			} else if(level < 5 || Dungeon.hero.heroRace() == HeroRace.HUMAN){
				desc += "你似乎可以把自己的意志施加在它上面来让自己变得幽灵化。\n" +
						"也许，你可以把这种经验对更多实验对象使用。";
			} else if (level < 10){
				desc += "似乎当你从幽灵形态再显形时，存在的物质会消散并变得不复存在。也许你可以通过奇袭来造成伤害。\n";
			} else {
				desc += "你竭力去驾驭这件护符，以发挥一些好的效果。\n" +
						"现在，没什么能反抗你了。" ;
			}
		}
		
		return desc;
	}
	public int getCharge(){
		return this.charge;
	}
	protected static CellSelector.Listener porter = new CellSelector.Listener() {
		@Override
		public String prompt() {
			return "选择传送的位置";
		}
		@Override
		public void onSelect(Integer target) {
			HashSet<Mob> victim = new HashSet<Mob>();
			if (target != null && (Level.passable[target])) {
				if(Actor.findChar(target) != null){
					victim.add((Mob) Actor.findChar(target));
				}

				if (target == curUser.pos) {
					GLog.i(TXT_SELFSELECT);
					return;
				}

				QuickSlotButton.target(Actor.findChar(target));
					if(Actor.findChar(target) != null){
							if(Level.distance(Dungeon.hero.pos, target) == 2) {
								if (Level.fieldOfView[target]) {
									final WraithAmulet amulet = (WraithAmulet) Item.curItem;
									amulet.charge--;
									amulet.exp += 10;
									Actor.findChar(target).damage(Actor.findChar(target).HT, WraithAmulet.class);
									Dungeon.hero.pos = target;
									Dungeon.hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
									WandOfBlink.appear(Dungeon.hero, target);
									Dungeon.level.press(target, Dungeon.hero);
									Dungeon.observe();
									GLog.i("你暗杀了一个怪物！");
								} else {
									GLog.i(TXT_FAR);
								}
							} else {
								GLog.i(TXT_FAR);
							}
						} else {
							GLog.i(TXT_NOTHING_THERE);
						}
					}
			}
	};

}
