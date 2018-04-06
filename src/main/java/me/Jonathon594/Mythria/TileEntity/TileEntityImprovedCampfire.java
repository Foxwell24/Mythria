package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.BlockImprovedCampfire;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import net.minecraft.util.ITickable;

public class TileEntityImprovedCampfire extends TileEntityCookingFireAdvanced implements ITickable {

    public TileEntityImprovedCampfire() {
        super(6, 6.0);
    }

    @Override
    public void setBlockState(boolean lit) {
        world.setBlockState(pos, MythriaBlocks.IMPROVED_CAMPFIRE.getDefaultState().withProperty(BlockImprovedCampfire.BURNING, lit));
    }

    @Override
    public void light() {
        setLit(true);
    }

    @Override
    public void addFriction() {
        this.friction+= Math.random()*20;
        if(friction > 100) light();
    }

    @Override
    public boolean hasFuel() {
        return !cookItems.getStackInSlot(0).isEmpty() || ticksLeft > 0;
    }
}
