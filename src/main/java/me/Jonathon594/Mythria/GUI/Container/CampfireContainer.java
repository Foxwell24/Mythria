package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.TileEntity.TileEntityCookingFireBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CampfireContainer extends Container {

    private final IItemHandler inventory;

    public CampfireContainer(final TileEntityCookingFireBasic campfire, final IInventory playerInventory) {
        super();
        inventory = campfire.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new CookingSlotItemHandler(inventory, 0, 57, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 1, 79, 17));
        addSlotToContainer(new CookingSlotItemHandler(inventory, 2, 101, 17));
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

            if (index > 2)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 12) {
                        if (!this.mergeItemStack(copy, 12, 30, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 3, 13, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,3, 30, false))
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
