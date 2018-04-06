package me.Jonathon594.Mythria.TileEntity;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityPrimativeCrate extends TileEntityLockableLoot implements ITickable {
    public int numPlayersUsing;
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
    /**
     * Server sync counter (once per 20 ticks)
     */
    private int ticksSinceSync;

    public TileEntityPrimativeCrate() {
    }

    public static void registerFixesChest(final DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY,
                new ItemStackDataLists(TileEntityPrimativeCrate.class, "Items"));
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        fillWithLoot(playerIn);
        return new ContainerChest(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return "mythria:primative_crate";
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName() {
        return hasCustomName() ? customName : "container.primative_crate";
    }

    public net.minecraftforge.items.IItemHandler getSingleChestHandler() {
        return super.getCapability(net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void invalidate() {
        super.invalidate();
        updateContainingBlockInfo();
    }

    @Override
    public boolean receiveClientEvent(final int id, final int type) {
        if (id == 1) {
            numPlayersUsing = type;
            return true;
        } else
            return super.receiveClientEvent(id, type);
    }

    @Override
    public void openInventory(final EntityPlayer player) {
        if (!player.isSpectator()) {
            if (numPlayersUsing < 0)
                numPlayersUsing = 0;

            ++numPlayersUsing;
            world.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);
            world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
        }
    }

    @Override
    public void closeInventory(final EntityPlayer player) {
        if (!player.isSpectator() && getBlockType() instanceof BlockChest) {
            --numPlayersUsing;
            world.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);
            world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return chestContents;
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        chestContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

        if (!checkLootAndRead(compound))
            ItemStackHelper.loadAllItems(compound, chestContents);

        if (compound.hasKey("CustomName", 8))
            customName = compound.getString("CustomName");
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return 18;
    }

    @Override
    public boolean isEmpty() {
        for (final ItemStack itemstack : chestContents)
            if (!itemstack.isEmpty())
                return false;

        return true;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64,
     * possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (!checkLootAndWrite(compound))
            ItemStackHelper.saveAllItems(compound, chestContents);

        if (hasCustomName())
            compound.setString("CustomName", customName);

        return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        final int i = pos.getX();
        final int j = pos.getY();
        final int k = pos.getZ();
        ++ticksSinceSync;

        if (!world.isRemote && numPlayersUsing != 0 && (ticksSinceSync + i + j + k) % 200 == 0) {
            numPlayersUsing = 0;
            for (final EntityPlayer entityplayer : world.getEntitiesWithinAABB(EntityPlayer.class,
                    new AxisAlignedBB(i - 5.0F, j - 5.0F, k - 5.0F, i + 1 + 5.0F, j + 1 + 5.0F, k + 1 + 5.0F)))
                if (entityplayer.openContainer instanceof ContainerChest) {
                    final IInventory iinventory = ((ContainerChest) entityplayer.openContainer)
                            .getLowerChestInventory();

                    if (iinventory == this)
                        ++numPlayersUsing;
                }
        }
    }
}
