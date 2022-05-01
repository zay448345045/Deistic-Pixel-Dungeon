package com.avmoga.dpixel.items.weapon.missiles;

import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ChestMimic extends MissileWeapon {

    {
        name = "食我箱子啦";
        image = ItemSpriteSheet.CHEST;
        rapperValue = 0;

        MIN = 10;
        MAX = 20;

        bones = false;
    }

    public ChestMimic() {
        this(1);
    }

    public ChestMimic(int number) {
        super();
        quantity = number;
    }

    @Override
    public String desc() {
        return "A wave of energy or something";
    }

    @Override
    public Item random() {
        quantity = Random.Int(10, 20);
        return this;
    }

    @Override
    public int price() {
        return quantity * 2;
    }
}

