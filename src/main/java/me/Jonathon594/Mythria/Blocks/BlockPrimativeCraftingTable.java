package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPrimativeCraftingTable extends MythriaBlock {
    public BlockPrimativeCraftingTable(Material materialIn, String nameIn, double weight, int staminaCost) {
        super(materialIn, nameIn, SoundType.WOOD, weight, staminaCost, 5, 5);

        setHardness(0.5f);
        setResistance(0.5f);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn,
                                final BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isTopSolid()) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
    }

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 11.0/16.0, 1D);

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return false;
        playerIn.openGui(Mythria.instance, MythriaGui.MYTHRIA_CUSTOM_CRAFTING_GUI.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return false;
    }

    @Override
    public boolean isFullCube(final IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return AABB;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(final IBlockAccess worldIn, final IBlockState state, final BlockPos pos,
                                            final EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isOpaqueCube(final IBlockState state) {
        return false;
    }
}
