package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CookingSlotItemHandler extends SlotItemHandler {

    public CookingSlotItemHandler(final IItemHandler inventory, final int index, final int xPosition,
                                  final int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        if (stack.isEmpty())
            return false;

        if (!super.isItemValid(stack))
            return false;
        return stack.hasCapability(FoodProvider.FOOD_CAP, null);
    }
}
