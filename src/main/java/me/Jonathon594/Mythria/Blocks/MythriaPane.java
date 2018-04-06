package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MythriaPane extends BlockPane implements IBlockData {

    private final int flammability;
    private final int fireSpreadSpeed;
    private double weight;
    private int staminaCost;

    protected MythriaPane(final Material materialIn, final boolean canDrop, final String nameIn, SoundType soundType, double weight, int staminaCost, int flammability, int fireSpreadSpeed) {
        super(materialIn, canDrop);
        setUnlocalizedName(nameIn);
        this.setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.5f);
        setResistance(0.5f);
        this.blockSoundType = soundType;

        this.weight = weight;
        this.staminaCost = staminaCost;

        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return fireSpreadSpeed;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return flammability;
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
