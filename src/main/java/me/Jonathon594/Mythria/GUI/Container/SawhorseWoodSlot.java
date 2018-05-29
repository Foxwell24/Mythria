package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.ItemLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SawhorseWoodSlot extends SlotItemHandler {
    public SawhorseWoodSlot(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.getItem() instanceof ItemLog ||
                stack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.LOG2)) ||
                stack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.LOG))) return true;
        return false;
    }
}
