package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Managers.CastingManager;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMoldEmpty extends MythriaItem {

    private final SmithingManager.EnumMetalShape moldType;
    private int metalRequired;

    public ItemMoldEmpty(String name, double weight, int metalRequired, SmithingManager.EnumMetalShape moldType) {
        super(name, 1, weight);
        this.metalRequired = metalRequired;
        this.moldType = moldType;
    }

    public int getMetalRequired() {
        return metalRequired;
    }

    public SmithingManager.EnumMetalShape getMoldType() {
        return moldType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote) return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));

        IVessel vessel = null;
        for(int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
            ItemStack is = playerIn.inventory.getStackInSlot(i);

            if(is.hasCapability(VesselProvider.VESSEL_CAP, null)) {
                vessel = is.getCapability(VesselProvider.VESSEL_CAP, null);
                break;
            }
        }

        if(vessel == null || !vessel.hasMetal() || vessel.getMetalCount() < metalRequired) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }

        SmeltingRecipe.EnumMetal metal = vessel.getMetal();
        int meltingPoint = SmeltingManager.getRecipe(metal).getMeltingPoint();

        if(vessel.getTemp() < meltingPoint) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }

        ItemStack result = new ItemStack(CastingManager.getFilledMold(metal, moldType), 1);
        if(result == null || result.isEmpty()) return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        vessel.setMetalCount(vessel.getMetalCount() - metalRequired);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, result);
    }
}
