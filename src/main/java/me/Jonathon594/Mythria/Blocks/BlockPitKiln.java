package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Module.FireMakingModule;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.TileEntity.TileEntityPitKiln;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPitKiln extends MythriaBlock implements ITileEntityProvider {
    public static PropertyInteger FULL = PropertyInteger.create("full", 0, 7);
    public static PropertyBool BURNING = PropertyBool.create("burning");

    public BlockPitKiln(Material materialIn, String nameIn, SoundType soundType, double weight, int staminaCost) {
        super(materialIn, nameIn, soundType, weight, staminaCost, 0, 0);

        setDefaultState(this.getDefaultState().withProperty(BURNING, false));
        setHardness(0.5f);
        setResistance(0.5f);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if(meta == 1) return this.getDefaultState().withProperty(BURNING, true);
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if(state.getValue(BURNING) == true) return 1;
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING, FULL);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) return false;

        final IProfile p = playerIn.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (!p.hasFlag(AttributeFlag.SMELTING)) {
            playerIn.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
            return false;
        }

        if(hand.equals(EnumHand.OFF_HAND)) return false;

        TileEntity te = worldIn.getTileEntity(pos);
        if(te == null) return false;
        if(te instanceof TileEntityPitKiln) {
            TileEntityPitKiln pk = (TileEntityPitKiln) te;
            int fuel = pk.getFuel();
            ItemStack is = playerIn.getHeldItem(hand);
            if(pk.isBurning()) {

            } else {
                if (fuel < 3 && is.getItem().equals(MythriaItems.THATCH)) {
                    is.shrink(1);
                    pk.setFuel(pk.getFuel() + 1);
                } else if (fuel >= 3 && is.getItem().equals(MythriaItems.LOG) && fuel < 7) {
                    is.shrink(1);
                    pk.setFuel(pk.getFuel() + 1);
                } else if (fuel == 7) {
                    if(is.getItem().equals(Items.STICK)) {
                        FireMakingModule.TryLight(playerIn, hand, pk);
                    } else {
                        playerIn.openGui(Mythria.instance, MythriaGui.MYTHRIA_PITKILN_GUI.ordinal(), worldIn, pos.getX(),
                                pos.getY(), pos.getZ());
                    }
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        float y = (blockState.getActualState(worldIn, pos).getValue(FULL)+1) * 2 / 16;
        return new AxisAlignedBB(new BlockPos(0, 0, 0), new BlockPos(0, y, 0));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        boolean lit = state.getValue(BURNING);

        if(lit) return 15;
        return 0;
    }

    @Override
    public void randomDisplayTick(final IBlockState stateIn, final World worldIn, final BlockPos pos,
                                  final Random rand) {
        // TODO Auto-generated method stub
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (stateIn.getValue(BURNING)) {

            final int a = 45;
            for (int i = 0; i < a; i++) {
                final double x = pos.getX() + Math.random();
                final double y = pos.getY() + Math.random() * 0.2 + 1;
                final double z = pos.getZ() + Math.random();
                worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, Math.random() * 0.08D, 0.0D);
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        int full = 0;

        if (tileentity instanceof TileEntityPitKiln) {
            TileEntityPitKiln teic = (TileEntityPitKiln) tileentity;
            full = teic.getFuel();
        }

        return state.withProperty(FULL, full);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPitKiln();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!canPlaceBlockAt(worldIn, pos)) worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        final TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityPitKiln) {
            final ItemStackHandler vessels = ((TileEntityPitKiln) te).getVessels();
            for (int i = 0; i < vessels.getSlots(); i++) {
                final ItemStack is = vessels.getStackInSlot(i);
                vessels.setStackInSlot(i, ItemStack.EMPTY);
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        if(!worldIn.getBlockState(pos.down()).isFullCube())  return false;
        if(!worldIn.getBlockState(pos.north()).isFullCube()) return false;
        if(!worldIn.getBlockState(pos.east()).isFullCube()) return false;
        if(!worldIn.getBlockState(pos.south()).isFullCube()) return false;
        if(!worldIn.getBlockState(pos.west()).isFullCube()) return false;

        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
    }
}
