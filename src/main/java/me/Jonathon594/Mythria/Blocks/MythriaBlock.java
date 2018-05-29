package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MythriaBlock extends Block implements IBlockData {

    private double weight;
    private int staminaCost;
    private int flammability;
    private int spreadSpeed;

    public MythriaBlock(final Material materialIn, final String nameIn, SoundType soundType, double weight, int staminaCost, int flammability, int spreadSpeed) {
        super(materialIn);

        setUnlocalizedName(nameIn);
        this.setRegistryName(getUnlocalizedName().substring(5));
        this.blockSoundType = soundType;

        this.weight = weight;
        this.staminaCost = staminaCost;

        setResistance(0.5f);
        setHardness(0.5f);

        this.flammability = flammability;
        this.spreadSpeed = spreadSpeed;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getStaminaCostForBreaking() {
        return staminaCost;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return spreadSpeed;
    }
}
