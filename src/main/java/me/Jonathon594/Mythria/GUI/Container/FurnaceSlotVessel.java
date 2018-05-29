package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.ItemCeramicVessel;
import me.Jonathon594.Mythria.Items.ItemOre;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FurnaceSlotVessel extends SlotItemHandler {
    public FurnaceSlotVessel(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() instanceof ItemCeramicVessel) return true;
        return false;
    }
}
