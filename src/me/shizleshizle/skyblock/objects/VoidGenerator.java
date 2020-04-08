package me.shizleshizle.skyblock.objects;

import me.shizleshizle.core.objects.User;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator {

    public static World generateWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.generator(new ChunkGenerator() {
            public ChunkData generateChunkData(World w, Random r, int x, int z) {
                return createChunkData(w);
            }
        });
        return wc.createWorld();
    }
}
