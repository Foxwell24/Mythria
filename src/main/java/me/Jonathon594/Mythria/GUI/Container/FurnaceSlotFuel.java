package me.Jonathon594.Mythria.GUI.Container;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FurnaceSlotFuel extends SlotItemHandler {
    public FurnaceSlotFuel(IItemHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.getItem().equals(Items.COAL) || stack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.COAL_BLOCK))) return true;
        return false;
    }
}
