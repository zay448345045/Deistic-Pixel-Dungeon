package com.avmoga.dpixel.items.rings;

import com.avmoga.dpixel.Messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfWealth extends Ring {
	{
		name = Messages.get(this, "name");
	}

	@Override
	protected RingBuff buff() {
		return new Wealth();
	}

	@Override
	public String desc() {
		return isKnown() ? Messages.get(this, "desc") : super.desc();
	}

	public class Wealth extends RingBuff {
	}
}
