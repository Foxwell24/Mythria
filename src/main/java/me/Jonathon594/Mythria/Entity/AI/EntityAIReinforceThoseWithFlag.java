package me.Jonathon594.Mythria.Entity.AI;

import ibxm.Player;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class EntityAIReinforceThoseWithFlag extends EntityAITarget {
    private final AttributeFlag flag;
    EntityCreature creature;
    EntityLivingBase attacker;
    EntityLivingBase defending;
    private int timestamp;

    public EntityAIReinforceThoseWithFlag(EntityCreature entityIn, AttributeFlag flagIn) {
        super(entityIn, false);
        this.creature = entityIn;
        this.flag = flagIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        List<EntityPlayer> nearby = creature.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(-10,-10,-10,10,10,10).offset(creature.getPosition()));
        for(EntityPlayer player : nearby) {
            EntityLivingBase entitylivingbase = player;
            if(!player.getCapability(ProfileProvider.PROFILE_CAP, null).hasFlag(flag)) continue;

            if (entitylivingbase == null) {
                return false;
            } else {
                this.attacker = entitylivingbase.getLastAttackedEntity();
                this.defending = entitylivingbase;
                int i = entitylivingbase.getLastAttackedEntityTime();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false);
            }
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.attacker);

        if (defending != null) {
            this.timestamp = defending.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
