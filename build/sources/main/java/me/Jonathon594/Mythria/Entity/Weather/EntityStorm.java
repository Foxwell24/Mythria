package me.Jonathon594.Mythria.Entity.Weather;

import me.Jonathon594.Mythria.Enum.Season;
import me.Jonathon594.Mythria.Managers.WeatherManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

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
