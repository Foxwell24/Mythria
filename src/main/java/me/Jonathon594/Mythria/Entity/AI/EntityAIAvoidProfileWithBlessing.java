package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Capability.DeityFavor.DeityFavorProvider;
import me.Jonathon594.Mythria.Capability.DeityFavor.IDeityFavor;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.BlessingType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIAvoidProfileWithBlessing<T extends Entity> extends EntityAIAvoidEntity {
    private BlessingType blessingType;

    public EntityAIAvoidProfileWithBlessing(EntityCreature entityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, BlessingType blessingType) {
        super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);

        this.blessingType = blessingType;
    }

    @Override
    public boolean shouldExecute() {
        boolean execute = super.shouldExecute();
        if(closestLivingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) closestLivingEntity;
            IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
            if(df.hasBlessing(blessingType)) {
                return true;
            }
        }
        return false;
    }
}
