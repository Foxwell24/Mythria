package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.entity.player.EntityPlayer;

public abstract class DeityAbility {
    private String name;
    private int cost;
    private Deity owner;

    protected DeityAbility(String name, int cost, Deity owner) {
        this.name = name;
        this.cost = cost;
        this.owner = owner;
    }

    public boolean canChargeCost() {
        int currentPower = DeityManager.getDeityPower(getOwner());
        if(currentPower - getCost() < 0) return false;
        return true;
    }

    public void chargeCost() {
        int currentPower = DeityManager.getDeityPower(getOwner());
        DeityManager.setDeityPower(getOwner(), currentPower-getCost());
    }

    public abstract boolean execute(EntityPlayer player, EntityPlayer target);

    public Deity getOwner() {
        return owner;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
