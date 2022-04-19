package com.avmoga.dpixel.items.rings;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfForce extends Ring {

	{
		name = Messages.get(this, "name");
	}

	@Override
	protected RingBuff buff() {
		return new Force();
	}

	@Override
	public String desc() {
		if (isKnown()) {
			String desc = Messages.get(this, "desc");
			int str = Dungeon.hero.STR() - 8;
			desc += levelKnown ? Messages.get(this, "avg_dmg", str / 2 + level, (int) (str * 0.5f * level) + str * 2)
					: Messages.get(this, "typical_avg_dmg", str / 2 + 1, (int) (str * 0.5f) + str * 2);
			return desc;
		} else
			return super.desc();
	}

	public class Force extends RingBuff {
	}
}
