package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.TileEntity.TileEntityPitKiln;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerPitkiln extends Container {

    private final IItemHandler inventory;

    public ContainerPitkiln(TileEntityPitKiln pitKiln, InventoryPlayer inventoryPlayer) {
        inventory = pitKiln.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new PitKilnSlot(inventory, 0, 71, 25));
        this.addSlotToContainer(new PitKilnSlot(inventory, 1, 89, 25));
        this.addSlotToContainer(new PitKilnSlot(inventory, 2, 71, 43));
        this.addSlotToContainer(new PitKilnSlot(inventory, 3, 89, 43));

        int row;
        int col;

        for (row = 0; row < 9; ++row) {
            this.addSlotToContainer(new Slot(inventoryPlayer, row, 8 + row * 18, 142));
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col)
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

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

            if (index > 3)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 11) {
                        if (!this.mergeItemStack(copy, 13, 40, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 4, 13, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,4, 30, false))
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
}

