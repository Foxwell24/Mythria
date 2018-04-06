package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWaterBolt extends EntityMagicBolt {

    public EntityWaterBolt(World worldIn, EntityPlayerMP throwerIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        thrower = throwerIn;
    }

    public EntityWaterBolt(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    @Override
    protected int getDamage() {
        return 3;
    }

    @Override
    protected PotionEffect getPotionEffect() {
        return new PotionEffect(Potion.getPotionById(2), 60, 1, false, false);
    }

    public EntityWaterBolt(final World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            if (Math.random() < 0.5)
                world.playSound(posX, posY, posZ, SoundEvents.BLOCK_WATER_AMBIENT,
                        SoundCategory.HOSTILE, 1.0f, 1.0f, true);
        }
    }

    @Override
    protected SoundEvent getLoopSound() {
        return SoundEvents.BLOCK_WATER_AMBIENT;
    }

    @Override
    protected SoundEvent getSpawnSound() {
        return SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    protected int getLifeTime() {
        return 240;
    }

    @Override
    protected void onImpact(final RayTraceResult result) {
        super.onImpact(result);
        if (result.entityHit != null)
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 2);

        if (!world.isRemote) {

        } else
            world.playSound(posX, posY, posZ, SoundEvents.ITEM_BUCKET_EMPTY,
                    SoundCategory.HOSTILE, 1.0f, 1.0f, true);
    }

    @Override
    protected float getGravityWhenFalling() {
        return 0.08f;
    }

    @Override
    protected int getGravityTime() {
        return 40;
    }
}
