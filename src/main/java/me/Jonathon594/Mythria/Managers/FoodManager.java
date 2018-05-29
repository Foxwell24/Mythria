package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Capability.Food.IFood;
import me.Jonathon594.Mythria.DataTypes.FoodRecipe;
import me.Jonathon594.Mythria.DataTypes.MythriaFoodData;
import me.Jonathon594.Mythria.Enum.CookType;
import me.Jonathon594.Mythria.GUI.Container.ContainerAttribute;
import me.Jonathon594.Mythria.GUI.Container.PerkMenuGuiContainer;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Open;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    public static final List<FoodRecipe> RECIPES = new ArrayList<>();
    public static List<MythriaFoodData> FOOD_DATA = new ArrayList<>();

    public static void Cook(final ItemStackHandler cookItems, final double cookspeed) {
        for (int i = 0; i < cookItems.getSlots(); i++) {
            ItemStack is = cookItems.getStackInSlot(i);
            if (is.isEmpty())
                continue;
            if (!is.hasCapability(FoodProvider.FOOD_CAP, null))
                continue;
            IFood food = is.getCapability(FoodProvider.FOOD_CAP, null);
            final FoodRecipe fr = FoodManager.getFoodRecipe(is.getItem());
            int cook = food.getCooked();
            cook += Math.random() * cookspeed;
            food.setCooked(cook);
            FoodManager.UpdateFood(is);
            if (cook > 75 && fr != null) {
                is = new ItemStack(fr.getResult(), 1);
                food = is.getCapability(FoodProvider.FOOD_CAP, null);
                food.setCooked(cook);
                FoodManager.UpdateFood(is);
                cookItems.setStackInSlot(i, is);
            }
            if (cook > 125)
                is.setCount(0);
        }
    }

    public static FoodRecipe getFoodRecipe(final Item item) {
        for (final FoodRecipe fr : RECIPES)
            if (fr.getIngredient().equals(item))
                return fr;
        return null;
    }

    public static void UpdateFood(final ItemStack is) {
        if (is.isEmpty())
            return;
        if (!is.hasCapability(FoodProvider.FOOD_CAP, null))
            return;
        final IFood food = is.getCapability(FoodProvider.FOOD_CAP, null);
        if (food.getOrigin() == 0)
            food.setOrigin(TimeManager.getCurrentDate().getMGD());
        final double deltaAge = TimeManager.getCurrentDate().getMGD() - food.getOrigin();
        food.setAge(food.getAge() + deltaAge);
        final double age = food.getAge();
        final double maxAge = getFoodLifeTime(is.getItem());
        if (age > maxAge) {
            is.setCount(0);
            return;
        }
        final ArrayList<String> lines = new ArrayList<>();
        lines.add(MythriaConst.CONT_COLOR + "This food looks good.");
        lines.add(MythriaConst.CONT_COLOR + "" + food.getCooked() + "% Cooked.");
        MythriaUtil.addLoreToItemStack(is, lines);
    }

    public static double getFoodLifeTime(final Item food) {
        for (final MythriaFoodData mfd : FOOD_DATA)
            if (mfd.getItem().equals(food))
                return mfd.getMaxLife();
        return 0;
    }

    public static void Initialize() {
        FOOD_DATA.add(new MythriaFoodData(Items.APPLE, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.BAKED_POTATO, 10));
        FOOD_DATA.add(new MythriaFoodData(Items.BEETROOT, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.BEETROOT_SOUP, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.BREAD, 21));
        FOOD_DATA.add(new MythriaFoodData(Items.CARROT, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.CHORUS_FRUIT, 18));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_CHICKEN, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_MUTTON, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_PORKCHOP, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_RABBIT, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_FISH, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKIE, 8));
        FOOD_DATA.add(new MythriaFoodData(Items.FISH, 2));
        FOOD_DATA.add(new MythriaFoodData(Items.GOLDEN_APPLE, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.GOLDEN_CARROT, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.MELON, 4));
        FOOD_DATA.add(new MythriaFoodData(Items.MUSHROOM_STEW, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.POISONOUS_POTATO, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.POTATO, 14));
        FOOD_DATA.add(new MythriaFoodData(Items.PUMPKIN_PIE, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.RABBIT_STEW, 7));
        FOOD_DATA.add(new MythriaFoodData(Items.BEEF, 2));
        FOOD_DATA.add(new MythriaFoodData(Items.CHICKEN, 1));
        FOOD_DATA.add(new MythriaFoodData(Items.MUTTON, 3));
        FOOD_DATA.add(new MythriaFoodData(Items.PORKCHOP, 2));
        FOOD_DATA.add(new MythriaFoodData(Items.RABBIT, 3));
        FOOD_DATA.add(new MythriaFoodData(Items.SPIDER_EYE, 6));
        FOOD_DATA.add(new MythriaFoodData(Items.COOKED_BEEF, 14));

        // Roasting (Campfire)
        RECIPES.add(new FoodRecipe(Items.COOKED_BEEF, Items.BEEF, CookType.ROAST));
        RECIPES.add(new FoodRecipe(Items.COOKED_MUTTON, Items.MUTTON, CookType.ROAST));
        RECIPES.add(new FoodRecipe(Items.COOKED_PORKCHOP, Items.PORKCHOP, CookType.ROAST));
        RECIPES.add(new FoodRecipe(Items.COOKED_FISH, Items.FISH, CookType.ROAST));
        RECIPES.add(new FoodRecipe(Items.COOKED_CHICKEN, Items.CHICKEN, CookType.ROAST));
    }

    public static void onPlayerPickupItem(final EntityItemPickupEvent event) {
        if (event.isCanceled())
            return;
        UpdateFood(event.getItem().getItem());
    }

    public static void UpdateFoodItems(final Open event) {
        final Container c = event.getContainer();
        if (c instanceof PerkMenuGuiContainer || c instanceof ContainerAttribute)
            return;
        for (int i = 0; i < c.getInventory().size(); i++) {
            final ItemStack is = c.getInventory().get(i);
            UpdateFood(is);
            c.putStackInSlot(i, is);
        }
        UpdatePlayerInventory(event.getEntityPlayer());
    }

    public static void UpdatePlayerInventory(final EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack is = player.inventory.getStackInSlot(i);
            UpdateFood(is);
            player.inventory.setInventorySlotContents(i, is);
        }
    }

    public static boolean isRaw(ItemStack item) {
        if(item == null) return false;
        Item i = item.getItem();
        if(i.equals(Items.BEEF)) return true;
        if(i.equals(Items.CHICKEN)) return true;
        if(i.equals(Items.PORKCHOP)) return true;
        if(i.equals(Items.RABBIT)) return true;
        if(i.equals(Items.MUTTON)) return true;
        if(i.equals(Items.FISH)) return true;
        return false;
    }
}
