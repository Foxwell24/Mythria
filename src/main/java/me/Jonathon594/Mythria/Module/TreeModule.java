package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class TreeModule {
    public static ArrayList<Block> HandleTreeChop(final EntityPlayer p, final BlockPos block) {
        final ArrayList<Block> blocks = new ArrayList<>();
        Block b = p.world.getBlockState(block).getBlock();
        if (b != Blocks.LOG && b != Blocks.LOG2)
            return null;
        if (!MythriaUtil.isAxe(p.getHeldItemMainhand().getItem()))
            return null;
        if (p.isSneaking())
            return null;
        final int itemd = p.getHeldItemMainhand().getItemDamage();
        final int itemmd = p.getHeldItemMainhand().getMaxDamage();
        final int itemr = (itemmd - itemd);
        ArrayList<BlockPos> list = new ArrayList<>();
        BlockModule.getConnected(p.getEntityWorld(), block, list,
                new Block[] {Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2}, 500, 8, 75, block);
        for(BlockPos pos : list) {
            blocks.add(p.world.getBlockState(pos).getBlock());
            IBlockState bs = p.world.getBlockState(pos);
            bs.getBlock().dropBlockAsItem(p.world, pos, bs, 0);
            if(bs.getBlock().equals(Blocks.LEAVES) || bs.getBlock().equals(Blocks.LEAVES2)) {
                if(Math.random() < 0.3) {
                    InventoryHelper.spawnItemStack(p.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(MythriaItems.THATCH, 1));
                } else if(Math.random() < 0.5) {
                    InventoryHelper.spawnItemStack(p.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(Items.STICK, 1));
                } else if(Math.random() < 0.5) {
                    InventoryHelper.spawnItemStack(p.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            getLogItemFromLeaves(bs));
                }

            }
            p.world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        if (!hasLeaves(blocks))
            return null;
        final ArrayList<Block> consideredMaterials = new ArrayList<>();
        int d = 0;
        for (Block b1 : blocks) {
            if (b1 == Blocks.LOG || b1 == Blocks.LOG2) {
                d++;
                consideredMaterials.add(b1);
            }
        }
        p.getHeldItemMainhand()
                .setItemDamage((p.getHeldItemMainhand().getItemDamage() + d));
        if (d > itemr) {
            p.setHealth(p.getHealth() - 4);
            p.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        }
        return consideredMaterials;
    }

    private static ItemStack getLogItemFromLeaves(IBlockState bs) {
        int meta = 0;
        int bmeta = bs.getBlock().getMetaFromState(bs);
        meta = bmeta % 4;

        if(bs.getBlock().equals(Blocks.LEAVES)) {
            return new ItemStack(MythriaItems.LOG, 1, meta);
        }
        if(bs.getBlock().equals(Blocks.LEAVES2)) {
            return new ItemStack(MythriaItems.LOG, 1, meta+4);
        }
        return null;
    }

    private static boolean hasLeaves(final ArrayList<Block> blocks) {
        for (final Block b : blocks)
            if (b == Blocks.LEAVES || b == Blocks.LEAVES2)
                return true;
        return false;
    }
}
