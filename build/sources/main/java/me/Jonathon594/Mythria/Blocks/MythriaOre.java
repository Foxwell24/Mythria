package me.Jonathon594.Mythria.Blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class MythriaOre extends MythriaBlock {
    private Item itemDropped;

    public MythriaOre(final Material materialIn, final String nameIn, double weight, int staminaCost, Item itemDropped) {
        super(Material.ROCK, nameIn, SoundType.STONE, weight, staminaCost,0 ,0);
        this.itemDropped = itemDropped;
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(3.0f);
        setResistance(5.0f);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return itemDropped;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(4) + 1;
    }
}
