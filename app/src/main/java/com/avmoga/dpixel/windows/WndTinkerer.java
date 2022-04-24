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

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Statistics;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Dewcharge;
import com.avmoga.dpixel.actors.mobs.npcs.Tinkerer1;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.Mushroom;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.utils.GLog;
import com.avmoga.dpixel.utils.Utils;

public class WndTinkerer extends Window {

	private static final String TXT_MESSAGE = "感谢您送来的毒菌蘑菇！\n" +
			"我可以为你升级你的露水瓶。\n" +
			"我可以让它从某些被击败的敌人身上吸取露水，或者我可以让它能够通过用露水浇灌周围的地牢来再生植被。";

	private static final String TXT_WATER = "浇灌露水";
	private static final String TXT_DRAW = "露珠充能";

	private static final String TXT_FARAWELL = "祝你好运!";
	private static final String TXT_FARAWELL_DRAW = "祝你好运!";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndTinkerer(final Tinkerer1 tinkerer, final Item item) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Utils.capitalize(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(TXT_MESSAGE, 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);


		RedButton btnBattle = new RedButton(TXT_WATER) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer, 1);
			}
		};
		btnBattle.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add(btnBattle);

		/*
		BitmapTextMultiline message_draw = PixelScene
				.createMultiline(TXT_MESSAGE_DRAW, 6);
		message_draw.maxWidth = WIDTH;
		message_draw.measure();
		message_draw.y = btnBattle.bottom() + GAP;
		add(message_draw);
		*/

		RedButton btnNonBattle = new RedButton(TXT_DRAW) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer, 2);
			}
		};

		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);

		resize(WIDTH, (int) btnNonBattle.bottom());
	}

	private void selectUpgrade(Tinkerer1 tinkerer, int type) {

		hide();

		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);

		if (type==1){

			Dungeon.dewWater=true;

		} else if (type==2){

			Dungeon.dewDraw=true;
		}

		if (type==1){
			tinkerer.yell(Utils.format(TXT_FARAWELL, Dungeon.hero.givenName()));
		} else if (type==2){
			tinkerer.yell(Utils.format(TXT_FARAWELL_DRAW, Dungeon.hero.givenName()));
			Statistics.prevfloormoves=500;
			Buff.prolong(Dungeon.hero, Dewcharge.class, Dewcharge.DURATION+50);
			GLog.p("你感觉地牢的露珠能量正在袭来！");
		}

		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
}
