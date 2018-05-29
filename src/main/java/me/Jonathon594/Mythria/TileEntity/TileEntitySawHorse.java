package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Sounds.MythriaSounds;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.CarpentryManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntitySawHorse extends TileEntity {
    ItemStackHandler inventory = new ItemStackHandler();

    public TileEntitySawHorse() {
        inventory.setSize(3);
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

    public void buttonPressed(EntityPlayerMP player) {
        ItemStack input = inventory.getStackInSlot(0);
        ItemStack saw = inventory.getStackInSlot(1);
        if(saw == null || saw.isEmpty())
            return;
        CarpentryManager.SawResult result = CarpentryManager.getSawResult(input);
        if(result == null) return;
        inventory.getStackInSlot(0).shrink(1);
        ItemStack leftOver = inventory.insertItem(2, result.getOutput(), false);
        if(!leftOver.isEmpty()) {
            InventoryHelper.spawnItemStack(world, pos.getX() +0.5, pos.getY()+0.5, pos.getZ() + 0.5, leftOver);
        }
        saw.setItemDamage(saw.getItemDamage()+result.getOutput().getCount());
        if(saw.getItemDamage() >= saw.getMaxDamage()) inventory.setStackInSlot(1, ItemStack.EMPTY);
        player.getCapability(ProfileProvider.PROFILE_CAP, null).addSkillExperience(MythicSkills.CRAFTING, result.getOutput().getCount(), player);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), MythriaSounds.SAWHORSE_SAW, SoundCategory.BLOCKS, 1f, 1f);
    }
}
