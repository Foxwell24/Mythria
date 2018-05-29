package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Interface.IWorkable;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import me.Jonathon594.Mythria.TileEntity.TileEntityIngotPile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MythriaIngot extends MythriaItem implements IWorkable {
    private SmeltingRecipe.EnumMetal metalType;
    private SmithingManager.EnumMetalShape metalShape;

    public MythriaIngot(String name, int weight, SmeltingRecipe.EnumMetal metalType, SmithingManager.EnumMetalShape metalShape) {
        super(name, 1, weight);
        this.metalType = metalType;
        this.metalShape = metalShape;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) return EnumActionResult.PASS; // If client side, return
        if(hand.equals(EnumHand.OFF_HAND)) return EnumActionResult.PASS; // If offhand, return
        if(!metalShape.equals(SmithingManager.EnumMetalShape.INGOT)) return EnumActionResult.PASS; // If not ingot shape, return
        if(!facing.equals(EnumFacing.UP)) return EnumActionResult.PASS; // If not clicking on the top of a block, return
        if(!worldIn.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) return EnumActionResult.PASS; // If the block above is not air
        if(!worldIn.getBlockState(pos).isTopSolid()) return EnumActionResult.PASS; // If top is not solid, return
        worldIn.setBlockState(pos.up(), MythriaBlocks.INGOT_PILE.getDefaultState());
        TileEntityIngotPile tep = (TileEntityIngotPile) worldIn.getTileEntity(pos.up());
        tep.getInventory().setStackInSlot(0, new ItemStack(this, 1));
        if(!player.isCreative()) player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public SmeltingRecipe.EnumMetal getMetalType() {
        return metalType;
    }

    @Override
    public SmithingManager.EnumMetalShape getMetalShape() {
        return metalShape;
    }
}
