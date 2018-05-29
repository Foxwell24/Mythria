package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ContainerVessel extends Container {

    private int bagsSlotNum;
    EntityPlayer player;
    public ItemStackHandler containerInv;

    public ContainerVessel(InventoryPlayer inventoryPlayer) {
        IVessel v = inventoryPlayer.getCurrentItem().getCapability(VesselProvider.VESSEL_CAP, null);
        containerInv = v.getInventory();
        this.player = inventoryPlayer.player;

        bagsSlotNum = inventoryPlayer.player.inventory.currentItem;

        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 5; x++) {
                this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, y * 5 + x, 44 + x * 18, 18 + y * 18));
            }
        }

        int row;
        int col;

        for (row = 0; row < 9; ++row) {
            if (row == bagsSlotNum)
                this.addSlotToContainer(new SlotForShowOnly(inventoryPlayer, row, 8 + row * 18, 142));
            else
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

            if (index > 9)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 10, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 18) {
                        if (!this.mergeItemStack(copy, 19, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 10, 19, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,10, 45, false))
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

