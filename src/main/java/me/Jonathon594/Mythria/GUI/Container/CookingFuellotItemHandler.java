package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CookingFuellotItemHandler extends SlotItemHandler {
    public CookingFuellotItemHandler(final IItemHandler inventory, final int index, final int xPosition,
                                     final int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        if (stack.isEmpty())
            return false;

        return stack.getItem().equals(MythriaItems.LOG);
    }
}
