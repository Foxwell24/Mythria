package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;

public class MythriaCoin extends MythriaItem {

    public MythriaCoin(final String name, double weight) {
        super(name, weight);
        setMaxStackSize(50);
        setCreativeTab(CreativeTabs.MISC);
    }

}
