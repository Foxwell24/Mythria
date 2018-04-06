package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class CastingManager {
    private static ArrayList<CastingRecipe> recipes = new ArrayList<>();

    public static void Initialize() {
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.AXE, MythriaItems.MOLD_AXE_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.DAGGER, MythriaItems.MOLD_DAGGER_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.HOE, MythriaItems.MOLD_HOE_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.PICK, MythriaItems.MOLD_PICK_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.SHOVEL, MythriaItems.MOLD_SHOVEL_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.SWORD, MythriaItems.MOLD_SWORD_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.HAMMER, MythriaItems.MOLD_HAMMER_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.CHISEL, MythriaItems.MOLD_CHISEL_BRONZE);
        new CastingRecipe(SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.SAW, MythriaItems.MOLD_SAW_BRONZE);

        new CastingRecipe(SmeltingRecipe.EnumMetal.IRON, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_IRON);
        new CastingRecipe(SmeltingRecipe.EnumMetal.STEEL, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_STEEL);
        new CastingRecipe(SmeltingRecipe.EnumMetal.COPPER, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_COPPER);
        new CastingRecipe(SmeltingRecipe.EnumMetal.SILVER, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_SILVER);
        new CastingRecipe(SmeltingRecipe.EnumMetal.GOLD, SmithingManager.EnumMetalShape.INGOT, MythriaItems.MOLD_INGOT_GOLD);
    }

    public static void addRecipe(CastingRecipe recipe) {
        recipes.add(recipe);
    }

    public static Item getFilledMold(SmeltingRecipe.EnumMetal metal, SmithingManager.EnumMetalShape moldType) {
        for(CastingRecipe recipe : recipes) {
            if(recipe.getMetal().equals(metal) && recipe.getMoldType().equals(moldType)) return recipe.getResult();
        }
        return null;
    }

    private static class CastingRecipe {
        private SmeltingRecipe.EnumMetal metal;
        private SmithingManager.EnumMetalShape moldType;
        private Item result;

        private CastingRecipe(SmeltingRecipe.EnumMetal metal, SmithingManager.EnumMetalShape moldType, Item result) {
            this.metal = metal;
            this.moldType = moldType;
            this.result = result;

            CastingManager.addRecipe(this);
        }

        public Item getResult() {
            return result;
        }

        public SmithingManager.EnumMetalShape getMoldType() {
            return moldType;
        }

        public SmeltingRecipe.EnumMetal getMetal() {
            return metal;
        }
    }
}
