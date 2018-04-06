package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightManager {
    public static Map<Item, Float> ItemWeightMap = new HashMap<>();
    public static List<EntityPlayer> WeightUpdateQue = new ArrayList<>();

    public static void Initialize() {
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHER_BRICK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ACACIA_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ACACIA_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ACACIA_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ACACIA_STAIRS), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ACTIVATOR_RAIL), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.AIR), 0f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ANVIL), 100f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.APPLE), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ARMOR_STAND), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ARROW), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BAKED_POTATO), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BANNER), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BARRIER), 0f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BEACON), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BED), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BEDROCK), 1000f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BEETROOT), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BEETROOT_SEEDS), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BEETROOT_SOUP), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BIRCH_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BIRCH_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BIRCH_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BIRCH_STAIRS), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BLAZE_POWDER), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BLAZE_ROD), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SPRUCE_BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DARK_OAK_BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BIRCH_BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.JUNGLE_BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ACACIA_BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BOAT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BONE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BONE_BLOCK), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BOOK), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WRITABLE_BOOK), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BOOKSHELF), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BOW), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BOWL), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BREAD), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BREWING_STAND), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BRICK_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BRICK_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BROWN_MUSHROOM), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BUCKET), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CACTUS), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CAKE), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CARPET), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CARROT), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CARROT_ON_A_STICK), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CAULDRON), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHAINMAIL_BOOTS), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHAINMAIL_CHESTPLATE), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHAINMAIL_HELMET), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHAINMAIL_LEGGINGS), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CHEST), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CHORUS_FLOWER), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHORUS_FRUIT), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHORUS_FRUIT_POPPED), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CHORUS_PLANT), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CLAY), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CLAY_BALL), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BRICK), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COAL), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.COAL_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.COAL_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.COBBLESTONE_WALL), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.COBBLESTONE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_STAIRS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.COCOA), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COMPASS), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_BEEF), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_CHICKEN), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_FISH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_MUTTON), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_RABBIT), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKIE), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DARK_OAK_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DARK_OAK_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DARK_OAK_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DARK_OAK_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DAYLIGHT_DETECTOR), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DEADBUSH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DETECTOR_RAIL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_AXE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_HORSE_ARMOR), 75f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DIAMOND_BLOCK), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_BOOTS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_CHESTPLATE), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_HELMET), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_HOE), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_LEGGINGS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DIAMOND_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_PICKAXE), 35f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_SHOVEL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DIAMOND_SWORD), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.REPEATER), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DIRT), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DISPENSER), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DRAGON_EGG), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DRAGON_BREATH), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.DROPPER), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.EGG), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ELYTRA), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.EMERALD), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.EMERALD_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.EMERALD_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MAP), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ENCHANTED_BOOK), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ENCHANTING_TABLE), 100f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.END_BRICKS), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.END_CRYSTAL), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.END_GATEWAY), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.END_ROD), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ENDER_CHEST), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ENDER_PEARL), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.END_STONE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.EXPERIENCE_BOTTLE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.TNT_MINECART), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ENDER_EYE), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FEATHER), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.OAK_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.OAK_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FERMENTED_SPIDER_EYE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FIRE_CHARGE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FIREWORKS), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FIREWORK_CHARGE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FISHING_ROD), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FLINT), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FLINT_AND_STEEL), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.FLOWER_POT), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.FROSTED_ICE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.FURNACE), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GHAST_TEAR), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GLASS), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GLASS_BOTTLE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GLOWSTONE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GLOWSTONE_DUST), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_AXE), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_HORSE_ARMOR), 100f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GOLD_BLOCK), 80f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_BOOTS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_CHESTPLATE), 60f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_HELMET), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_HOE), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLD_INGOT), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_LEGGINGS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLD_NUGGET), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GOLD_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_PICKAXE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_SHOVEL), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_SWORD), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_APPLE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GOLDEN_CARROT), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GRAVEL), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COOKED_PORKCHOP), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.HARDENED_CLAY), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.HAY_BLOCK), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.HOPPER), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.HOPPER_MINECART), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RED_MUSHROOM_BLOCK), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.BROWN_MUSHROOM_BLOCK), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.ICE), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.DYE), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_AXE), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_HORSE_ARMOR), 75f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_BOOTS), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_CHESTPLATE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.IRON_DOOR), 70f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.IRON_BARS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_HELMET), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_HOE), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_INGOT), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_LEGGINGS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.IRON_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_PICKAXE), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_SHOVEL), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.IRON_SWORD), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.IRON_BLOCK), 80f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.IRON_TRAPDOOR), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ITEM_FRAME), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LIT_PUMPKIN), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.JUKEBOX), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.JUNGLE_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.JUNGLE_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.JUNGLE_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.JUNGLE_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LADDER), 8f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LAPIS_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LAPIS_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LAVA_BUCKET), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEAD), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEATHER), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEATHER_BOOTS), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEATHER_CHESTPLATE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEATHER_HELMET), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LEATHER_LEGGINGS), 8f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LEAVES), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LEAVES2), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LEVER), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.LINGERING_POTION), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LOG), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.LOG2), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.MAGMA), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MAGMA_CREAM), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MAP), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MELON), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MELON_SEEDS), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MILK_BUCKET), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MINECART), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.MOSSY_COBBLESTONE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MUSHROOM_STEW), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.MUTTON), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.MYCELIUM), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.NAME_TAG), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHER_BRICK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHER_BRICK_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHER_BRICK_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.NETHER_WART), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.NETHER_STAR), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHER_WART_BLOCK), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NETHERRACK), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.NOTEBLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.OBSIDIAN), 150f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PACKED_ICE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PAINTING), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PAPER), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PISTON), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PISTON_EXTENSION), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PISTON_HEAD), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STICKY_PISTON), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.POISONOUS_POTATO), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PORKCHOP), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.POTATO), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.POTIONITEM), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FURNACE_MINECART), 75f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GOLDEN_RAIL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PRISMARINE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PRISMARINE_CRYSTALS), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PRISMARINE_SHARD), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PUMPKIN), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PUMPKIN_PIE), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.PUMPKIN_SEEDS), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PURPUR_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PURPUR_PILLAR), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PURPUR_SLAB), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PURPUR_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.QUARTZ), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.QUARTZ_BLOCK), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.QUARTZ_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.QUARTZ_STAIRS), 8f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.RABBIT), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.RABBIT_FOOT), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.RABBIT_HIDE), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.RABBIT_STEW), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RAIL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.BEEF), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHICKEN), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.FISH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RED_MUSHROOM), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RED_NETHER_BRICK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RED_SANDSTONE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.RED_SANDSTONE_STAIRS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.REDSTONE), 0.2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.REDSTONE_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.COMPARATOR), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.REDSTONE_LAMP), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.REDSTONE_ORE), 4f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.REDSTONE_TORCH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.UNLIT_REDSTONE_TORCH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.REDSTONE_WIRE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.ROTTEN_FLESH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SADDLE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SAND), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SANDSTONE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SANDSTONE_STAIRS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SAPLING), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SEA_LANTERN), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WHEAT_SEEDS), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SHEARS), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SHIELD), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SIGN), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SKULL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SLIME_BALL), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SLIME_BLOCK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONEBRICK), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_BRICK_STAIRS), 30f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SNOW), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SNOWBALL), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SNOW), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SOUL_SAND), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SPECKLED_MELON), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SPECTRAL_ARROW), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SPIDER_EYE), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SPLASH_POTION), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SPONGE), 0.1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SPRUCE_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SPRUCE_FENCE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SPRUCE_FENCE_GATE), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.SPRUCE_STAIRS), 25f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STAINED_HARDENED_CLAY), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STAINED_GLASS), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STAINED_GLASS_PANE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WOODEN_SLAB), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_SLAB), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_SLAB2), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STICK), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE), 50f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STONE_AXE), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_BUTTON), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STONE_HOE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STONE_PICKAXE), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_PRESSURE_PLATE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.STONE_SLAB2), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STONE_SHOVEL), 8f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STONE_SWORD), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CHEST_MINECART), 60f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.STRING), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.SUGAR), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.REEDS), 0.10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.GUNPOWDER), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.GLASS_PANE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.TIPPED_ARROW), 0.01f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.TNT), 35f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.TORCH), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.TRAPDOOR), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.TRAPPED_CHEST), 20f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.TRIPWIRE_HOOK), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.VINE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.CLOCK), 0.5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WATER_BUCKET), 15f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WATERLILY), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WEB), 3f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WHEAT), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.PLANKS), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_AXE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WOODEN_BUTTON), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.OAK_DOOR), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_HOE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_PICKAXE), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WOODEN_PRESSURE_PLATE), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_SHOVEL), 10f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.OAK_STAIRS), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WOODEN_SWORD), 5f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.WOOL), 2f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.CRAFTING_TABLE), 40f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Items.WRITTEN_BOOK), 1f);
        ItemWeightMap.put(MythriaUtil.getItemFromItemOrBlock(Blocks.YELLOW_FLOWER), 0.1f);
    }
}