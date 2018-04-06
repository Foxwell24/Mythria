package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.RenderState;

public class AbilityRenderData {
    private final String abilityName;
    private final RenderState state;
    private final int entityID;

    public AbilityRenderData(final RenderState state, final int entityID, final String abilityName) {
        super();
        this.state = state;
        this.entityID = entityID;
        this.abilityName = abilityName;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public int getEntityID() {
        return entityID;
    }

    public RenderState getState() {
        return state;
    }
}
