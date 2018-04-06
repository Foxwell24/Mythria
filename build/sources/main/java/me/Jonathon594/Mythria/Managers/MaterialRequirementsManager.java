package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MaterialRequirementsManager {
    public static Map<Item, List<Perk>> AttributesForCrafting = new HashMap<>();
    public static Map<Block, List<Perk>> AttributesForPlacing = new HashMap<>();
    public static Map<Block, List<Perk>> AttributesForBreaking = new HashMap<>();

    public static Map<Block, Integer> staminaCostForBreakingPlacing = new HashMap<>();

    public static void Initialize() {
        for (final Perk pa : PerkManager.getAttributes()) {
            for (final Item i : pa.getCraftable()) {
                final List<Perk> l2 = AttributesForCrafting.get(i);
                if (l2 == null)
                    AttributesForCrafting.put(i, new ArrayList<Perk>());
                AttributesForCrafting.get(i).add(pa);

            }

            for (final Block b : pa.getPlacable()) {
                final List<Perk> l3 = AttributesForPlacing.get(b);
                if (l3 == null)
                    AttributesForPlacing.put(b, new ArrayList<Perk>());
                AttributesForPlacing.get(b).add(pa);
            }

            for (final Block b : pa.getBreakable()) {
                final List<Perk> l = AttributesForBreaking.get(b);
                if (l == null)
                    AttributesForBreaking.put(b, new ArrayList<Perk>());
                AttributesForBreaking.get(b).add(pa);
            }
        }

        setupStaminaCosts();
    }

    private static void setupStaminaCosts() {
        staminaCostForBreakingPlacing.put(Blocks.AIR, 0);
        staminaCostForBreakingPlacing.put(Blocks.STONE, 50);
        staminaCostForBreakingPlacing.put(Blocks.GRASS, 20);
        staminaCostForBreakingPlacing.put(Blocks.DIRT, 25);
        staminaCostForBreakingPlacing.put(Blocks.COBBLESTONE, 5);
        staminaCostForBreakingPlacing.put(Blocks.PLANKS, 5);
        staminaCostForBreakingPlacing.put(Blocks.SAPLING, 15);
        staminaCostForBreakingPlacing.put(Blocks.SAND, 5);
        staminaCostForBreakingPlacing.put(Blocks.GRAVEL, 8);
        staminaCostForBreakingPlacing.put(Blocks.GOLD_ORE, 58);
        staminaCostForBreakingPlacing.put(Blocks.IRON_ORE, 52);
        staminaCostForBreakingPlacing.put(Blocks.COAL_ORE, 48);
        staminaCostForBreakingPlacing.put(Blocks.LOG, 30);
        staminaCostForBreakingPlacing.put(Blocks.LOG2, 30);
        staminaCostForBreakingPlacing.put(Blocks.LEAVES, 5);
        staminaCostForBreakingPlacing.put(Blocks.LEAVES2, 5);
        staminaCostForBreakingPlacing.put(Blocks.SPONGE, 3);
        staminaCostForBreakingPlacing.put(Blocks.GLASS, 13);
        staminaCostForBreakingPlacing.put(Blocks.LAPIS_ORE, 48);
        staminaCostForBreakingPlacing.put(Blocks.LAPIS_BLOCK, 35);
        staminaCostForBreakingPlacing.put(Blocks.DISPENSER, 15);
        staminaCostForBreakingPlacing.put(Blocks.SANDSTONE, 25);
        staminaCostForBreakingPlacing.put(Blocks.NOTEBLOCK, 15);
        staminaCostForBreakingPlacing.put(Blocks.BED, 15);
        staminaCostForBreakingPlacing.put(Blocks.GOLDEN_RAIL, 25);
        staminaCostForBreakingPlacing.put(Blocks.DETECTOR_RAIL, 25);
        staminaCostForBreakingPlacing.put(Blocks.STICKY_PISTON, 15);
        staminaCostForBreakingPlacing.put(Blocks.WEB, 1);
        staminaCostForBreakingPlacing.put(Blocks.TALLGRASS, 5);
        staminaCostForBreakingPlacing.put(Blocks.DEADBUSH, 3);
        staminaCostForBreakingPlacing.put(Blocks.PISTON, 15);
        staminaCostForBreakingPlacing.put(Blocks.PISTON_HEAD, 15);
        staminaCostForBreakingPlacing.put(Blocks.WOOL, 7);
        staminaCostForBreakingPlacing.put(Blocks.PISTON_EXTENSION, 15);
        staminaCostForBreakingPlacing.put(Blocks.YELLOW_FLOWER, 1);
        staminaCostForBreakingPlacing.put(Blocks.RED_FLOWER, 1);
        staminaCostForBreakingPlacing.put(Blocks.BROWN_MUSHROOM, 1);
        staminaCostForBreakingPlacing.put(Blocks.RED_MUSHROOM, 1);
        staminaCostForBreakingPlacing.put(Blocks.GOLD_BLOCK, 60);
        staminaCostForBreakingPlacing.put(Blocks.IRON_BLOCK, 60);
        staminaCostForBreakingPlacing.put(Blocks.DOUBLE_STONE_SLAB, 40);
        staminaCostForBreakingPlacing.put(Blocks.STONE_SLAB, 20);
        staminaCostForBreakingPlacing.put(Blocks.BRICK_BLOCK, 20);
        staminaCostForBreakingPlacing.put(Blocks.TNT, 4);
        staminaCostForBreakingPlacing.put(Blocks.BOOKSHELF, 10);
        staminaCostForBreakingPlacing.put(Blocks.MOSSY_COBBLESTONE, 6);
        staminaCostForBreakingPlacing.put(Blocks.OBSIDIAN, 100);
        staminaCostForBreakingPlacing.put(Blocks.TORCH, 1);
        staminaCostForBreakingPlacing.put(Blocks.FIRE, 3);
        staminaCostForBreakingPlacing.put(Blocks.MOB_SPAWNER, 0);
        staminaCostForBreakingPlacing.put(Blocks.OAK_STAIRS, 10);
        staminaCostForBreakingPlacing.put(Blocks.CHEST, 7);
        staminaCostForBreakingPlacing.put(Blocks.REDSTONE_WIRE, 3);
        staminaCostForBreakingPlacing.put(Blocks.DIAMOND_ORE, 65);
        staminaCostForBreakingPlacing.put(Blocks.DIAMOND_BLOCK, 70);
        staminaCostForBreakingPlacing.put(Blocks.CRAFTING_TABLE, 10);
        staminaCostForBreakingPlacing.put(Blocks.WHEAT, 15);
        staminaCostForBreakingPlacing.put(Blocks.FARMLAND, 20);
        staminaCostForBreakingPlacing.put(Blocks.FURNACE, 5);
        staminaCostForBreakingPlacing.put(Blocks.LIT_FURNACE, 8);
        staminaCostForBreakingPlacing.put(Blocks.STANDING_SIGN, 5);
        staminaCostForBreakingPlacing.put(Blocks.OAK_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.SPRUCE_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.BIRCH_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.JUNGLE_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.ACACIA_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.DARK_OAK_DOOR, 7);
        staminaCostForBreakingPlacing.put(Blocks.LADDER, 6);
        staminaCostForBreakingPlacing.put(Blocks.RAIL, 25);
        staminaCostForBreakingPlacing.put(Blocks.STONE_STAIRS, 5);
        staminaCostForBreakingPlacing.put(Blocks.WALL_SIGN, 5);
        staminaCostForBreakingPlacing.put(Blocks.LEVER, 3);
        staminaCostForBreakingPlacing.put(Blocks.STONE_PRESSURE_PLATE, 2);
        staminaCostForBreakingPlacing.put(Blocks.IRON_DOOR, 17);
        staminaCostForBreakingPlacing.put(Blocks.WOODEN_PRESSURE_PLATE, 2);
        staminaCostForBreakingPlacing.put(Blocks.REDSTONE_ORE, 45);
        staminaCostForBreakingPlacing.put(Blocks.LIT_REDSTONE_ORE, 45);
        staminaCostForBreakingPlacing.put(Blocks.UNLIT_REDSTONE_TORCH, 1);
        staminaCostForBreakingPlacing.put(Blocks.REDSTONE_TORCH, 1);
        staminaCostForBreakingPlacing.put(Blocks.STONE_BUTTON, 1);
        staminaCostForBreakingPlacing.put(Blocks.SNOW_LAYER, 3);
        staminaCostForBreakingPlacing.put(Blocks.ICE, 13);
        staminaCostForBreakingPlacing.put(Blocks.SNOW, 24);
        staminaCostForBreakingPlacing.put(Blocks.CACTUS, 14);
        staminaCostForBreakingPlacing.put(Blocks.CLAY, 27);
        staminaCostForBreakingPlacing.put(Blocks.REEDS, 4);
        staminaCostForBreakingPlacing.put(Blocks.JUKEBOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.OAK_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.SPRUCE_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.BIRCH_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.JUNGLE_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.DARK_OAK_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.ACACIA_FENCE, 18);
        staminaCostForBreakingPlacing.put(Blocks.PUMPKIN, 5);
        staminaCostForBreakingPlacing.put(Blocks.NETHERRACK, 25);
        staminaCostForBreakingPlacing.put(Blocks.SOUL_SAND, 7);
        staminaCostForBreakingPlacing.put(Blocks.GLOWSTONE, 17);
        staminaCostForBreakingPlacing.put(Blocks.PORTAL, 70);
        staminaCostForBreakingPlacing.put(Blocks.LIT_PUMPKIN, 3);
        staminaCostForBreakingPlacing.put(Blocks.CAKE, 2);
        staminaCostForBreakingPlacing.put(Blocks.UNPOWERED_REPEATER, 5);
        staminaCostForBreakingPlacing.put(Blocks.POWERED_REPEATER, 5);
        staminaCostForBreakingPlacing.put(Blocks.TRAPDOOR, 6);
        staminaCostForBreakingPlacing.put(Blocks.MONSTER_EGG, 5);
        staminaCostForBreakingPlacing.put(Blocks.STONEBRICK, 20);
        staminaCostForBreakingPlacing.put(Blocks.BROWN_MUSHROOM_BLOCK, 7);
        staminaCostForBreakingPlacing.put(Blocks.RED_MUSHROOM_BLOCK, 7);
        staminaCostForBreakingPlacing.put(Blocks.IRON_BARS, 27);
        staminaCostForBreakingPlacing.put(Blocks.GLASS_PANE, 6);
        staminaCostForBreakingPlacing.put(Blocks.MELON_BLOCK, 5);
        staminaCostForBreakingPlacing.put(Blocks.PUMPKIN_STEM, 3);
        staminaCostForBreakingPlacing.put(Blocks.MELON_STEM, 3);
        staminaCostForBreakingPlacing.put(Blocks.VINE, 1);
        staminaCostForBreakingPlacing.put(Blocks.OAK_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.SPRUCE_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.BIRCH_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.JUNGLE_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.DARK_OAK_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.ACACIA_FENCE_GATE, 8);
        staminaCostForBreakingPlacing.put(Blocks.BRICK_STAIRS, 17);
        staminaCostForBreakingPlacing.put(Blocks.STONE_BRICK_STAIRS, 15);
        staminaCostForBreakingPlacing.put(Blocks.MYCELIUM, 25);
        staminaCostForBreakingPlacing.put(Blocks.WATERLILY, 1);
        staminaCostForBreakingPlacing.put(Blocks.NETHER_BRICK, 37);
        staminaCostForBreakingPlacing.put(Blocks.NETHER_BRICK_FENCE, 27);
        staminaCostForBreakingPlacing.put(Blocks.NETHER_BRICK_STAIRS, 27);
        staminaCostForBreakingPlacing.put(Blocks.NETHER_WART, 8);
        staminaCostForBreakingPlacing.put(Blocks.ENCHANTING_TABLE, 130);
        staminaCostForBreakingPlacing.put(Blocks.BREWING_STAND, 20);
        staminaCostForBreakingPlacing.put(Blocks.CAULDRON, 38);
        staminaCostForBreakingPlacing.put(Blocks.END_PORTAL, 80);
        staminaCostForBreakingPlacing.put(Blocks.END_PORTAL_FRAME, 1);
        staminaCostForBreakingPlacing.put(Blocks.END_STONE, 70);
        staminaCostForBreakingPlacing.put(Blocks.DRAGON_EGG, 6);
        staminaCostForBreakingPlacing.put(Blocks.REDSTONE_LAMP, 23);
        staminaCostForBreakingPlacing.put(Blocks.LIT_REDSTONE_LAMP, 23);
        staminaCostForBreakingPlacing.put(Blocks.DOUBLE_WOODEN_SLAB, 6);
        staminaCostForBreakingPlacing.put(Blocks.WOODEN_SLAB, 3);
        staminaCostForBreakingPlacing.put(Blocks.COCOA, 2);
        staminaCostForBreakingPlacing.put(Blocks.SANDSTONE_STAIRS, 20);
        staminaCostForBreakingPlacing.put(Blocks.EMERALD_ORE, 55);
        staminaCostForBreakingPlacing.put(Blocks.ENDER_CHEST, 17);
        staminaCostForBreakingPlacing.put(Blocks.TRIPWIRE_HOOK, 4);
        staminaCostForBreakingPlacing.put(Blocks.TRIPWIRE, 2);
        staminaCostForBreakingPlacing.put(Blocks.EMERALD_BLOCK, 64);
        staminaCostForBreakingPlacing.put(Blocks.SPRUCE_STAIRS, 6);
        staminaCostForBreakingPlacing.put(Blocks.BIRCH_STAIRS, 6);
        staminaCostForBreakingPlacing.put(Blocks.JUNGLE_STAIRS, 6);
        staminaCostForBreakingPlacing.put(Blocks.COMMAND_BLOCK, 1);
        staminaCostForBreakingPlacing.put(Blocks.BEACON, 80);
        staminaCostForBreakingPlacing.put(Blocks.COBBLESTONE_WALL, 4);
        staminaCostForBreakingPlacing.put(Blocks.FLOWER_POT, 2);
        staminaCostForBreakingPlacing.put(Blocks.CARROTS, 13);
        staminaCostForBreakingPlacing.put(Blocks.POTATOES, 13);
        staminaCostForBreakingPlacing.put(Blocks.WOODEN_BUTTON, 1);
        staminaCostForBreakingPlacing.put(Blocks.SKULL, 1);
        staminaCostForBreakingPlacing.put(Blocks.ANVIL, 85);
        staminaCostForBreakingPlacing.put(Blocks.TRAPPED_CHEST, 7);
        staminaCostForBreakingPlacing.put(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 4);
        staminaCostForBreakingPlacing.put(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 7);
        staminaCostForBreakingPlacing.put(Blocks.UNPOWERED_COMPARATOR, 4);
        staminaCostForBreakingPlacing.put(Blocks.POWERED_COMPARATOR, 4);
        staminaCostForBreakingPlacing.put(Blocks.DAYLIGHT_DETECTOR, 18);
        staminaCostForBreakingPlacing.put(Blocks.DAYLIGHT_DETECTOR_INVERTED, 18);
        staminaCostForBreakingPlacing.put(Blocks.REDSTONE_BLOCK, 50);
        staminaCostForBreakingPlacing.put(Blocks.QUARTZ_ORE, 40);
        staminaCostForBreakingPlacing.put(Blocks.HOPPER, 48);
        staminaCostForBreakingPlacing.put(Blocks.QUARTZ_BLOCK, 60);
        staminaCostForBreakingPlacing.put(Blocks.QUARTZ_STAIRS, 45);
        staminaCostForBreakingPlacing.put(Blocks.ACTIVATOR_RAIL, 25);
        staminaCostForBreakingPlacing.put(Blocks.DROPPER, 15);
        staminaCostForBreakingPlacing.put(Blocks.STAINED_HARDENED_CLAY, 35);
        staminaCostForBreakingPlacing.put(Blocks.BARRIER, 1);
        staminaCostForBreakingPlacing.put(Blocks.IRON_TRAPDOOR, 26);
        staminaCostForBreakingPlacing.put(Blocks.HAY_BLOCK, 25);
        staminaCostForBreakingPlacing.put(Blocks.CARPET, 6);
        staminaCostForBreakingPlacing.put(Blocks.HARDENED_CLAY, 35);
        staminaCostForBreakingPlacing.put(Blocks.COAL_BLOCK, 38);
        staminaCostForBreakingPlacing.put(Blocks.PACKED_ICE, 30);
        staminaCostForBreakingPlacing.put(Blocks.ACACIA_STAIRS, 7);
        staminaCostForBreakingPlacing.put(Blocks.DARK_OAK_STAIRS, 7);
        staminaCostForBreakingPlacing.put(Blocks.SLIME_BLOCK, 7);
        staminaCostForBreakingPlacing.put(Blocks.DOUBLE_PLANT, 2);
        staminaCostForBreakingPlacing.put(Blocks.STAINED_GLASS, 13);
        staminaCostForBreakingPlacing.put(Blocks.STAINED_GLASS_PANE, 6);
        staminaCostForBreakingPlacing.put(Blocks.PRISMARINE, 30);
        staminaCostForBreakingPlacing.put(Blocks.SEA_LANTERN, 27);
        staminaCostForBreakingPlacing.put(Blocks.STANDING_BANNER, 7);
        staminaCostForBreakingPlacing.put(Blocks.WALL_BANNER, 7);
        staminaCostForBreakingPlacing.put(Blocks.RED_SANDSTONE, 25);
        staminaCostForBreakingPlacing.put(Blocks.RED_SANDSTONE_STAIRS, 20);
        staminaCostForBreakingPlacing.put(Blocks.DOUBLE_STONE_SLAB2, 50);
        staminaCostForBreakingPlacing.put(Blocks.STONE_SLAB2, 25);
        staminaCostForBreakingPlacing.put(Blocks.END_ROD, 30);
        staminaCostForBreakingPlacing.put(Blocks.CHORUS_PLANT, 3);
        staminaCostForBreakingPlacing.put(Blocks.CHORUS_FLOWER, 9);
        staminaCostForBreakingPlacing.put(Blocks.PURPUR_BLOCK, 40);
        staminaCostForBreakingPlacing.put(Blocks.PURPUR_PILLAR, 25);
        staminaCostForBreakingPlacing.put(Blocks.PURPUR_STAIRS, 25);
        staminaCostForBreakingPlacing.put(Blocks.PURPUR_DOUBLE_SLAB, 40);
        staminaCostForBreakingPlacing.put(Blocks.PURPUR_SLAB, 20);
        staminaCostForBreakingPlacing.put(Blocks.END_BRICKS, 20);
        staminaCostForBreakingPlacing.put(Blocks.BEETROOTS, 7);
        staminaCostForBreakingPlacing.put(Blocks.GRASS_PATH, 30);
        staminaCostForBreakingPlacing.put(Blocks.END_GATEWAY, 1);
        staminaCostForBreakingPlacing.put(Blocks.REPEATING_COMMAND_BLOCK, 1);
        staminaCostForBreakingPlacing.put(Blocks.CHAIN_COMMAND_BLOCK, 1);
        staminaCostForBreakingPlacing.put(Blocks.FROSTED_ICE, 50);
        staminaCostForBreakingPlacing.put(Blocks.MAGMA, 47);
        staminaCostForBreakingPlacing.put(Blocks.NETHER_WART_BLOCK, 20);
        staminaCostForBreakingPlacing.put(Blocks.RED_NETHER_BRICK, 20);
        staminaCostForBreakingPlacing.put(Blocks.BONE_BLOCK, 25);
        staminaCostForBreakingPlacing.put(Blocks.STRUCTURE_VOID, 1);
        staminaCostForBreakingPlacing.put(Blocks.OBSERVER, 15);
        staminaCostForBreakingPlacing.put(Blocks.WHITE_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.ORANGE_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.MAGENTA_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.LIGHT_BLUE_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.YELLOW_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.LIME_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.PINK_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.GRAY_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.SILVER_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.CYAN_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.PURPLE_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.BLUE_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.BROWN_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.GREEN_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.RED_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.BLACK_SHULKER_BOX, 15);
        staminaCostForBreakingPlacing.put(Blocks.WHITE_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.ORANGE_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.MAGENTA_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.YELLOW_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.LIME_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.PINK_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.GRAY_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.SILVER_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.CYAN_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.PURPLE_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.BLUE_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.BROWN_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.GREEN_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.RED_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.BLACK_GLAZED_TERRACOTTA, 47);
        staminaCostForBreakingPlacing.put(Blocks.CONCRETE, 100);
        staminaCostForBreakingPlacing.put(Blocks.CONCRETE_POWDER, 7);
        staminaCostForBreakingPlacing.put(Blocks.STRUCTURE_BLOCK, 1);
    }

    public static void onBlockBreak(final BreakEvent event, final EntityPlayer player, final IProfile p) {
        if (event.isCanceled())
            return;
        boolean able = false;
        final List<Perk> list = AttributesForBreaking.get(event.getState().getBlock());
        if (list == null)
            return;
        for (final Perk pa : list)
            if (p.getPlayerSkills().contains(pa)) {
                able = true;
                for (final Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                    p.addSkillExperience(s.getKey(), 30 * (s.getValue() / 10.0 + 1), (EntityPlayerMP) player);
            }

        if (!able) {
            event.setCanceled(true);
            return;
        }
    }

    public static void onBlockPlace(final PlaceEvent event) {
        final EntityPlayer player = event.getPlayer();
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        boolean able = false;
        final List<Perk> list = AttributesForPlacing.get(event.getPlacedBlock().getBlock());
        if (list == null)
            return;
        for (final Perk pa : list)
            if (p.getPlayerSkills().contains(pa)) {
                able = true;
                for (final Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                    p.addSkillExperience(s.getKey(), 30 * (s.getValue() / 10.0 + 1), (EntityPlayerMP) player);
            }

        if (!able) {
            event.setCanceled(true);
            return;
        }
    }
}
