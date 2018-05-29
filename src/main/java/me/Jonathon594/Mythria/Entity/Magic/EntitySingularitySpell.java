package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySingularitySpell extends EntityMagicProjectile {

    private double range = 32;

    public EntitySingularitySpell(World worldIn) {
        super(worldIn);
    }

    public EntitySingularitySpell(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        motionX *= 0.95;
        motionZ *= 0.95;
        motionY *= 0.95;

        if(world.isRemote) return;

        if(ticksExisted > 20 * 30) {
            world.setEntityState(this, (byte) 3);
            setDead();
            return;
        }

        for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(-range, -range, -range, range, range, range).offset(getPositionVector()))) {
            if(e.equals(getThrower())) continue;
            Vec3d targetVector = getPositionVector().subtract(e.getPositionVector()).normalize().scale(0.1);
            e.addVelocity(targetVector.x, targetVector.y, targetVector.z);
            e.velocityChanged=true;
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
