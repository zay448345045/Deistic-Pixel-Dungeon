package com.avmoga.dpixel.levels.features;

/**
 * Created by Matthew on 10/17/2016.
 */

import java.util.Iterator;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.actors.hero.Hero;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.scenes.GameScene;
import com.avmoga.dpixel.windows.WndBag;
import com.avmoga.dpixel.windows.WndOptions;

public class Altar {
    private static final String TXT_FINDSACRIFICE = "Select an item to sacrifice.";
    private static final String TXT_OPTIONS = "Will you commune with a new Diety, or make an offering to a Diety?";
    private static final String TXT_COMMUNE = "Commune";
    private static final String TXT_SACRIFICE = "Sacrifice";
    private static final String TXT_NAME = "Altar";
    public static Hero hero;
    public static int pos;
    public static Item curItem = null;

    public static void operate(Hero hero, int pos) {

        Altar.hero = hero;
        Altar.pos = pos;

        Iterator<Item> items = hero.belongings.iterator();
        Heap heap = Dungeon.level.heaps.get(pos);

        if(heap == null){
            GameScene.show(new WndOptions(TXT_NAME, TXT_OPTIONS,
                    TXT_COMMUNE, TXT_SACRIFICE) {

                @Override
                protected void onSelect(int index) {
                    if (index == 0) {
                        //TODO Select a new God from those not yet discovered.
                    } else
                        GameScene.selectItem(itemSelector,
                                WndBag.Mode.ALL, TXT_FINDSACRIFICE);
                }
            });
        }

    }
    private static final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {
            if (item != null) {
                item.cast(hero, pos);
            }
        }
    };

}
