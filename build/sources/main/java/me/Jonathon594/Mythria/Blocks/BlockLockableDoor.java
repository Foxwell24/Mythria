package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.TileEntity.TileEntityDoor;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLockableDoor extends BlockDoor implements ITileEntityProvider, IBlockData {
    private double weight;
    private int stamina;

    protected BlockLockableDoor(Material materialIn, String name, double weight, int staminaCost) {
        super(materialIn);

        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));

        this.stamina = staminaCost;
        this.weight = weight;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (this.blockMaterial == Material.IRON)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
            IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                return false;
            }
            else
            {
                if(hand.equals(EnumHand.OFF_HAND)) return false;
                if(playerIn.isSneaking()) {
                    if(isBehind(facing, state)) {
                        TileEntityDoor ted = getTileEntityDoor(worldIn, pos, state);
                        ted.toggledBarred();
                        if(!worldIn.isRemote) playerIn.sendMessage(new TextComponentString("You have " + (ted.isBarred() ? "barred " : "unbarred ") + "the door."));
                        return true;
                    }
                }
                if(!iblockstate.getValue(OPEN)) {
                    if(!canOpenDoor(worldIn, pos, state, playerIn, hand, facing)) {
                        if(!worldIn.isRemote) playerIn.sendMessage(new TextComponentString("The door is locked."));
                        return false;
                    }
                }
                state = iblockstate.cycleProperty(OPEN);
                worldIn.setBlockState(blockpos, state, 10);
                worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
                worldIn.playEvent(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                return true;
            }
        }
    }

    private boolean isBehind(EnumFacing facing, IBlockState state) {
        return facing.equals(state.getValue(FACING));
    }

    private boolean canOpenDoor(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing) {
        TileEntityDoor ted = getTileEntityDoor(worldIn, pos, state);

        if(ted.isBarred() || ted.isLocked()) {
            return false;
        } else {
            return true;
        }
    }

    private TileEntityDoor getTileEntityDoor(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityDoor ted;
        if(state.getValue(HALF).equals(EnumDoorHalf.UPPER)) {
            ted = (TileEntityDoor) worldIn.getTileEntity(pos.down());
        } else {
            ted = (TileEntityDoor) worldIn.getTileEntity(pos);
        }
        return ted;
    }

    private int getOpenSound()
    {
        return this.blockMaterial == Material.IRON ? 1005 : 1006;
    }

    private int getCloseSound()
    {
        return this.blockMaterial == Material.IRON ? 1011 : 1012;
    }


    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getStaminaCostForBreaking() {
        return stamina;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDoor();
    }
}
