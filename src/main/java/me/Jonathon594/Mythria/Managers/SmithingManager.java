package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Interface.IWorkable;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SmithingManager {
    private static ArrayList<SmithingRecipe> smithingRecipes = new ArrayList<>();
    private static ArrayList<WeldingRecipe> weldingRecipes = new ArrayList<>();

    public static void Initialize() {
        new SmithingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.DOUBLE_INGOT, MythriaItems.BRONZE_SHEET, EnumMetalShape.SHEET).setRequiredFlag(AttributeFlag.BASIC_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.SHEET, MythriaItems.BRONZE_BOOTS, EnumMetalShape.BOOTS).setRequiredFlag(AttributeFlag.BASIC_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.SHEET, MythriaItems.BRONZE_HELMET, EnumMetalShape.HELMET).setRequiredFlag(AttributeFlag.BASIC_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.DOUBLE_SHEET, MythriaItems.BRONZE_CHESTPLATE, EnumMetalShape.CHESTPLATE).setRequiredFlag(AttributeFlag.BASIC_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.DOUBLE_SHEET, MythriaItems.BRONZE_LEGGINGS, EnumMetalShape.LEGGINGS).setRequiredFlag(AttributeFlag.BASIC_SMITHING);

        //Iron
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_AXE_HEAD, EnumMetalShape.AXE).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_CHISEL_HEAD, EnumMetalShape.CHISEL).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_HAMMER_HEAD, EnumMetalShape.HAMMER).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_HOE_HEAD, EnumMetalShape.HOE).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_PICKAXE_HEAD, EnumMetalShape.PICK).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_SHOVEL_HEAD, EnumMetalShape.SHOVEL).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_DAGGER_BLADE, EnumMetalShape.DAGGER).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, MythriaItems.IRON_SAW_HEAD, EnumMetalShape.SAW).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_INGOT, MythriaItems.IRON_SWORD_BLADE, EnumMetalShape.SWORD).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_INGOT, MythriaItems.IRON_SHEET, EnumMetalShape.SHEET).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, Items.IRON_BOOTS, EnumMetalShape.BOOTS).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, Items.IRON_HELMET, EnumMetalShape.HELMET).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.IRON_CHESTPLATE, EnumMetalShape.CHESTPLATE).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.IRON_LEGGINGS, EnumMetalShape.LEGGINGS).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.IRON_HORSE_ARMOR, EnumMetalShape.BARDING).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, ItemBlock.getItemFromBlock(Blocks.IRON_BARS), EnumMetalShape.BARS).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, Items.BUCKET, EnumMetalShape.BUCKET).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.IRON_DOOR, EnumMetalShape.DOOR).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.CAULDRON, EnumMetalShape.BUCKET).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_INGOT, Items.SHEARS, EnumMetalShape.SHEARS).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, ItemBlock.getItemFromBlock(Blocks.IRON_TRAPDOOR), EnumMetalShape.DOOR)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, ItemBlock.getItemFromBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), EnumMetalShape.FLAT)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, Items.COMPASS, EnumMetalShape.CLOCK_COMPAS)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING).addExtraIngredients(new Item[] {Items.REDSTONE});
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_INGOT, ItemBlock.getItemFromBlock(Blocks.RAIL), EnumMetalShape.RAIL)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING).addExtraIngredients(new Item[] {MythriaItems.LOG});
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.DOUBLE_SHEET, Items.MINECART, EnumMetalShape.MINECART)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, Items.SHIELD, EnumMetalShape.FLAT)
                .setRequiredFlag(AttributeFlag.ADVANCED_SMITHING).addExtraIngredients(new Item[] {MythriaItems.LOG,
                MythriaItems.LOG,
                MythriaItems.LOG});

        //Steel
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_AXE_HEAD, EnumMetalShape.AXE).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_CHISEL_HEAD, EnumMetalShape.CHISEL).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_HAMMER_HEAD, EnumMetalShape.HAMMER).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_HOE_HEAD, EnumMetalShape.HOE).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_PICKAXE_HEAD, EnumMetalShape.PICK).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_SHOVEL_HEAD, EnumMetalShape.SHOVEL).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_DAGGER_BLADE, EnumMetalShape.DAGGER).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.SHEET, MythriaItems.STEEL_SAW_HEAD, EnumMetalShape.SAW).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.DOUBLE_INGOT, MythriaItems.STEEL_SWORD_BLADE, EnumMetalShape.SWORD).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.DOUBLE_INGOT, MythriaItems.STEEL_SHEET, EnumMetalShape.SHEET).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.SHEET, MythriaItems.STEEL_BOOTS, EnumMetalShape.BOOTS).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.SHEET, MythriaItems.STEEL_HELMET, EnumMetalShape.HELMET).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.DOUBLE_SHEET, MythriaItems.STEEL_CHESTPLATE, EnumMetalShape.CHESTPLATE).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.DOUBLE_SHEET, MythriaItems.STEEL_LEGGINGS, EnumMetalShape.LEGGINGS).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        //Gold
        new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.INGOT, Items.CLOCK, EnumMetalShape.CLOCK_COMPAS)
                .setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING).addExtraIngredients(new Item[] {Items.REDSTONE});
        new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.DOUBLE_INGOT, Item.getItemFromBlock(Blocks.GOLDEN_RAIL), EnumMetalShape.RAIL)
                .setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING).addExtraIngredients(new Item[] {MythriaItems.LOG});

        //new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.SHEET, Items.GOLDEN_BOOTS, EnumMetalShape.BOOTS).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        //new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.SHEET, Items.GOLDEN_HELMET, EnumMetalShape.HELMET).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        //new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.DOUBLE_SHEET, Items.GOLDEN_CHESTPLATE, EnumMetalShape.CHESTPLATE).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);
        //new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.DOUBLE_SHEET, Items.GOLDEN_LEGGINGS, EnumMetalShape.LEGGINGS).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        //new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.DOUBLE_SHEET, Items.GOLDEN_LEGGINGS, EnumMetalShape.LEGGINGS).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        //Valuble Metals
        new SmithingRecipe(SmeltingRecipe.EnumMetal.COPPER, EnumMetalShape.INGOT, MythriaItems.COPPER_COIN, 10, EnumMetalShape.COIN).setRequiredFlag(AttributeFlag.BASIC_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.SILVER, EnumMetalShape.INGOT, MythriaItems.SILVER_COIN,10, EnumMetalShape.COIN).setRequiredFlag(AttributeFlag.ADVANCED_SMITHING);
        new SmithingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.INGOT, MythriaItems.GOLD_COIN, 10, EnumMetalShape.COIN).setRequiredFlag(AttributeFlag.SOPHISTICATED_SMITHING);

        //Welding
        new WeldingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.INGOT, MythriaItems.BRONZE_DOUBLE_INGOT);
        new WeldingRecipe(SmeltingRecipe.EnumMetal.BRONZE, EnumMetalShape.SHEET, MythriaItems.BRONZE_DOUBLE_SHEET);

        new WeldingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.INGOT, MythriaItems.IRON_DOUBLE_INGOT);
        new WeldingRecipe(SmeltingRecipe.EnumMetal.IRON, EnumMetalShape.SHEET, MythriaItems.IRON_DOUBLE_SHEET);

        new WeldingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.INGOT, MythriaItems.STEEL_DOUBLE_INGOT);
        new WeldingRecipe(SmeltingRecipe.EnumMetal.STEEL, EnumMetalShape.SHEET, MythriaItems.STEEL_DOUBLE_SHEET);

        new WeldingRecipe(SmeltingRecipe.EnumMetal.GOLD, EnumMetalShape.DOUBLE_INGOT, MythriaItems.GOLD_DOUBLE_INGOT);
    }

    public static ArrayList<SmithingRecipe> getRecipeFor(IWorkable input) {
        ArrayList<SmithingRecipe> recipes = (ArrayList<SmithingRecipe>) smithingRecipes.clone();
        for(SmithingRecipe recipe : smithingRecipes) {
            if(recipe.getMetalType() != input.getMetalType()) recipes.remove(recipe);
            if(recipe.getInputShape() != input.getMetalShape())
                recipes.remove(recipe);
        }

        return recipes;
    }

    public static WeldingRecipe getWeldingRecipeForInput(IWorkable input) {
        for(WeldingRecipe welding : weldingRecipes) {
            if(welding.getInputShape().equals(input.getMetalShape()) &&
                    welding.getMetalType().equals(input.getMetalType()))
                return welding;
        }
        return null;
    }

    public static EnumHitType[] getRequiredFinalHitsForMetalShape(EnumMetalShape shape) {
        EnumHitType[] finalHits = new EnumHitType[3];
        switch(shape) {
            case SHEET:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.MEDIUM_HIT;
                finalHits[2] = EnumHitType.MEDIUM_HIT;
                break;
            case PICK:
                finalHits[0] = EnumHitType.DRAW;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.SHRINK;
                break;
            case AXE:
                finalHits[0] = EnumHitType.MEDIUM_HIT;
                finalHits[1] = EnumHitType.SOFT_HIT;
                finalHits[2] = EnumHitType.SHRINK;
                break;
            case SHOVEL:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.HEAVY_HIT;
                finalHits[2] = EnumHitType.HEAVY_HIT;
                break;
            case HOE:
                finalHits[0] = EnumHitType.DRAW;
                finalHits[1] = EnumHitType.SOFT_HIT;
                finalHits[2] = EnumHitType.SHRINK;
                break;
            case SWORD:
                finalHits[0] = EnumHitType.MEDIUM_HIT;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case HAMMER:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.MEDIUM_HIT;
                finalHits[2] = EnumHitType.UPSET;
                break;
            case CHISEL:
                finalHits[0] = EnumHitType.DRAW;
                finalHits[1] = EnumHitType.SOFT_HIT;
                finalHits[2] = EnumHitType.SHRINK;
                break;
            case LEGGINGS:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.MEDIUM_HIT;
                finalHits[2] = EnumHitType.SOFT_HIT;
                break;
            case CHESTPLATE:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.UPSET;
                finalHits[2] = EnumHitType.SOFT_HIT;
                break;
            case HELMET:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.BEND;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case BOOTS:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.SOFT_HIT;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case BARS:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.PUNCH;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case BUCKET:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.UPSET;
                finalHits[2] = EnumHitType.UPSET;
                break;
            case DOOR:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.HEAVY_HIT;
                finalHits[2] = EnumHitType.HEAVY_HIT;
                break;
            case SHEARS:
                finalHits[0] = EnumHitType.PUNCH;
                finalHits[1] = EnumHitType.PUNCH;
                finalHits[2] = EnumHitType.SOFT_HIT;
                break;
            case FLAT:
                finalHits[0] = EnumHitType.MEDIUM_HIT;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.MEDIUM_HIT;
                break;
            case CLOCK_COMPAS:
                finalHits[0] = EnumHitType.PUNCH;
                finalHits[1] = EnumHitType.SOFT_HIT;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case RAIL:
                finalHits[0] = EnumHitType.DRAW;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.DRAW;
                break;
            case MINECART:
                finalHits[0] = EnumHitType.UPSET;
                finalHits[1] = EnumHitType.UPSET;
                finalHits[2] = EnumHitType.HEAVY_HIT;
                break;
            case DAGGER:
                finalHits[0] = EnumHitType.DRAW;
                finalHits[1] = EnumHitType.PUNCH;
                finalHits[2] = EnumHitType.SOFT_HIT;
                break;
            case COIN:
                finalHits[0] = EnumHitType.HEAVY_HIT;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.UPSET;
                break;
            case BARDING:
                finalHits[0] = EnumHitType.BEND;
                finalHits[1] = EnumHitType.DRAW;
                finalHits[2] = EnumHitType.PUNCH;
                break;
            case SAW:
                finalHits[0] = EnumHitType.PUNCH;
                finalHits[1] = EnumHitType.PUNCH;
                finalHits[2] = EnumHitType.HEAVY_HIT;
                break;
        }
        return finalHits;
    }

    public static int getDifficultyForMetal(SmeltingRecipe.EnumMetal metal) {
        switch (metal) {
            case SILVER:
                return 2;
            case STEEL:
                return 3;
            case IRON:
                return 2;
            case COPPER:
                return 1;
            case GOLD:
                return 3;
            case BRONZE:
                return 0;
        }
        return 0;
    }

    public static SmithingRecipe getRecipe(Item item) {
        for(SmithingRecipe recipe : smithingRecipes) {
            if(recipe.getResultItem().equals(item)) {
                return recipe;
            }
        }
        return null;
    }

    public static double getMaxDeviation(int difficulty) {
        if (difficulty > 4) difficulty = 4;
        if(difficulty < 0) difficulty = 0;
        return (5.0-(double)difficulty)/50;
    }

    public static SmithingRecipe getRecipeFor(EnumMetalShape moldType, SmeltingRecipe.EnumMetal metal) {
        for(SmithingRecipe recipe : smithingRecipes) {
            if(recipe.getMetalType().equals(moldType) && recipe.getMetalType().equals(metal)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean canSmithMetal(SmeltingRecipe.EnumMetal metalType, int tier) {
        switch (metalType) {
            case SILVER:
                if(tier >= 1) return true;
                break;
            case STEEL:
                if(tier >= 2) return true;
                break;
            case IRON:
                if(tier >= 1) return true;
                break;
            case COPPER:
                if(tier >= 1) return true;
                break;
            case GOLD:
                if(tier >= 1) return true;
                break;
            case BRONZE:
                if(tier >= 0) return true;
                break;
        }
        return false;
    }

    public enum EnumMetalShape {
        INGOT, DOUBLE_INGOT, SHEET, DOUBLE_SHEET, PICK, AXE, SHOVEL, HOE, SWORD, HAMMER, CHISEL, LEGGINGS, CHESTPLATE, HELMET, BOOTS, COIN, BARS, BUCKET, DOOR, SHEARS, FLAT, CLOCK_COMPAS, RAIL, MINECART, SAW, BARDING, DAGGER
    }

    public enum EnumHitType {
        SOFT_HIT, MEDIUM_HIT, HEAVY_HIT, DRAW, PUNCH, BEND, UPSET, SHRINK
    }

    public static class SmithingRecipe {
        private final int count;
        private SmeltingRecipe.EnumMetal metalType;
        private EnumMetalShape inputShape;
        private String internalName;
        private Item resultItem;
        private EnumMetalShape outputShape;
        private AttributeFlag requiredFlag;

        private Item[] extraIngredients = new Item[3];

        public SmithingRecipe(SmeltingRecipe.EnumMetal metalType, EnumMetalShape inputShape, Item resultItem, EnumMetalShape outputShape) {
            this.metalType = metalType;
            this.inputShape = inputShape;
            this.internalName = resultItem.getUnlocalizedName();
            this.resultItem = resultItem;
            this.outputShape = outputShape;
            this.count = 1;

            SmithingManager.addSmithingRecipe(this);
        }

        public SmithingRecipe addExtraIngredients(Item[] ingredients) {
            extraIngredients = ingredients;
            return this;
        }

        public SmithingRecipe(SmeltingRecipe.EnumMetal metalType, EnumMetalShape inputShape, Item resultItem, int count, EnumMetalShape outputShape) {
            this.metalType = metalType;
            this.inputShape = inputShape;
            this.internalName = resultItem.getUnlocalizedName();
            this.resultItem = resultItem;
            this.outputShape = outputShape;
            this.count = count;

            SmithingManager.addSmithingRecipe(this);
        }

        public SmithingRecipe setRequiredFlag(AttributeFlag flag) {
            this.requiredFlag = flag;
            return this;
        }

        public SmeltingRecipe.EnumMetal getMetalType() {
            return metalType;
        }

        public EnumMetalShape getInputShape() {
            return inputShape;
        }

        public String getInternalName() {
            return internalName;
        }

        public Item getResultItem() {
            return resultItem;
        }

        public EnumMetalShape getOutputShape() {
            return outputShape;
        }

        public int getCount() {
            return count;
        }

        public AttributeFlag getRequiredFlag() {
            return requiredFlag;
        }

        public Item[] getExtraIngredients() {
            return extraIngredients;
        }
    }

    public static class WeldingRecipe {
        private SmeltingRecipe.EnumMetal metalType;
        private EnumMetalShape inputShape;
        private String internalName;
        private Item resultItem;

        public WeldingRecipe(SmeltingRecipe.EnumMetal metalType, EnumMetalShape inputShape, Item resultItem) {
            this.metalType = metalType;
            this.inputShape = inputShape;
            this.internalName = resultItem.getUnlocalizedName();
            this.resultItem = resultItem;

            SmithingManager.addWeldingRecipe(this);
        }

        public SmeltingRecipe.EnumMetal getMetalType() {
            return metalType;
        }

        public EnumMetalShape getInputShape() {
            return inputShape;
        }

        public String getInternalName() {
            return internalName;
        }

        public Item getResultItem() {
            return resultItem;
        }
    }

    private static void addWeldingRecipe(WeldingRecipe weldingRecipe) {
        weldingRecipes.add(weldingRecipe);
    }

    public static void addSmithingRecipe(SmithingRecipe smithingRecipe) {
        smithingRecipes.add(smithingRecipe);
    }
}
