package com.avmoga.dpixel.actors.mobs.pets;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.sprites.BlueDragonSprite;
import com.watabou.utils.Random;

public class Statuette extends PET {

	{
		name = "animated statuette";
		spriteClass = BlueDragonSprite.class;
		flying = false;
		state = HUNTING;
		level = 1;
		type = 12;
		cooldown=1000;

	}
	
	@Override
	protected boolean act() {
		
		if (cooldown>0){
			cooldown=Math.max(cooldown-(level*level),0);
			if (cooldown==0)
				yell("My power has peaked!");
		}
		
		if (Random.Float()<regenChance && HP<HT){HP+=regen;}

		return super.act();
	}
	
	@Override
	public int damageRoll() {
		
		int dmg=0;
		if (cooldown==0){
			dmg=Random.NormalIntRange(HT/2, HT); 
			yell("Hammerfist!");
			cooldown=1000;
		} else {
			dmg=Random.NormalIntRange(HT/5, HT/2) ;
		}
		return dmg;
			
	}
	
	protected int regen = 1;	
	protected float regenChance = 0.05f;	

	@Override
	public void interact() {

		int curPos = pos;

		moveSprite(pos, Dungeon.hero.pos);
		move(Dungeon.hero.pos);

		Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		Dungeon.hero.move(curPos);

		Dungeon.hero.spend(1 / Dungeon.hero.speed());
		Dungeon.hero.busy();
	}
}
