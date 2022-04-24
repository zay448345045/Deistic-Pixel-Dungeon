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

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Badges;
import com.avmoga.dpixel.Chrome;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.GamesInProgress;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.actors.hero.HeroClass;
import com.avmoga.dpixel.actors.hero.HeroRace;
import com.avmoga.dpixel.actors.hero.HeroSubRace;
import com.avmoga.dpixel.effects.Speck;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.ui.Archs;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.SimpleButton;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.utils.Utils;
import com.avmoga.dpixel.windows.WndOptions;
import com.avmoga.dpixel.windows.WndRace;
import com.avmoga.dpixel.windows.WndTitledMessage;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;

import java.util.HashMap;

public class RaceScene extends PixelScene {

	private static final float BUTTON_HEIGHT	= 24;
	private static final float GAP				= 2;

	private static final String TXT_TITLE	= "选择你的种族";

	private static final String TXT_LOAD	= "读取游戏";
	private static final String TXT_NEW		= "新游戏";

	private static final String TXT_ERASE		= "Erase current game";
	private static final String TXT_DPTH_LVL	= "Depth: %d, level: %d";

	private static final String TXT_REALLY	= "Do you really want to start new game?";
	private static final String TXT_WARNING	= "Your current game progress will be erased.";
	private static final String TXT_YES		= "Yes, start new game";
	private static final String TXT_NO		= "No, return to main menu";

	private static final String TXT_UNLOCK	= "To unlock this character class, slay the 3rd boss with any other class";

	private float width;
	private float height;
	private float top;
	private float left;

	private static HashMap<HeroRace, GemButton> gems = new HashMap<HeroRace, GemButton>();

	private RaceScene.Avatar avatar;
	private NinePatch frame;
	private RenderedText className;

	private SimpleButton btnMastery;

	private RaceScene.GameButton btnLoad;
	private RaceScene.GameButton btnNewGame;

	private boolean huntressUnlocked;
	private Group unlock;
	public static HeroClass curClass;
	public static HeroRace curRace;

	@Override
	public void create() {

		super.create();

		Badges.loadGlobal();

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		width = 128;
		height = 220;
		left = (w - width) / 2;
		top = (h - height) / 2;

		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );

		RenderedText title = PixelScene.renderText( TXT_TITLE, 9 );
		title.hardlight( Window.TITLE_COLOR );
		title.x = align( (w - title.width()) / 2 );
		title.y = align( top );
		add( title );

		float pos = title.y + title.height() + GAP;

		GemButton btns[] = {
				new GemButton( HeroRace.HUMAN ),
				new GemButton( HeroRace.DWARF ),
				new GemButton( HeroRace.WRAITH ),
				new GemButton( HeroRace.GNOLL ) };

		float space = width;
		for (GemButton btn : btns) {
			space -= btn.width();
		}

		float p = 0;
		for (GemButton btn : btns) {
			add( btn );
			btn.setPos( align( left + p ), align( pos ) );
			p += btn.width() + space / 3;
		}


		frame = Chrome.get( Chrome.Type.TOAST_TR );
		add( frame );

		btnNewGame = new GameButton( TXT_NEW ) {
			@Override
			protected void onClick() {
				if (GamesInProgress.check( curClass ) != null) {
					RaceScene.this.add( new WndOptions( Messages.get(StartScene.class, "really"),
							Messages.get(StartScene.class, "warning"),
							Messages.get(StartScene.class, "yes"),
							Messages.get(StartScene.class, "no")) {
						@Override
						protected void onSelect( int index ) {
							if (index == 0) {
								startNewGame();
							}
						}
					} );

				} else {
					startNewGame();
				}
			}
		};
		add( btnNewGame );

		btnLoad = new GameButton( TXT_LOAD ) {
			@Override
			protected void onClick() {
				InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
				Game.switchScene( InterlevelScene.class );
			}
		};
		add( btnLoad );

