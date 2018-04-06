package me.Jonathon594.Mythria.Items;

import net.minecraft.item.Item;

public class ItemPottery extends MythriaItem {
    private Item firingResult;

    public ItemPottery(String name, double weight, Item firingResult) {
        super(name, 1, weight);
        this.firingResult = firingResult;
    }

    public ItemPottery(String name, int stackSize, double weight, Item firingResult) {
        super(name, stackSize, weight);
        this.firingResult = firingResult;
    }

    public Item getFiringResult() {
        return firingResult;
    }
}
