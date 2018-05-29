package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.TileEntity.TileEntityCookingFireAdvanced;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CampfireContainerImproved extends Container {

    private final IItemHandler inventory;

    public CampfireContainerImproved(final TileEntityCookingFireAdvanced campfire, final IInventory playerInventory) {
        super();
        inventory = campfire.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new CookingFuellotItemHandler(inventory, 0, 79, 55));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 1, 35, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 2, 57, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 3, 79, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 4, 101, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 5, 123, 17));

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; ++k)
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
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

            if (index > 5)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.inventorySlots.get(1).isItemValid(copy) ) {
                    if (!this.mergeItemStack(copy, 1, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                }else {
                    if(index < 15) {
                        if (!this.mergeItemStack(copy, 15, 34, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 6, 14, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,6, 34, false))
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

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return true;
    }
}
