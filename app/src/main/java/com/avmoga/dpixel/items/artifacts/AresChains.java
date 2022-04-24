package com.avmoga.dpixel.items.artifacts;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Bleeding;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Cripple;
import com.avmoga.dpixel.actors.buffs.Paralysis;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.effects.Chain;
import com.avmoga.dpixel.effects.Pushing;
import com.avmoga.dpixel.effects.particles.FlameParticle;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.mechanics.Ballistica;
import com.avmoga.dpixel.scenes.CellSelector;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AresChains extends Artifact {
	{
		name = "战神锁链";
		image = ItemSpriteSheet.ARTIFACT_SHIELD;
		
		level = 0;
		levelCap = 4;
		defaultAction = AC_CHAIN;
		
		charge = ((level * 2) +3 );
		partialCharge = 0;
		chargeCap = (((level + 1) / 2) + 3);
		//Level Progression: 0, 2, 5, 7, 10
	}
	public static int mobsSinceUpgrade = 0;
	private static final float TIMETOUSE = 1f;
	private static final String AC_CHAIN = "束缚敌人";
	private static final String AC_PULL = "拽";
	private static final String YELL = "你在我手里了！";
	private static final String BUFF = "增益";
	private static final String TXT_FAIL = "这里没人能让你拽过来...";
	private static final String TXT_NO_CHARGE = "充能不足...";
	private static final String TXT_SELF_TARGET = "你不能以自己为目标。";
	
	public void castChain(int cell){

		Char ch = Actor.findChar(cell);
		if (ch != null) {
			curUser.sprite.parent.add(new Chain(Dungeon.hero.pos, ch.pos, null));
			Dungeon.hero.busy();
			Buff.prolong(ch, Cripple.class, Random.Int(3 + level));
			Buff.affect(ch, Bleeding.class).set(3);
			ch.damage(Random.Int(1, 10), this);
			charge--;
			ch.sprite.emitter().burst(FlameParticle.FACTORY, 5);
			Dungeon.hero.spendAndNext(TIMETOUSE);
		}
	}
	
	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			if (activeBuff != null) {
				activeBuff.detach();
				activeBuff = null;
			}
			return true;
		} else
			return false;
	}
	
	protected static CellSelector.Listener chain = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {
			final AresChains chains = (AresChains) Item.curItem;
			if (target != null) {

				final int cell = Ballistica.cast(curUser.pos, target, true,	true);
				
				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i(TXT_SELF_TARGET);
					return;
				}
				chains.castChain(cell);

			}
		}

		@Override
		public String prompt() {
			return "选择拖拽位置";
		}
	};
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if(isEquipped(hero) && !cursed){
			if ((hero.heroRace() == HeroRace.DWARF || hero.heroRace() == HeroRace.HUMAN))
				actions.add(AC_CHAIN);
			
			if (hero.heroRace() == HeroRace.DWARF && level >= 5)
					actions.add(AC_PULL);
		}
		return actions;
	}
	
	protected boolean useableBasic(){
		if(Dungeon.hero.heroRace() == HeroRace.HUMAN || Dungeon.hero.heroRace() == HeroRace.DWARF){
			return true;
		}
		return false;
	}
	
	protected boolean useable(){
		if(Dungeon.hero.heroRace() == HeroRace.DWARF){
			return true;
		}
		return false;
	}
	
	@Override
	protected ArtifactBuff passiveBuff(){
		return new chainsRecharge();
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_CHAIN)) {
			if (!isEquipped(hero))
				GLog.i("你需要先装备战神锁链。");
			else if(cursed)
				GLog.i("战神锁链拒绝俯冲你的意志！");
			else if(!useableBasic()){
				GLog.n("战神锁链：你想要干什么？你是矮人吗?");
			} else if (charge == 0) {
				GLog.w("战神锁链充能不够……");
			}
			else {
				GameScene.selectCell(chain);
				}
		} else if (action.equals(AC_PULL) && useable()) {
			Dungeon.hero.checkVisibleMobs();
			
			if(charge >= 0 && (Dungeon.hero.visibleEnemies()) > 0){
				Dungeon.hero.yell(YELL);
				for (final Mob mob : Dungeon.level.mobs) {
					if(Level.fieldOfView[mob.pos]){
						for(int space : Level.NEIGHBOURS8){
							final int newSpace = Dungeon.hero.pos + space;
							if((Actor.findChar(newSpace) == null) && (charge > 0) && (Level.passable[newSpace] ||Level.avoid[newSpace])){
								curUser.busy();
								curUser.sprite.parent.add(new Chain(curUser.pos, mob.pos, new Callback() {
									@Override
									public void call() {
										Actor.add(new Pushing(mob, mob.pos, newSpace));
										mob.pos = newSpace;
										Dungeon.observe();
										curUser.spendAndNext(1f);
										Dungeon.level.mobPress(mob);
										GLog.i("拽过来一只 " + mob.name + "!");
										mob.damage(Random.Int(mob.HT / 10, mob.HT / 4), this);
										if (!mob.immunities().contains(Paralysis.class)){
											Buff.prolong(mob, Paralysis.class, Paralysis.duration(mob));
										}
									}
								}));
								charge--;
								break;
							} 
						}
					}
					if(!(charge > 0)){
						break;
					}
				}
				Dungeon.observe();
				Dungeon.hero.spendAndNext(TIME_TO_THROW);
			} else if((charge == 0)){
				GLog.i(TXT_NO_CHARGE);
			} else if(!(Dungeon.hero.visibleEnemies() > 0)){
				GLog.i(TXT_FAIL);
			}
		}
	}
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		if (activeBuff != null)
			bundle.put(BUFF, activeBuff);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		
		if (bundle.contains(BUFF)) {
			Bundle buffBundle = bundle.getBundle(BUFF);

			activeBuff.restoreFromBundle(buffBundle);
		}
	}
	@Override
	public String desc(){
		String desc = "你发现了一个有趣的东西——一个有着战神阿瑞斯纹章的盾牌\n\n";
		if(Dungeon.hero.heroRace() == HeroRace.DWARF){
			desc += "当你拿到这块盾牌的时候，它似乎立刻就喜欢上了你。它抓住了你的手臂并且不肯放开。看起来你可以用他来妨碍一下敌人，而且随着使用次数的增多，你还可以用这枚盾牌将敌人拽向你。";
		} else if(Dungeon.hero.heroRace() == HeroRace.HUMAN){
			desc += "你注意到盾牌上连着的锁链似乎可以缠住附近敌人的腿部；也许你可以把这些锁链扔向敌人来妨碍他们的行动。你确信那些好战的矮人们用这件武器会更顺手。";
		} else {
			desc += "这些锁链对你来说太重了，你很难有效的利用它。因为锁链和盾牌是组合起来的，过于沉重而导致你无法使用。你也许应该把它转化为更好用的物品。";
		}
		if(cursed && isEquipped(Dungeon.hero)){
			desc += "\n\n在你接触到它的时候，盾牌突然绑在了你的手臂上，锁链则在你腿边漂浮着。\n\n" +
					"直觉告诉你盾牌中很有可能封印着一个残暴的灵魂。";
		}
		return desc;
	}
	public class chainsRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed) {
				partialCharge += 1 / (45f - (chargeCap - charge) * 2f);

				if (partialCharge >= 1) {
					partialCharge--;
					charge++;

					if (charge == chargeCap) {
						partialCharge = 0;
					}
				}

			} else if (cursed && Random.Int(10) == 0)
				((Hero) target).spend(TICK);

			updateQuickslot();

			spend(TICK);

			return true;
		}
		public void gainEXP(float partialEXP){
			if (cursed) return;

			exp += Math.round(partialEXP);
			
			if(Random.Int(exp, 10000) > 9500 && level < levelCap){
				exp = 0;
				upgrade();
				updateQuickslot();
			}
		}
	}
}
