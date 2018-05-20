package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class DAHeal extends DeityAbilityPotionEffect {
    public DAHeal() {
        super("Heal", 8, Deity.FELIXIA);
    }

    @Override
    public PotionEffect getPotionEffect() {
        return new PotionEffect(MobEffects.REGENERATION, 100, 2, false, false);
    }
}
