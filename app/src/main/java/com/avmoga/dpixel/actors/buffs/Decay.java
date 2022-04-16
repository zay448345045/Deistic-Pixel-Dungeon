package com.avmoga.dpixel.actors.buffs;


import com.avmoga.dpixel.ui.BuffIndicator;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.ResultDescriptions;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.plants.Sungrass.Health;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Decay extends Buff implements Hero.Doom {
	
	private final String TXT_CLEANSE = "You feel the Sungrass sap beginning to cleanse your plague.";
	private final String TXT_PROC = "You are damaged by the decaying!";
	
	private static final String TIMER = "timer";
	private static final String POWER = "power";
	
	protected int power;
	private int timer;
	
	@Override
	public String toString() {
		return "Decaying";
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
