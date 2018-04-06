package me.Jonathon594.Mythria.Entity.Magic;

import me.Jonathon594.Mythria.Client.Renderer.LightningRenderer;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityLightningJolt extends EntityMagicBolt {

    public static final DataParameter<NBTTagCompound> START_LOCATION = EntityDataManager.createKey(EntityLightningJolt.class, DataSerializers.COMPOUND_TAG);

    public EntityLightningJolt(World worldIn, EntityPlayerMP throwerIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        thrower = throwerIn;
    }

    public EntityLightningJolt(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    public EntityLightningJolt(final World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        NBTTagCompound comp = new NBTTagCompound();
        comp.setDouble("X", posX);
        comp.setDouble("Y", posY);
        comp.setDouble("Z", posZ);
        dataManager.register(START_LOCATION, comp);
    }

    @Override
    public void cast() {
        setStartLocation(getPositionVector());
        RayTraceResult result = MythriaUtil.RayTrace(getThrower(), 0, 60, true);
        Vec3d hit = result.hitVec;
        if (result.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            hit = new Vec3d(result.getBlockPos()).add(new Vec3d(0.5, 0.5, 0.5));
        }
        if (result.typeOfHit.equals(RayTraceResult.Type.ENTITY)) {
            damageEntity(result.entityHit);
            hit = result.entityHit.getPositionVector();
        }
        setPosition(hit.x, hit.y, hit.z);
    }

    @Override
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        // Nothing
    }

    @Override
    protected float getBoltVelocity() {
        return 4.5f;
    }

    @Override
    public void onUpdate() {
        if (ticksExisted > 3) kill();

        Vec3d cvec = getPositionVector().subtract(getStartLocation()).normalize();
        float cyaw = (float) MythriaUtil.getYawFromVec3d(cvec);
        float cpitch = (float) MythriaUtil.getPitchFromVec3d(cvec);
        LightningRenderer.renderLightningBolt(getStartLocation(), getPositionVector(), getEntityId(), getEntityWorld(), true, cyaw, cpitch);

        super.onUpdate();
    }

    public Vec3d getStartLocation() {
        NBTTagCompound comp = dataManager.get(START_LOCATION);
        Vec3d vec = new Vec3d(comp.getDouble("X"),
                comp.getDouble("Y"),
                comp.getDouble("Z"));
        return vec;
    }

    public void setStartLocation(Vec3d vec) {
        NBTTagCompound comp = new NBTTagCompound();
        comp.setDouble("X", vec.x);
        comp.setDouble("Y", vec.y);
        comp.setDouble("Z", vec.z);
        dataManager.set(START_LOCATION, comp);
    }

    @Override
    protected int getGravityTime() {
        return 0;
    }

    @Override
    protected float getGravityWhenFalling() {
        return 0f;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        // Nothing
    }

    @Override
    protected int getDamage() {
        return 0;
    }

    @Override
    protected int getFireSeconds() {
        return 1;
    }

    @Override
    protected SoundEvent getSpawnSound() {
        return SoundEvents.ENTITY_LIGHTNING_THUNDER;
    }

    @Override
    protected SoundEvent getLoopSound() {
        return SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE;
    }

    @Override
    protected int getLifeTime() {
        return 60;
    }
}
