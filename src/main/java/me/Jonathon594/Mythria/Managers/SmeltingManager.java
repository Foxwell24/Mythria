package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.Vessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.GUI.Container.ContainerAttribute;
import me.Jonathon594.Mythria.GUI.Container.PerkMenuGuiContainer;
import me.Jonathon594.Mythria.Interface.ISmelter;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

public class SmeltingManager {
    private static ArrayList<SmeltingRecipe> recipes = new ArrayList<>();

    public static ArrayList<SmeltingRecipe> getRecipes() {
        return recipes;
    }

    public static void addRecipe(SmeltingRecipe recipe) {
        recipes.add(recipe);
    }

    public static SmeltingRecipe getRecipe(SmeltingRecipe.EnumMetal metal) {
        for(SmeltingRecipe recipe : recipes) {
            if(recipe.getMetal().equals(metal)) return recipe;
        }
        return null;
    }

    public static void smeltVessal(ItemStack is, ISmelter smelter, double ambientTemp) {
        if(!is.getItem().equals(MythriaItems.CERAMIC_VESSEL)) return;
        IVessel vessel = is.getCapability(VesselProvider.VESSEL_CAP, null);
        IItemHandler items = vessel.getInventory();
        vessel.update(ambientTemp, is);

        if(!vessel.hasMetal()) {
            for (SmeltingRecipe r : recipes) {
                if (r.matches(items)) {
                    int ore = 0;
                    for (int i = 0; i < items.getSlots(); i++) {
                        if (items.getStackInSlot(i).isEmpty()) continue;
                        ore++;
                    }
                    for (int i = 0; i < items.getSlots(); i++) {
                        vessel.getInventory().setStackInSlot(i, ItemStack.EMPTY);
                    }
                    vessel.setMetal(r.getMetal());

                    double percent = (double) ore / 10.0;
                    vessel.setMetalCount((int) (2000 * percent));
                    vessel.setTemp(ambientTemp);
                }
            }
        } else {
            double currentTemp = vessel.getTemp();
            double furnaceTemp = smelter.getTemp();

            double newTemp = currentTemp + (furnaceTemp - currentTemp) / 100;
            vessel.setTemp(newTemp);

            System.out.println(smelter.getTemp() + " " + vessel.getTemp());
        }
    }

    public static void Initialize() {
        new SmeltingRecipe(1, SmeltingRecipe.EnumMetal.COPPER, 1083, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.COPPER_ORE, 1.0));
        new SmeltingRecipe(1, SmeltingRecipe.EnumMetal.SILVER, 961, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.SILVER_ORE, 1.0));
        new SmeltingRecipe(1, SmeltingRecipe.EnumMetal.GOLD, 1064, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.GOLD_ORE, 1.0));

        new SmeltingRecipe(1, SmeltingRecipe.EnumMetal.BRONZE, 1083, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.COPPER_ORE, 0.8),
                new SmeltingRecipe.SmeltingRecipePair(MythriaItems.TIN_ORE, 0.2));

        new SmeltingRecipe(1, SmeltingRecipe.EnumMetal.IRON, 1204, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.IRON_ORE, 1.0));

        new SmeltingRecipe(2, SmeltingRecipe.EnumMetal.STEEL, 1204, new SmeltingRecipe.SmeltingRecipePair(MythriaItems.IRON_ORE, 0.7),
                new SmeltingRecipe.SmeltingRecipePair(Items.COAL, 0.3));
    }

    public static void updateVessels(PlayerContainerEvent.Open event) {
        final Container c = event.getContainer();
        if (c instanceof PerkMenuGuiContainer || c instanceof ContainerAttribute)
            return;
        for (int i = 0; i < c.getInventory().size(); i++) {
            final ItemStack is = c.getInventory().get(i);
            if(is.hasCapability(VesselProvider.VESSEL_CAP, null)) {
                is.getCapability(VesselProvider.VESSEL_CAP, null).update(event.getEntityPlayer().world.getBiome(event.getEntityPlayer().getPosition())
                        .getTemperature(event.getEntityPlayer().getPosition()), is);
            }
        }
    }
}
