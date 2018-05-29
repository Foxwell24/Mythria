package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Blocks.MythriaBlock;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BlockModule {
    /**
     * Adds a tree to the given lists.
     *
     * @param anchor   anchor block
     * @param blocks   blocks
     * @param mats     TODO
     * @param maxCount TODO
     * @param radius   TODO
     * @param up       TODO
     * @param original TODO
     */
    public static void getConnected(World worldIn, final BlockPos anchor, final ArrayList<BlockPos> blocks, final Block[] mats,
                             final int maxCount, final int radius, final int up, final BlockPos original) {
        // Limits:
        if (blocks.size() > maxCount)
            return;
        BlockPos nextAnchor = null;
        final BlockPos centerLine = original;
        // North:
        nextAnchor = anchor.north();
        Block nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
        // East:
        nextAnchor = anchor.east();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
        // south:
        nextAnchor = anchor.south();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
        // west:
        nextAnchor = anchor.west();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
        // up:
        nextAnchor = anchor.up();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
        // down:
        nextAnchor = anchor.down();
        nextBlock = worldIn.getBlockState(nextAnchor).getBlock();
        if (Arrays.asList(mats).contains(nextBlock) && !blocks.contains(nextAnchor)) {
            centerLine.crossProduct(new Vec3i(1,0,1));
            if (centerLine.getDistance(nextAnchor.getX(), nextAnchor.getY(), nextAnchor.getZ()) > radius)
                return;
            if (nextAnchor.getY() - original.getY() > up)
                return;
            blocks.add(nextAnchor);
            getConnected(worldIn, nextAnchor, blocks, mats, maxCount, radius, up, original);
        }
    }

    public static void physicsCheck(World worldIn, BlockPos pos, final Block block, final int recursionCount, final boolean forceCheck) {
        BlockModule.physicsTask(worldIn, pos, block, forceCheck, recursionCount);
    }

    public static ArrayList<Block> getFallingBlocks() {
        final ArrayList<Block> fallingBlocks = new ArrayList<>();
        fallingBlocks.add(Blocks.COBBLESTONE);
        fallingBlocks.add(Blocks.COAL_ORE);
        fallingBlocks.add(Blocks.REDSTONE_ORE);
        fallingBlocks.add(Blocks.LIT_REDSTONE_ORE);
        fallingBlocks.add(Blocks.DIAMOND_ORE);
        fallingBlocks.add(Blocks.EMERALD_ORE);
        fallingBlocks.add(Blocks.LAPIS_ORE);
        fallingBlocks.add(Blocks.QUARTZ_ORE);
        fallingBlocks.add(Blocks.NETHER_WART_BLOCK);
        fallingBlocks.add(Blocks.HAY_BLOCK);
        fallingBlocks.add(Blocks.MOSSY_COBBLESTONE);
        fallingBlocks.add(Blocks.DIRT);
        fallingBlocks.add(Blocks.GRASS);
        fallingBlocks.add(Blocks.MYCELIUM);
        fallingBlocks.add(Blocks.NETHERRACK);
        fallingBlocks.add(Blocks.SOUL_SAND);
        fallingBlocks.add(Blocks.SLIME_BLOCK);
        fallingBlocks.add(Blocks.CLAY);

        fallingBlocks.add(MythriaBlocks.COPPER_ORE);
        fallingBlocks.add(MythriaBlocks.TIN_ORE);
        fallingBlocks.add(MythriaBlocks.SILVER_ORE);
        fallingBlocks.add(MythriaBlocks.IRON_ORE);
        fallingBlocks.add(MythriaBlocks.GOLD_ORE);
        fallingBlocks.add(MythriaBlocks.TITANIUM_ORE);
        fallingBlocks.add(MythriaBlocks.TUNGSTEN_ORE);
        fallingBlocks.add(MythriaBlocks.PLATINUM_ORE);
        return fallingBlocks;
    }

    @SuppressWarnings("deprecation")
    public static UUID applyPhysics(World worldIn, final Block block, BlockPos pos) {
        if (block == Blocks.AIR)
            return null;
        IBlockState blockState = worldIn.getBlockState(pos);
        if (block == Blocks.GRASS || block == Blocks.MYCELIUM)
            worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
        final EntityFallingBlock fallingBlock = new EntityFallingBlock(worldIn, pos.getX() + 0.5, pos.getY(),
                pos.getZ() + 0.5, blockState);
        fallingBlock.setDropItemsWhenDead(false);
        fallingBlock.fallTime = 1;
        // remove original block
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        worldIn.spawnEntity(fallingBlock);
        return fallingBlock.getUniqueID();
    }

    private static void physicsTask(World worldIn, BlockPos pos, Block block, boolean force, int recursionCount) {
        boolean fall = false;
        final ArrayList<Block> fallingBlocks = BlockModule.getFallingBlocks();
        final Block underBlock = worldIn.getBlockState(pos.down()).getBlock();
        if ((underBlock == Blocks.AIR || underBlock instanceof BlockFluidBase || underBlock == Blocks.TORCH)
                && (block == Blocks.SAND || block == Blocks.GRAVEL
                || (fallingBlocks.contains(block)) && block != Blocks.AIR)) {
            applyPhysics(worldIn, block, pos);
            fall = true;
        }
        if (fall || force)
            if (recursionCount >= 0) {
                if (force) {
                    BlockPos neighbor = pos.down();
                    Block b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                    neighbor = neighbor.down();
                    b1 = worldIn.getBlockState(neighbor).getBlock();
                    physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);
                }

                BlockPos neighbor = pos.up();
                Block b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                neighbor = pos.down();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                neighbor = pos.north();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                neighbor = pos.east();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                neighbor = pos.west();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);

                neighbor = pos.south();
                b1 = worldIn.getBlockState(neighbor).getBlock();
                physicsCheck(worldIn, neighbor, b1, recursionCount -1, false);
            }
    }

    public static void softenStone(World world, BlockPos pos, Block block) {
        if(MythriaUtil.isOre(block)) {
            if(world.getBlockState(pos.up()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.up(), Blocks.COBBLESTONE.getDefaultState());

            if(world.getBlockState(pos.down()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.down(), Blocks.COBBLESTONE.getDefaultState());

            if(world.getBlockState(pos.north()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.north(), Blocks.COBBLESTONE.getDefaultState());

            if(world.getBlockState(pos.east()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.east(), Blocks.COBBLESTONE.getDefaultState());

            if(world.getBlockState(pos.west()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.west(), Blocks.COBBLESTONE.getDefaultState());

            if(world.getBlockState(pos.south()).getBlock() == Blocks.STONE)
                world.setBlockState(pos.south(), Blocks.COBBLESTONE.getDefaultState());
        }
    }
}
