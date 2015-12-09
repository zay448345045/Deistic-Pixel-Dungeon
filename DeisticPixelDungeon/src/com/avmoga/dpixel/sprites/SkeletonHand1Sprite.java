/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.avmoga.dpixel.sprites;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.sprites.CharSprite.State;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.MovieClip.Animation;

public class SkeletonHand1Sprite extends MobSprite {

	public SkeletonHand1Sprite() {
		super();

		texture(Assets.SKELETONHAND1);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 1);

		run = new Animation(4, true);
		run.frames(frames, 0, 1);

		attack = new Animation(5, false);
		attack.frames(frames, 2, 3, 4, 5);

		die = new Animation(5, false);
		die.frames(frames, 6, 7, 8, 9);

		play(idle);
	}
	
	@Override
	public void link(Char ch) {
		super.link(ch);
		add(State.BURNING);
	}

	@Override
	public void die() {
		super.die();
		remove(State.BURNING);
	}

}
