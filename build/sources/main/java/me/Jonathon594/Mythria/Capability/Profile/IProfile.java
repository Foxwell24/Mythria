package me.Jonathon594.Mythria.Capability.Profile;

import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.PersonallityTrait;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Enum.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public interface IProfile {
    PersonallityTrait getPersonality();

    void addAttribute(Perk attribute);

    void addHealthCondition(HealthConditionInstance hci);

    void addSkillExperience(MythicSkills type, double value, EntityPlayerMP p);

    void fromNBT(NBTTagCompound comp);

    List<Perk> getPlayerSkills();

    Date getBirthDay();

    void setBirthDay(Date date);

    HashMap<Consumable, Double> getConsumables();

    boolean getCreated();

    void setCreated(boolean created);

    String getFirstName();

    void setFirstName(String string);

    int getGender();

    void setGender(int i);

    ArrayList<HealthConditionInstance> getHealthConditions();

    String getLastName();

    void setLastName(String string);

    String getMiddleName();

    void setMiddleName(String string);

    int getAttributePoints();

    void setAttributePoints(int attributePoints);

    EntityPlayer getPlayer();

    void setPlayer(EntityPlayer player);

    int getPlayerLevel();

    UUID getProfileUUID();

    void setProfileUUID(UUID profileUUID);

    Integer getSkillLevel(final MythicSkills type);

    HashMap<MythicSkills, Double> getSkillLevels();

    boolean hasFlag(AttributeFlag firemaking1);

    void setConsumable(Consumable c, double v);

    int getAttribute(Attribute attribute);

    void syncToClient();

    NBTTagCompound toNBT();

    HashMap<Attribute, Integer> getAttributes();

    double getStat(StatType statType);

    void removeAttribute(Perk perk);
}
