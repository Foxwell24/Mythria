package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class MythriaItemSword extends ItemSword implements IItemData{
    private double weight;

    public MythriaItemSword(final String name, final ToolMaterial mat, double weight) {
        super(mat);
        this.weight = weight;
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.COMBAT);

        MythriaItems.addItem(this);
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
