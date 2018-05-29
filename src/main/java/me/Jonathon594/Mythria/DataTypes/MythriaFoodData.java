package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.item.Item;

public class MythriaFoodData {
    private final Item item;
    private final double maxLife;

    public MythriaFoodData(final Item item, final double maxLife) {
        super();
        this.item = item;
        this.maxLife = maxLife;
    }

    public Item getItem() {
        return item;
    }

    public double getMaxLife() {
        return maxLife;
    }
}
