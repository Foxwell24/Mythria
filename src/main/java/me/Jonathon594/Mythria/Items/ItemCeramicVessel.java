package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCeramicVessel extends MythriaItem {
    public ItemCeramicVessel(String name, double weight) {
        super(name, weight);
    }

    public ItemCeramicVessel(String name, int stackSize, double weight) {
        super(name, stackSize, weight);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(worldIn.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

        BlockPos pos = playerIn.getPosition();
        IVessel vessel = itemstack.getCapability(VesselProvider.VESSEL_CAP, null);
        if(!vessel.hasMetal()) playerIn.openGui(Mythria.instance, MythriaGui.MYTHRIA_SMALL_VESSEL_GUI.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if(worldIn.isRemote) return;
        if(worldIn.getWorldTime() % 20 == 0) {
            IVessel vessel = stack.getCapability(VesselProvider.VESSEL_CAP, null);
            vessel.update(worldIn.getBiome(entityIn.getPosition()).getTemperature(entityIn.getPosition()), stack);
        }
    }
}
