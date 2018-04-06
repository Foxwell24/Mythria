package me.Jonathon594.Mythria.Entity.Weather;


import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Tornado extends Entity {
    private float heading = 0f;
    private float speed = 0.1f;

    public Tornado(World worldIn, float heading, float speed) {
        super(worldIn);
        this.heading = heading;
        this.speed = speed;
    }

    public Tornado(World worldIn) {
        this(worldIn, 0, 0f);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        heading = compound.getFloat("heading");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setDouble("heading", heading);
    }
}
