package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EntityStorm extends Entity {
    public EntityStorm(World worldIn) {
        super(worldIn);
    }

    public long getLifeTime() {
        return 1200;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(!world.isRemote) {
            if (ticksExisted > getLifeTime()) {
                world.setEntityState(this, (byte) 3);
                setDead();
            }
        }
    }

    public abstract void onSpawn();
}
