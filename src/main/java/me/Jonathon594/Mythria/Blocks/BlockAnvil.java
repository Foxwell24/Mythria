package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.TileEntity.TileEntityAnvil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockAnvil extends MythriaFallingBlock implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
    protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
    private int tier;

    public BlockAnvil(Material materialIn, String nameIn, SoundType soundType, double weight, int staminaCost, int tier) {
        super(materialIn, nameIn, soundType, weight, staminaCost);
        this.tier = tier;

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
        return enumfacing.getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand.equals(EnumHand.OFF_HAND))
            return false;
        if (worldIn.isRemote)
            return false;
        IProfile profile = playerIn.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (profile.hasFlag(AttributeFlag.BASIC_SMITHING)) {
            //Open Anvil Gui
            playerIn.openGui(Mythria.instance, MythriaGui.ANVIL_GUI.ordinal(), worldIn, pos.getX(),
                    pos.getY(), pos.getZ());
        } else {
            playerIn.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAnvil(tier);
    }
}
