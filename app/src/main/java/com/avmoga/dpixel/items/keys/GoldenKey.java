
package com.avmoga.dpixel.items.keys;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

public class GoldenKey extends Key {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.GOLDEN_KEY;
	}

	public GoldenKey() {
		this(0);
	}

	public GoldenKey(int depth) {
		super();
		this.depth = depth;
	}

	@Override
	public String info() {
		return Messages.get(this, "desc");
	}
}
