package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Capability.DeityFavor.DeityFavorProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.BlessingType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class EntityAIReinforceThoseWithBlessing extends EntityAITarget {
    private final BlessingType blessingType;
    EntityCreature creature;
    EntityLivingBase attacker;
    EntityLivingBase defending;
    private int timestamp;

    public EntityAIReinforceThoseWithBlessing(EntityCreature entityIn, BlessingType flagIn) {
        super(entityIn, false);
        this.creature = entityIn;
        this.blessingType = flagIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        List<EntityPlayer> nearby = creature.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(-10,-10,-10,10,10,10).offset(creature.getPosition()));
        for(EntityPlayer player : nearby) {
            EntityLivingBase entitylivingbase = player;
            if(!player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null).hasBlessing(blessingType)) continue;

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
