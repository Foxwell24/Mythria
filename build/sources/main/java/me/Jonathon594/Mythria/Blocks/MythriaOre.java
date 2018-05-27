package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class MythriaOre extends MythriaBlock {
    public MythriaOre(final Material materialIn, final String nameIn, double weight, int staminaCost) {
        super(Material.ROCK, nameIn, SoundType.STONE, weight, staminaCost,0 ,0);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(3.0f);
        setResistance(5.0f);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if(this.equals(MythriaBlocks.TIN_ORE)) return MythriaItems.TIN_ORE;
        else if(this.equals(MythriaBlocks.COPPER_ORE)) return MythriaItems.COPPER_ORE;
        else if(this.equals(MythriaBlocks.IRON_ORE)) return MythriaItems.IRON_ORE;
        else if(this.equals(MythriaBlocks.SILVER_ORE)) return MythriaItems.SILVER_ORE;
        else if(this.equals(MythriaBlocks.GOLD_ORE)) return MythriaItems.GOLD_ORE;
        else if(this.equals(MythriaBlocks.TITANIUM_ORE)) return MythriaItems.TITANIUM_ORE;
        else if(this.equals(MythriaBlocks.TUNGSTEN_ORE)) return MythriaItems.TUNGSTEN_ORE;
        else if(this.equals(MythriaBlocks.PLATINUM_ORE)) return MythriaItems.PLATINUM_ORE;
        return null;
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
