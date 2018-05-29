package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBrickPile extends TileEntity {

    protected final ItemStackHandler inventory = new ItemStackHandler();

    public TileEntityBrickPile() {
        inventory.setSize(1);
    }

    public int getBrickCount() {
        return inventory.getStackInSlot(0).isEmpty() ? 0 : inventory.getStackInSlot(0).getCount();
    }

    public BrickType getBrickType() {
        Item i = inventory.getStackInSlot(0).getItem();
        if(i.equals(Items.BRICK)) return BrickType.CLAY;
        if(i.equals(MythriaItems.STONE_BRICK)) return BrickType.STONE;
        return null;
    }

    public ItemStack getStack() {
        return inventory.getStackInSlot(0);
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

    private void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
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
            return (T) inventory;
        return super.getCapability(capability, facing);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public void removeBrick() {
        ItemStack brick = inventory.getStackInSlot(0);
        brick.shrink(1); // Shrink the brick stack by 1
        sendUpdates();
        if(brick.isEmpty()) world.setBlockState(pos, Blocks.AIR.getDefaultState()); // If brick stack is empty, delete brick pile
    }

    public boolean addBrick(Item i) {
        ItemStack brick = inventory.getStackInSlot(0);
        if(brick.getItem().equals(i)) { // If item match
            if(brick.getCount() < 4) { // If the stack has room
                brick.grow(1); // Increase stack by 1
                sendUpdates();
                return true; // Return true
            }
        }
        return false;
    }

    public void dropAllItems() {
        for(int i = 0; i < inventory.getStackInSlot(0).getCount(); i++) {
            ItemStack brick = inventory.getStackInSlot(0).copy();
            brick.setCount(1);
            InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, brick);
        }
    }

    public enum BrickType {
        STONE, CLAY
    }
}
