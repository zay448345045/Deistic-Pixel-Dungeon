package com.avmoga.dpixel.scenes;

import com.avmoga.dpixel.Chrome;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.ui.Archs;
import com.avmoga.dpixel.ui.ExitButton;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.ScrollPane;
import com.avmoga.dpixel.ui.Window;
import com.avmoga.dpixel.ui.changelist.ChangeInfo;
import com.avmoga.dpixel.ui.changelist.v0_1_X_Changes;

import com.avmoga.dpixel.ui.changelist.v0_2_X_Changes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class ChangesScene extends PixelScene {

    public static int changesSelected = 0;

    @Override
    public void create() {
        super.create();

        int w = Camera.main.width;
        int h = Camera.main.height;

        RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
        title.hardlight(Window.TITLE_COLOR);
        title.x = (w - title.width()) / 2f;
        title.y = (20 - title.baseLine()) / 2f;
        align(title);
        add(title);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        NinePatch panel = Chrome.get(Chrome.Type.TOAST);

        int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
        int ph = h - 35;

        panel.size( pw, ph );
        panel.x = (w - pw) / 2f;
        panel.y = title.y + title.height();
        align( panel );
        add( panel );

        final ArrayList<ChangeInfo> changeInfos = new ArrayList<>();

        switch (changesSelected){
            case 0: default:
                v0_1_X_Changes.addAllChanges(changeInfos);
        }

        ScrollPane list = new ScrollPane( new Component() ){

            @Override
            public void onClick(float x, float y) {
                for (ChangeInfo info : changeInfos){
                    if (info.onClick( x, y )){
                        return;
                    }
                }
            }

        };
        add( list );

        Component content = list.content();
        content.clear();

        float posY = 0;
        float nextPosY = 0;
        boolean second = false;
        for (ChangeInfo info : changeInfos){
            if (info.major) {
                posY = nextPosY;
                second = false;
                info.setRect(0, posY, panel.innerWidth(), 0);
                content.add(info);
                posY = nextPosY = info.bottom();
            } else {
                if (!second){
                    second = true;
                    info.setRect(0, posY, panel.innerWidth()/2f, 0);
                    content.add(info);
                    nextPosY = info.bottom();
                } else {
                    second = false;
                    info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
                    content.add(info);
                    nextPosY = Math.max(info.bottom(), nextPosY);
                    posY = nextPosY;
                }
            }
        }

        content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

        list.setRect(
                panel.x + panel.marginLeft(),
                panel.y + panel.marginTop() - 1,
                panel.innerWidth(),
                panel.innerHeight() + 2);
        list.scrollTo(0, 0);

        RedButton btn0_7 = new RedButton("汉化 & 改动"){
            @Override
            protected void onClick() {
                super.onClick();
                if (changesSelected != 0) {
                    changesSelected = 0;
                    ShatteredPixelDungeon.seamlessResetScene();
                }
            }
        };
        if (changesSelected == 0) btn0_7.textColor(Window.TITLE_COLOR);
        btn0_7.setRect(list.left()-3, list.bottom()+5, 140, 14);
        add(btn0_7);

        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        fadeIn();
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(TitleScene.class);
    }
}
