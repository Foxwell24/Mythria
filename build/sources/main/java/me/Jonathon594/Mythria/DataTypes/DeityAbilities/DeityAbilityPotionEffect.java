package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public abstract class DeityAbilityPotionEffect extends DeityAbility {
    protected DeityAbilityPotionEffect(String name, int cost, Deity owner) {
        super(name, cost, owner);
    }

    @Override
    public boolean execute(EntityPlayer player, EntityPlayer target) {
        if(target == null) return false;

        target.addPotionEffect(getPotionEffect());
        return true;
    }

    public abstract PotionEffect getPotionEffect();
}