		frame.size( width, BUTTON_HEIGHT + frame.marginVer() );
		frame.x = align( left );
		frame.y = align( h - top - frame.height() );

		avatar = new Avatar();

		NinePatch avFrame = Chrome.get( Chrome.Type.TOAST_TR );
		avFrame.size( avatar.width() * 1.6f, avatar.height() * 1.6f );
		avFrame.x = align( (w - avFrame.width()) / 2 );
		avFrame.y = align( (frame.y + btns[0].bottom() - avFrame.height()) / 2 );
		add( avFrame );

		className = PixelScene.renderText( "Placeholder", 9 );
		className.y = align( avFrame.y + avFrame.innerBottom() - className.height() );
		add( className );

		avatar.point( avFrame.center() );
		avatar.camera = Camera.main;
		align( avatar );
		add( avatar );

		Image iconInfo = Icons.INFO.get();
		iconInfo.x = avFrame.x + avFrame.innerRight() - iconInfo.width();
		iconInfo.y = avFrame.y + avFrame.marginTop();
		add( iconInfo );

		add( new TouchArea( avFrame ) {
			@Override
			protected void onClick( Touchscreen.Touch touch ) {
				add( new WndRace( curRace ) );
			}
		} );

		btnMastery = new SimpleButton( new ItemSprite(ItemSpriteSheet.SPECIALTY, new ItemSprite.Glowing( 0xFFFF00 )) ) {
			@Override
			protected void onClick() {
				String text = null;
				switch (curRace) {
					case HUMAN:
						text = HeroSubRace.MERCENARY.desc() + "\n\n" + HeroSubRace.DEMOLITIONIST.desc();
						break;
					case DWARF:
						text = HeroSubRace.WARLOCK.desc() + "\n\n" + HeroSubRace.MONK.desc();
						break;
					case WRAITH:
						text = HeroSubRace.RED.desc() + "\n\n" + HeroSubRace.BLUE.desc();
						break;
					case GNOLL:
						text = HeroSubRace.SHAMAN.desc() + "\n\n" + HeroSubRace.BRUTE.desc();
						break;
				}
				RaceScene.this.add( new WndTitledMessage(new ItemSprite(ItemSpriteSheet.SPECIALTY,
						new ItemSprite.Glowing( 0x009999 )), "种族之书",
						text ) );
			}
		};
		btnMastery.setPos(
				avFrame.x + avFrame.innerRight() - btnMastery.width(),
				avFrame.y + avFrame.innerBottom() - btnMastery.height() );
		add( btnMastery );

		unlock = new Group();
		add( unlock );

		curRace = null;
		updateClass( HeroRace.values()[ShatteredPixelDungeon.lastClass()] );

