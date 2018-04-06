package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.BlockMythriaFurnace;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Interface.ILightable;
import me.Jonathon594.Mythria.Interface.ISmelter;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

public class TileEntityMythriaFurnace extends TileEntity implements ITickable,ILightable,ISmelter {

    protected final ItemStackHandler inventory = new ItemStackHandler();
    protected int tick = 0;
    private int maxTicks = 1;
    private double temperature;

    public void setBurning(boolean burning) {
        this.burning = burning;
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockMythriaFurnace.BURNING, burning));
        sendUpdates();
    }

    protected boolean burning;
    protected int ticksLeft = 6000;
    protected double friction;

    public TileEntityMythriaFurnace() {
        super();
        inventory.setSize(2);
        this.burning = burning;
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

    public int getTicksLeft() {
        return ticksLeft;
    }

    public boolean isBurning() {
        return burning;
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        ticksLeft = compound.getInteger("ticksleft");
        maxTicks = compound.getInteger("maxTicks");
        burning = compound.getBoolean("burning");
        temperature = compound.getDouble("temperature");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("ticksleft", ticksLeft);
        compound.setInteger("maxTicks", maxTicks);
        compound.setBoolean("burning", burning);
        compound.setDouble("temperature", temperature);
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

    @Override
    public void update() {
        double ambientTemp = world.getBiome(pos).getTemperature(pos);
        //If Lit
        if (burning) {
            temperature += 0.5;
            if(temperature > getMaxTemperature()) temperature = getMaxTemperature();
            //Get the stack in fuel slot
            final ItemStack fuel = inventory.getStackInSlot(0);
            ticksLeft -= 1;
            //If ticks left <= 0
            if (ticksLeft <= 0)
                //If there is no fuel left
                if (fuel == null || fuel.getCount() == 0) {
                    //Set burning false
                    setBurning(false);
                } else {
                    int burnTime = getBurnTime(fuel.getItem());
                    //Else, if we are the server
                    if (!world.isRemote)
                        //Shrink the fuel stack by one
                        fuel.shrink(1);
                    //Set the new burn time on both client/server
                    ticksLeft = burnTime;
                }
            //Increment the tick
            tick++;
            //Once every second
            if (tick == 20) {
                //Set tick to 0
                tick = 0;
                //If we are the server;
                if (!world.isRemote) {
                    //Cook
                    ItemStack is = inventory.getStackInSlot(1);
                    SmeltingManager.smeltVessal(is, this, ambientTemp);
                    for(Entity e : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-5,-5,-5), pos.add(5,5,5)))) {
                        if(e instanceof EntityPlayer) {
                            IProfile profile = e.getCapability(ProfileProvider.PROFILE_CAP, null);
                            if(profile.hasFlag(AttributeFlag.SMELTING)) profile.addSkillExperience(MythicSkills.SMITHING, 1, (EntityPlayerMP) e);
                        }
                    }
                }
            }
        } else {
            if (friction > 1)
                friction -= 1;

            if(temperature > ambientTemp) temperature -= 0.1;
        }

        if(temperature < ambientTemp) temperature = ambientTemp;
    }

    @Override
    public double getMaxTemperature() {
        return 1250;
    }

    private int getBurnTime(Item item) {
        if(item.equals(Items.COAL)) return 1600;
        if(item.equals(ItemBlock.getItemFromBlock(Blocks.COAL_BLOCK))) return 16000;
        return 0;
    }

    @Override
    public void light() {
        final ItemStack fuel = inventory.getStackInSlot(0);
        int burnTime = getBurnTime(fuel.getItem());
        setMaxTicks(burnTime);
        setTicksLeft(burnTime);
        fuel.shrink(1);
        setBurning(true);
    }

    @Override
    public void addFriction() {
        this.friction+= Math.random() * 20;
        if(this.friction > 100) light();
    }

    @Override
    public boolean hasFuel() {
        final ItemStack fuel = inventory.getStackInSlot(0);
        if(fuel != null && fuel.getCount() > 0) return true;
        return false;
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    public void setMaxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
        sendUpdates();
    }

    public void setTicksLeft(int burnTime) {
        this.ticksLeft = burnTime;
        sendUpdates();
    }

    @Override
    public double getTemp() {
        return temperature;
    }
}
