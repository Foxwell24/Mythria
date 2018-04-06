package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Entity.EntityNPCPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIHurtByTargetNPC extends EntityAITarget {
    private final boolean entityCallsForHelp;
    /**
     * Store the previous revengeTimer value
     */
    private int revengeTimerOld;

    public EntityAIHurtByTargetNPC(final EntityCreature creatureIn, final boolean entityCallsForHelpIn) {
        super(creatureIn, true);
        entityCallsForHelp = entityCallsForHelpIn;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        final int i = taskOwner.getRevengeTimer();
        final EntityLivingBase entitylivingbase = taskOwner.getRevengeTarget();
        return i != revengeTimerOld && entitylivingbase != null && this.isSuitableTarget(entitylivingbase, false);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        taskOwner.setAttackTarget(taskOwner.getRevengeTarget());
        target = taskOwner.getAttackTarget();
        revengeTimerOld = taskOwner.getRevengeTimer();
        unseenMemoryTicks = 300;

        if (entityCallsForHelp)
            alertOthers();

        super.startExecuting();
    }

    protected void alertOthers() {
        final double d0 = getTargetDistance();

        for (final EntityCreature entitycreature : taskOwner.world.getEntitiesWithinAABB(taskOwner.getClass(),
                new AxisAlignedBB(taskOwner.posX, taskOwner.posY, taskOwner.posZ, taskOwner.posX + 1.0D,
                        taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D).grow(d0, 10.0D, d0)))
            if (taskOwner != entitycreature && entitycreature.getAttackTarget() == null
                    && (!(taskOwner instanceof EntityTameable)
                    || ((EntityTameable) taskOwner).getOwner() == ((EntityTameable) entitycreature).getOwner())
                    && !entitycreature.isOnSameTeam(taskOwner.getRevengeTarget()))
                if (entitycreature instanceof EntityNPCPlayer)
                    setEntityAttackTarget(entitycreature, taskOwner.getRevengeTarget());
    }

    protected void setEntityAttackTarget(final EntityCreature creatureIn, final EntityLivingBase entityLivingBaseIn) {
        creatureIn.setAttackTarget(entityLivingBaseIn);
    }
}