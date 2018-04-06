package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFireBolt extends EntityMagicBolt {

    public EntityFireBolt(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    public EntityFireBolt(final World worldIn) {
        super(worldIn);
    }

    public EntityFireBolt(World worldIn, EntityLivingBase throwerIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        thrower = throwerIn;
    }

    @Override
    protected int getFireSeconds() {
        return 3;
    }

    @Override
    protected int getDamage() {
        return 2;
    }

    @Override
    protected SoundEvent getLoopSound() {
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }

    @Override
    protected SoundEvent getSpawnSound() {
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }

    @Override
    protected int getLifeTime() {
        return 60;
    }

    @Override
    protected float getGravityWhenFalling() {
        return 0f;
    }

    @Override
    protected int getGravityTime() {
        return 0;
    }
}
