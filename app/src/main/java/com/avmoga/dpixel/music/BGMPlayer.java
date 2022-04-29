package com.avmoga.dpixel.music;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Dungeon;
import com.watabou.noosa.audio.Music;

public class BGMPlayer {
    public static void playBGMWithDepth() {
        int d = Dungeon.depth;
        if (d > 0 && d <= 5) {
            Music.INSTANCE.play(Assets.BGM_1, true);
        } else if (d > 5 && d <= 10) {
            Music.INSTANCE.play(Assets.BGM_2, true);
        } else if (d > 10 && d <= 15) {
            Music.INSTANCE.play(Assets.BGM_3, true);
        } else if (d > 15 && d <= 20) {
            Music.INSTANCE.play(Assets.BGM_4, true);
        } else if (d > 20 && d <= 25) {
            Music.INSTANCE.play(Assets.BGM_5, true);
        } else if (d == 27) {
            Music.INSTANCE.play(Assets.BGM_GO, true);
        } else if (d == 28) {
            Music.INSTANCE.play(Assets.BGM_GO, true);
        } else if (d == 29) {
            Music.INSTANCE.play(Assets.BGM_GO, true);
        } else if (d == 30) {
            Music.INSTANCE.play(Assets.BGM_GO, true);
        } else if (d == 36) {
            Music.INSTANCE.play(Assets.BGM_GO, true);
        } else if (d > 31 && d <= 35) {
            Music.INSTANCE.play(Assets.BGM_6,true);
        } else if (d > 36 && d <= 42) {
            Music.INSTANCE.play(Assets.BGM_7,true);
        } else
            //default
            Music.INSTANCE.play(Assets.TUNE, true);
    }

    public static void playBoss() {
        int t = Dungeon.depth;
        if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 5) {
            Music.INSTANCE.play(Assets.BGM_BOSSA, true);
        } else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 10) {
            Music.INSTANCE.play(Assets.BGM_BOSSB, true);
        } else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 15) {
            Music.INSTANCE.play(Assets.BGM_BOSSC, true);
        } else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 20) {
            Music.INSTANCE.play(Assets.BGM_BOSSD, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 25){
            Music.INSTANCE.play(Assets.BGM_BOSSE, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 35)   {
            Music.INSTANCE.play(Assets.BGM_BOSSE, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 36)   {
            Music.INSTANCE.play(Assets.BGM_BOSSC, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 37)   {
            Music.INSTANCE.play(Assets.BGM_BOSSC, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 38)   {
            Music.INSTANCE.play(Assets.BGM_BOSSB, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 39)   {
            Music.INSTANCE.play(Assets.BGM_BOSSD, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 40)   {
            Music.INSTANCE.play(Assets.BGM_BOSSA, true);
        }else if (Dungeon.bossLevel()||Dungeon.bossLevelFW() && t == 41) {
            Music.INSTANCE.play(Assets.BGM_BOSSE, true);
        }
    }
}
