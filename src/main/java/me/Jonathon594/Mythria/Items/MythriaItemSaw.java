package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class MythriaItemSaw extends ItemTool implements IItemData{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final double weight;

    public MythriaItemSaw(float damage, float speed, ToolMaterial material, String name, double weight) {
        super(damage, speed, material, EFFECTIVE_ON);
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.TOOLS);
        this.weight = weight;

        MythriaItems.addItem(this);
    }


    @Override
    public double getWeight() {
        return weight;
    }
}
