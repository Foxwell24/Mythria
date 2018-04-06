package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Managers.MythriaInventoryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {

    public SlotLocked(final IInventory inventory, final int slotIndex, final int xPos, final int yPos) {
        super(inventory, slotIndex, xPos, yPos);
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        return MythriaInventoryManager.isSlotOpen(Minecraft.getMinecraft().player, getSlotIndex()) && super.isItemValid(stack);
    }

    @Override
    public ItemStack decrStackSize(final int amount) {
        return MythriaInventoryManager.isSlotOpen(Minecraft.getMinecraft().player, getSlotIndex())
                ? super.decrStackSize(amount)
                : null;
    }

    @Override
    public boolean canTakeStack(final EntityPlayer playerIn) {
        return MythriaInventoryManager.isSlotOpen(Minecraft.getMinecraft().player, getSlotIndex()) && super.canTakeStack(playerIn);
    }

    @Override
    public boolean isEnabled() {
        return MythriaInventoryManager.isSlotOpen(Minecraft.getMinecraft().player, getSlotIndex()) && super.isEnabled();
    }
}
