package me.Jonathon594.Mythria.Const;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Blacklist {
    public static boolean isBlacklisted(Item item) {
        if(item.equals(Items.IRON_INGOT)) return true;
        if(item.equals(Items.GOLD_INGOT)) return true;
        return false;
    }
}
