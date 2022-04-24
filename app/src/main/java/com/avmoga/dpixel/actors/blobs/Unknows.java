package com.avmoga.dpixel.actors.blobs;

import com.avmoga.dpixel.Dungeon;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.actors.Actor;
import com.avmoga.dpixel.actors.Char;
import com.avmoga.dpixel.actors.buffs.Buff;
import com.avmoga.dpixel.actors.buffs.Unkonwn;
import com.avmoga.dpixel.effects.BlobEmitter;
import com.avmoga.dpixel.effects.particles.SmokeParticle;
import com.avmoga.dpixel.items.Heap;
import com.avmoga.dpixel.levels.Level;
import com.avmoga.dpixel.levels.Terrain;
import com.avmoga.dpixel.scenes.GameScene;
import com.watabou.utils.Random;

public class Unknows extends Blob {

    @Override
    protected void evolve() {

        boolean[] flamable = Level.flamable;

        int from = WIDTH + 1;
        int to = Level.getLength() - WIDTH - 1;

        boolean observe = false;

        for (int pos = from; pos < to; pos++) {

            int fire;

            if (cur[pos] > 0) {

                burn(pos);

                fire = cur[pos] - 1;
                if (fire <= 0 && flamable[pos]) {

                    int oldTile = Dungeon.level.map[pos];
                    Level.set(pos, Terrain.EMBERS);

                    observe = true;
                    GameScene.updateMap(pos);
                    if (Dungeon.visible[pos]) {
                        GameScene.discoverTile(pos, oldTile);
                    }
                }

            } else {

                if (flamable[pos]
                        && (cur[pos - 1] > 0 || cur[pos + 1] > 0
                        || cur[pos - WIDTH] > 0 || cur[pos + WIDTH] > 0)) {
                    fire = 4;
                    burn(pos);
                } else {
                    fire = 0;
                }

            }

            volume += (off[pos] = fire);

        }

        if (observe) {
            Dungeon.observe();
        }
    }

    private void burn(int pos) {
        Char ch = Actor.findChar(pos);
        if (ch != null) {
            Buff.affect(ch, Unkonwn.class).set(2);
            ch.damage(Random.Int(ch.HT / 10, ch.HT), this);
        }

        Heap heap = Dungeon.level.heaps.get(pos);
        if (heap != null) {
            heap.burn();
        }
    }

    @Override
    public void seed(int cell, int amount) {
        if (cur[cell] == 0) {
            volume += amount;
            cur[cell] = amount;
        }
    }

    @Override
    public void use(BlobEmitter emitter) {
        super.use(emitter);
        emitter.start(SmokeParticle.FACTORY, 0.03f, 0);
    }

    @Override
    public String tileDesc() {
        return Messages.get(this, "desc");
    }
}

