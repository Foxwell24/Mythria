package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Entity.AI.EntityAIAvoidProfileWithFlag;
import me.Jonathon594.Mythria.Entity.AI.EntityAIReinforceThoseWithFlag;
import me.Jonathon594.Mythria.Entity.AI.EntityAIWolfHostileUnlessBone;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class VanillaEntityManager {

    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        final Entity e = event.getEntity();
        if (e instanceof EntityLivingBase) {
            final EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity instanceof EntityWolf)
                handleWolf((EntityWolf) entity);
            if (entity instanceof EntityCow || entity instanceof EntityChicken || entity instanceof EntityPig
                    || entity instanceof EntitySheep)
                handleFarmAnimal((EntityCreature) entity);
            
            if(MythriaUtil.isLilasiaMob(entity)) {
                handleLilasiaMob((EntityCreature) entity);
            }
        }
    }

    private static void handleLilasiaMob(EntityCreature entity) {
        entity.tasks.addTask(0, new EntityAIAvoidProfileWithFlag<>(entity, EntityPlayer.class, 6.0f, 1.0D, 1.2D, AttributeFlag.FELIXIA_INTIMIDATION));
        entity.targetTasks.addTask(0, new EntityAIReinforceThoseWithFlag(entity, AttributeFlag.LILASIA_REINFORCEMENTS));
    }

    private static void handleWolf(final EntityWolf entity) {
        entity.targetTasks.addTask(0, new EntityAIWolfHostileUnlessBone<>(entity, EntityPlayer.class));
    }

    private static void handleFarmAnimal(final EntityCreature entity) {
        entity.targetTasks.addTask(0, new EntityAIAvoidEntity<>(entity, EntityPlayer.class, 6.0f, 1.0D, 1.2D));
    }
}
