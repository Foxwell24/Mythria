package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.HealthConditionTree;
import me.Jonathon594.Mythria.Enum.HealthProgressionType;
import me.Jonathon594.Mythria.Enum.ObtainConditionType;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Managers.HealthManager;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthCondition {
    private final String name;
    private final HashMap<StatType, Double> statModifiers = new HashMap<>();
    private final int duration;
    private final boolean curable;
    private final int cureTime;
    private final Item menuIcon;
    private final ArrayList<ObtainCondition> obtainConditions = new ArrayList<>();
    private final HealthConditionTree tree;
    private HealthProgressionType progressionType = HealthProgressionType.CURE;
    private String progressionTarget = "";
    private ArrayList<PotionEffect> potionEffects = new ArrayList<>();
    private ArrayList<CureCondition> cureConditions = new ArrayList<CureCondition>();

    private HealthCondition addCureConditions (CureCondition... conditions) {
        for(CureCondition c : conditions) {
            cureConditions.add(c);
        }
        return this;
    }

    public HealthCondition addPotionEffect(PotionEffect pe) {
        potionEffects.add(pe);
        return this;
    }

    public HealthCondition(final String name, final int duration, final boolean curable, final int cureTime,
                           final Item menuIcon, final HealthConditionTree tree) {
        super();
        HealthManager.addHealhCondition(this);
        this.name = name;
        this.duration = duration;
        this.curable = curable;
        this.cureTime = cureTime;
        this.menuIcon = menuIcon;
        this.tree = tree;
    }

    public HealthCondition addObtainCondition(final ObtainCondition oc) {
        obtainConditions.add(oc);
        return this;
    }

    public HealthCondition addStatModifier(final StatType s, final double v) {
        statModifiers.put(s, v);
        return this;
    }

    public int getCureTime() {
        return cureTime;
    }

    public int getDuration() {
        return duration;
    }

    public Item getMenuIcon() {
        return menuIcon;
    }

    public String getName() {
        return name;
    }

    public ObtainCondition getObtainCondition(final ObtainConditionType type) {
        for (final ObtainCondition oc : obtainConditions)
            if (oc.getType().equals(type))
                return oc;
        return null;
    }

    public String getProgressionTarget() {
        return progressionTarget;
    }

    public HealthCondition setProgressionTarget(final String progressionTarget) {
        this.progressionTarget = progressionTarget;
        return this;
    }

    public HealthProgressionType getProgressionType() {
        return progressionType;
    }

    public HealthCondition setProgressionType(final HealthProgressionType progressionType) {
        this.progressionType = progressionType;
        return this;
    }

    public HashMap<StatType, Double> getStatModifiers() {
        return statModifiers;
    }

    public HealthConditionTree getTree() {
        return tree;
    }

    public boolean isCurable() {
        return curable;
    }

    public ArrayList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public ArrayList<CureCondition> getCureConditions() {
        return cureConditions;
    }
}
