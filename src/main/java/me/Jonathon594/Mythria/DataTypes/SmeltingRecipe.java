package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

public class SmeltingRecipe {
    private final int difficulty;

    private EnumMetal metal;

    private HashMap<Item, Double> getRecipe() {
        return recipe;
    }

    private HashMap<Item, Double> recipe = new HashMap<>();

    private int meltingPoint;

    public SmeltingRecipe(int difficulty, EnumMetal metal, int meltingPoint, SmeltingRecipePair... items) {
        this.metal = metal;
        this.meltingPoint = meltingPoint;
        double p = 0;

        for(SmeltingRecipePair entry : items) {
            recipe.put(entry.getItem(), entry.getProp());
            p+=entry.getProp();
        }

        this.difficulty = difficulty;

        if (p != 1) {
            System.out.println("INVALID SMELTING RECIPE");
            return;
        }

        SmeltingManager.addRecipe(this);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean matches(IItemHandler items) {
        HashMap<Item, Integer> vesselContents = new HashMap<>();
        for(int i = 0; i < items.getSlots(); i++) {
            ItemStack is = items.getStackInSlot(i);
            if(vesselContents.containsKey(is.getItem())) {
                vesselContents.put(is.getItem(), vesselContents.get(is.getItem()) + is.getCount());
            } else {
                vesselContents.put(is.getItem(), is.getCount());
            }
        }

        for(Map.Entry<Item, Double> entry : recipe.entrySet()) {
            if (!vesselContents.containsKey(entry.getKey())) return false;
            int vesselCount = vesselContents.get(entry.getKey());
            int total = items.getSlots()-1;
            double prop = (double) vesselCount / (double) total;
            double variance = (4-difficulty) * 0.025;
            if(prop < entry.getValue() - variance || prop > entry.getValue() + variance) return false;
        }

        return true;
    }

    public EnumMetal getMetal() {
        return metal;
    }

    public int getMeltingPoint() {
        return meltingPoint;
    }

    public enum EnumMetal {
        SILVER, STEEL, IRON, COPPER, GOLD, BRONZE
    }

    public static class SmeltingRecipePair {
        private Item item;
        private double prop;

        public SmeltingRecipePair(Item item, double prop) {

            this.item = item;
            this.prop = prop;
        }

        public Item getItem() {
            return item;
        }

        public double getProp() {
            return prop;
        }
    }
}
