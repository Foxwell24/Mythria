package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.TileEntity.TileEntityBrickPile;
import me.Jonathon594.Mythria.TileEntity.TileEntityIngotPile;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockBrickPile extends MythriaBlock implements ITileEntityProvider {
    public BlockBrickPile(Material materialIn, String nameIn, SoundType soundType, double weight, int staminaCost) {
        super(materialIn, nameIn, soundType, weight, staminaCost, 0, 0);

        setHardness(0.5f);
        setResistance(0.5f);
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityIngotPile();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityBrickPile tep = (TileEntityBrickPile) worldIn.getTileEntity(pos);
        if(!worldIn.isRemote) {
            tep.dropAllItems();
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        //int meta = world.getBlockMetadata(x, y, z);
        //int direction = getDirectionFromMetadata(meta);
        TileEntityBrickPile te = (TileEntityBrickPile) source.getTileEntity(pos);

        if (te != null && te.getInventory().getStackInSlot(0) != null)
            return new AxisAlignedBB(0, (double)0, (double)0, (double)1, ((te.getBrickCount() + 7) / 8) * 0.125, (double)1);

        return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) return false; // If clientside, return false
        if(hand.equals(EnumHand.OFF_HAND)) return false; // If offhand, return false
        ItemStack is = playerIn.getHeldItemMainhand(); // Get the players held item
        TileEntityBrickPile tep = (TileEntityBrickPile) worldIn.getTileEntity(pos);

        if(is == null || is.isEmpty()) { // If item is air
            ItemStack brick = tep.getInventory().getStackInSlot(0).copy(); // Get a copy of the pile's brick
            brick.setCount(1); // Set it's count to 1
            if(playerIn.inventory.addItemStackToInventory(brick)) { //If it can be added to the players inventory, do, then
                tep.removeBrick(); // Remove one brick from the pile
            } else { //If it cannot
                return false; // Return false
            }
        } else { // If the player is holding something
            if(is.getItem().equals(Items.BRICK) || is.getItem().equals(MythriaItems.STONE_BRICK)) { // If it is a brick
                    if(tep.addBrick(is.getItem())) { // If the stack has room, grow it, and
                        if(!playerIn.isCreative()) is.shrink(1); // Shrink the players stacks
                    }
            }
        }

        return false;
    }
}
