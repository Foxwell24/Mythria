package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Module.FireMakingModule;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.TileEntity.TileEntityMythriaFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class BlockMythriaFurnace extends MythriaBlock implements ITileEntityProvider {

    public static final PropertyBool BURNING = PropertyBool.create("burning");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockMythriaFurnace(final Material materialIn, final String nameIn, double weight, int staminaCost) {
        super(materialIn, nameIn, SoundType.STONE, weight, staminaCost, 0, 0);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.5f);
        setResistance(0.5f);
        setDefaultState(this.getDefaultState().withProperty(BURNING, false));
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityMythriaFurnace();
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(BURNING))
        {
            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
            {
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing)
            {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityMythriaFurnace) {
            final ItemStackHandler cookItems = ((TileEntityMythriaFurnace) te).getInventory();
            for (int i = 0; i < cookItems.getSlots(); i++) {
                final ItemStack is = cookItems.getStackInSlot(i);
                cookItems.setStackInSlot(i, ItemStack.EMPTY);
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BURNING);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        boolean lit = meta >= 6;
        if(lit) meta -= 6;

        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(BURNING, lit);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        boolean lit = state.getValue(BURNING);

        if(lit) return 15;
        return 0;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex() + (state.getValue(BURNING) ? 6 : 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state,
                                    final EntityPlayer player, final EnumHand hand, final EnumFacing facing, final float x, final float y,
                                    final float z) {
        if (hand.equals(EnumHand.OFF_HAND))
            return false;
        if (world.isRemote)
            return false;
        final TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMythriaFurnace) {
            final TileEntityMythriaFurnace cf = (TileEntityMythriaFurnace) te;
            final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            if (!p.hasFlag(AttributeFlag.SMELTING)) {
                player.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
                return false;
            }
            if (!cf.isBurning()) {
                if(FireMakingModule.TryLight(player, hand, cf)) return true;
            }

            player.openGui(Mythria.instance, MythriaGui.MYTHRIA_FURNACE_GUI.ordinal(), world, pos.getX(),
                    pos.getY(), pos.getZ());
        }
        return true;
    }
}
