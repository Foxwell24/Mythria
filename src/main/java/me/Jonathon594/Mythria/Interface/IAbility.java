package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Enum.AbilityActivateType;
import me.Jonathon594.Mythria.Enum.AbilityPhase;
import me.Jonathon594.Mythria.Enum.AbilityType;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;

public interface IAbility {
    AbilityActivateType getActivationType();

    boolean Activate(EntityPlayer player, IProfile profile);

    void addSkillExperience(IProfile profile, EntityPlayer player);

    IAbility createNewInstance(EntityPlayer player, IProfile profile);

    void Deactivate(EntityPlayer player, final IProfile profile);

    AbilityType getAbilityType();

    long getCooldown();

    String getName();

    HashMap<Consumable, Double> getRequirements();

    boolean isMarkedForRemoval();

    boolean isPassive();

    boolean isStackable();

    void markForRemove();

    void Update(EntityPlayer player, IProfile profile);

    AbilityPhase getPhase();
    void setPhase(AbilityPhase phase);

    void playDeactivateSound();

    void playActivateSound();

    void localRender(EntityPlayer player);

    void ServerFunction(int ordinal, EntityPlayerMP player, Entity e, double x, double y, double z);
}
