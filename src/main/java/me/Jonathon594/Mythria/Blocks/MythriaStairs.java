package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class MythriaStairs extends BlockStairs implements IBlockData {
    private int staminaCost;
    private double weight;

    public MythriaStairs(final IBlockState base, final String nameIn, double weight, int staminaCost) {
        super(base);

        setUnlocalizedName(nameIn);
        this.setRegistryName(getUnlocalizedName().substring(5));

        this.weight = weight;
        this.staminaCost = staminaCost;

        setHardness(0.5f);
        setResistance(0.5f);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getStaminaCostForBreaking() {
        return staminaCost;
    }
}
