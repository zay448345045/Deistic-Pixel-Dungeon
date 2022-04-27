package com.avmoga.dpixel.items.bags;

import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.artifacts.*;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

public class ArtifactBox extends Bag{

	{
		name = "神器宝盒";
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
		return "这个收藏箱内衬舒适的面料，并有一个大的钥匙孔供你保管物品。\n\n" +
				"你可以在这个盒子里储存相当数量的神器物品。";
	}
}
