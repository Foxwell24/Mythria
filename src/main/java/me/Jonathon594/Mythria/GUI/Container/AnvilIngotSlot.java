package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Metal.MetalProvider;
import me.Jonathon594.Mythria.Interface.IWorkable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class AnvilIngotSlot extends SlotItemHandler {
    public AnvilIngotSlot(IItemHandler inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.getItem() instanceof IWorkable) return true;
        return false;
    }
}
