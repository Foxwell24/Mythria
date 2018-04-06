package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class MythriaFallingBlock extends BlockFalling implements IBlockData {
    private double weight;
    private int staminaCost;

    public MythriaFallingBlock(final Material materialIn, final String nameIn, SoundType soundType, double weight, int staminaCost) {
        this(materialIn, nameIn, soundType, weight, staminaCost, false);
    }

    public MythriaFallingBlock(final Material materialIn, final String nameIn, SoundType soundType, double weight, int staminaCost, boolean replaceVanilla) {
        super(materialIn);

        setUnlocalizedName(nameIn);
        if(replaceVanilla) this.setRegistryName(new ResourceLocation("minecraft:"+getUnlocalizedName().substring(5)));
        else this.setRegistryName(getUnlocalizedName().substring(5));
        this.blockSoundType = soundType;

        this.weight = weight;
        this.staminaCost = staminaCost;

        setResistance(0.5f);
        setHardness(0.5f);
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
