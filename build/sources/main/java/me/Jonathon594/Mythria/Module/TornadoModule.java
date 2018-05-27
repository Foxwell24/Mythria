package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Entity.Weather.EntityTornado;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;

public class TornadoModule {
    private static HashMap<Integer, ArrayList<BlockPos>> tornadoBlockShapes;
    private static HashMap<Integer, ArrayList<Vec3d>> tornadoVerticies;

    public static ArrayList<BlockPos> getBlockShape(int tier) {
        if(tornadoBlockShapes.containsKey(tier)) return tornadoBlockShapes.get(tier);
        return null;
    }

    public static boolean isEdible(Block b, int tier) {
        double tornadoPower = 0;
        switch (tier) {
            case 0: tornadoPower = 2;
            break;
            case 1: tornadoPower = 9;
            break;
            case 2: tornadoPower = 15;
            break;
            case 3: tornadoPower = 30;
            break;
            case 4: tornadoPower = 45;
            break;
        }
        if(isBlockImmune(b)) return false;
        if(b.getExplosionResistance(null) * 5 <= tornadoPower) return true;
        return false;
    }

    private static boolean isBlockImmune(Block b) {
        if(b.equals(Blocks.SAND) || b.equals(Blocks.STONE) || b.equals(Blocks.DIRT) || b.equals(Blocks.AIR)) return true;
        return false;
    }

    public static void createBlockShapes() {
        tornadoBlockShapes = new HashMap<>();
        createVertexShapes();

        for(int i = 0; i < 5; i++) {
            ArrayList<BlockPos> blockShape = new ArrayList<>();
            ArrayList<Vec3d> points = getTornadoVerticies(i);
            for (Vec3d v : points) {
                Vec3d adjusted = new Vec3d(Math.round(v.x), Math.round(v.y), Math.round(v.z));
                BlockPos pos = new BlockPos(adjusted);
                if (!blockShape.contains(pos)) blockShape.add(pos);
            }
            tornadoBlockShapes.put(i, blockShape);
        }
    }

    public static ArrayList<Vec3d> getTornadoVerticies(int i) {
        if(tornadoVerticies.containsKey(i)) return tornadoVerticies.get(i);
        return null;
    }

    public static void createVertexShapes() {
        tornadoVerticies = new HashMap<>();

        for(int i = 0; i < 5; i++) {
            double height = EntityTornado.getHeight(i);
            double width = EntityTornado.getWidth(i);
            double minWidth = 0.04 + (i * 0.02);
            double segments = 12 + (i * 2);

            ArrayList<Vec3d> points = new ArrayList<>();
            for (double y = 0.0; y < height; y += 0.2) {
                for (double r = 0; r < Math.PI * 2; r += Math.PI / (segments + y/10)) {
                    double x1 = Math.cos(r + (y / height) * Math.PI);
                    double z1 = Math.sin(r + (y / height) * Math.PI);
                    double scale = (Math.pow(y / height, 2) + minWidth) * width;

                    points.add(new Vec3d(x1 * scale, y, z1 * scale));
                }
            }

            tornadoVerticies.put(i, points);
        }
    }

    public static Block getReplaceBlock(Block b) {
        if(b.equals(Blocks.GRASS)) return Blocks.DIRT;
        return null;
    }
}
