package me.Jonathon594.Mythria.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemToolHead extends MythriaItem {
    private Item result;

    public ItemToolHead(String name, int weight, Item result) {
        super(name, 1, weight);
        this.result = result;
        this.setMaxDamage(result.getMaxDamage());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote) return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));

        for(int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
            ItemStack is = playerIn.inventory.getStackInSlot(i);
            if(is.getItem().equals(MythriaItems.TOOL_HANDLE)) {
                is.shrink(1);
                ItemStack r = new ItemStack(result, 1);
                r.setItemDamage(playerIn.getHeldItem(handIn).getItemDamage());
                ItemStack head = playerIn.getHeldItem(handIn);
                if(head.getTagCompound() != null) {
                    NBTTagCompound comp = head.getTagCompound().getCompoundTag("display");
                    NBTTagCompound n = new NBTTagCompound();
                    n.setTag("display", comp);
                    r.setTagCompound(n);
                }
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, r);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
