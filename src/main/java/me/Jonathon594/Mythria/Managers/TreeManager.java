package me.Jonathon594.Mythria.Managers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TreeManager {
    public static void dropSticks(BlockPos pos, World world, Random rand) {
        for(int i = -3; i < 4; i++) {
            for (int x = -3; x < 4; x++) {
                if(rand.nextDouble() < 0.2) new WorldGenGroundStick().generate(world, rand, pos.add(i, 0 ,x));
            }
        }
    }
}
