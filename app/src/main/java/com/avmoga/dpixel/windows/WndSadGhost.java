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

import com.avmoga.dpixel.Challenges;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.actors.mobs.npcs.Ghost;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.FetidRatSprite;
import com.avmoga.dpixel.sprites.GnollTricksterSprite;
import com.avmoga.dpixel.sprites.GreatCrabSprite;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.utils.GLog;

public class WndSadGhost extends Window {

	private static final String TXT_RAT = Messages.get(WndSadGhost.class, "rat");
	private static final String TXT_GNOLL = Messages.get(WndSadGhost.class, "gnoll");
	private static final String TXT_CRAB = Messages.get(WndSadGhost.class, "crab");
	private static final String TXT_GIVEITEM = Messages.get(WndSadGhost.class, "giveitem");
	private static final String TXT_WEAPON = Messages.get(WndSadGhost.class, "weapon");
	private static final String TXT_ARMOR = Messages.get(WndSadGhost.class, "armor");

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndSadGhost(final Ghost ghost, final int type) {

		super();

		IconTitle titlebar = new IconTitle();
		RenderedTextMultiline message;
		switch (type) {
		case 1:
		default:
			titlebar.icon(new FetidRatSprite());
			titlebar.label("击败腐臭老鼠");
			message = PixelScene.renderMultiline(TXT_RAT + TXT_GIVEITEM, 6);
			break;
		case 2:
			titlebar.icon(new GnollTricksterSprite());
			titlebar.label("击败豺狼诡术师");
			message = PixelScene.renderMultiline(TXT_GNOLL + TXT_GIVEITEM, 6);
			break;
		case 3:
			titlebar.icon(new GreatCrabSprite());
			titlebar.label("击败巨大螃蟹");
			message = PixelScene.renderMultiline(TXT_CRAB + TXT_GIVEITEM, 6);
			break;

		}

		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		message.maxWidth = WIDTH;
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnWeapon = new RedButton(TXT_WEAPON) {
			@Override
			protected void onClick() {
				selectReward(ghost, Ghost.Quest.weapon);
			}
		};
		btnWeapon.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnWeapon);

		if (!Dungeon.isChallenged(Challenges.NO_ARMOR)) {
			RedButton btnArmor = new RedButton(TXT_ARMOR) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.armor);
				}
			};
			btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnArmor);

			resize(WIDTH, (int) btnArmor.bottom());
		} else {
			resize(WIDTH, (int) btnWeapon.bottom());
		}
	}

	private void selectReward(Ghost ghost, Item reward) {

		hide();

		if (reward.doPickUp(Dungeon.hero)) {
			GLog.i(Hero.TXT_YOU_NOW_HAVE, reward.name());
		} else {
			Dungeon.level.drop(reward, ghost.pos).sprite.drop();
		}

		ghost.yell(Messages.get(WndSadGhost.class, "farewell"));
		ghost.die(null);

		Ghost.Quest.complete();
	}
}
