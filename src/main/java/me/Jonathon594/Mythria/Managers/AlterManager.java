package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AlterManager {
    private static HashMap<Deity, HashMap<Item, Integer>> dataMap = new HashMap<>();

    private static HashMap<Deity, ArrayList<ItemStack>> deityPrizeMap = new HashMap<>();

    public static void initialize() {
        createDataMap();

        //Create prizes
        ArrayList<ItemStack> felixiaItems = new ArrayList<>();
        ItemStack gemOfLight = new ItemStack(Items.DIAMOND, 1);
        gemOfLight.setStackDisplayName(TextFormatting.WHITE + "Gem of Light");
        gemOfLight.addEnchantment(Enchantments.UNBREAKING, 1);
        felixiaItems.add(gemOfLight);
        deityPrizeMap.put(Deity.FELIXIA, felixiaItems);
    }

    private static void createDataMap() {
        HashMap<Item, Integer> felixiaMap = new HashMap<>();
        felixiaMap.put(Items.NETHER_STAR, 1000);
        felixiaMap.put(Items.GLOWSTONE_DUST, 100);
        felixiaMap.put(Items.MELON, 10);
        felixiaMap.put(Items.QUARTZ, 5);
        felixiaMap.put(Items.SKULL, -100);
        felixiaMap.put(Items.BONE, -100);
        felixiaMap.put(Items.ROTTEN_FLESH, -100);
        felixiaMap.put(Items.COAL, -100);
        dataMap.put(Deity.FELIXIA, felixiaMap);

        HashMap<Item, Integer> asanaMap = new HashMap<>();
        asanaMap.put(Items.DIAMOND, 1000);
        asanaMap.put(MythriaItems.GOLD_INGOT, 500);
        asanaMap.put(MythriaItems.IRON_INGOT, 100);
        asanaMap.put(Items.COAL, 10);
        asanaMap.put(Items.WHEAT_SEEDS, -10);
        asanaMap.put(Items.MELON, -100);
        asanaMap.put(Items.ROTTEN_FLESH, -1000);
        dataMap.put(Deity.ASANA, asanaMap);
    }

    public static int getItemValueForDeity(Deity deity, Item item) {
        HashMap<Item, Integer> deityMap = dataMap.get(deity);
        return deityMap.containsKey(item) ? deityMap.get(item) : 1;
    }

    public static ItemStack getRandomDeityConsolidationPrize(Deity deity) {
        ArrayList<ItemStack> items = deityPrizeMap.get(deity);
        Collections.shuffle(items);
        return items.get(0).copy();
    }
}
