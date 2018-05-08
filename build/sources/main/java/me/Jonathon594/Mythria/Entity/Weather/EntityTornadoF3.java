package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.world.World;

public class EntityTornadoF3 extends EntityTornado {
    public EntityTornadoF3(World worldIn) {
        this(worldIn, 0, 0.1f);
    }

    @Override
    public int getTier() {
        return 2;
    }

    public EntityTornadoF3(World worldIn, float heading, float speed) {
        super(worldIn, speed);

        setSize(getWidth(getTier()), getHeight(getTier()));
    }
}
