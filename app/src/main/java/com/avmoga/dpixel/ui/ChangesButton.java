package com.avmoga.dpixel.ui;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.scenes.ChangesScene;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class ChangesButton extends Button {

    protected Image image;

    public ChangesButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        image = Icons.SHPX.get();
        add( image );
    }

    @Override
    protected void layout() {
        super.layout();

        image.x = x;
        image.y = y;
    }

    @Override
    protected void onTouchDown() {
        image.brightness( 1.5f );
        Sample.INSTANCE.play( Assets.SND_CLICK );
    }

    @Override
    protected void onTouchUp() {
        image.resetColor();
    }

    @Override
    protected void onClick() {
        //ChangesScene.changesSelected = 0;
        ShatteredPixelDungeon.switchNoFade(ChangesScene.class);
    }
}
