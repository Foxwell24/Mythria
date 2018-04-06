package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIAvoidProfileWithFlag<T extends Entity> extends EntityAIAvoidEntity {
    private AttributeFlag flag;

    public EntityAIAvoidProfileWithFlag(EntityCreature entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, AttributeFlag flag) {
        super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);

        this.flag = flag;
    }

    @Override
    public boolean shouldExecute() {
        boolean execute = super.shouldExecute();
        if(closestLivingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) closestLivingEntity;
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            if(profile.hasFlag(flag)) {
                return true;
            }
        }
        return false;
    }
}
