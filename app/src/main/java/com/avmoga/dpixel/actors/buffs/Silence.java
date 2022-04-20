package com.avmoga.dpixel.actors.buffs;

import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.items.rings.RingOfElements.Resistance;
import com.avmoga.dpixel.ui.BuffIndicator;

public class Silence extends FlavourBuff {


	public static final float DURATION = 10f;

	@Override
	public int icon() {
		return BuffIndicator.VERTIGO;
	}

	@Override
	public String toString() {
		return "沉默";
	}

	@Override
	public String desc() {
		return "沉默诅咒会使你的法杖完全无法使用魔力。剩余的沉默效果时长："+dispTurns()+"回合";
	}

	public static float duration(Char ch) {
		Resistance r = ch.buff(Resistance.class);
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}

}
