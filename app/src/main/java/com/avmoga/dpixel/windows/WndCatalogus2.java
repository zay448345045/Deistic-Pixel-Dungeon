package com.avmoga.dpixel.windows;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.wands.Wand;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.ScrollPane;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndCatalogus2 extends WndTabbed {

    private static final int WIDTH    = 112;
    private static final int HEIGHT    = 141;

    private static final int ITEM_HEIGHT	= 17;

    private RedButton btnJournal;
    private RedButton btnTitle;
    private RedButton SbtnTitle;
    private ScrollPane list;

    private ArrayList<WndCatalogus.ListItem> items = new ArrayList<>();

    private static boolean showPotions = true;

    public WndCatalogus2() {

        super();

        resize( WIDTH, HEIGHT );

        btnJournal = new RedButton("图鉴P1", 9 ){
            @Override
            protected void onClick() {
                hide();
                GameScene.show(new WndCatalogus());
            }
        };
        btnJournal.setRect(0, 0, WIDTH/3f - 1, btnJournal.reqHeight());
        PixelScene.align( btnJournal );
        add( btnJournal );

        //does nothing, we're already in the catalog
        btnTitle = new RedButton( "图鉴P2", 9 ){
            @Override
            protected void onClick() {
                hide();
                GameScene.show(new WndCatalogus2());
            }
        };
        btnTitle.textColor( 0xffff00 );
        btnTitle.setRect(WIDTH/3f+1, 0, WIDTH/3f - 1, btnTitle.reqHeight());
        PixelScene.align(btnTitle);
        add( btnTitle );

        SbtnTitle = new RedButton( "图鉴P3", 9 ){
            @Override
            protected void onClick() {
                hide();
                GameScene.show(new WndCatalogus3());
            }
        };
        SbtnTitle.setRect(WIDTH/3f+39, 0, WIDTH/3f - 1, SbtnTitle.reqHeight());
        PixelScene.align(SbtnTitle);
        add( SbtnTitle );

        list = new ScrollPane( new Component() ) {
            @Override
            public void onClick( float x, float y ) {
                int size = items.size();
                for (int i=0; i < size; i++) {
                    if (items.get( i ).onClick( x, y )) {
                        break;
                    }
                }
            }
        };
        add( list );
        list.setRect( 0, btnTitle.height() + 1, width, height - btnTitle.height() - 1 );

        boolean showPotions = WndCatalogus2.showPotions;
        Tab[] tabs = {
                new LabeledTab(  "法杖" ) {
                    protected void select( boolean value ) {
                        super.select( value );
                        WndCatalogus2.showPotions = !value;
                        updateList();
                    };
                }
        };
        for (Tab tab : tabs) {
            add( tab );
        }

        layoutTabs();

        select( showPotions ? 0 : 0);
    }

    private void updateList() {

        items.clear();

        Component content = list.content();
        content.clear();
        list.scrollTo( 0, 0 );

        float pos = 0;
        for (Class<? extends Item> itemClass : showPotions ? Wand.getKnown() : Wand.getKnown()) {
            WndCatalogus.ListItem item = new WndCatalogus.ListItem( itemClass );
            item.setRect( 0, pos, width, ITEM_HEIGHT );
            content.add( item );
            items.add( item );

            pos += item.height();
        }

        for (Class<? extends Item> itemClass : showPotions ? Wand.getUnknown() : Wand.getUnknown()) {
            WndCatalogus.ListItem item = new WndCatalogus.ListItem( itemClass );
            item.setRect( 0, pos, width, ITEM_HEIGHT );
            content.add( item );
            items.add( item );

            pos += item.height();
        }

        content.setSize( width, pos );
        list.setSize( list.width(), list.height() );
    }

    private static class ListItem extends Component {

        private Item item;
        private boolean identified;

        private ItemSprite sprite;
        private RenderedTextMultiline label;
        private ColorBlock line;

        public ListItem( Class<? extends Item> cl ) {
            super();

            try {
                item = cl.newInstance();
                if (identified = item.isIdentified()) {
                    sprite.view( item.image(), null );
                    label.text( Messages.titleCase(item.name()) );
                } else {
                    sprite.view( ItemSpriteSheet.WEAPON, null );
                    label.text( Messages.titleCase(item.trueName()) );
                    label.hardlight( 0xCCCCCC );
                }
            } catch (Exception e) {
                ShatteredPixelDungeon.reportException(e);
            }
        }

        @Override
        protected void createChildren() {
            sprite = new ItemSprite();
            add( sprite );

            label = PixelScene.renderMultiline( 7 );
            add( label );

            line = new ColorBlock( 1, 1, 0xFF222222);
            add(line);
        }

        @Override
        protected void layout() {
            sprite.y = y + 1 + (height - 1 - sprite.height) / 2f;
            PixelScene.align(sprite);

            line.size(width, 1);
            line.x = 0;
            line.y = y;

            label.maxWidth((int)(width - sprite.width - 1));
            label.setPos(sprite.x + sprite.width + 1,  y + 1 + (height - 1 - label.height()) / 2f);
            PixelScene.align(label);
        }

        public boolean onClick( float x, float y ) {
            if (inside( x, y )) {
                if (identified)
                    GameScene.show( new WndInfoItem( item ) );
                else
                    GameScene.show( new WndTitledMessage( new ItemSprite(ItemSpriteSheet.WEAPON, null),
                            Messages.titleCase(item.trueName()), item.desc() ));
                return true;
            } else {
                return false;
            }
        }
    }
}

