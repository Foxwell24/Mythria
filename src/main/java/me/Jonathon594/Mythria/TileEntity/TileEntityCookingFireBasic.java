package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Interface.ILightable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileEntityCookingFireBasic extends TileEntity implements ITickable,ILightable {

    protected final ItemStackHandler cookItems = new ItemStackHandler();
    protected int tick = 0;

    protected boolean lit;

    public int getTicksLeft() {
        return ticksLeft;
    }

    protected int ticksLeft = 6000;

    public int getMaxTicks() {
        return maxTicks;
    }

    protected int maxTicks = 6000;
    protected double friction;

    public TileEntityCookingFireBasic(final int size, final double cookSpeed) {
        super();
        cookItems.setSize(size);
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound comp = new NBTTagCompound();
        return this.writeToNBT(comp);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
    }

    protected void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public ItemStackHandler getCookItems() {
        return cookItems;
    }

    public boolean isLit() {
        return lit;
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        cookItems.deserializeNBT(compound.getCompoundTag("inventory"));
        ticksLeft = compound.getInteger("ticksleft");
        lit = compound.getBoolean("burning");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", cookItems.serializeNBT());
        compound.setInteger("ticksleft", ticksLeft);
        compound.setBoolean("burning", lit);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState,
                                 final IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return (T) cookItems;
        return super.getCapability(capability, facing);
    }
}
