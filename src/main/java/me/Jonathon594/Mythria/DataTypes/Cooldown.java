package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.entity.player.EntityPlayer;

public class Cooldown {
    public CooldownType getType() {
        return type;
    }

    public long getEndTime() {
        return endTime;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public Cooldown(CooldownType type, long endTime, EntityPlayer player) {
        this.type = type;
        this.endTime = endTime;
        this.player = player;
    }

    private CooldownType type;
    private long endTime;
    private EntityPlayer player;

    public enum CooldownType {
        EARTH_CRUMPLE
    }
}
