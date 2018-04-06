package me.Jonathon594.Mythria.Items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ItemFilledMold extends MythriaItem {

    private Item originalMold;
    private Item castResult;

    public ItemFilledMold(String name, double weight, Item originalMold, Item castResult) {
        super(name, 1, weight);
        this.originalMold = originalMold;
        this.castResult = castResult;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        // Separate mold
        ItemStack emptyMold = new ItemStack(originalMold, 1);
        ItemStack result = new ItemStack(castResult, 1);

        if(Math.random() < 0.85) {
            if (!playerIn.inventory.addItemStackToInventory(emptyMold)) {
                InventoryHelper.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, emptyMold);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, result);
    }

    public Item getCastResult() {
        return castResult;
    }

    public Item getOriginalMold() {
        return originalMold;
    }
}
