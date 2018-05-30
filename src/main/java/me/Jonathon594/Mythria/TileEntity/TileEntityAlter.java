package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.AlterManager;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;

public class TileEntityAlter extends TileEntity implements ITickable {
    protected final ItemStackHandler inventory = new ItemStackHandler();
    private long acceptTime = 0;

    public TileEntityAlter() {
        inventory.setSize(9);
    }

    @Override
    public void update() {
        if(acceptTime == 0) return;
        long currentTime = System.currentTimeMillis();
        if(currentTime % 1000 < 50) {
            if (acceptTime > currentTime) {
                acceptTime = 0;
                int score = calculateScore();

                DeityManager.setDeityPower(getDeity(), (int) (DeityManager.getDeityPower(getDeity()) + ((Math.random() * score) / 100)));
                for(int i = 0; i < 9; i++) {
                    inventory.setStackInSlot(i, ItemStack.EMPTY);
                }
                if(score < 0) {
                    causeRandomNegativeEvent();
                } else {
                    considerConsolationPrize(score);
                }
                sendUpdates();
            }
        }
    }

    private void considerConsolationPrize(int score) {
        double chance = Math.min(score / 100000.0, 0.1);
        if(Math.random() < chance) {
            ItemStack is = AlterManager.getRandomDeityConsolidationPrize(getDeity());
            inventory.setStackInSlot(4, is);
        }
    }

    private void causeRandomNegativeEvent() {
        switch (getDeity()) {
            case FELIXIA:
                break;
            case SELINA:
                break;
            case RAIKA:
                break;
            case ELIANA:
                break;
            case MELINIAS:
                break;
            case KASAI:
                break;
            case ASANA:
                break;
            case LILASIA:
                break;
        }
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
    public void readFromNBT(final NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        acceptTime = compound.getLong("acceptTime");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setLong("acceptTime", acceptTime);
        return super.writeToNBT(compound);
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

    public boolean canOfferItems() {
        for(int i = 0; i < 9; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            if(is == null || is.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void offerItems() {
        long currentTime = System.currentTimeMillis();
        long acceptDelay = (long) (Math.random() * (60 * 60 * 1000));
        acceptTime = currentTime + acceptDelay;
    }

    public int calculateScore() {
        int score = 0;
        ArrayList<Item> used = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            score += AlterManager.getItemValueForDeity(getDeity(), is.getItem());
            if(!used.contains(is.getItem())) used.add(is.getItem());
        }
        int mult = used.size();
        int finalScore = score * mult;
        return finalScore;
    }

    private Deity getDeity() {
        if (getState().getBlock().equals(MythriaBlocks.ALTER_FELIXIA)) return Deity.FELIXIA;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_ELIANA)) return Deity.ELIANA;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_ASANA)) return Deity.ASANA;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_SELINA)) return Deity.SELINA;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_MELINIAS)) return Deity.MELINIAS;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_KASAI)) return Deity.KASAI;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_RAIKA)) return Deity.RAIKA;
        if (getState().getBlock().equals(MythriaBlocks.ALTER_LILASIA)) return Deity.LILASIA;
        return null;
    }
}
