package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class MythriaItemBlock extends ItemBlock implements IItemData {

    public MythriaItemBlock(final Block block) {
        super(block);
        setRegistryName(getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    @Override
    public double getWeight() {
        if(block instanceof IBlockData) {
            return ((IBlockData) block).getWeight();
        }
        return 0;
    }
}
