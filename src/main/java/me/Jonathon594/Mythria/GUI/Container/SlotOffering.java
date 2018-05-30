package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.ItemFilledMold;
import me.Jonathon594.Mythria.Items.ItemMoldEmpty;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOffering extends SlotItemHandler {
    public SlotOffering(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() instanceof ItemBlock) return false;
        return true;
    }
}
