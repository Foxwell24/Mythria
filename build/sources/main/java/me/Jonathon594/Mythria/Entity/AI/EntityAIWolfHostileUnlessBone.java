package me.Jonathon594.Mythria.Entity.AI;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;

import javax.annotation.Nullable;

public class EntityAIWolfHostileUnlessBone<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
    private final EntityWolf wolf;
    private final int targetChance = 20;

    public EntityAIWolfHostileUnlessBone(final EntityWolf wolf, final Class<T> classTarget) {
        super(wolf, classTarget, 20, true, true, null);
        this.wolf = wolf;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && taskOwner.getRNG().nextInt(this.targetChance) != 0)
            return false;
        targetEntity = (T) taskOwner.world.getNearestAttackablePlayer(taskOwner.posX,
                taskOwner.posY + taskOwner.getEyeHeight(), taskOwner.posZ,
                getTargetDistance(), getTargetDistance(), new Function<EntityPlayer, Double>() {
                    @Override
                    @Nullable
                    public Double apply(@Nullable final EntityPlayer p_apply_1_) {
                        return 1.0D;
                    }
                }, (Predicate<EntityPlayer>) targetEntitySelector);
        if (targetEntity == null) return false;
        return (targetEntity.getHeldItem(EnumHand.MAIN_HAND) == null
                || !targetEntity.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(Items.BONE)) && !wolf.isTamed();
    }
}
