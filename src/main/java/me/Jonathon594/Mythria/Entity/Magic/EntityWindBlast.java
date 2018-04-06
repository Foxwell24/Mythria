package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityWindBlast extends EntityMagicBolt {

    public EntityWindBlast(World worldIn, EntityPlayerMP throwerIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        thrower = throwerIn;
    }

    @Override
    protected int getDamage() {
        return 1;
    }

    public EntityWindBlast(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    @Override
    protected SoundEvent getLoopSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEvent getSpawnSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    public EntityWindBlast(final World worldIn) {
        super(worldIn);
    }

    @Override
    protected int getLifeTime() {
        return 40;
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
