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

import com.avmoga.dpixel.items.DewVial;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.utils.Utils;

public class WndDewDrawInfo extends Window {
	
	//if people don't get it after this, I quit. I just quit.

	private static final String TXT_MESSAGE = "浇灌露水可以让特殊级别的生物滴露水来装满你的小瓶。"
			+"此外，您的角色在每个正常级别开始时都会获得露珠充能 "
			+"只要你带上露水瓶，敌人死亡就会掉落露水来装满你的瓶子。 ";

	private static final String TXT_MESSAGE2 = "每个级别的露水都会向您充能的一定数量的露珠能量。 \n" +
			"每个级别还有一个移动目标，用于杀死所有常规生成的敌人。（不是像雕像和食人鱼这样的特殊敌人）\n" +
			"杀死所有随关卡生成的常规敌人会有额外加成。";

	private static final String TXT_MESSAGE3 = "如果您以少于目标的步数清除一个级别，则额外的步数将添加到您的下一个级别的露水充能中。 \n" +
			"你需要尽快通关才能获得升级。";

	private static final String TXT_MESSAGE4 = "露水瓶还允许您选择在祝福时应用升级的项目。";

	private static final String TXT_WATER = "好的，我明白了";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndDewDrawInfo(final Item item) {

		super();
		
		Item dewvial = new DewVial();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(dewvial.image(), null));
		titlebar.label(Utils.capitalize(dewvial.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(TXT_MESSAGE, 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RenderedTextMultiline message2 = PixelScene.renderMultiline(TXT_MESSAGE2, 6);
		message2.maxWidth(WIDTH);
		message2.setPos(0, message.top() + message.height() + GAP);
		add(message2);

		RenderedTextMultiline message3 = PixelScene.renderMultiline(TXT_MESSAGE3, 6);
		message3.maxWidth(WIDTH);
		message3.setPos(0, message2.top() + message2.height() + GAP);
		add(message3);

		RenderedTextMultiline message4 = PixelScene.renderMultiline(TXT_MESSAGE4, 6);
		message4.maxWidth(WIDTH);
		message4.setPos(0, message3.top() + message3.height() + GAP);
		add(message4);


		RedButton btnBattle = new RedButton(TXT_WATER) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		btnBattle.setRect(0, message4.top() + message4.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		

		resize(WIDTH, (int) btnBattle.bottom());
	}

	
}
