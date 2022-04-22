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
package com.avmoga.dpixel.scenes;

import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.effects.Flare;
import com.avmoga.dpixel.ui.Archs;
import com.avmoga.dpixel.ui.ExitButton;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;

public class AboutScene extends PixelScene {

	private static final String TTL_SHPX = "Deistic Pixel Dungeon";

	private static final String TXT_SHPX =
			"One-Go-Ling-汉化组\n\n鸣谢以下所有人员：\n\n" +
					"发芽作者: Dachhack\n\n" +
					"自然作者: Cipheus\n\n" +
					"ESPD作者: G2159687\n\n" +
					"破碎地牢：Evan\n\n" +
					"DPD汉化总监：JDSA Ling\n\n" +
					"翻译组详见翻译名单";

	private static final String TTL_LOPX = "汉化采用ESPD,SHPD双重汉化\n\n";

	private static final String TTL_WATA = "Pixel Dungeon & SPD";

	private static final String TXT_WATA =
			"Code & Graphics: Watabou\n" +
					"Music: Cube_Code";

	private static final String LNK_WATA = "pixeldungeon.watabou.ru";

	@Override
	public void create() {
		super.create();

		final float colWidth = Camera.main.width / (ShatteredPixelDungeon.landscape() ? 2 : 1);
		final float colTop = (Camera.main.height / 2) - (ShatteredPixelDungeon.landscape() ? 30 : 90);
		final float wataOffset = ShatteredPixelDungeon.landscape() ? colWidth : 0;

		Image shpx = Icons.SHPX.get();
		shpx.x = (colWidth - shpx.width()) / 2;
		shpx.y = colTop;
		align(shpx);
		add( shpx );

		new Flare( 7, 64 ).color( 0xff00ff, true ).show( shpx, 0 ).angularSpeed = +20;

		RenderedText shpxtitle = renderText( TTL_SHPX, 8 );
		shpxtitle.hardlight( 0xff00ff );
		add( shpxtitle );

		shpxtitle.x = (colWidth - shpxtitle.width()) / 2;
		shpxtitle.y = shpx.y + shpx.height + 5;
		align(shpxtitle);

		RenderedText shpxetitle = renderText( TTL_LOPX, 6 );
		shpxetitle.hardlight( 0x00ffff );
		add( shpxetitle );

		shpxetitle.x = (colWidth - shpxetitle.width()) / 2;
		shpxetitle.y = shpx.y + shpx.height + 20;
		align(shpxetitle);


		RenderedTextMultiline shpxtext = renderMultiline( TXT_SHPX, 7 );
		shpxtext.maxWidth((int)Math.min(colWidth, 120));
		add( shpxtext );

		shpxtext.setPos((colWidth - shpxtext.width()) / 2, shpxtitle.y + shpxtitle.height() + 12);
		align(shpxtext);

		Image wata = Icons.SHPX.get();
		wata.x = wataOffset + (colWidth - wata.width()) / 2;
		wata.y = ShatteredPixelDungeon.landscape() ?
				colTop:
				shpxtext.top() + wata.height + 100;
		align(wata);
		add( wata );

		new Flare( 7, 64 ).color( 0x663399, true ).show( wata, 0 ).angularSpeed = +20;

		RenderedText wataTitle = renderText( TTL_WATA, 8 );
		wataTitle.hardlight(0x663399);
		add( wataTitle );

		wataTitle.x = wataOffset + (colWidth - wataTitle.width()) / 2;
		wataTitle.y = wata.y + wata.height + 11;
		align(wataTitle);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}
}