package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.world.World;

public class EntityTornadoF1 extends EntityTornado {
    public EntityTornadoF1(World worldIn) {
        this(worldIn, 0, 0.2f);
    }

    @Override
    public int getTier() {
        return 0;
    }

    public EntityTornadoF1(World worldIn, float heading, float speed) {
        super(worldIn, speed);

        setSize(getWidth(getTier()), getHeight(getTier()));
    }
}
