package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.MagicalAffinity;
import net.minecraft.entity.player.EntityPlayer;

public interface IExperimentableMagic {
    void failCast(EntityPlayer player);

    IAbility getAbility();

    MagicalAffinity getAffinity();

    int getConcentrationLevel();

    int getEnlightenmentLevel();

    void wildCast(EntityPlayer player);

    String getRequiredAttribute();
}
