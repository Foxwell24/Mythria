package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

public class MythriaItemHoe extends ItemHoe implements IItemData {
    private double weight;

    public MythriaItemHoe(final String name, final ToolMaterial mat, double weight) {
        super(mat);
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
