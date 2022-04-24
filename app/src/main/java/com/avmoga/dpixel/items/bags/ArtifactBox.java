package com.avmoga.dpixel.items.bags;

import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.artifacts.*;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

public class ArtifactBox extends Bag{

	{
		name = "artifact collection";
		name = "神器宝箱";
		image = ItemSpriteSheet.ARTIFACT_BOX;

		size = 16;
		
	}
	@Override
	public boolean grab(Item item) {
		if (item instanceof Artifact && !(item instanceof RingOfDisintegration)){
			return true;
			} else {
			return false;
			}
	}
	@Override
	public String info() {
		return "This collector's box is lined with a pleasant-feeling fabric, and there is a large keyhole for "
				+ "you to keep the contents secure with. You could probably store a decent number of valuable "
				+"items in this box.";
	}
}
