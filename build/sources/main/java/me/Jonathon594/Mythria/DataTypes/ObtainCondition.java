package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.ObtainConditionType;

public class ObtainCondition {
    private final ObtainConditionType type;
    private final double threshhold;
    private final double chance;
    private final Object object;

    public ObtainCondition(final ObtainConditionType type, final double threshhold, final double chance,
                           final Object object) {
        super();
        this.type = type;
        this.threshhold = threshhold;
        this.chance = chance;
        this.object = object;
    }

    public double getChance() {
        return chance;
    }

    public Object getObject() {
        return object;
    }

    public double getThreshhold() {
        return threshhold;
    }

    public ObtainConditionType getType() {
        return type;
    }
}
