package me.Jonathon594.Mythria.Items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SubItemPropertyGetter implements IItemPropertyGetter {
    @Override
    public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
        // TODO Auto-generated method stub
        return stack.getItemDamage();
    }
}
