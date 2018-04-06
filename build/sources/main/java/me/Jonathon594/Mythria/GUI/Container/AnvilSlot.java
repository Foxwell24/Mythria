package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Interface.IWorkable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class AnvilSlot extends SlotItemHandler {
    public AnvilSlot(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return true;
    }
}
