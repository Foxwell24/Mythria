package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Entity.Magic.EntitySingularitySpell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemWand extends MythriaItem {
    public ItemWand(String name, double weight) {
        super(name, weight);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

        EntitySingularitySpell curse = new EntitySingularitySpell(entityLiving.world, entityLiving);
        curse.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0f, 2f, 0f);
        entityLiving.world.spawnEntity(curse);

        return true;
    }
}
