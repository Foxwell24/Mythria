package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.TileEntity.TileEntityAlter;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scala.sys.Prop;

import javax.annotation.Nullable;

public class BlockAlter extends MythriaBlock implements ITileEntityProvider {
    public BlockAlter(Material materialIn, String nameIn, SoundType soundType, double weight, int staminaCost, int flammability, int spreadSpeed) {
        super(materialIn, nameIn, soundType, weight, staminaCost, flammability, spreadSpeed);

        this.setResistance(5);
        this.setHardness(10);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAlter();
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

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 12.0/16.0, 1D);

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand.equals(EnumHand.OFF_HAND))
            return false;
        if (worldIn.isRemote)
            return false;
        if(playerIn.isSneaking()) {
            TileEntityAlter alter = (TileEntityAlter) worldIn.getTileEntity(pos);
            if(alter.canOfferItems()) {
                alter.offerItems();
            } else {
                playerIn.sendMessage(new TextComponentString("You cannot offer an empty offering."));
            }
        } else {
            playerIn.openGui(Mythria.instance, MythriaGui.ALTER_GUI.ordinal(), worldIn, pos.getX(),
                    pos.getY(), pos.getZ());
        }
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
