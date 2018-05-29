package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenGroundStick extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        boolean leaves = false;
        position = new BlockPos(position.getX(), 256, position.getZ());
        for (IBlockState iblockstate = worldIn.getBlockState(position);
             (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position))
                     && position.getY() > 0; iblockstate = worldIn.getBlockState(position)) {
            if(iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) {
                leaves = true;
            }
            position = position.down();
        }

        BlockPos blockpos = position.add(0, 1, 0);
        if(!leaves) return false;

        Block b = MythriaBlocks.GROUND_STICK;
        if(Math.random() < 0.2) b = MythriaBlocks.GROUND_STICK_PILE;

        if (worldIn.isAirBlock(blockpos) && b.canPlaceBlockAt(worldIn, blockpos)) {
            worldIn.setBlockState(blockpos, b.getDefaultState().withProperty(BlockGroundCover.FACING,
                    EnumFacing.values()[rand.nextInt(4)+2]), 2);
        }

        return true;
    }
}
