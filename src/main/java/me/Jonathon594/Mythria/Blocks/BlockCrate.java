package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.TileEntity.TileEntityPrimativeCrate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCrate extends Block implements ITileEntityProvider, IBlockData {
    private double weight;
    private int staminaCost;

    protected BlockCrate(final String nameIn, double weight, int staminaCost) {
        super(Material.WOOD);
        this.weight = weight;
        this.staminaCost = staminaCost;
        setUnlocalizedName(nameIn);
        setHardness(0.5f);
        setResistance(0.5f);
        this.setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.DECORATIONS);
        this.blockSoundType = SoundType.WOOD;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5;
    }

    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
                                    final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX,
                                    final float hitY, final float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            final ILockableContainer ilockablecontainer = getLockableContainer(worldIn, pos);

            if (ilockablecontainer != null) {
                playerIn.displayGUIChest(ilockablecontainer);

                playerIn.addStat(StatList.CHEST_OPENED);
            }

            return true;
        }
    }

    @Nullable
    public ILockableContainer getLockableContainer(final World worldIn, final BlockPos pos) {
        return getContainer(worldIn, pos, false);
    }

    @Nullable
    public ILockableContainer getContainer(final World worldIn, final BlockPos pos, final boolean allowBlocking) {
        final TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityPrimativeCrate))
            return null;
        else {
            final ILockableContainer ilockablecontainer = (TileEntityPrimativeCrate) tileentity;

            if (!allowBlocking && isBlocked(worldIn, pos))
                return null;
            else
                return ilockablecontainer;
        }
    }

    private boolean isBlocked(final World worldIn, final BlockPos pos) {
        return isBelowSolidBlock(worldIn, pos) || isOcelotSittingOnChest(worldIn, pos);
    }

    private boolean isBelowSolidBlock(final World worldIn, final BlockPos pos) {
        return worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN);
    }

    private boolean isOcelotSittingOnChest(final World worldIn, final BlockPos pos) {
        for (final Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(),
                pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1))) {
            final EntityOcelot entityocelot = (EntityOcelot) entity;

            if (entityocelot.isSitting())
                return true;
        }

        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the
     * block.
     */
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityPrimativeCrate();
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getStaminaCostForBreaking() {
        return staminaCost;
    }
}
