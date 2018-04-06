package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.block.Block;

public class OreSpawnData {
    private int type, size, rarity, min = 5, max = 128, vDensity, hDensity;
    private Block block;

    public int getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getRarity() {
        return rarity;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getvDensity() {
        return vDensity;
    }

    public int gethDensity() {
        return hDensity;
    }

    public Block getBlock() {
        return block;
    }
}
