package com.avmoga.dpixel.windows;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.ui.RedButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.Window;
import com.watabou.gltextures.TextureCache;
import com.watabou.utils.FileOperations;

import java.io.IOException;

public class WndRecoverSave extends Window {

    private static final String TXT_MESSAGE = Messages.get(WndRecoverSave.class, "msg");

    private static final String TXT_YES = Messages.get(WndRecoverSave.class, "yes");
    private static final String TXT_NO = Messages.get(WndRecoverSave.class, "no");

    private static final int WIDTH = 120;
    private static final int BTN_HEIGHT = 20;
    private static final float GAP = 2;

    public WndRecoverSave() {

        super();

        RenderedTextMultiline message = PixelScene
                .renderMultiline(TXT_MESSAGE, 6);
        message.maxWidth(WIDTH);
        message.setPos(0, GAP);
        add(message);

        RedButton btnYes = new RedButton(TXT_YES) {
            @Override
            protected void onClick() {
                FileOperations.delete(TextureCache.context.getFilesDir());
                try {
                    FileOperations.copyDirectiory(TextureCache.context.getExternalFilesDir(null).getAbsolutePath(), TextureCache.context.getFilesDir().getAbsolutePath());
                    this.parent.add(new WndMessage(Messages.get(WndRecoverSave.class, "success", new Object[0])));
                } catch (IOException e) {
                    this.parent.add(new WndMessage(Messages.get(WndRecoverSave.class, "fail", new Object[0])));
                }
            }
        };
        btnYes.setRect(0, message.top() + message.height() + GAP, WIDTH,
                BTN_HEIGHT);
        add(btnYes);

        RedButton btnNo = new RedButton(TXT_NO) {
            @Override
            protected void onClick() {
                hide();
            }
        };

        btnNo.setRect(0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnNo);


        resize(WIDTH, (int) btnNo.bottom());
    }

}

