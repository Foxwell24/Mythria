package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.world.World;

public class EntityTornadoF2 extends EntityTornado {
    public EntityTornadoF2(World worldIn) {
        this(worldIn, 0, 0.15f);
    }

    @Override
    public int getTier() {
        return 1;
    }

    public EntityTornadoF2(World worldIn, float heading, float speed) {
        super(worldIn, speed);

        setSize(getWidth(getTier()), getHeight(getTier()));
    }
}
