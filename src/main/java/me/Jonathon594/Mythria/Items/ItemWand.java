package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Entity.Magic.EntityFireBolt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemWand extends MythriaItem {

    public ItemWand(final String name, double weight) {
        super(name, weight);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if(entityLiving.world.isRemote) return false;

        EntityFireBolt bolt = new EntityFireBolt(entityLiving.world, entityLiving, entityLiving.posX, entityLiving.posY + 1, entityLiving.posZ);
        bolt.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0f, 0.5f, 0.1f);
        entityLiving.world.spawnEntity(bolt);
        return true;
    }
}
