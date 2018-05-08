package me.Jonathon594.Mythria.Entity.Weather;

import me.Jonathon594.Mythria.Managers.SoundManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityEarthQuake extends EntityStorm {
    public static final DataParameter<Float> MAGNITUDE = EntityDataManager.createKey(EntityEarthQuake.class, DataSerializers.FLOAT);

    public EntityEarthQuake(World worldIn) {
        this(worldIn, 0);
        if(worldIn.isRemote) {
            applyRandomMagnitude();
        }
    }

    public EntityEarthQuake(World worldIn, float mag) {
        super(worldIn);
        setMagnitude(mag);
    }

    public void applyRandomMagnitude() {
        double rand = Math.random();
        float mag = 0;
        if(rand == 0) mag = 10f;
        else {
            mag = (float) Math.min(1 / rand, 10);
        }
        mag = (float) MythriaUtil.round(mag, 1);
        setMagnitude(mag);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(world.isRemote) return;
        if(world.getWorldTime() % 5 == 0) {
            double range = 128 * (getMagnitude() * 128);
            for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(-range, -range, -range, range, range, range).offset(getPositionVector()))) {
                double a = Math.random() * Math.PI * 2;
                double x = Math.cos(a);
                double z = Math.sin(a);
                e.addVelocity(x, 0, z);
                e.velocityChanged = true;
                if(e instanceof EntityPlayerMP) {
                    SoundManager.playForPlayerOnly((EntityPlayerMP) e, SoundEvents.ENTITY_GENERIC_EXPLODE, 1f);
                }
            }
        }
    }

    public void setMagnitude(float magnitude) {
        dataManager.set(MAGNITUDE, magnitude);
    }

    public float getMagnitude() {
        return dataManager.get(MAGNITUDE);
    }

    @Override
    protected void entityInit() {
        dataManager.register(MAGNITUDE, 0f);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        dataManager.set(MAGNITUDE, compound.getFloat("magnitude"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setFloat("magnitude", dataManager.get(MAGNITUDE));
    }
}
