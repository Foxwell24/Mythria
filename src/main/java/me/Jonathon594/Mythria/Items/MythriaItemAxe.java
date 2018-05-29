package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

public class MythriaItemAxe extends ItemAxe implements IItemData {
    private double weight;

    public MythriaItemAxe(final String name, final ToolMaterial mat, final float speed, double weight, float damage) {
        super(mat, mat.getAttackDamage(), speed);
        this.attackDamage = damage;
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
