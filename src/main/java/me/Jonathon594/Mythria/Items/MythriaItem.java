package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MythriaItem extends Item implements IItemData {
    private double weight;

    public MythriaItem(final String name, double weight) {
        this(name, 1, weight);
    }

    public MythriaItem(final String name, final int stackSize, double weight) {
        super();
        this.weight = weight;
        setMaxStackSize(stackSize);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.MISC);

        MythriaItems.addItem(this);
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
