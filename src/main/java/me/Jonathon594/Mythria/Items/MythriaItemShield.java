package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;

public class MythriaItemShield extends ItemShield implements IItemData {
    private double weight;

    public MythriaItemShield(final String name, final ToolMaterial mat, double weight) {
        this.weight = weight;
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.TOOLS);

        MythriaItems.addItem(this);
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
