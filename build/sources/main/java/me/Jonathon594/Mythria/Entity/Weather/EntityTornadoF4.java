package me.Jonathon594.Mythria.Entity.Weather;

import net.minecraft.world.World;

public class EntityTornadoF4 extends EntityTornado {
    public EntityTornadoF4(World worldIn) {
        this(worldIn, 0, 0.08f);
    }

    @Override
    public int getTier() {
        return 3;
    }

    public EntityTornadoF4(World worldIn, float heading, float speed) {
        super(worldIn, speed);

        setSize(getWidth(getTier()), getHeight(getTier()));
    }
}
