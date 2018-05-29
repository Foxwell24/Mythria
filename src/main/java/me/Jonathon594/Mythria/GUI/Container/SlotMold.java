package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.ItemCeramicVessel;
import me.Jonathon594.Mythria.Items.ItemFilledMold;
import me.Jonathon594.Mythria.Items.ItemMoldEmpty;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMold extends SlotItemHandler {
    public SlotMold(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() instanceof ItemMoldEmpty || stack.getItem() instanceof ItemFilledMold) return true;
        return false;
    }
}
