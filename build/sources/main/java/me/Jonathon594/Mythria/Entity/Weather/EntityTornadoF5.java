package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.world.World;

public class EntityTornadoF5 extends EntityTornado {
    public EntityTornadoF5(World worldIn) {
        this(worldIn, 0, 0.05f);
    }

    @Override
    public int getTier() {
        return 4;
    }

    public EntityTornadoF5(World worldIn, float heading, float speed) {
        super(worldIn, speed);

        setSize(getWidth(getTier()), getHeight(getTier()));
    }
}
