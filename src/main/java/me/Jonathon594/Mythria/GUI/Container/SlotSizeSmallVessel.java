package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.ItemOre;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotSizeSmallVessel extends SlotItemHandler {
    public SlotSizeSmallVessel(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() instanceof ItemOre || stack.getItem().equals(Items.COAL)) return true;
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
