package com.avmoga.dpixel.items.bags;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.food.BlueMilk;
import com.avmoga.dpixel.items.food.DeathCap;
import com.avmoga.dpixel.items.food.Earthstar;
import com.avmoga.dpixel.items.food.GoldenJelly;
import com.avmoga.dpixel.items.food.JackOLantern;
import com.avmoga.dpixel.items.food.PixieParasol;
import com.avmoga.dpixel.items.potions.Potion;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

/**
 * Created by debenhame on 05/02/2015.
 */
public class PotionBandolier extends Bag {

	{
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.BANDOLIER;

		size = 20;
	}

	@Override
	public boolean grab(Item item) {
		return item instanceof Potion 
				|| item instanceof BlueMilk
				|| item instanceof DeathCap
				|| item instanceof Earthstar
				|| item instanceof GoldenJelly
				|| item instanceof JackOLantern
				|| item instanceof PixieParasol;
	}

	@Override
	public int price() {
		return 50;
	}
	@Override
	public String info() {
		return Messages.get(this, "desc");
	}
}
