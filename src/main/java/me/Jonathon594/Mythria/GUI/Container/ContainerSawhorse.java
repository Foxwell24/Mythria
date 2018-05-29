package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.TileEntity.TileEntitySawHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerSawhorse extends Container {

    private final IItemHandler inventory;
    private final TileEntitySawHorse sawHorse;

    public ContainerSawhorse(TileEntitySawHorse sawHorse, InventoryPlayer inventoryPlayer) {
        inventory = sawHorse.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.sawHorse = sawHorse;
        this.addSlotToContainer(new SawhorseWoodSlot(inventory, 0, 87, 12));
        this.addSlotToContainer(new SawhotseSawSlot(inventory, 1, 105, 12));
        this.addSlotToContainer(new OutputSlot(inventory, 2, 96, 34));

        int row;
        int col;

        for (row = 0; row < 9; ++row) {
            this.addSlotToContainer(new Slot(inventoryPlayer, row, 24 + row * 18, 140));
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col)
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, 24 + col * 18, 82 + row * 18));

        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack copy = slot.getStack();
            original = copy.copy();

            if (index > 2)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 16) {
                        if (!this.mergeItemStack(copy, 12, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 3, 12, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (copy.getCount() == 0)
            {
                slot.putStack((ItemStack)ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (copy.getCount() == original.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, copy);
        }
        return original;
    }

    public TileEntitySawHorse getSawHorse() {
        return sawHorse;
    }
}

