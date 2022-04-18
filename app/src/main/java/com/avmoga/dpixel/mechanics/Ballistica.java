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
package com.avmoga.dpixel.mechanics;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.levels.Level;

import java.util.ArrayList;

public class Ballistica {

	private void collide(int cell){
		if (collisionPos == null)
			collisionPos = cell;
	}

	private void build( int from, int to, boolean stopTarget, boolean stopChars, boolean stopTerrain, boolean ignoreSoftSolid ) {
		int w = Dungeon.level.width();

		int x0 = from % w;
		int x1 = to % w;
		int y0 = from / w;
		int y1 = to / w;

		int dx = x1 - x0;
		int dy = y1 - y0;

		int stepX = dx > 0 ? +1 : -1;
		int stepY = dy > 0 ? +1 : -1;

		dx = Math.abs( dx );
		dy = Math.abs( dy );

		int stepA;
		int stepB;
		int dA;
		int dB;

		if (dx > dy) {

			stepA = stepX;
			stepB = stepY * w;
			dA = dx;
			dB = dy;

		} else {

			stepA = stepY * w;
			stepB = stepX;
			dA = dy;
			dB = dx;

		}

		int cell = from;

		int err = dA / 2;
		while (Dungeon.level.insideMap(cell)) {

			//if we're in a wall, collide with the previous cell along the path.
			//we don't use solid here because we don't want to stop short of closed doors
			if (stopTerrain && cell != sourcePos && !Dungeon.level.passable[cell] && !Dungeon.level.avoid[cell]) {
				collide(path.get(path.size() - 1));
			}

			path.add(cell);

			if (stopTerrain && cell != sourcePos && Dungeon.level.solid[cell]) {
				if (ignoreSoftSolid && (Dungeon.level.passable[cell] || Dungeon.level.avoid[cell])) {
					//do nothing
				} else {
					collide(cell);
				}
			} else if (cell != sourcePos && stopChars && Actor.findChar( cell ) != null) {
				collide(cell);
			} else if  (cell == to && stopTarget){
				collide(cell);
			}

			cell += stepA;

			err += dB;
			if (err >= dA) {
				err = err - dA;
				cell = cell + stepB;
			}
		}
	}

	public ArrayList<Integer> path = new ArrayList<>();
	public Integer sourcePos = null;
	public Integer collisionPos = null;
	public Integer collisionProperties = null;
	public Integer dist = 0;

	//parameters to specify the colliding cell
	public static final int STOP_TARGET = 1;    //ballistica will stop at the target cell
	public static final int STOP_CHARS = 2;     //ballistica will stop on first char hit
	public static final int STOP_SOLID = 4;     //ballistica will stop on solid terrain
	public static final int IGNORE_SOFT_SOLID = 8; //ballistica will ignore soft solid terrain, such as doors and webs

	public static final int PROJECTILE =  	STOP_TARGET	| STOP_CHARS	| STOP_SOLID;

	public static final int MAGIC_BOLT =    STOP_CHARS  | STOP_SOLID;

	public static final int WONT_STOP =     0;

	public static int[] trace = new int[Math.max(Level.getWidth(), Level.HEIGHT)];
	public static int distance;

	public Ballistica( int from, int to, int params ){
		sourcePos = from;
		collisionProperties = params;
		build(from, to,
				(params & STOP_TARGET) > 0,
				(params & STOP_CHARS) > 0,
				(params & STOP_SOLID) > 0,
				(params & IGNORE_SOFT_SOLID) > 0);

		if (collisionPos != null) {
			dist = path.indexOf(collisionPos);
		} else if (!path.isEmpty()) {
			collisionPos = path.get(dist = path.size() - 1);
		} else {
			path.add(from);
			collisionPos = from;
			dist = 0;
		}
	}


    public static int cast(int from, int to, boolean magic, boolean hitChars) {

		int w = Level.getWidth();

		int x0 = from % w;
		int x1 = to % w;
		int y0 = from / w;
		int y1 = to / w;

		int dx = x1 - x0;
		int dy = y1 - y0;

		int stepX = dx > 0 ? +1 : -1;
		int stepY = dy > 0 ? +1 : -1;

		dx = Math.abs(dx);
		dy = Math.abs(dy);

		int stepA;
		int stepB;
		int dA;
		int dB;

		if (dx > dy) {

			stepA = stepX;
			stepB = stepY * w;
			dA = dx;
			dB = dy;

		} else {

			stepA = stepY * w;
			stepB = stepX;
			dA = dy;
			dB = dx;

		}

		distance = 1;
		trace[0] = from;

		int cell = from;

		int err = dA / 2;
		while (cell != to || magic) {

			cell += stepA;

			err += dB;
			if (err >= dA) {
				err = err - dA;
				cell = cell + stepB;
			}

			trace[distance++] = cell;

			if (!Level.passable[cell] && !Level.avoid[cell]) {
				return trace[--distance - 1];
			}

			if (Level.losBlocking[cell]
					|| (hitChars && Actor.findChar(cell) != null)) {
				return cell;
			}
		}

		trace[distance++] = cell;

		return to;
	}
}
