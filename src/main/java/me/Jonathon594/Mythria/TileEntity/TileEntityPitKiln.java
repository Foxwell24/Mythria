package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.BlockPitKiln;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Interface.ILightable;
import me.Jonathon594.Mythria.Module.KilnModule;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityPitKiln extends TileEntity implements ITickable, ILightable {

    public TileEntityPitKiln() {
        vessels.setSize(4);
    }

    private int fuel = 0;
    private boolean burning;
    private double friction;

    private ItemStackHandler vessels = new ItemStackHandler();

    private int ticksBurning = 0;
    private final int burnTime = 600;

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
        sendUpdates();
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
        world.setBlockState(pos, MythriaBlocks.PIT_KILN.getDefaultState().withProperty(BlockPitKiln.BURNING, burning));
        sendUpdates();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        vessels.deserializeNBT(compound.getCompoundTag("inventory"));
        ticksBurning = compound.getInteger("ticks_burning");
        burning = compound.getBoolean("burning");
        fuel = compound.getInteger("fuel");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", vessels.serializeNBT());
        compound.setInteger("ticks_burning", ticksBurning);
        compound.setBoolean("burning", burning);
        compound.setInteger("fuel", fuel);
        return super.writeToNBT(compound);
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

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState,
                                 final IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return (T) vessels;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (friction > 1)
            friction -= 1;

        if(burning) {
            ticksBurning++;

            if(world.isRemote) return;
            if(ticksBurning > burnTime) {
                burning = false;
                //Drop Items
                for (int i = 0; i < vessels.getSlots(); i++) {
                    final ItemStack is = KilnModule.fire(vessels.getStackInSlot(i).copy());
                    vessels.setStackInSlot(i, ItemStack.EMPTY);
                    EntityItem entityitem = new EntityItem(world, pos.getX()+ 0.5, pos.getY(), pos.getZ()+0.5,
                            is);
                    float f3 = 0.05F;
                    Random rand = new Random();
                    entityitem.motionX = rand.nextGaussian() * 0.05000000074505806D;
                    entityitem.motionY = rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
                    entityitem.motionZ = rand.nextGaussian() * 0.05000000074505806D;
                    world.spawnEntity(entityitem);
                    for(Entity e : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-5,-5,-5), pos.add(5,5,5)))) {
                        if(e instanceof EntityPlayer) {
                            IProfile profile = e.getCapability(ProfileProvider.PROFILE_CAP, null);
                            if(profile.hasFlag(AttributeFlag.SMELTING))profile.addSkillExperience(MythicSkills.SMITHING, 30, (EntityPlayerMP) e);
                        }
                    }
                }
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public ItemStackHandler getVessels() {
        return vessels;
    }

    @Override
    public void light() {
        setBurning(true);
    }

    @Override
    public void addFriction() {
        this.friction += Math.random() * 20;
        if(this.friction > 100) light();
    }

    @Override
    public boolean hasFuel() {
        return fuel == 7;
    }
}
