
package com.avmoga.dpixel.items.keys;

import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

public class SkeletonKey extends Key {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.SKELETON_KEY;
		stackable = false;
	}

	public SkeletonKey() {
		this(0);
	}

	public SkeletonKey(int depth) {
		super();
		this.depth = depth;
	}

	@Override
	public boolean isSimilar(Item item) {
		return false;
	}

	@Override
	public String info() {
		return Messages.get(this, "desc");
	}
}
