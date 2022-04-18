package com.avmoga.dpixel.ui.changelist;

import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class ChangeButton extends Component {

    protected Image icon;
    protected String title;
    protected String message;

    public ChangeButton( Image icon, String title, String message){
        super();

        this.icon = icon;
        add(this.icon);

        this.title = Messages.titleCase(title);
        this.message = message;

        layout();
    }

    public ChangeButton(Item item, String message ){
        this( new ItemSprite(item), item.name(), message);
    }

    protected void onClick() {
        ShatteredPixelDungeon.scene().add(new ChangesWindow(new Image(icon), title, message));
    }

    @Override
    protected void layout() {
        super.layout();

        icon.x = x + (width - icon.width) / 2f;
        icon.y = y + (height - icon.height) / 2f;
        PixelScene.align(icon);
    }
}

