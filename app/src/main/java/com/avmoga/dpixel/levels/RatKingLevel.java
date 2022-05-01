package com.avmoga.dpixel.levels;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Bones;
import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.mobs.BrownBat;
import com.avmoga.dpixel.actors.mobs.GreyRat;
import com.avmoga.dpixel.actors.mobs.HermitCrab;
import com.avmoga.dpixel.actors.mobs.Mob;
import com.avmoga.dpixel.actors.mobs.RatBoss;
import com.avmoga.dpixel.actors.mobs.RatKingBoss;
import com.avmoga.dpixel.actors.mobs.Shell;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.items.Item;
import com.avmoga.dpixel.items.keys.SkeletonKey;
import com.avmoga.dpixel.levels.painters.Painter;
import com.avmoga.dpixel.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RatKingLevel extends Level {

    {
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 8;
    }

    private static final int TOP = 2;
    private static final int HALL_WIDTH = 23;
    private static final int HALL_HEIGHT = 25;
    private static final int CHAMBER_HEIGHT = 3;

    private static final int LEFT = (getWidth() - HALL_WIDTH) / 2;
    private static final int CENTER = LEFT + HALL_WIDTH / 2;

    private int arenaDoor;
    private boolean enteredArena = false;
    private boolean keyDropped = false;

    @Override
    public String tilesTex() {
        return Assets.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    private static final String DOOR = "door";
    private static final String ENTERED = "entered";
    private static final String DROPPED = "droppped";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DOOR, arenaDoor);
        bundle.put(ENTERED, enteredArena);
        bundle.put(DROPPED, keyDropped);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        arenaDoor = bundle.getInt(DOOR);
        enteredArena = bundle.getBoolean(ENTERED);
        keyDropped = bundle.getBoolean(DROPPED);
    }

    @Override
    protected boolean build() {

        Painter.fill(this, LEFT, TOP, HALL_WIDTH, HALL_HEIGHT, Terrain.WATER);
        Painter.fill(this, CENTER, TOP, 1, HALL_HEIGHT, Terrain.WATER);

        int y = TOP + 2;
        while (y < TOP + HALL_HEIGHT) {
            map[y * WIDTH + CENTER - 4] = Terrain.PEDESTAL;
            map[y * WIDTH + CENTER - 2] = Terrain.PEDESTAL;
            map[y * WIDTH + CENTER + 2] = Terrain.PEDESTAL;
            map[y * WIDTH + CENTER + 4] = Terrain.PEDESTAL;
            y += 2;
        }

        exit = (TOP - 1) * WIDTH + CENTER;
        map[exit] = Terrain.LOCKED_EXIT;

        arenaDoor = (TOP + HALL_HEIGHT) * WIDTH + CENTER;
        map[arenaDoor] = Terrain.DOOR;

        Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, HALL_WIDTH,
                CHAMBER_HEIGHT, Terrain.EMPTY);
        Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT,
                Terrain.WATER);
        Painter.fill(this, LEFT + HALL_WIDTH - 1, TOP + HALL_HEIGHT + 1, 1,
                CHAMBER_HEIGHT, Terrain.WATER);

        entrance = (TOP + HALL_HEIGHT + 2 + Random.Int(CHAMBER_HEIGHT - 1))
                * WIDTH + LEFT + (/* 1 + */Random.Int(HALL_WIDTH - 2));
        map[entrance] = Terrain.PEDESTAL;

        map[exit] = Terrain.WALL;

        return true;
    }

    @Override
    protected void decorate() {

        for (int i = 0; i < getLength(); i++) {
            if (map[i] == Terrain.EMPTY && Random.Int(10) == 0) {
                map[i] = Terrain.EMPTY_DECO;
            } else if (map[i] == Terrain.WALL && Random.Int(8) == 0) {
                map[i] = Terrain.WALL_DECO;
            }
        }


        int potionpos = arenaDoor + 2*getWidth();

        for (int i = 0; i < getLength(); i++) {
            if (map[i] == Terrain.WALL && Random.Int(8) == 0) {
                map[i] = Terrain.WALL_DECO;
            }
            if (map[i]==Terrain.ENTRANCE){map[i] = Terrain.EMPTY;}
            if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.20){map[i] = Terrain.HIGH_GRASS;}
            if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.25){map[i] = Terrain.GRASS;}
            if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.30){map[i] = Terrain.SHRUB;}
        }

        //int sign = arenaDoor + WIDTH + 1;
        //map[sign] = Terrain.SIGN;
    }

    public static int pedestal(boolean left) {
        if (left) {
            return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER - 2;
        } else {
            return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER + 2;
        }
    }

    @Override
    protected void createMobs() {
    }

    @Override
    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            int pos;
            do {
                pos = Random.IntRange(LEFT + 1, LEFT + HALL_WIDTH - 2)
                        + Random.IntRange(TOP + HALL_HEIGHT + 1, TOP
                        + HALL_HEIGHT + CHAMBER_HEIGHT) * getWidth();
            } while (pos == entrance || map[pos] == Terrain.SIGN);
            drop(item, pos).type = Heap.Type.REMAINS;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    @Override
    public void press(int cell, Char hero) {

        super.press(cell, hero);

        if (!enteredArena && outsideEntraceRoom(cell) && hero == Dungeon.hero) {

            enteredArena = true;
            //locked = true;

            Mob boss = new RatKingBoss();
            Mob shella = new Shell();
            Mob shellb = new BrownBat();
            Mob shellc = new BrownBat();
            Mob shelld = new BrownBat();
            Mob crab1 = new RatBoss();
            Mob crab2 = new GreyRat();
            Mob crab3 = new HermitCrab();
            Mob crab4 = new HermitCrab();
            boss.state = boss.HUNTING;
            crab1.state = crab1.HUNTING;
            crab2.state = crab2.HUNTING;
            crab3.state = crab3.HUNTING;
            crab4.state = crab4.HUNTING;
            shella.state = shella.HUNTING;
            shellb.state = shellb.HUNTING;
            shellc.state = shellc.HUNTING;
            shelld.state = shelld.HUNTING;
            int count = 0;
            do {
                boss.pos = Random.Int(LENGTH);
                shella.pos = (TOP + 2) * WIDTH + CENTER+1;
                shellb.pos = (TOP + 4) * WIDTH + CENTER+1;
                shellc.pos = (TOP + 6) * WIDTH + CENTER+1;
                shelld.pos = (TOP + 8) * WIDTH + CENTER+1;
                crab1.pos = (TOP + 1) * WIDTH + CENTER+1;
                crab2.pos = (TOP + 1) * WIDTH + CENTER-1;
                crab3.pos = (TOP + 2) * WIDTH + CENTER;
                crab4.pos = (TOP + 0) * WIDTH + CENTER;
            } while (!passable[boss.pos]
                    || !outsideEntraceRoom(boss.pos)
                    || (Dungeon.visible[boss.pos] && count++ < 20));

            GameScene.add(boss);

            GameScene.add(shella);
            GameScene.add(shellb);
            GameScene.add(shellc);
            GameScene.add(shelld);

            GameScene.add(crab1);
            GameScene.add(crab2);
            GameScene.add(crab3);
            GameScene.add(crab4);

            if (Dungeon.visible[boss.pos]) {
                boss.notice();
                boss.sprite.alpha(0);
                boss.sprite.parent.add(new AlphaTweener(boss.sprite, 1, 0.1f));
            }

            //set(arenaDoor, Terrain.WALL);
            //GameScene.updateMap(arenaDoor);
            Dungeon.observe();
        }
    }

    @Override
    public Heap drop(Item item, int cell) {

        if (!keyDropped && item instanceof SkeletonKey) {

            keyDropped = true;
            locked = false;

            set(arenaDoor, Terrain.DOOR);
            GameScene.updateMap(arenaDoor);
            Dungeon.observe();
        }

        return super.drop(item, cell);
    }

    private boolean outsideEntraceRoom(int cell) {
        return cell / getWidth() < arenaDoor / getWidth();
    }

    public String tileName(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(SewerLevel.class, "water_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(SewerLevel.class, "high_grass_name");
            default:
                return super.tileName(tile);
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(SewerLevel.class, "empty_deco_desc");
            case Terrain.HIGH_GRASS:
                return Messages.get(SewerLevel.class, "high_grass_desc");
            case Terrain.PEDESTAL:
                return "神秘的基座蕴含着力量，敌人在上面可以获得特殊加成！";
            default:
                return super.tileDesc(tile);
        }
    }

    @Override
    public void addVisuals(Scene scene) {
        CityLevel.addVisuals(this, scene);
    }
}

