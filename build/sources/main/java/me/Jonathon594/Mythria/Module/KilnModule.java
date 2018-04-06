package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Items.ItemPottery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KilnModule {
    public static ItemStack fire(ItemStack itemStack) {
        ItemStack result = ItemStack.EMPTY;
        if(itemStack.getItem() instanceof ItemPottery) {
            ItemPottery pottery = (ItemPottery) itemStack.getItem();
            Item resultItem = pottery.getFiringResult();
            result = new ItemStack(resultItem, 1);
        }
        return result;
    }
}
