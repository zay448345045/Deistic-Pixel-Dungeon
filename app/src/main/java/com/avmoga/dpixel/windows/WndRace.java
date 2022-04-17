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
package com.avmoga.dpixel.windows;

import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.hero.HeroSubRace;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.utils.Utils;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Group;

public class WndRace extends WndTabbed {

	private static final String TXT_MASTERY = "专精";

	private static final int WIDTH = 110;

	private static final int TAB_WIDTH = 50;

	private HeroRace cl;

	private PerksTab tabPerks;
	private MasteryTab tabMastery;

	public WndRace(HeroRace cl2) {

		super();

		this.cl = cl2;

		tabPerks = new PerksTab();
		add(tabPerks);

		Tab tab = new RankingTab(Utils.capitalize(cl2.title()), tabPerks);
		tab.setSize(TAB_WIDTH, tabHeight());
		add(tab);

			tabMastery = new MasteryTab();
			add(tabMastery);

			tab = new RankingTab(TXT_MASTERY, tabMastery);
			add(tab);

			resize((int) Math.max(tabPerks.width, tabMastery.width),
					(int) Math.max(tabPerks.height, tabMastery.height));

		layoutTabs();

		select(0);
	}

	private class RankingTab extends LabeledTab {

		private Group page;

		public RankingTab(String label, Group page) {
			super(label);
			this.page = page;
		}

		@Override
		protected void select(boolean value) {
			super.select(value);
			if (page != null) {
				page.visible = page.active = selected;
			}
		}
	}

	private class PerksTab extends Group {

		private static final int MARGIN = 4;
		private static final int GAP = 4;

		public float height;
		public float width;

		public PerksTab() {
			super();

			float dotWidth = 0;

			String[] items = cl.perks();
			float pos = MARGIN;

			for (int i = 0; i < items.length; i++) {

				if (i > 0) {
					pos += GAP;
				}

				BitmapText dot = PixelScene.createText("-", 6);
				dot.x = MARGIN;
				dot.y = pos;
				if (dotWidth == 0) {
					dot.measure();
					dotWidth = dot.width();
				}
				add(dot);

				RenderedTextMultiline item = PixelScene.renderMultiline(items[i], 6);
				item.maxWidth((int) (WIDTH - MARGIN * 2 - dotWidth));
				item.setPos(dot.x + dotWidth, pos);
				add(item);

				pos += item.height();
				float w = item.width();
				if (w > width) {
					width = w;
				}
			}

			width += MARGIN + dotWidth;
			height = pos + MARGIN;
		}
	}

	private class MasteryTab extends Group {

		private static final int MARGIN = 4;

		public float height;
		public float width;

		public MasteryTab() {
			super();

			String message = null;
			switch (cl) {
				case HUMAN:
					message = HeroSubRace.DEMOLITIONIST.desc() + "\n\n"
							+ HeroSubRace.MERCENARY.desc();
					break;
				case DWARF:
					message = HeroSubRace.WARLOCK.desc() + "\n\n"
							+ HeroSubRace.MONK.desc();
					break;
				case WRAITH:
					message = HeroSubRace.RED.desc() + "\n\n"
							+ HeroSubRace.BLUE.desc();
					break;
				case GNOLL:
					message = HeroSubRace.SHAMAN.desc() + "\n\n"
							+ HeroSubRace.BRUTE.desc();
					break;
			}

			RenderedTextMultiline text = PixelScene.renderMultiline(6);
			text.text(message, WIDTH - MARGIN * 2);
			text.setPos(MARGIN, MARGIN);
			add(text);

			height = text.bottom() + MARGIN;
			width = text.right() + MARGIN;
		}
	}
}