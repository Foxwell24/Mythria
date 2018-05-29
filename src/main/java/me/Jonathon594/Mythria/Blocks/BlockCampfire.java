package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Module.FireMakingModule;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.TileEntity.TileEntityCampfire;
import me.Jonathon594.Mythria.TileEntity.TileEntityCookingFireBasic;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class BlockCampfire extends MythriaBlock implements ITileEntityProvider {

    public static PropertyBool BURNING = PropertyBool.create("burning");
    protected static final AxisAlignedBB CAMPFIRE_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.25D, 0.9D);
    private final AttributeFlag requiredFlag;

    public BlockCampfire(final Material materialIn, final String nameIn,
                         final AttributeFlag requiredFlag, double weight, int staminaCost) {
        super(materialIn, nameIn, SoundType.WOOD, weight, staminaCost, 0, 0);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.5f);
        setResistance(0.5f);
        this.requiredFlag = requiredFlag;
        setDefaultState(this.getDefaultState().withProperty(BURNING, false));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getActualState(world, pos).getValue(BURNING) ? 15 : 0;
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityCampfire();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
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
    public boolean isFullCube(final IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return CAMPFIRE_AABB;
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

    @Override
    public void randomDisplayTick(final IBlockState stateIn, final World worldIn, final BlockPos pos,
                                  final Random rand) {
        // TODO Auto-generated method stub
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (stateIn.getValue(BURNING)) {

            final int a = 45;
            for (int i = 0; i < a; i++) {
                final double x = pos.getX() + Math.random() / 2 + 0.25;
                final double y = pos.getY() + Math.random() * 0.2;
                final double z = pos.getZ() + Math.random() / 2 + 0.25;
                worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, Math.random() * 0.08D, 0.0D);
            }
        }
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
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityCookingFireBasic) {
            final ItemStackHandler cookItems = ((TileEntityCookingFireBasic) te).getCookItems();
            for (int i = 0; i < cookItems.getSlots(); i++) {
                final ItemStack is = cookItems.getStackInSlot(i);
                cookItems.setStackInSlot(i, ItemStack.EMPTY);
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
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
        if (te instanceof TileEntityCookingFireBasic) {
            final TileEntityCookingFireBasic cf = (TileEntityCookingFireBasic) te;
            if (cf.isLit()) {
                final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
                if (!p.hasFlag(requiredFlag)) {
                    player.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
                    return false;
                }
                player.openGui(Mythria.instance, MythriaGui.MYTHRIA_OPEN_CAMPFIRE_GUI.ordinal(), world, pos.getX(),
                        pos.getY(), pos.getZ());
            } else
                FireMakingModule.TryLight(player, hand, cf);
        }
        return true;
    }
}