		fadeIn();
	}

	private void updateClass(HeroRace cl ) {

		if (curRace == cl) {
			add(new WndRace(cl));
			return;
		}

		if (curRace != null) {
			gems.get( curRace ).highlight( false );
		}

		gems.get( curRace = cl ).highlight( true );

		className.text( Utils.capitalize( cl.title() ) );
		className.x = align( frame.center().x - className.width() / 2 );

		if (cl != HeroRace.D || huntressUnlocked) {

			unlock.visible = true;

			float buttonPos = frame.y + frame.innerBottom() - BUTTON_HEIGHT;

			GamesInProgress.Info info = GamesInProgress.check( curClass );
			if (info != null) {

				btnLoad.visible = true;
				btnLoad.secondary( Utils.format( TXT_DPTH_LVL, info.depth, info.level ) );
				btnNewGame.visible = true;
				btnNewGame.secondary( TXT_ERASE );

				float w = (frame.innerWidth() - GAP) / 2;

				btnLoad.setRect(
						frame.x + frame.marginLeft(), buttonPos, w, BUTTON_HEIGHT );
				btnNewGame.setRect(
						btnLoad.right() + GAP, buttonPos, w, BUTTON_HEIGHT );

			} else {
				btnLoad.visible = false;

				btnNewGame.visible = true;
				btnNewGame.secondary( null );
				btnNewGame.setRect(
						frame.x + frame.marginLeft(), buttonPos, frame.innerWidth(), BUTTON_HEIGHT );
			}
			btnMastery.active =
					btnMastery.visible =
							true;

		} else {

			unlock.visible = true;
			btnLoad.visible = false;
			btnNewGame.visible = false;
			btnMastery.active = btnMastery.visible = true;

		}

		avatar.selectClass( curRace );
	}

	private void startNewGame() {

		Dungeon.hero = null;
		InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

		if (ShatteredPixelDungeon.intro()) {
			ShatteredPixelDungeon.intro(false);
			Game.switchScene( IntroScene.class );
		} else {
			Game.switchScene( InterlevelScene.class );
		}
	}

	@Override
	protected void onBackPressed() {
		Game.switchScene( StartScene.class );
	}

	private static class Avatar extends Image {

		private static final int WIDTH	= 24;
		private static final int HEIGHT	= 32;
		private static final int SCALE	= 2;

		private TextureFilm frames;

		private float brightness = 0;

		public Avatar() {
			super( Assets.RACEAVATARS );

			frames = new TextureFilm( texture, WIDTH, HEIGHT );
			selectClass( HeroRace.HUMAN );
			scale.set( SCALE );

			origin.set( width() / 2, height() / 2 );
		}

		public void selectClass(HeroRace cl ) {
			frame( frames.get( cl.ordinal() ) );
		}

		public void flash() {
			brightness = 1f;
		}

		@Override
		public void update() {
			super.update();

			if (brightness > 0) {
				ra = ga = ba = brightness;
				brightness -= Game.elapsed * 4;
				if (brightness < 0) {
					resetColor();
				}
			}
		}
	}

	private class GemButton extends Button {

		private NinePatch bg;
		private Image icon;

		private HeroRace cl;

		public GemButton( HeroRace cl ) {
			super();

			this.cl = cl;
			gems.put( cl, this );

			icon.copy( Icons.get( cl ) );
			setSize( 32, 32 );

			highlight( false );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			bg = Chrome.get( Chrome.Type.GEM );
			add( bg );

			icon = new Image();
			add( icon );
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;
			bg.size( width, height );

			icon.x = x + (width - icon.width) / 2;
			icon.y = y + (height - icon.height) / 2;
		}

		@Override
		protected void onTouchDown() {
			Emitter emitter = (Emitter)recycle( Emitter.class );
			emitter.revive();
			emitter.pos( bg );
			emitter.burst( Speck.factory( Speck.LIGHT ), 3 );

			updateClass( cl );
			avatar.flash();

			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 1.2f );
		}

		public void highlight( boolean value ) {
			if (value) {
				bg.rm = 1.2f;
				bg.gm = 1.2f;
				bg.bm = 1.1f;
				bg.am = 0.8f;
			} else {
				bg.rm = 1.0f;
				bg.gm = 1.0f;
				bg.bm = 1.0f;
				bg.am = 0.6f;
			}
		}
	}

	private static class GameButton extends RedButton {

		private static final int SECONDARY_COLOR	= 0xCACFC2;

		private RenderedText secondary;

		public GameButton( String primary ) {
			super( primary );

			this.secondary.text( null );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			secondary = renderText( 6 );
			secondary.hardlight( SECONDARY_COLOR );
			add( secondary );
		}

		@Override
		protected void layout() {
			super.layout();

			if (secondary.text().length() > 0) {
				text.y = y + (height - text.height() - secondary.baseLine()) / 2;

				secondary.x = align( x + (width - secondary.width()) / 2 );
				secondary.y = align( text.y + text.height() );
			} else {
				text.y = y + (height - text.baseLine()) / 2;
			}
		}

		public void secondary( String text ) {
			secondary.text( text );
		}
	}
}