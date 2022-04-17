package com.watabou.noosa.ui;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.windows.WndRecoverSave;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class ResetButton extends Button {

    protected Image image;

    public ResetButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        image = Icons.WARNING.get();
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

    public void hide() {
        if (parent != null) {
            parent.erase(this);
        }
        destroy();
    }

    @Override
    protected void onTouchUp() {
        image.resetColor();
    }

    @Override
    protected void onClick() {
        Game.scene().add(new WndRecoverSave());
    }
}

