package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MythriaBlocks {
    public static final Block CAMPFIRE = new BlockCampfire(Material.WOOD, "campfire", AttributeFlag.COOKING1,
            10, 25);

    public static final Block IMPROVED_CAMPFIRE = new BlockImprovedCampfire(Material.WOOD, "improved_campfire",
            AttributeFlag.COOKING1, 30, 50);

    public static final MythriaOre TIN_ORE = new MythriaOre(Material.ROCK, "tin_ore", 10, 25, MythriaItems.TIN_ORE);
    public static final MythriaOre COPPER_ORE = new MythriaOre(Material.ROCK, "copper_ore", 10, 25, MythriaItems.COPPER_ORE);
    public static final MythriaOre SILVER_ORE = new MythriaOre(Material.ROCK, "silver_ore", 10, 25, MythriaItems.SILVER_ORE);
    public static final MythriaOre PLATINUM_ORE = new MythriaOre(Material.ROCK, "platinum_ore", 10, 25, MythriaItems.PLATINUM_ORE);
    public static final MythriaOre TITANIUM_ORE = new MythriaOre(Material.ROCK, "titanium_ore", 10, 25, MythriaItems.TITANIUM_ORE);
    public static final MythriaOre TUNGSTEN_ORE = new MythriaOre(Material.ROCK, "tungsten_ore", 10, 25, MythriaItems.TUNGSTEN_ORE);

    public static final MythriaOre IRON_ORE = new MythriaOre(Material.ROCK, "iron_ore", 10, 25, MythriaItems.IRON_ORE);
    public static final MythriaOre GOLD_ORE = new MythriaOre(Material.ROCK, "gold_ore", 10, 25, MythriaItems.GOLD_ORE);

    public static final Block COPPER_BLOCK = new MythriaBlock(Material.IRON, "copper_block", SoundType.METAL, 75,
            50, 0, 0).setHardness(5.0F)
            .setResistance(10.0F).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

    public static final BlockCrate PRIMATIVE_CRATE = new BlockCrate("primative_crate", 20, 30);
    public static final MythriaPane PRIMATIVE_WOODEN_WALL = new MythriaPane(Material.WOOD, true,
            "primative_wooden_wall", SoundType.WOOD, 40, 50, 5, 5);

    public static final Block THATCH_BLOCK = new MythriaBlock(Material.WOOD, "thatch_block", SoundType.PLANT,
            8, 20, 20, 60);
    public static final BlockStairs THATCH_STAIR = new MythriaStairs(THATCH_BLOCK.getDefaultState(), "thatch_stair",
            6, 15);

    public static final BlockPrimativeCraftingTable PRIMATIVE_CRAFTING_TABLE = new BlockPrimativeCraftingTable(Material.WOOD,
            "primative_crafting_table", 45, 50);

    public static final BlockGroundCover GROUND_STICK = new BlockGroundCover(Material.WOOD, "ground_stick", SoundType.WOOD,
            2, 10, Items.STICK);
    public static final BlockGroundCover GROUND_STICK_PILE = new BlockGroundCover(Material.WOOD, "ground_stick_pile",
            SoundType.WOOD, 8, 40, MythriaItems.BUNDLE_OF_STICKS);

    public static final BlockGroundCover ROCK = new BlockGroundCover(Material.WOOD, "rock",
            SoundType.STONE, 4, 25, MythriaItems.ROCK);

    public static final BlockPitKiln PIT_KILN = new BlockPitKiln(Material.WOOD, "pit_kiln", SoundType.STONE,
            50,25);

    //Devices
    public static final BlockSawHorse SAW_HORSE = new BlockSawHorse(Material.WOOD, "saw_horse", SoundType.WOOD, 50, 50);

    public static final BlockMythriaFurnace FURNACE = new BlockMythriaFurnace(Material.ROCK, "furnace", 80, 50);
    public static final BlockAnvil BRICK_ANVIL = new BlockAnvil(Material.ROCK, "stone_brick_anvil", SoundType.STONE, 50, 50, 0);
    public static final BlockAnvil BRONZE_ANVIL = new BlockAnvil(Material.IRON, "bronze_anvil", SoundType.METAL, 50, 50, 1);
    public static final BlockAnvil IRON_ANVIL = new BlockAnvil(Material.IRON, "iron_anvil", SoundType.METAL, 50, 50, 2);

    public static final BlockIngotPile INGOT_PILE = new BlockIngotPile(Material.IRON, "ingot_pile", SoundType.METAL, 50, 50);
    public static final BlockBrickPile BRICK_PILE = new BlockBrickPile(Material.ROCK, "brick_pile", SoundType.STONE, 50, 50);

    @SubscribeEvent
    public static void RegisterBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(CAMPFIRE);

        event.getRegistry().register(COPPER_ORE);
        event.getRegistry().register(TIN_ORE);
        event.getRegistry().register(IRON_ORE);
        event.getRegistry().register(SILVER_ORE);
        event.getRegistry().register(GOLD_ORE);
        event.getRegistry().register(PLATINUM_ORE);
        event.getRegistry().register(TITANIUM_ORE);
        event.getRegistry().register(TUNGSTEN_ORE);

        event.getRegistry().register(PRIMATIVE_CRATE);
        event.getRegistry().register(PRIMATIVE_WOODEN_WALL);
        event.getRegistry().register(IMPROVED_CAMPFIRE);
        event.getRegistry().register(COPPER_BLOCK);
        event.getRegistry().register(PRIMATIVE_CRAFTING_TABLE);
        event.getRegistry().register(THATCH_BLOCK);
        event.getRegistry().register(THATCH_STAIR);
        event.getRegistry().register(GROUND_STICK);
        event.getRegistry().register(GROUND_STICK_PILE);
        event.getRegistry().register(ROCK);
        event.getRegistry().register(PIT_KILN);
        event.getRegistry().register(FURNACE);

        event.getRegistry().register(BRICK_ANVIL);
        event.getRegistry().register(BRONZE_ANVIL);
        event.getRegistry().register(IRON_ANVIL);

        event.getRegistry().register(SAW_HORSE);

        event.getRegistry().register(INGOT_PILE);
    }

    @SubscribeEvent
    public static void RegisterItemBlocks(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new MythriaItemBlock(CAMPFIRE));

        event.getRegistry().register(new MythriaItemBlock(COPPER_ORE));
        event.getRegistry().register(new MythriaItemBlock(TIN_ORE));
        event.getRegistry().register(new MythriaItemBlock(IRON_ORE));
        event.getRegistry().register(new MythriaItemBlock(SILVER_ORE));
        event.getRegistry().register(new MythriaItemBlock(GOLD_ORE));
        event.getRegistry().register(new MythriaItemBlock(PLATINUM_ORE));
        event.getRegistry().register(new MythriaItemBlock(TITANIUM_ORE));
        event.getRegistry().register(new MythriaItemBlock(TUNGSTEN_ORE));

        event.getRegistry().register(new MythriaItemBlock(PRIMATIVE_CRATE));
        event.getRegistry().register(new MythriaItemBlock(PRIMATIVE_WOODEN_WALL));
        event.getRegistry().register(new MythriaItemBlock(IMPROVED_CAMPFIRE));
        event.getRegistry().register(new MythriaItemBlock(COPPER_BLOCK));
        event.getRegistry().register(new MythriaItemBlock(PRIMATIVE_CRAFTING_TABLE));
        event.getRegistry().register(new MythriaItemBlock(THATCH_BLOCK));
        event.getRegistry().register(new MythriaItemBlock(THATCH_STAIR));
        event.getRegistry().register(new MythriaItemBlock(FURNACE));

        event.getRegistry().register(new MythriaItemBlock(BRICK_ANVIL));
        event.getRegistry().register(new MythriaItemBlock(BRONZE_ANVIL));
        event.getRegistry().register(new MythriaItemBlock(IRON_ANVIL));

        event.getRegistry().register(new MythriaItemBlock(SAW_HORSE));
    }

    public static void RegisterRenders() {
        RegisterRender(CAMPFIRE);

        RegisterRender(COPPER_ORE);
        RegisterRender(TIN_ORE);
        RegisterRender(IRON_ORE);
        RegisterRender(SILVER_ORE);
        RegisterRender(GOLD_ORE);
        RegisterRender(PLATINUM_ORE);
        RegisterRender(TITANIUM_ORE);
        RegisterRender(TUNGSTEN_ORE);

        RegisterRender(PRIMATIVE_CRATE);
        RegisterRender(PRIMATIVE_WOODEN_WALL);
        RegisterRender(IMPROVED_CAMPFIRE);
        RegisterRender(COPPER_BLOCK);
        RegisterRender(PRIMATIVE_CRAFTING_TABLE);
        RegisterRender(THATCH_BLOCK);
        RegisterRender(THATCH_STAIR);
        RegisterRender(FURNACE);

        RegisterRender(BRICK_ANVIL);
        RegisterRender(BRONZE_ANVIL);
        RegisterRender(IRON_ANVIL);

        RegisterRender(SAW_HORSE);
    }

    public static void RegisterRender(final Block b) {
        final Item item = Item.getItemFromBlock(b);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
