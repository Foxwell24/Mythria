package me.Jonathon594.Mythria.Capability.Profile;

import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.PersonallityTrait;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Managers.StatManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.*;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class Profile implements IProfile {

    private final List<Perk> attributes = new ArrayList<>();
    private final HashMap<MythicSkills, Double> skillLevels = new HashMap<>();
    private final HashMap<Consumable, Double> consumables = new HashMap<>();
    private final ArrayList<HealthConditionInstance> healthConditions = new ArrayList<>();
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private Date birthDay = new Date();
    private int gender = 0;
    private boolean created = false;
    private EntityPlayer player;
    private int attributePoints = 1;
    private UUID profileUUID;
    private HashMap<Attribute, Integer> attributeValues = new HashMap<>();

    public Profile() {
        for (final MythicSkills sk : MythicSkills.values())
            skillLevels.put(sk, (double) 0);

        for (final Attribute a : Attribute.values())
            attributeValues.put(a, 0);
        for (final Consumable s : Consumable.values())
            consumables.put(s, 0.0);
    }

    @Override
    public PersonallityTrait getPersonality() {
        for (Perk pa : attributes) {
            if (pa instanceof PersonallityTrait) return (PersonallityTrait) pa;
        }
        return null;
    }

    @Override
    public void addAttribute(final Perk attribute) {
        if (attribute == null)
            return;
        if (attributes.contains(attribute))
            return;
        if(attribute instanceof PersonallityTrait) {
            if(getPersonality() == null) {
                PersonallityTrait pers = (PersonallityTrait) attribute;
                for(Entry<Attribute, Integer> e : pers.getStartingAttributes().entrySet()) {
                    attributeValues.put(e.getKey(), e.getValue());
                }
            }
        }
        attributes.add(attribute);
        if (player == null)
            return;
        if (player.world.isRemote)
            return;

        syncToClient();
    }

    @Override
    public void addHealthCondition(final HealthConditionInstance hci) {
        for (final HealthConditionInstance h : healthConditions)
            if (h.getCondition().getTree().equals(hci.getCondition().getTree())) {
                h.setCureStep(0);
                return;
            }
        healthConditions.add(hci);
        if (player == null)
            return;
        if (player.world.isRemote)
            return;
        MythriaPacketHandler.INSTANCE.sendTo(new SPacketAddHealthConditionInstance(hci), (EntityPlayerMP) player);
    }

    @Override
    public void addSkillExperience(final MythicSkills type, double value, final EntityPlayerMP p) {
        if (type == null)
            return;
        value *= getStat(StatType.XP_RATE);
        final int ol = getPlayerLevel();
        final int l = MythriaUtil.getLevelForXP(skillLevels.get(type));
        skillLevels.put(type, skillLevels.get(type) + value);
        MythriaPacketHandler.INSTANCE.sendTo(new SPacketUpdateExperience(type, skillLevels.get(type)), p);
        final int lb = getSkillLevel(type);
        if (lb > l)
            if (p != null) {
                p.sendMessage(new TextComponentString(
                        String.format(MythriaConst.LEVE_UP_SKILL, type.name(), "(" + l + "->" + lb + ")")));
                final int nl = getPlayerLevel();
                if (nl > ol) {
                    p.sendMessage(new TextComponentString(String.format(MythriaConst.PLAYER_LEVEL_UP, nl + ".")));
                    attributePoints++;
                }
            }

    }

    @Override
    public void fromNBT(final NBTTagCompound comp) {
        MythriaUtil.ProfileFromNBTForLoading(this, comp);
    }

    @Override
    public List<Perk> getPlayerSkills() {
        return attributes;
    }

    @Override
    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public void setBirthDay(final Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public HashMap<Consumable, Double> getConsumables() {
        return consumables;
    }

    @Override
    public boolean getCreated() {
        return created;
    }

    @Override
    public void setCreated(final boolean created) {
        this.created = created;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int getGender() {
        return gender;
    }

    @Override
    public void setGender(final int gender) {
        this.gender = gender;
    }

    @Override
    public ArrayList<HealthConditionInstance> getHealthConditions() {
        return healthConditions;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    @Override
    public int getAttributePoints() {
        return attributePoints;
    }

    @Override
    public void setAttributePoints(final int attributePoints) {
        this.attributePoints = attributePoints;
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(final EntityPlayer player) {
        this.player = player;
    }

    @Override
    public int getPlayerLevel() {
        int totalLevels = 0;
        for (final Entry<MythicSkills, Double> e : skillLevels.entrySet())
            totalLevels += MythriaUtil.getLevelForXP(e.getValue());
        int newLevel = 0;
            newLevel = (int) Math.floor(totalLevels / 10);
        return newLevel;
    }

    @Override
    public UUID getProfileUUID() {
        return profileUUID;
    }

    @Override
    public void setProfileUUID(final UUID profileUUID) {
        this.profileUUID = profileUUID;
    }

    @Override
    public Integer getSkillLevel(final MythicSkills type) {
        return MythriaUtil.getLevelForXP(skillLevels.get(type));
    }

    @Override
    public HashMap<MythicSkills, Double> getSkillLevels() {
        return skillLevels;
    }

    @Override
    public boolean hasFlag(final AttributeFlag flag) {
        if(player == null) return false;
        if(DeityManager.getFlagsGrantedByDeity(DeityManager.getSelectedDeities().get(player.getEntityId())).contains(flag)) return true;
        for (final Perk pa : attributes) {
            if (pa == null)
                continue;
            if (pa.getAttributeFlags().contains(flag))
                return true;
        }
        return false;
    }

    @Override
    public void setConsumable(final Consumable t, double v) {
        final double old = v;
        if (!created)
            return;
        switch (t) {
            case HUNGER:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case STAMINA:
                v = MathHelper.clamp(v, 0, getStat(StatType.MAX_STAMINA));
                consumables.put(t, v);
                break;
            case TEMPERATURE:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case THIRST:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
            case WEIGHT:
                v = MathHelper.clamp(v, 0, Double.MAX_VALUE);
                consumables.put(t, v);
                StatManager.UpdateSpeed(this, player, old);
                break;
            case FATIGUE:
                v = MathHelper.clamp(v, 0, 0.9);
                consumables.put(t, v);
                StatManager.UpdateSpeed(this, player, old);
                break;
            case PAIN:
                v = MathHelper.clamp(v, 0, 20);
                consumables.put(t, v);
                break;
        }
        if (player == null)
            return;
        if (!player.world.isRemote)
            MythriaPacketHandler.INSTANCE.sendTo(new SPacketUpdateConsumables(t, v), (EntityPlayerMP) player);
    }

    public double getStat(StatType statType) {
        double modifier = 0;
        for(HealthConditionInstance hci : healthConditions) {
            if(hci.getCondition().getStatModifiers().containsKey(statType)) {
                double v = hci.getCondition().getStatModifiers().get(statType);
                modifier += v;
            }
        }

        switch(statType) {
            case MAX_STAMINA:
                return modifier + 100 + getAttribute(Attribute.ENDURANCE) * 10;
            case MAX_SPEED:
                return  modifier + getAttribute(Attribute.DEXTERITY) * 0.0005;
            case MAX_WEIGHT:
                return modifier + 100 + getAttribute(Attribute.STRENGTH) * 5;
            case MAX_HEALTH:
                return modifier + getAttribute(Attribute.VIGOR) / 2;
            case XP_RATE:
                return modifier + 0.75 + getAttribute(Attribute.INTELLIGENCE) * 0.01;
        }
        return 0;
    }

    @Override
    public void removeAttribute(Perk perk) {
        attributes.remove(perk);

        if(perk instanceof PersonallityTrait) {
            if(getPersonality() == null) {
                PersonallityTrait pers = (PersonallityTrait) perk;
                for(Entry<Attribute, Integer> e : pers.getStartingAttributes().entrySet()) {
                    attributeValues.put(e.getKey(), attributeValues.get(e.getKey()) - e.getValue());
                }
            }
        }

        if (player == null)
            return;
        if (player.world.isRemote)
            return;

        syncToClient();
    }

    @Override
    public int getAttribute(Attribute attribute) {
        return attributeValues.get(attribute);
    }

    @Override
    public void syncToClient() {
        MythriaPacketHandler.INSTANCE.sendTo(new ClientProfileDataCachePacket(toNBT()), (EntityPlayerMP) player);
    }

    @Override
    public NBTTagCompound toNBT() {
        return MythriaUtil.ProfileToNBTForSaving(this);
    }

    public HashMap<Attribute, Integer> getAttributes() {
        return attributeValues;
    }
}
