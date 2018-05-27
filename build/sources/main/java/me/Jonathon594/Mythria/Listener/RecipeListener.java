package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.DataTypes.MythriaFoodData;
import me.Jonathon594.Mythria.Interface.ConditionalRecipe;
import me.Jonathon594.Mythria.Managers.FoodManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class RecipeListener {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterLow(final RegistryEvent.Register<IRecipe> event) {
        for (final MythriaFoodData food : FoodManager.FOOD_DATA) {
            removeFurnaceRecipes(food.getItem());
            removeAllRecipesFor(food.getItem());
        }

        //Machines
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Blocks.FURNACE));

        //Wood Tools
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_SWORD));
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_SHOVEL));
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_PICKAXE));
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_HOE));
        removeAllRecipesFor(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_AXE));

        //Wood
        IForgeRegistryModifiable registryModifiable = (IForgeRegistryModifiable) event.getRegistry();
        registryModifiable.remove(new ResourceLocation("minecraft:oak_planks"));
        registryModifiable.remove(new ResourceLocation("minecraft:birch_planks"));
        registryModifiable.remove(new ResourceLocation("minecraft:spruce_planks"));
        registryModifiable.remove(new ResourceLocation("minecraft:acacia_planks"));
        registryModifiable.remove(new ResourceLocation("minecraft:jungle_planks"));
        registryModifiable.remove(new ResourceLocation("minecraft:dark_oak_planks"));


        //Blacklisted Items
        removeAllRecipesForIngredient(Items.IRON_INGOT);
        removeAllRecipesForIngredient(Items.GOLD_INGOT);
        removeAllRecipesForIngredient(Items.DIAMOND);

        removeAllRecipesForIngredient(Items.OAK_DOOR);
        removeAllRecipesForIngredient(Items.SPRUCE_DOOR);
        removeAllRecipesForIngredient(Items.JUNGLE_DOOR);
        removeAllRecipesForIngredient(Items.BIRCH_DOOR);
        removeAllRecipesForIngredient(Items.DARK_OAK_DOOR);
        removeAllRecipesForIngredient(Items.ACACIA_DOOR);

    }

    private static void removeAllRecipesForIngredient(Item item) {
        final IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) ForgeRegistries.RECIPES;
        final List<IRecipe> toRemove = new ArrayList<>();

        for (final IRecipe recipe : registry) {
            for(Ingredient i : recipe.getIngredients()) {
                for(ItemStack is : i.getMatchingStacks()) {
                    if(is.getItem().equals(item))
                        toRemove.add(recipe);
                }
            }
        }

        for (final IRecipe recipe : toRemove)
            registry.remove(recipe.getRegistryName());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRegisterHigh(final RegistryEvent.Register<IRecipe> event) {
        replaceAllWithConditional();
    }

    private static void removeFurnaceRecipes(final Item item) {
        final List<Entry<ItemStack, ItemStack>> toRemove = new ArrayList<>();
        for (final Entry<ItemStack, ItemStack> i : FurnaceRecipes.instance().getSmeltingList().entrySet())
            if (i.getValue().getItem().equals(item))
                toRemove.add(i);

        for (final Entry<ItemStack, ItemStack> e : toRemove)
            FurnaceRecipes.instance().getSmeltingList().remove(e.getKey(), e.getValue());
    }

    private static void removeAllRecipesFor(final Item item) {
        final IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) ForgeRegistries.RECIPES;
        final List<IRecipe> toRemove = new ArrayList<>();

        for (final IRecipe recipe : registry)
            if (recipe.getRecipeOutput().getItem().equals(item))
                toRemove.add(recipe);

        for (final IRecipe recipe : toRemove)
            registry.remove(recipe.getRegistryName());
    }

    public static void replaceAllWithConditional() {
        final IForgeRegistry<IRecipe> registry = ForgeRegistries.RECIPES;
        final List<IRecipe> toRemove = new ArrayList<>();

        for (final IRecipe recipe : registry) {
            toRemove.add(recipe);
            registry.register(new ConditionalRecipe(recipe));
        }
    }
}
