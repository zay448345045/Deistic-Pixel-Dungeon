package com.avmoga.dpixel.plants;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.blobs.Blob;
import com.avmoga.dpixel.actors.blobs.Unknows;
import com.avmoga.dpixel.effects.CellEmitter;
import com.avmoga.dpixel.effects.particles.SmokeParticle;
import com.avmoga.dpixel.items.potions.PotionOfLiquidFlame;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class BombFruit extends Plant{

	{
		image = 13;
		plantName = "炸弹果实";
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

//	@Override
//	public void activate(Char ch) {
//		if (ch != null) {
//			Buff.affect(ch, Burning.class).reignite(ch);
//		} else {
//			Buff.affect(ch, Burning.class).reignite(ch);
//		}
//		super.activate(ch);
//	}

	private static final String TXT_DESC = "这差不多是种活着的地雷，当爆炸草感知到周围有别的生物时，会将它们的种子猛烈的喷发出来。尸体将会形成致命的爆炸烟雾。";
	private static final String TXT_BLEW_UP = "你被你自己的爆炸草杀死了。";

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		GameScene.add(Blob.seed(pos, 2, Unknows.class));

		if (Dungeon.visible[pos]) {
			CellEmitter.get(pos).burst(SmokeParticle.FACTORY, 5);
		}
	}


	public static class Seed extends Plant.Seed {
		{
			plantName = "Blastseed";

			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_SUNGRASS;

			Sample.INSTANCE.play(Assets.SND_BLAST, 2);

			plantClass = BombFruit.class;
			alchemyClass = PotionOfLiquidFlame.class;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}


	}

}
