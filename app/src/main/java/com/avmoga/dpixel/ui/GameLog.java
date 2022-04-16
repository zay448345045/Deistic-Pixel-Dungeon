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
package com.avmoga.dpixel.ui;

import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.CharSprite;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Signal;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class GameLog extends Component implements Signal.Listener<String> {

	private static final int MAX_MESSAGES = 3;
	private static final int MAX_LINES = 3;
	private static final Pattern PUNCTUATION = Pattern.compile(".*[.,;?! ]$");

	private RenderedTextMultiline lastEntry;
	private int lastColor;
	private static ArrayList<Entry> entries = new ArrayList<>();
	public GameLog() {
		super();
		GLog.update.add(this);

		newLine();
	}

	public void newLine() {
		lastEntry = null;
	}

	@Override
	public void onSignal(String text) {

		int color = CharSprite.DEFAULT;
		if (text.startsWith(GLog.POSITIVE)) {
			text = text.substring(GLog.POSITIVE.length());
			color = CharSprite.POSITIVE;
		} else if (text.startsWith(GLog.NEGATIVE)) {
			text = text.substring(GLog.NEGATIVE.length());
			color = CharSprite.NEGATIVE;
		} else if (text.startsWith(GLog.WARNING)) {
			text = text.substring(GLog.WARNING.length());
			color = CharSprite.WARNING;
		} else if (text.startsWith(GLog.HIGHLIGHT)) {
			text = text.substring(GLog.HIGHLIGHT.length());
			color = CharSprite.NEUTRAL;
		}

		text = Utils.capitalize(text)
				+ (PUNCTUATION.matcher(text).matches() ? "" : "");

		if (lastEntry != null && color == lastColor && lastEntry.nLines < MAX_LINES) {

			String lastMessage = lastEntry.text();
			lastEntry.text(lastMessage.length() == 0 ? text : lastMessage + " " + text);

			entries.get(entries.size() - 1).text = lastEntry.text();

		} else {

			lastEntry = PixelScene.renderMultiline(text, 6);
			lastEntry.hardlight(color);
			lastColor = color;
			add(lastEntry);

			entries.add(new Entry(text, color));

		}

		if (length > MAX_MESSAGES) {
			remove(members.get(0));
		}

		layout();
	}

	@Override
	protected void layout() {
		float pos = y;
		for (int i = length - 1; i >= 0; i--) {
			RenderedTextMultiline entry = (RenderedTextMultiline) members.get(i);
			entry.maxWidth((int) width);
			entry.setPos(x, pos - entry.height());
			pos -= entry.height();
		}
	}

	private static class Entry {
		public String text;
		public int color;

		public Entry(String text, int color) {
			this.text = text;
			this.color = color;
		}
	}

	@Override
	public void destroy() {
		GLog.update.remove(this);
		super.destroy();
	}

	public static void wipe() {
		entries.clear();
	}
}
