package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.world.World;

public abstract class EntityMagicProjectile extends EntityThrowable {

    public EntityMagicProjectile(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityMagicProjectile(World worldIn) {
        super(worldIn);
    }
}
