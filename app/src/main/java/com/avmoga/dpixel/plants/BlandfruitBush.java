package com.avmoga.dpixel.plants;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.items.food.Blandfruit;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;

/**
 * Created by Evan on 13/08/2014.
 */
public class BlandfruitBush extends Plant {

	private static final String TXT_DESC = Messages.get(BlandfruitBush.class, "desc");

	{
		image = 8;
		plantName = Messages.get(BlandfruitBush.class, "name");
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		Dungeon.level.drop(new Blandfruit(), pos).sprite.drop();
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = Messages.get(BlandfruitBush.class, "name");

			name = Messages.get(this, "name");
			image = ItemSpriteSheet.SEED_BLANDFRUIT;

			plantClass = BlandfruitBush.class;
			alchemyClass = null;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}
