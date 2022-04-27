package com.avmoga.dpixel.actors.buffs;


import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.ResultDescriptions;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.plants.Sungrass.Health;
import com.avmoga.dpixel.ui.BuffIndicator;
import com.avmoga.dpixel.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Decay extends Buff implements Hero.Doom {
	
	private final String TXT_CLEANSE = "你感到阳春草的汁液在净化你体内的瘟疫!";
	private final String TXT_PROC = "你因腐败受到了伤害!";
	
	private static final String TIMER = "timer";
	private static final String POWER = "power";
	
	protected int power;
	private int timer;
	
	@Override
	public String toString() {
		return "腐败";
	}

	@Override
	public String desc() {
		return "腐败魔法会无休止的侵蚀玩家的身体，时间越长，越致命\n\n据说只有阳春草的汁液能净化这种恶毒的魔法";
	}
	
	public void set(int value) {
		this.power =  value;
	}
	
	@Override
	public void storeInBundle(Bundle bundle){
		super.storeInBundle(bundle);
		bundle.put(TIMER, timer);
		bundle.put(POWER, power);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle){
		super.restoreFromBundle(bundle);
		power = bundle.getInt(POWER);
		timer = bundle.getInt(TIMER);
	}
	
	@Override
	public boolean act() {
		if(target.isAlive()){
			timer++;
			if(timer >= 10){
				GLog.w(TXT_PROC);
				target.damage((power / 2) + Random.Int(10), this);
				timer = 0;
			}
			for (Buff b : target.buffs()){
				if (b instanceof Health){
					GLog.i(TXT_CLEANSE);
					power -= 1;
					break;
				}
			}
			spend(TICK);
			if(!(power >= 0)){
				detach();
			}
		} else{
			detach();
		}
		return true;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.CORRUPT;
	}
	
	@Override
	public void onDeath() {
		Badges.validateDeathFromDecay();
		
		Dungeon.fail(ResultDescriptions.DECAY);
	}
}
