package me.Jonathon594.Mythria.Entity.Magic;

import me.Jonathon594.Mythria.Interface.ILargeMagicAbility;
import me.Jonathon594.Mythria.Interface.ISmallMagicAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityMagicBolt extends EntityThrowable implements ISmallMagicAbility {
    public EntityMagicBolt(final World worldIn) {
        super(worldIn);
    }


    @Override
    public boolean shouldRenderInPass(int pass) {
        return super.shouldRenderInPass(pass);
    }

    public void cast() {
        shoot(getThrower(), getThrower().rotationPitch, getThrower().rotationYaw, 0, 1.5f, 0.0f);
    }

    @Override
    public void shoot(final Entity entityThrower, final float rotationPitchIn, final float rotationYawIn,
                      final float pitchOffset, final float velocity, final float inaccuracy) {
        final float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        final float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        final float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot(f, f1, f2, getBoltVelocity(), inaccuracy);
    }

    protected float getBoltVelocity() {
        return 1.5f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (getThrower() == null) kill();
        if (isInWater()) kill();

        if (!world.isRemote) {
            final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D));
            for (int i = 0; i < list.size(); i++) {
                final Entity e = list.get(i);
                if (e instanceof ISmallMagicAbility)
                    hitSmallAbility();
                else if (e instanceof ILargeMagicAbility)
                    hitLargeAbility();
            }
        } else {
            if (ticksExisted == 1) {
                world.playSound(posX, posY, posZ, getSpawnSound(), SoundCategory.HOSTILE, 1f, 1f, true);
            } else {
                if (Math.random() < 0.1)
                    world.playSound(posX, posY, posZ, getLoopSound(),
                            SoundCategory.HOSTILE, 1.0f, 1.0f, true);
            }
        }
    }

    @Override
    protected float getGravityVelocity() {
        return (ticksExisted > getGravityTime()) ? getGravityWhenFalling() : 0f;
    }

    protected abstract int getGravityTime();

    protected abstract float getGravityWhenFalling();

    @Override
    protected void onImpact(final RayTraceResult result) {
        if (result.typeOfHit.equals(RayTraceResult.Type.ENTITY)) {
            damageEntity(result.entityHit);
        }

        if (!world.isRemote)
            kill();
    }

    protected void damageEntity(Entity entityHit) {
        entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), getDamage());
        if (getFireSeconds() > 0) entityHit.setFire(getFireSeconds());
        PotionEffect pe = getPotionEffect();
        if (pe != null) {
            if (entityHit instanceof EntityLivingBase) {
                EntityLivingBase eb = (EntityLivingBase) entityHit;
                eb.addPotionEffect(pe);
            }
        }
    }

    protected abstract int getDamage();

    protected int getFireSeconds() {
        return 0;
    }

    protected PotionEffect getPotionEffect() {
        return null;
    }

    public void kill() {
        if (!world.isRemote) {
            world.setEntityState(this, (byte) 3);
            setDead();
        }
    }

    private void hitSmallAbility() {
        kill();
    }

    private void hitLargeAbility() {
        kill();
    }

    protected abstract SoundEvent getSpawnSound();

    protected abstract SoundEvent getLoopSound();

    protected abstract int getLifeTime();
}


