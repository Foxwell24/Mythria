package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Enum.LockType;
import me.Jonathon594.Mythria.Enum.Wood;
import me.Jonathon594.Mythria.Interface.IMetaLookup;
import me.Jonathon594.Mythria.Interface.IVariantData;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class MythriaItems {
    private static ArrayList<Item> mythriaItems = new ArrayList<>();

    public static final ToolMaterial BRONZE_TM = EnumHelper.addToolMaterial("bronze", 2, 200, 5.0f, 1.5f, 10);
    public static final ToolMaterial STEEL_TM = EnumHelper.addToolMaterial("steel", 2, 480, 7.0f, 2.5f, 14);

    //Daggers
    public static final Item STONE_DAGGER = new ItemDagger("stone_dagger", ToolMaterial.STONE, 3);
    public static final Item BRONZE_DAGGER = new ItemDagger("bronze_dagger", BRONZE_TM, 4);
    public static final Item IRON_DAGGER = new ItemDagger("iron_dagger", ToolMaterial.IRON, 2);
    public static final Item STEEL_DAGGER = new ItemDagger("steel_dagger", STEEL_TM, 2);
    public static final Item GOLDEN_DAGGER = new ItemDagger("golden_dagger", ToolMaterial.GOLD, 1);
    public static final Item DIAMOND_DAGGER = new ItemDagger("diamond_dagger", ToolMaterial.DIAMOND, 1);
    public static final Item OBSIDIAN_DAGGER = new ItemDagger("obsidian_dagger", ToolMaterial.DIAMOND, 4);

    //Bronze Tools
    public static final Item BRONZE_SHOVEL = new MythriaItemSpade("bronze_shovel", BRONZE_TM, 9);
    public static final Item BRONZE_AXE = new MythriaItemAxe("bronze_axe", BRONZE_TM, -3.1f, 9, 8f);
    public static final Item BRONZE_PICKAXE = new MythriaItemPickaxe("bronze_pickaxe", BRONZE_TM, 9);
    public static final Item BRONZE_SWORD = new MythriaItemSword("bronze_sword", BRONZE_TM,9);
    public static final Item BRONZE_HOE = new MythriaItemHoe("bronze_hoe", BRONZE_TM, 5);
    public static final Item BRONZE_CHISEL = new MythriaItemChisel(1, -2.0f, BRONZE_TM, "bronze_chisel", 2);
    public static final Item BRONZE_HAMMER = new MythriaItemHammer(6, -3.5f, BRONZE_TM, "bronze_hammer", 10);
    public static final Item BRONZE_SAW = new MythriaItemSaw(0, -3.5f, BRONZE_TM, "bronze_saw", 10);

    //Iron
    public static final Item IRON_CHISEL = new MythriaItemChisel(1, -1.5f, STEEL_TM, "iron_chisel", 2);
    public static final Item IRON_HAMMER = new MythriaItemHammer(7, -3.0f, STEEL_TM, "iron_hammer", 10);
    public static final Item IRON_SAW = new MythriaItemSaw(-1, -3.5f, STEEL_TM, "iron_saw", 10);

    //Steel Tools
    public static final Item STEEL_SHOVEL = new MythriaItemSpade("steel_shovel", STEEL_TM, 9);
    public static final Item STEEL_AXE = new MythriaItemAxe("steel_axe", STEEL_TM, -3.1f, 9, 8.5f);
    public static final Item STEEL_PICKAXE = new MythriaItemPickaxe("steel_pickaxe", STEEL_TM, 9);
    public static final Item STEEL_SWORD = new MythriaItemSword("steel_sword", STEEL_TM,9);
    public static final Item STEEL_HOE = new MythriaItemHoe("steel_hoe", STEEL_TM, 5);
    public static final Item STEEL_CHISEL = new MythriaItemChisel(1, -1.5f, STEEL_TM, "steel_chisel", 2);
    public static final Item STEEL_HAMMER = new MythriaItemHammer(7, -3.0f, STEEL_TM, "steel_hammer", 10);
    public static final Item STEEL_SAW = new MythriaItemSaw(1, -2.5f, STEEL_TM, "steel_saw", 10);

    //Ingots
    public static final Item BRONZE_INGOT = new MythriaIngot("bronze_ingot", 2, SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.INGOT);
    public static final Item BRONZE_DOUBLE_INGOT = new MythriaIngot("bronze_double_ingot", 4, SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.DOUBLE_INGOT);
    public static final Item BRONZE_SHEET = new MythriaIngot("bronze_sheet", 4, SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.SHEET);
    public static final Item BRONZE_DOUBLE_SHEET = new MythriaIngot("bronze_double_sheet", 8, SmeltingRecipe.EnumMetal.BRONZE, SmithingManager.EnumMetalShape.DOUBLE_SHEET);

    public static final Item IRON_INGOT = new MythriaIngot("iron_ingot", 2, SmeltingRecipe.EnumMetal.IRON, SmithingManager.EnumMetalShape.INGOT);
    public static final Item IRON_DOUBLE_INGOT = new MythriaIngot("iron_double_ingot", 4, SmeltingRecipe.EnumMetal.IRON, SmithingManager.EnumMetalShape.DOUBLE_INGOT);
    public static final Item IRON_SHEET = new MythriaIngot("iron_sheet", 4, SmeltingRecipe.EnumMetal.IRON, SmithingManager.EnumMetalShape.SHEET);
    public static final Item IRON_DOUBLE_SHEET = new MythriaIngot("iron_double_sheet", 8, SmeltingRecipe.EnumMetal.IRON, SmithingManager.EnumMetalShape.DOUBLE_SHEET);

    public static final Item STEEL_INGOT = new MythriaIngot("steel_ingot", 2, SmeltingRecipe.EnumMetal.STEEL, SmithingManager.EnumMetalShape.INGOT);
    public static final Item STEEL_DOUBLE_INGOT = new MythriaIngot("steel_double_ingot", 4, SmeltingRecipe.EnumMetal.STEEL, SmithingManager.EnumMetalShape.DOUBLE_INGOT);
    public static final Item STEEL_SHEET = new MythriaIngot("steel_sheet", 4, SmeltingRecipe.EnumMetal.STEEL, SmithingManager.EnumMetalShape.SHEET);
    public static final Item STEEL_DOUBLE_SHEET = new MythriaIngot("steel_double_sheet", 8, SmeltingRecipe.EnumMetal.STEEL, SmithingManager.EnumMetalShape.DOUBLE_SHEET);

    public static final Item SILVER_INGOT = new MythriaIngot("silver_ingot", 3, SmeltingRecipe.EnumMetal.SILVER, SmithingManager.EnumMetalShape.INGOT);
    public static final Item COPPER_INGOT = new MythriaIngot("copper_ingot", 3, SmeltingRecipe.EnumMetal.COPPER, SmithingManager.EnumMetalShape.INGOT);
    public static final Item GOLD_INGOT = new MythriaIngot("gold_ingot", 3, SmeltingRecipe.EnumMetal.GOLD, SmithingManager.EnumMetalShape.INGOT);

    public static final Item GOLD_DOUBLE_INGOT = new MythriaIngot("gold_double_ingot", 6, SmeltingRecipe.EnumMetal.GOLD, SmithingManager.EnumMetalShape.DOUBLE_INGOT);


    public static final Item GOLD_COIN = new MythriaCoin("gold_coin", 0.1);
    public static final Item SILVER_COIN = new MythriaCoin("silver_coin", 0.1);
    public static final Item COPPER_COIN = new MythriaCoin("copper_coin", 0.1);

    public static final Item MOLD_INGOT = new ItemMoldEmpty("ceramic_mold_ingot", 1, 500, SmithingManager.EnumMetalShape.INGOT);
    public static final Item MOLD_AXE = new ItemMoldEmpty("ceramic_mold_axe", 1, 500, SmithingManager.EnumMetalShape.AXE);
    public static final Item MOLD_PICK = new ItemMoldEmpty("ceramic_mold_pick", 1, 500, SmithingManager.EnumMetalShape.PICK);
    public static final Item MOLD_SHOVEL = new ItemMoldEmpty("ceramic_mold_shovel", 1, 500, SmithingManager.EnumMetalShape.SHOVEL);
    public static final Item MOLD_SWORD = new ItemMoldEmpty("ceramic_mold_sword", 1, 500, SmithingManager.EnumMetalShape.SWORD);
    public static final Item MOLD_HOE = new ItemMoldEmpty("ceramic_mold_hoe", 1, 300, SmithingManager.EnumMetalShape.HOE);
    public static final Item MOLD_DAGGER = new ItemMoldEmpty("ceramic_mold_dagger", 1, 200, SmithingManager.EnumMetalShape.DAGGER);
    public static final Item MOLD_HAMMER = new ItemMoldEmpty("ceramic_mold_hammer", 1, 500, SmithingManager.EnumMetalShape.HAMMER);
    public static final Item MOLD_CHISEL = new ItemMoldEmpty("ceramic_mold_chisel", 1, 100, SmithingManager.EnumMetalShape.CHISEL);
    public static final Item MOLD_SAW = new ItemMoldEmpty("ceramic_mold_saw", 1, 100, SmithingManager.EnumMetalShape.SAW);

    public static final Item CERAMIC_VESSEL = new ItemCeramicVessel("ceramic_vessel", 2);


    //Molds
    public static final Item MOLD_INGOT_CLAY = new ItemPottery("clay_mold_ingot", 1, MOLD_INGOT);
    public static final Item MOLD_AXE_CLAY = new ItemPottery("clay_mold_axe", 1, MOLD_AXE);
    public static final Item MOLD_PICK_CLAY = new ItemPottery("clay_mold_pick", 1, MOLD_PICK);
    public static final Item MOLD_SHOVEL_CLAY = new ItemPottery("clay_mold_shovel", 1, MOLD_SHOVEL);
    public static final Item MOLD_SWORD_CLAY = new ItemPottery("clay_mold_sword", 1, MOLD_SWORD);
    public static final Item MOLD_HOE_CLAY = new ItemPottery("clay_mold_hoe", 1, MOLD_HOE);
    public static final Item MOLD_DAGGER_CLAY = new ItemPottery("clay_mold_dagger", 1, MOLD_DAGGER);
    public static final Item MOLD_HAMMER_CLAY = new ItemPottery("clay_mold_hammer", 1, MOLD_HAMMER);
    public static final Item MOLD_CHISEL_CLAY = new ItemPottery("clay_mold_chisel", 1, MOLD_CHISEL);
    public static final Item MOLD_SAW_CLAY = new ItemPottery("clay_mold_saw", 1, MOLD_SAW);

    public static final Item CLAY_BRICK = new ItemPottery("clay_brick", 1, Items.BRICK);
    public static final Item CLAY_VESSEL = new ItemPottery("clay_vessel", 2, MythriaItems.CERAMIC_VESSEL);

    //Copper Tool Heads
    public static final Item BRONZE_SWORD_BLADE = new ItemToolHead("bronze_sword_blade", 8, MythriaItems.BRONZE_SWORD);
    public static final Item BRONZE_AXE_HEAD = new ItemToolHead("bronze_axe_head", 8, MythriaItems.BRONZE_AXE);
    public static final Item BRONZE_PICKAXE_HEAD = new ItemToolHead("bronze_pickaxe_head", 8, MythriaItems.BRONZE_PICKAXE);
    public static final Item BRONZE_SHOVEL_HEAD = new ItemToolHead("bronze_shovel_head", 8, MythriaItems.BRONZE_SHOVEL);
    public static final Item BRONZE_HOE_HEAD = new ItemToolHead("bronze_hoe_head", 6, MythriaItems.BRONZE_HOE);
    public static final Item BRONZE_DAGGER_BLADE = new ItemToolHead("bronze_dagger_blade", 5, MythriaItems.BRONZE_DAGGER);
    public static final Item BRONZE_HAMMER_HEAD = new ItemToolHead("bronze_hammer_head", 10, MythriaItems.BRONZE_HAMMER);
    public static final Item BRONZE_CHISEL_HEAD = new ItemToolHead("bronze_chisel_head", 1, MythriaItems.BRONZE_CHISEL);
    public static final Item BRONZE_SAW_HEAD = new ItemToolHead("bronze_saw_blade", 1, MythriaItems.BRONZE_SAW);

    //Iron Tool Heads
    public static final Item IRON_SWORD_BLADE = new ItemToolHead("iron_sword_blade", 8, Items.IRON_SWORD);
    public static final Item IRON_AXE_HEAD = new ItemToolHead("iron_axe_head", 8, Items.IRON_AXE);
    public static final Item IRON_PICKAXE_HEAD = new ItemToolHead("iron_pickaxe_head", 8, Items.IRON_PICKAXE);
    public static final Item IRON_SHOVEL_HEAD = new ItemToolHead("iron_shovel_head", 8, Items.IRON_SHOVEL);
    public static final Item IRON_HOE_HEAD = new ItemToolHead("iron_hoe_head", 6, Items.IRON_HOE);
    public static final Item IRON_DAGGER_BLADE = new ItemToolHead("iron_dagger_blade", 5, MythriaItems.IRON_DAGGER);
    public static final Item IRON_HAMMER_HEAD = new ItemToolHead("iron_hammer_head", 10, MythriaItems.IRON_HAMMER);
    public static final Item IRON_CHISEL_HEAD = new ItemToolHead("iron_chisel_head", 1, MythriaItems.IRON_CHISEL);
    public static final Item IRON_SAW_HEAD = new ItemToolHead("iron_saw_blade", 1, MythriaItems.IRON_SAW);

    public static final Item STEEL_SWORD_BLADE = new ItemToolHead("steel_sword_blade", 8, MythriaItems.STEEL_SWORD);
    public static final Item STEEL_AXE_HEAD = new ItemToolHead("steel_axe_head", 8, MythriaItems.STEEL_AXE);
    public static final Item STEEL_PICKAXE_HEAD = new ItemToolHead("steel_pickaxe_head", 8, MythriaItems.STEEL_PICKAXE);
    public static final Item STEEL_SHOVEL_HEAD = new ItemToolHead("steel_shovel_head", 8, MythriaItems.STEEL_SHOVEL);
    public static final Item STEEL_HOE_HEAD = new ItemToolHead("steel_hoe_head", 6, MythriaItems.STEEL_HOE);
    public static final Item STEEL_DAGGER_BLADE = new ItemToolHead("steel_dagger_blade", 5, MythriaItems.STEEL_DAGGER);
    public static final Item STEEL_HAMMER_HEAD = new ItemToolHead("steel_hammer_head", 10, MythriaItems.STEEL_HAMMER);
    public static final Item STEEL_CHISEL_HEAD = new ItemToolHead("steel_chisel_head", 1, MythriaItems.STEEL_CHISEL);
    public static final Item STEEL_SAW_HEAD = new ItemToolHead("steel_saw_blade", 1, MythriaItems.STEEL_SAW);

    public static final Item MOLD_INGOT_BRONZE = new ItemFilledMold("ceramic_mold_ingot_bronze", 9, MOLD_INGOT, BRONZE_INGOT);
    public static final Item MOLD_AXE_BRONZE = new ItemFilledMold("ceramic_mold_axe_bronze", 9, MOLD_AXE, BRONZE_AXE_HEAD);
    public static final Item MOLD_PICK_BRONZE = new ItemFilledMold("ceramic_mold_pick_bronze", 9, MOLD_PICK, BRONZE_PICKAXE_HEAD);
    public static final Item MOLD_SHOVEL_BRONZE = new ItemFilledMold("ceramic_mold_shovel_bronze", 9, MOLD_SHOVEL, BRONZE_SHOVEL_HEAD);
    public static final Item MOLD_SWORD_BRONZE = new ItemFilledMold("ceramic_mold_sword_bronze", 9, MOLD_SWORD, BRONZE_SWORD_BLADE);
    public static final Item MOLD_HOE_BRONZE = new ItemFilledMold("ceramic_mold_hoe_bronze", 9, MOLD_HOE, BRONZE_HOE_HEAD);
    public static final Item MOLD_DAGGER_BRONZE = new ItemFilledMold("ceramic_mold_dagger_bronze", 9, MOLD_DAGGER, BRONZE_DAGGER_BLADE);
    public static final Item MOLD_HAMMER_BRONZE = new ItemFilledMold("ceramic_mold_hammer_bronze", 9, MOLD_HAMMER, BRONZE_HAMMER_HEAD);
    public static final Item MOLD_CHISEL_BRONZE = new ItemFilledMold("ceramic_mold_chisel_bronze", 9, MOLD_CHISEL, BRONZE_CHISEL_HEAD);
    public static final Item MOLD_SAW_BRONZE = new ItemFilledMold("ceramic_mold_saw_bronze", 9, MOLD_SAW, BRONZE_SAW_HEAD);

    public static final Item MOLD_INGOT_IRON = new ItemFilledMold("ceramic_mold_ingot_iron", 9, MOLD_INGOT, IRON_INGOT);
    public static final Item MOLD_INGOT_STEEL = new ItemFilledMold("ceramic_mold_ingot_steel", 9, MOLD_INGOT, STEEL_INGOT);

    public static final Item MOLD_INGOT_COPPER = new ItemFilledMold("ceramic_mold_ingot_copper", 9, MOLD_INGOT, COPPER_INGOT);
    public static final Item MOLD_INGOT_SILVER = new ItemFilledMold("ceramic_mold_ingot_silver", 9, MOLD_INGOT, SILVER_INGOT);
    public static final Item MOLD_INGOT_GOLD = new ItemFilledMold("ceramic_mold_ingot_gold", 9, MOLD_INGOT, GOLD_INGOT);

    //Doors
    public static final ItemDoor OAK_DOOR = new ItemDoor("oak_door", 50, MythriaBlocks.OAK_DOOR);
    public static final ItemDoor ACACIA_DOOR = new ItemDoor("acacia_door", 50, MythriaBlocks.ACACIA_DOOR);
    public static final ItemDoor BIRCH_DOOR = new ItemDoor("birch_door", 50, MythriaBlocks.BIRCH_DOOR);
    public static final ItemDoor DARK_OAK_DOOR = new ItemDoor("dark_oak_door", 50, MythriaBlocks.DARK_OAK_DOOR);
    public static final ItemDoor SPRUCE_DOOR = new ItemDoor("spruce_door", 50, MythriaBlocks.SPRUCE_DOOR);
    public static final ItemDoor JUNGLE_DOOR = new ItemDoor("jungle_door", 50, MythriaBlocks.JUNGLE_DOOR);

    public static final ArmorMaterial PRIMATIVE_AM = EnumHelper.addArmorMaterial("primative", "mythria:primative", 3,
            new int[]{0, 2, 1, 0}, 2, SoundEvents.BLOCK_CLOTH_PLACE, 0.0f)
            .setRepairItem(new ItemStack(MythriaItems.BRONZE_INGOT, 1));

    public static final MythriaItemArmor PRIMATIVE_BANDOLIER = new MythriaItemArmor("primative_bandolier", PRIMATIVE_AM,
            6, EntityEquipmentSlot.CHEST, 2, 9, 0);
    public static final MythriaItemArmor PRIMATIVE_BELT = new MythriaItemArmor("primative_belt", PRIMATIVE_AM, 6,
            EntityEquipmentSlot.LEGS, 1, 3, 5);
    public static final MythriaItem BUNDLE_OF_STICKS = new MythriaItem("bundle_of_sticks", 4, 1);

    private static final ArmorMaterial BRONZE_AM = EnumHelper.addArmorMaterial("bronze", "mythria:bronze", 12,
            new int[]{1, 4, 5, 1}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    private static final ArmorMaterial STEEL_AM = EnumHelper.addArmorMaterial("steel", "mythria:steel", 18,
            new int[]{2, 5, 6, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    public static final Item BRONZE_HELMET = new MythriaItemArmor("bronze_helmet", BRONZE_AM, 5,
            EntityEquipmentSlot.HEAD, 3, 0, 0);
    public static final Item BRONZE_CHESTPLATE = new MythriaItemArmor("bronze_chestplate", BRONZE_AM, 5,
            EntityEquipmentSlot.CHEST, 16, 3, 0);
    public static final Item BRONZE_LEGGINGS = new MythriaItemArmor("bronze_leggings", BRONZE_AM, 5,
            EntityEquipmentSlot.LEGS, 12, 3, 3);
    public static final Item BRONZE_BOOTS = new MythriaItemArmor("bronze_boots", BRONZE_AM, 5,
            EntityEquipmentSlot.FEET, 8, 0, 2);

    public static final Item STEEL_HELMET = new MythriaItemArmor("steel_helmet", STEEL_AM, 7,
            EntityEquipmentSlot.HEAD, 3, 0, 0);
    public static final Item STEEL_CHESTPLATE = new MythriaItemArmor("steel_chestplate", STEEL_AM, 7,
            EntityEquipmentSlot.CHEST, 16, 6, 0);
    public static final Item STEEL_LEGGINGS = new MythriaItemArmor("steel_leggings", STEEL_AM, 7,
            EntityEquipmentSlot.LEGS, 12, 3, 7);
    public static final Item STEEL_BOOTS = new MythriaItemArmor("steel_boots", STEEL_AM, 7,
            EntityEquipmentSlot.FEET, 8, 0, 2);

    //Tool Parts
    public static final Item TOOL_HANDLE = new MythriaItem("tool_handle", 4, 1);

    //Stone Tool Partss
    public static final Item STONE_HAMMER = new MythriaItemHammer(5, -4.0f, ToolMaterial.STONE, "stone_hammer", 15);
    public static final Item STONE_SAW = new MythriaItemSaw(1, 0.5f, ToolMaterial.STONE, "stone_saw", 15);

    public static final Item STONE_SWORD_BLADE = new ItemToolHead("stone_sword_blade", 14, Items.STONE_SWORD);
    public static final Item STONE_AXE_HEAD = new ItemToolHead("stone_axe_head", 14, Items.STONE_AXE);
    public static final Item STONE_PICKAXE_HEAD = new ItemToolHead("stone_pickaxe_head", 14, Items.STONE_PICKAXE);
    public static final Item STONE_SHOVEL_HEAD = new ItemToolHead("stone_shovel_head", 6, Items.STONE_SHOVEL);
    public static final Item STONE_HOE_HEAD = new ItemToolHead("stone_hoe_head", 9, Items.STONE_HOE);
    public static final Item STONE_DAGGER_BLADE = new ItemToolHead("stone_dagger_blade", 2, MythriaItems.STONE_DAGGER);
    public static final Item STONE_HAMMER_HEAD = new ItemToolHead("stone_hammer_head", 14, MythriaItems.STONE_HAMMER);
    public static final Item STONE_SAW_HEAD = new ItemToolHead("stone_saw_blade", 14, MythriaItems.STONE_SAW);

    public static final Item ROCK = new MythriaItem("rock", 4, 4);
    public static final Item STONE_BRICK = new MythriaItem("stone_brick_item", 1, 8);

    public static final Item LOG = new ItemLog("log", 10);
    public static final Item PLANK = new ItemPlank("plank", 5);

    //Ore
    public static final Item COPPER_ORE = new ItemOre("copper_ore_item", 3);
    public static final Item TIN_ORE = new ItemOre("tin_ore_item", 2);
    public static final Item IRON_ORE = new ItemOre("iron_ore_item", 5);
    public static final Item SILVER_ORE = new ItemOre("silver_ore_item", 5);
    public static final Item GOLD_ORE = new ItemOre("gold_ore_item", 20);
    public static final Item PLATINUM_ORE = new ItemOre("platinum_ore_item", 8);
    public static final Item TITANIUM_ORE = new ItemOre("titanium_ore_item", 12);
    public static final Item TUNGSTEN_ORE = new ItemOre("tungsten_ore_item", 14);

    public static final MythriaItem THATCH = new MythriaItem("thatch", 16, 1);

    public static final ItemWand OAK_WAND = new ItemWand("oak_wand", 0.2);

    @SubscribeEvent
    public static void Register(final RegistryEvent.Register<Item> event) {
        for(Item i : mythriaItems) {
            event.getRegistry().register(i);
        }
    }

    public static void RegisterRenders() {
        for(Item i : mythriaItems) {
            RegisterRender(i);
        }
    }

    public static void RegisterRender(final Item item) {
        if(!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation("mythria:" + item.getUnlocalizedName().substring(5), "inventory"));
        } else {
            IVariantData data = (IVariantData) item;
            registerItemWithVariants(item, data.getDefaultVariant());
        }
    }

    private static <T extends IMetaLookup> void registerItemWithVariants(Item item, T variant) {
        String variantName = variant.getID();
        NonNullList<ItemStack> subItems = NonNullList.create();
        item.getSubItems(CreativeTabs.SEARCH, subItems);
        for (ItemStack stack : subItems) {
            registerItemModelForMetal(item, stack.getMetadata(), variantName + "=" + variant.getByOrdinal(stack.getMetadata()));
        }
    }

    private static void registerItemModelForMetal(Item item, int metadata, String variant) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation("mythria:"+item.getUnlocalizedName().substring(5), variant));
    }

    public static void addItem(Item mythriaItem) {
        mythriaItems.add(mythriaItem);
    }
}
