package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.*;
import me.Jonathon594.Mythria.Enum.Alignment;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerkManager {
    private static List<TradeSkill> tradeList = new ArrayList<>();
    private static List<LifeSkill> lifeList = new ArrayList<>();
    private static List<CombatSkill> combatList = new ArrayList<>();
    private static List<MagicSkill> magicList = new ArrayList<>();

    public static List<BlessingPerk> getBlessingList() {
        return blessingList;
    }

    private static List<BlessingPerk> blessingList = new ArrayList<>();
    private static List<PersonallityTrait> personalityList = new ArrayList<>();

    public static void addCombatSkill(final CombatSkill combatSkill) {
        combatList.add(combatSkill);
    }

    public static void addLifeSkill(final LifeSkill lifeSkill) {
        lifeList.add(lifeSkill);
    }

    public static void addMagicSkill(final MagicSkill magicSkill) {
        magicList.add(magicSkill);
    }

    public static void addTradeSkill(final TradeSkill tradeSkill) {
        tradeList.add(tradeSkill);
    }

    public static void AttemptBuy(final EntityPlayer player, final Perk pa) {
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (player.isCreative()) {
            p.addAttribute(pa);
            return;
        }
        if (pa == null)
            return;
        Perk personality = p.getPersonality();
        if (personality != null && pa instanceof PersonallityTrait) {
            player.sendMessage(new TextComponentString(MythriaConst.ALREADY_PERSONALITY));
            return;
        }
        if (personality == null && !(pa instanceof PersonallityTrait)) {
            player.sendMessage(new TextComponentString(MythriaConst.NEED_PERSONALITY));
            return;
        }

        if (p.getPlayerSkills().contains(pa)) {
            player.sendMessage(new TextComponentString(MythriaConst.ALREADY_PERK));
            return; // Already Attribute;
        }
        if (!pa.hasRequiredAttributes(p)) {
            player.sendMessage(new TextComponentString(MythriaConst.NOT_HAVE_REQUIRED_PERK));
            return; // Not have required;
        }
        if (p.getBirthDay().getYearsFromCurrent() < pa.getMinimumAge()) {
            player.sendMessage(new TextComponentString(MythriaConst.TOO_YOUNG));
            return; // Too young;
        }
        if (!pa.hasRequiredSkills(p)) {
            player.sendMessage(new TextComponentString(MythriaConst.NOT_ENOUGH_LEVEL));
            return; // Level too low;
        }

        for(Map.Entry<Attribute, Integer> e : pa.getRequiredAttributes().entrySet()) {
            if(p.getAttribute(e.getKey()) < e.getValue()) {
                player.sendMessage(new TextComponentString(MythriaConst.NOT_ENOUGH_ATTRIBUTE_INVESTMENTS));
                return;
            }
        }

        p.addAttribute(pa);
        player.sendMessage(new TextComponentString(String.format(MythriaConst.PERK_OBTAINED, pa.getName())));
    }

    public static void CreateBlessings() {
        // Felixia
        // Blessings
        new BlessingPerk("FelixiaNoWither", AttributeFlag.FELIXIA_NO_WITHER);
        new BlessingPerk("FelixiaHealing", AttributeFlag.FELIXIA_HEALING);
        new BlessingPerk("FelixiaIntimidation", AttributeFlag.FELIXIA_INTIMIDATION);

        // Curses
        new BlessingPerk("FelixiaBurn", AttributeFlag.FELIXIA_BURN);

        // Eliana
        // Blessings
        new BlessingPerk("ElianaNoFall", AttributeFlag.ELIANA_NO_FALL);
        new BlessingPerk("ElianaBreathing", AttributeFlag.ELIANA_BREATHING);
        new BlessingPerk("ElianaFlight", AttributeFlag.ELIANA_FLIGHT);

        // Curses
        new BlessingPerk("ElianaSuffocate", AttributeFlag.ELIANA_SUFFOCATE);

        // Selina
        // Blessings
        new BlessingPerk("SelianNoNatureMobs", AttributeFlag.SELINA_NO_NATURE_MOBS);
        new BlessingPerk("SelinaProsperty", AttributeFlag.SELINA_PROSPERITY);
        new BlessingPerk("SelinaImmortality", AttributeFlag.SELINA_IMMORTALITY);

        // Curses
        new BlessingPerk("SelinaInfertility", AttributeFlag.SELINA_INFERTILITY);

        // Raika
        // Blessings
        new BlessingPerk("RaikaSpeed", AttributeFlag.RAIKA_SPEED);
        new BlessingPerk("RaikaElectrocute", AttributeFlag.RAIKA_ELECTROCUTE);
        new BlessingPerk("RaikaSmite", AttributeFlag.RAIKA_SMITE);

        // Curses
        new BlessingPerk("RaikaWrath", AttributeFlag.RAIKA_WRATH);

        // Melinias
        // Blessings
        new BlessingPerk("MeliniasNoMobs", AttributeFlag.MELINIAS_NO_MOBS);
        new BlessingPerk("MeliniasWaterBreathing", AttributeFlag.MELINIAS_WATER_BREATHING);
        new BlessingPerk("MeliniasWaterJet", AttributeFlag.MELINIAS_WATER_JET);

        // Curses
        new BlessingPerk("MeliniasWaterCurse", AttributeFlag.MELINIAS_WATER_CURSE);

        // Kasai
        // Blessings
        new BlessingPerk("KasaiNoMobs", AttributeFlag.KASAI_NO_MOBS);
        new BlessingPerk("KasaiNoFire", AttributeFlag.KASAI_NO_FIRE);
        new BlessingPerk("KasaiLavaJet", AttributeFlag.KASAI_LAVA_JET);

        // Curses
        new BlessingPerk("KasaiNetherCurse", AttributeFlag.KASAI_NETHER_CURSE);

        // Asana
        // Blessings
        new BlessingPerk("AsanaNoMobs", AttributeFlag.ASANA_NO_MOBS);
        new BlessingPerk("AsanaNoExplode", AttributeFlag.ASANA_NO_EXLODE);
        new BlessingPerk("AsanaEarthCrumple", AttributeFlag.ASANA_EARTH_CRUMPLE);

        // Curses
        new BlessingPerk("AsanaEarthPoison", AttributeFlag.ASANA_EARTH_POISON);

        // Melinias
        // Blessings
        new BlessingPerk("LilasiaNoMobs", AttributeFlag.LILASIA_NO_MOBS);
        new BlessingPerk("LilasiaShadowHealing", AttributeFlag.LILASIA_SHADOW_HEALING);
        new BlessingPerk("LilasiaReinforcements", AttributeFlag.LILASIA_REINFORCEMENTS);

        // Curses
        new BlessingPerk("LilasiaShadowCurse", AttributeFlag.LILASIA_SHADOW_CURSE);
    }

    public static void CreateCombats() {
        // Agility
        new CombatSkill("BasicAgility", Items.LEATHER_BOOTS, 1, 1, 1, 2, MythicSkills.AGILITY, 0, Alignment.AGILITY, "")
                .addRequiredAttribute(Attribute.DEXTERITY, 14).addAttributeFlag(AttributeFlag.DODGE);
        new CombatSkill("AdvancedAgility", Items.CHAINMAIL_BOOTS, 1, 2, 1, 0, MythicSkills.AGILITY, 25,
                Alignment.AGILITY, "BasicAgility")
                .addAttributeFlag(AttributeFlag.JUMP).setBonusFatigueMitigation(0.05)
                .addRequiredAttribute(Attribute.DEXTERITY, 18);
        new CombatSkill("SophisticatedAgility", Items.IRON_BOOTS, 1, 3, 1, 0, MythicSkills.AGILITY, 50,
                Alignment.AGILITY, "AdvancedAgility")
                .setBonusFatigueMitigation(0.1).addAttributeFlag(AttributeFlag.WALLJUMP1)
                .addRequiredAttribute(Attribute.DEXTERITY, 22).addRequiredAttribute(Attribute.ENDURANCE, 16);
        new CombatSkill("SuperiorAgility", Items.GOLDEN_BOOTS, 1, 4, 1, 0, MythicSkills.AGILITY, 75, Alignment.AGILITY,
                "SophisticatedAgility").setBonusFatigueMitigation(0.15)
                .addAttributeFlag(AttributeFlag.SOFTLANDING).addAttributeFlag(AttributeFlag.WALLJUMP2)
                .addRequiredAttribute(Attribute.DEXTERITY, 34).addRequiredAttribute(Attribute.ENDURANCE, 24);
        new CombatSkill("MasterlyAgility", Items.DIAMOND_BOOTS, 1, 5, 1, 0, MythicSkills.AGILITY, 100,
                Alignment.AGILITY, "SuperiorAgility")
                .setBonusFatigueMitigation(0.20)
                .addRequiredAttribute(Attribute.DEXTERITY, 52).addRequiredAttribute(Attribute.ENDURANCE, 32);
        new CombatSkill("LegendaryAgility", Items.DIAMOND_BOOTS, 1, 6, 1, 0, MythicSkills.AGILITY, 125,
                Alignment.AGILITY, "MasterlyAgility")
                .setBonusFatigueMitigation(0.25)
                .addRequiredAttribute(Attribute.DEXTERITY, 60).addRequiredAttribute(Attribute.ENDURANCE, 40);

        // Swords
        new CombatSkill("BasicSwordsmanship", Items.WOODEN_SWORD, 2, 1, 1, 2, MythicSkills.SWORDS, 0, Alignment.MELEE,
                "").addAttributeFlag(AttributeFlag.PARRY)
                .addRequiredAttribute(Attribute.DEXTERITY, 14).addRequiredAttribute(Attribute.STRENGTH, 12);
        new CombatSkill("AdvancedSwordsmanship", Items.STONE_SWORD, 2, 2, 1, 0, MythicSkills.SWORDS, 25,
                Alignment.MELEE, "BasicSwordsmanship").addAttributeFlag(AttributeFlag.CRITICAL_SWORD)
                .addRequiredAttribute(Attribute.DEXTERITY, 18).addRequiredAttribute(Attribute.STRENGTH, 20);
        new CombatSkill("SophisticatedSwordsmanship", Items.IRON_SWORD, 2, 3, 1, 0, MythicSkills.SWORDS, 50,
                Alignment.MELEE, "AdvancedSwordsmanship").addAttributeFlag(AttributeFlag.SWORD_MASTERY)
                .addRequiredAttribute(Attribute.DEXTERITY, 24).addRequiredAttribute(Attribute.STRENGTH, 28);
        new CombatSkill("SuperiorSwordsmanship", Items.GOLDEN_SWORD, 2, 4, 1, 0, MythicSkills.SWORDS, 75,
                Alignment.MELEE, "SophisticatedSwordsmanship").addAttributeFlag(AttributeFlag.COUNTERSTRIKE)
                .addRequiredAttribute(Attribute.DEXTERITY, 32).addRequiredAttribute(Attribute.STRENGTH, 36);
        new CombatSkill("MasterlySwordsmanship", Items.DIAMOND_SWORD, 2, 5, 1, 0, MythicSkills.SWORDS, 100,
                Alignment.MELEE, "SuperiorSwordsmanship").addAttributeFlag(AttributeFlag.FATAL_SWORD)
                .addRequiredAttribute(Attribute.DEXTERITY, 42).addRequiredAttribute(Attribute.STRENGTH, 46);
        new CombatSkill("LegendarySwordsmanship", Items.DIAMOND_SWORD, 2, 6, 1, 0, MythicSkills.SWORDS, 125,
                Alignment.MELEE, "MasterlySwordsmanship").addAttributeFlag(AttributeFlag.DECAPICATE)
                .addRequiredAttribute(Attribute.DEXTERITY, 56).addRequiredAttribute(Attribute.STRENGTH, 60);

        // Axes
        new CombatSkill("BasicAxes", Items.STONE_AXE, 3, 1, 1, 2, MythicSkills.AXES, 0, Alignment.MELEE, "")
                .addAttributeFlag(AttributeFlag.AXE_MASTERY)
                .addRequiredAttribute(Attribute.STRENGTH, 18);
        new CombatSkill("AdvancedAxes", MythriaItems.BRONZE_AXE, 3, 2, 1, 0, MythicSkills.AXES, 25, Alignment.MELEE,
                "BasicAxes").addAttributeFlag(AttributeFlag.AXE_RAGE)
                .addRequiredAttribute(Attribute.STRENGTH, 25);
        new CombatSkill("SophisticatedAxes", Items.IRON_AXE, 3, 3, 0, 0, MythicSkills.AXES, 50, Alignment.MELEE,
                "AdvancedAxes").addAttributeFlag(AttributeFlag.AXE_SMASH)
                .addRequiredAttribute(Attribute.STRENGTH, 42);
        new CombatSkill("SuperiorAxes", MythriaItems.STEEL_AXE, 3, 4, 1, 0, MythicSkills.AXES, 75, Alignment.MELEE,
                "SophisticatedAxes").addAttributeFlag(AttributeFlag.CRITICAL_AXE)
                .addRequiredAttribute(Attribute.STRENGTH, 54);
        new CombatSkill("MasterlyAxes", Items.GOLDEN_AXE, 3, 5, 1, 0, MythicSkills.AXES, 100, Alignment.MELEE,
                "SuperiorAxes").addAttributeFlag(AttributeFlag.FATAL_AXE)
                .addRequiredAttribute(Attribute.STRENGTH, 62);
        new CombatSkill("LegendaryAxes", Items.DIAMOND_AXE, 3, 6, 1, 0, MythicSkills.AXES, 125, Alignment.MELEE,
                "MasterlyAxes").addAttributeFlag(AttributeFlag.LEGENDARY_AXE)
                .addRequiredAttribute(Attribute.STRENGTH, 74);

        // Blocking
        new CombatSkill("BasicBlocking", Items.SHIELD, 4, 1, 1, 2, MythicSkills.BLOCK, 0, Alignment.MELEE, "")
                .addAttributeFlag(AttributeFlag.STAGGER)
                .addRequiredAttribute(Attribute.ENDURANCE, 14).addRequiredAttribute(Attribute.VIGOR, 16);
        new CombatSkill("AdvancedBlocking", Items.SHIELD, 4, 2, 1, 0, MythicSkills.BLOCK, 25, Alignment.MELEE,
                "BasicBlocking").addAttributeFlag(AttributeFlag.FORTIFY)
                .addRequiredAttribute(Attribute.ENDURANCE, 20).addRequiredAttribute(Attribute.VIGOR, 24);
        new CombatSkill("SophisticatedBlocking", Items.SHIELD, 4, 3, 1, 0, MythicSkills.BLOCK, 50, Alignment.MELEE,
                "AdvancedBlocking").addAttributeFlag(AttributeFlag.SHIELD_BASH).addAttributeFlag(AttributeFlag.SHIELD_ATTACKING);
        new CombatSkill("SuperiorBlocking", Items.SHIELD, 4, 4, 1, 0, MythicSkills.BLOCK, 75, Alignment.MELEE,
                "SophisticatedBlocking").addAttributeFlag(AttributeFlag.STAGGER_2)
                .addRequiredAttribute(Attribute.ENDURANCE, 24).addRequiredAttribute(Attribute.VIGOR, 36);
        new CombatSkill("MasterlyBlocking", Items.SHIELD, 4, 5, 1, 0, MythicSkills.BLOCK, 100, Alignment.MELEE,
                "SuperiorBlocking").addAttributeFlag(AttributeFlag.SHIELD_DISARM)
                .addRequiredAttribute(Attribute.ENDURANCE, 34).addRequiredAttribute(Attribute.VIGOR, 40);
        new CombatSkill("LegendaryBlocking", Items.SHIELD, 4, 6, 1, 0, MythicSkills.BLOCK, 125, Alignment.MELEE,
                "MasterlyBlocking").addAttributeFlag(AttributeFlag.SHIELD_CHARGE)
                .addRequiredAttribute(Attribute.ENDURANCE, 40).addRequiredAttribute(Attribute.VIGOR, 52);

        // MartialArts
        new CombatSkill("BasicMartialArts", Blocks.PLANKS, 5, 1, 1, 2, MythicSkills.UNARMED, 0, Alignment.MARTIALARTS,
                "").addAttributeFlag(AttributeFlag.FIST_TRAINING_1)
                .addRequiredAttribute(Attribute.DEXTERITY, 24);
        new CombatSkill("AdvancedMartialArts", Blocks.STONE, 5, 2, 1, 0, MythicSkills.UNARMED, 25,
                Alignment.MARTIALARTS, "BasicMartialArts").addAttributeFlag(AttributeFlag.DEFLECT).addAttributeFlag(AttributeFlag.DAZING_PUNCH)
                .addRequiredAttribute(Attribute.DEXTERITY, 35);
        new CombatSkill("SophisticatedMartialArts", Blocks.IRON_BLOCK, 5, 3, 1, 0, MythicSkills.UNARMED, 50,
                Alignment.MARTIALARTS, "AdvancedMartialArts").addAttributeFlag(AttributeFlag.CRITICAL_FIST)
                .addRequiredAttribute(Attribute.DEXTERITY, 42);
        new CombatSkill("SuperiorMartialArts", Blocks.GOLD_BLOCK, 5, 4, 1, 0, MythicSkills.UNARMED, 75,
                Alignment.MARTIALARTS, "SophisticatedMartialArts").addAttributeFlag(AttributeFlag.DISARM)
                .addRequiredAttribute(Attribute.DEXTERITY, 54).addRequiredAttribute(Attribute.STRENGTH, 16);
        new CombatSkill("MasterlyMartialArts", Blocks.DIAMOND_BLOCK, 5, 5, 1, 0, MythicSkills.UNARMED, 100,
                Alignment.MARTIALARTS, "SuperiorMartialArts").addAttributeFlag(AttributeFlag.PRESSURE_POINT)
                .addRequiredAttribute(Attribute.DEXTERITY, 62).addRequiredAttribute(Attribute.STRENGTH, 28);
        new CombatSkill("LegendaryMartialArts", Blocks.OBSIDIAN, 5, 6, 1, 0, MythicSkills.UNARMED, 125,
                Alignment.MARTIALARTS, "MasterlyMartialArts").addAttributeFlag(AttributeFlag.CARDIAC_PUNCH)
                .addRequiredAttribute(Attribute.DEXTERITY, 70).addRequiredAttribute(Attribute.STRENGTH, 40);

        // Daggers
        new CombatSkill("BasicDaggers", MythriaItems.STONE_DAGGER, 6, 1, 1, 0, MythicSkills.DAGGERS, 0,
                Alignment.THIEVERY, "").addAttributeFlag(AttributeFlag.DAGGER_SKILL_1)
                .addRequiredAttribute(Attribute.DEXTERITY, 12);
        new CombatSkill("AdvancedDaggers", MythriaItems.STONE_DAGGER, 6, 2, 1, 0, MythicSkills.DAGGERS, 25,
                Alignment.THIEVERY, "BasicDaggers").addAttributeFlag(AttributeFlag.CRITICAL_DAGGER)
                .addRequiredAttribute(Attribute.DEXTERITY, 20);
        new CombatSkill("SophisticatedDaggers", MythriaItems.IRON_DAGGER, 6, 3, 1, 0, MythicSkills.DAGGERS, 50,
                Alignment.THIEVERY, "AdvancedDaggers").addAttributeFlag(AttributeFlag.DAGGER_SKILL_2)
                .addRequiredAttribute(Attribute.DEXTERITY, 32);
        new CombatSkill("SuperiorDaggers", MythriaItems.GOLDEN_DAGGER, 6, 4, 1, 0, MythicSkills.DAGGERS, 75,
                Alignment.THIEVERY, "SophisticatedDaggers").addAttributeFlag(AttributeFlag.DAGGER_SKILL_3)
                .addRequiredAttribute(Attribute.DEXTERITY, 42);
        new CombatSkill("MasterlyDaggers", MythriaItems.DIAMOND_DAGGER, 6, 5, 1, 0, MythicSkills.DAGGERS, 100,
                Alignment.THIEVERY, "SuperiorDaggers").addAttributeFlag(AttributeFlag.FATAL_DAGGER)
                .addRequiredAttribute(Attribute.DEXTERITY, 52);
        new CombatSkill("LegendaryDaggers", MythriaItems.OBSIDIAN_DAGGER, 6, 6, 1, 0, MythicSkills.DAGGERS, 125,
                Alignment.THIEVERY, "MasterlyDaggers").addAttributeFlag(AttributeFlag.THROAT_SLICE)
                .addRequiredAttribute(Attribute.DEXTERITY, 60);

        // Archery
        new CombatSkill("BasicArchery", Items.FEATHER, 7, 1, 1, 0, MythicSkills.ARCHERY, 0,
                Alignment.THIEVERY, "").addAttributeFlag(AttributeFlag.SKILLSHOT_1)
                .addRequiredAttribute(Attribute.DEXTERITY, 12).addRequiredAttribute(Attribute.STRENGTH, 12);
        new CombatSkill("AdvancedArchery", Items.STICK, 7, 2, 1, 0, MythicSkills.ARCHERY, 25,
                Alignment.THIEVERY, "BasicArchery").addAttributeFlag(AttributeFlag.ARROW_STUN)
                .addRequiredAttribute(Attribute.DEXTERITY, 18).addRequiredAttribute(Attribute.STRENGTH, 20);
        new CombatSkill("SophisticatedArchery", Items.FLINT, 7, 3, 1, 0, MythicSkills.ARCHERY, 50,
                Alignment.THIEVERY, "AdvancedArchery").addAttributeFlag(AttributeFlag.SKILLSHOT_2)
                .addRequiredAttribute(Attribute.DEXTERITY, 24).addRequiredAttribute(Attribute.STRENGTH, 28);
        new CombatSkill("SuperiorArchery", Items.STRING, 7, 4, 1, 0, MythicSkills.ARCHERY, 75,
                Alignment.THIEVERY, "SophisticatedArchery").addAttributeFlag(AttributeFlag.ARROW_BLIND)
                .addRequiredAttribute(Attribute.DEXTERITY, 30).addRequiredAttribute(Attribute.STRENGTH, 32);
        new CombatSkill("MasterlyArchery", Items.BOW, 7, 5, 1, 0, MythicSkills.ARCHERY, 100,
                Alignment.THIEVERY, "SuperiorArchery").addAttributeFlag(AttributeFlag.ARROW_WEAKEN)
                .addRequiredAttribute(Attribute.DEXTERITY, 36).addRequiredAttribute(Attribute.STRENGTH, 38);
        new CombatSkill("LegendaryArchery", Items.TIPPED_ARROW, 7, 6, 1, 0, MythicSkills.ARCHERY, 125,
                Alignment.THIEVERY, "MasterlyArchery").addAttributeFlag(AttributeFlag.ARROW_EXECUTE)
                .addRequiredAttribute(Attribute.DEXTERITY, 44).addRequiredAttribute(Attribute.STRENGTH, 46);

        // Heavy Weaponry
    }

    public static void CreateLifes() {
//                new LifeSkill("BasicThievery", Items.EMERALD, 3, 1, 1, 1, MythicSkills.THIEVERY, 0, Alignment.THIEVERY, "")
//                        .setMinimumAge(12).setLearnTime(1).addAttributeFlag(AttributeFlag.PICKPICKET)
//                        .addRequiredAttribute(Attribute.DEXTERITY, 17)
//                        .addRequiredAttribute(Attribute.INTELLIGENCE, 16);
        //        new LifeSkill("AdvancedThievery", Items.IRON_INGOT, 3, 2, 1, 0, MythicSkills.THIEVERY, 25, Alignment.THIEVERY,
        //                "BasicThievery").setLearnTime(2);
        //        new LifeSkill("SophisticatedThievery", Items.GOLD_NUGGET, 3, 3, 2, 0, MythicSkills.THIEVERY, 50,
        //                Alignment.THIEVERY, "AdvancedThievery").setLearnTime(3);
        //        new LifeSkill("SuperiorThievery", Items.GOLD_INGOT, 3, 4, 3, 0, MythicSkills.THIEVERY, 75, Alignment.THIEVERY,
        //                "SophisticatedThievery").setLearnTime(6);
        //        new LifeSkill("MasterlyThievery", Items.DIAMOND, 3, 5, 3, 0, MythicSkills.THIEVERY, 100, Alignment.THIEVERY,
        //                "SuperiorThievery").setLearnTime(18);
        //        new LifeSkill("GodlyThievery", Blocks.CHEST, 3, 6, 4, 0, MythicSkills.THIEVERY, 125, Alignment.THIEVERY,
        //                "MasterlyThievery").setLearnTime(36);
        //        new LifeSkill("BasicMedical", Items.POTIONITEM, 4, 1, 1, 1, MythicSkills.MEDICAL, 0, Alignment.MEDICAL, "")
        //                .setMinimumAge(16).setLearnTime(1);
        //        new LifeSkill("AdvancedMedical", Items.POTIONITEM, 4, 2, 1, 0, MythicSkills.MEDICAL, 25, Alignment.MEDICAL,
        //                "BasicMedical").setLearnTime(3);
        //        new LifeSkill("SophisticatedMedical", Items.POTIONITEM, 4, 3, 1, 0, MythicSkills.MEDICAL, 50, Alignment.MEDICAL,
        //                "AdvancedMedical").setLearnTime(6);
        //        new LifeSkill("SuperiorMedical", Items.POTIONITEM, 4, 4, 2, 0, MythicSkills.MEDICAL, 75, Alignment.MEDICAL,
        //                "SophisticatedMedical").setLearnTime(18);
        //        new LifeSkill("MasterlyMedical", Items.POTIONITEM, 4, 5, 2, 0, MythicSkills.MEDICAL, 100, Alignment.MEDICAL,
        //                "SuperiorMedical").setLearnTime(36);
//        new LifeSkill("BasicFishing", Items.FISHING_ROD, 5, 1, 1, 1, MythicSkills.FISHING, 0, Alignment.FISHING, "")
//                .setMinimumAge(10);
        //        new LifeSkill("AdvancedFishing", Items.FISHING_ROD, 5, 2, 1, 0, MythicSkills.FISHING, 25, Alignment.FISHING,
        //                "BasicFishing").setLearnTime(1);
        //        new LifeSkill("SophisticatedFishing", Items.FISHING_ROD, 5, 3, 1, 0, MythicSkills.FISHING, 50,
        //                Alignment.FISHING, "AdvancedFishing").setLearnTime(1);
        //        new LifeSkill("SuperiorFishing", Items.FISHING_ROD, 5, 4, 2, 0, MythicSkills.FISHING, 75, Alignment.FISHING,
        //                "SophisticatedFishing").setLearnTime(2);
        //        new LifeSkill("MasterlyFishing", Items.FISHING_ROD, 5, 5, 2, 0, MythicSkills.FISHING, 100, Alignment.FISHING,
        //                "SuperiorFishing").setLearnTime(3);
        //        new LifeSkill("BasicSailing", Blocks.WOOL, 6, 1, 1, 1, MythicSkills.SAILING, 0, Alignment.EXPLORATION, "")
        //                .setMinimumAge(17).setLearnTime(6);
        //        new LifeSkill("AdvancedSailing", Blocks.WOOL, 6, 2, 2, 0, MythicSkills.SAILING, 0, Alignment.EXPLORATION,
        //                "BasicSailing").setLearnTime(18);
        //        new LifeSkill("SophisticatedSailing", Items.STICK, 6, 3, 3, 0, MythicSkills.SAILING, 0, Alignment.EXPLORATION,
        //                "AdvancedSailing").setLearnTime(36);
        //        new LifeSkill("SuperiorSailing", Blocks.IRON_BLOCK, 6, 4, 3, 0, MythicSkills.SAILING, 0, Alignment.EXPLORATION,
        //                "SophisticatedSailing").setLearnTime(72);
        //        new LifeSkill("BasicPiloting", Blocks.REDSTONE_BLOCK, 6, 5, 4, 0, MythicSkills.SAILING, 0,
        //                Alignment.EXPLORATION, "SuperiorSailing").setLearnTime(216);
        //        new LifeSkill("AdvancedPiloting", Blocks.IRON_BLOCK, 6, 6, 5, 0, MythicSkills.SAILING, 0, Alignment.EXPLORATION,
        //                "BasicPiloting").setLearnTime(432);
        //        new LifeSkill("BasicShipwright", Blocks.WOOL, 7, 1, 1, 1, MythicSkills.CRAFTING, 100, Alignment.EXPLORATION, "")
        //                .setMinimumAge(24).setLearnTime(6);
        //        new LifeSkill("AdvancedShipwright", Blocks.LOG, 7, 2, 2, 0, MythicSkills.CRAFTING, 125, Alignment.EXPLORATION,
        //                "BasicShipwright").setLearnTime(18);
        //        new LifeSkill("SophisticatedShipwright", Blocks.LOG, 7, 3, 3, 0, MythicSkills.CRAFTING, 150,
        //                Alignment.EXPLORATION, "AdvancedShipwright").setLearnTime(36);
        //        new LifeSkill("SuperiorShipwright", Blocks.PLANKS, 7, 4, 3, 0, MythicSkills.CRAFTING, 200,
        //                Alignment.EXPLORATION, "SophisticatedShipwright").setLearnTime(72);
        //        new LifeSkill("AirshipDesign", Blocks.REDSTONE_BLOCK, 7, 5, 4, 1, MythicSkills.ENGINEERING, 250,
        //                Alignment.EXPLORATION, "SuperiorShipwright").setLearnTime(216);
        //        new LifeSkill("AdvancedAirshipDesign", Blocks.COAL_BLOCK, 7, 6, 5, 0, MythicSkills.ENGINEERING, 300,
        //                Alignment.EXPLORATION, "AirshipDesign").setLearnTime(432);
        //        new LifeSkill("BasicGathering", Items.STICK, 8, 1, 1, 1, MythicSkills.GATHERING, 10, Alignment.SURVIVAL, "")
        //                .setMinimumAge(10);
        //        new LifeSkill("AdvancedGathering", Items.STICK, 8, 2, 1, 0, MythicSkills.GATHERING, 25, Alignment.SURVIVAL,
        //                "BasicGathering");
        //        new LifeSkill("SophisticatedGathering", Items.STICK, 8, 3, 1, 0, MythicSkills.GATHERING, 50, Alignment.SURVIVAL,
        //                "AdvancedGathering");
        //        new LifeSkill("SuperiorGathering", Items.STICK, 8, 4, 1, 0, MythicSkills.GATHERING, 75, Alignment.SURVIVAL,
        //                "SophisticatedGathering");
        //        new LifeSkill("MasterlyGathering", Items.STICK, 8, 5, 1, 0, MythicSkills.GATHERING, 100, Alignment.SURVIVAL,
        //                "SuperiorGathering");
        new LifeSkill("BasicFireMaking", MythriaBlocks.CAMPFIRE, 9, 1, 1, 1, MythicSkills.FIREMAKING, 0,
                Alignment.SURVIVAL, "").setMinimumAge(10).addAttributeFlag(AttributeFlag.FIREMAKING1)
            .addRequiredAttribute(Attribute.INTELLIGENCE, 16).addRequiredAttribute(Attribute.DEXTERITY, 10);
        //        new LifeSkill("AdvancedFireMaking", Items.FLINT_AND_STEEL, 9, 2, 1, 0, MythicSkills.FIREMAKING, 25,
        //                Alignment.SURVIVAL, "BasicFireMaking");
        //        new LifeSkill("SophisticatedFireMaking", Items.FLINT_AND_STEEL, 9, 3, 1, 0, MythicSkills.FIREMAKING, 50,
        //                Alignment.SURVIVAL, "AdvancedFireMaking");
        //        new LifeSkill("SuperiorFireMaking", Items.FLINT_AND_STEEL, 9, 4, 1, 0, MythicSkills.FIREMAKING, 75,
        //                Alignment.SURVIVAL, "SophisticatedFireMaking");
        //        new LifeSkill("MasterlyFireMaking", Items.FLINT_AND_STEEL, 9, 5, 1, 0, MythicSkills.FIREMAKING, 100,
        //                Alignment.SURVIVAL, "SuperiorFireMaking");
    }

    public static void CreatePersonalities() {
        new PersonallityTrait("Warrior", Items.IRON_SWORD, 1, 1)
                .addStartingAttributes(Attribute.STRENGTH, 12)
                .addStartingAttributes(Attribute.DEXTERITY, 14)
                .addStartingAttributes(Attribute.VIGOR, 16)
                .addStartingAttributes(Attribute.ENDURANCE, 10)
                .addStartingAttributes(Attribute.ATTUNEMENT, 0)
                .addStartingAttributes(Attribute.INTELLIGENCE, 8);

        new PersonallityTrait("Berserker", Items.IRON_AXE, 1, 2)
                .addStartingAttributes(Attribute.STRENGTH, 20)
                .addStartingAttributes(Attribute.DEXTERITY, 5)
                .addStartingAttributes(Attribute.VIGOR, 14)
                .addStartingAttributes(Attribute.ENDURANCE, 12)
                .addStartingAttributes(Attribute.ATTUNEMENT, 0)
                .addStartingAttributes(Attribute.INTELLIGENCE, 9);

        new PersonallityTrait("Ranger", Items.BOW, 1, 3)
                .addStartingAttributes(Attribute.STRENGTH, 12)
                .addStartingAttributes(Attribute.DEXTERITY, 14)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 10)
                .addStartingAttributes(Attribute.ATTUNEMENT, 6)
                .addStartingAttributes(Attribute.INTELLIGENCE, 8);

        new PersonallityTrait("Thief", Items.EMERALD, 1, 4)
                .addStartingAttributes(Attribute.STRENGTH, 8)
                .addStartingAttributes(Attribute.DEXTERITY, 17)
                .addStartingAttributes(Attribute.VIGOR, 8)
                .addStartingAttributes(Attribute.ENDURANCE, 6)
                .addStartingAttributes(Attribute.ATTUNEMENT, 5)
                .addStartingAttributes(Attribute.INTELLIGENCE, 16);

        new PersonallityTrait("MartialArtist", Blocks.PLANKS, 1, 5)
                .addStartingAttributes(Attribute.STRENGTH, 10)
                .addStartingAttributes(Attribute.DEXTERITY, 20)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 10)
                .addStartingAttributes(Attribute.ATTUNEMENT, 3)
                .addStartingAttributes(Attribute.INTELLIGENCE, 7);

        new PersonallityTrait("Athlete", Items.LEATHER_BOOTS, 1, 6)
                .addStartingAttributes(Attribute.STRENGTH, 10)
                .addStartingAttributes(Attribute.DEXTERITY, 12)
                .addStartingAttributes(Attribute.VIGOR, 8)
                .addStartingAttributes(Attribute.ENDURANCE, 16)
                .addStartingAttributes(Attribute.ATTUNEMENT, 6)
                .addStartingAttributes(Attribute.INTELLIGENCE, 8);

        new PersonallityTrait("Worker", Items.IRON_AXE, 2, 1)
                .addStartingAttributes(Attribute.STRENGTH, 12)
                .addStartingAttributes(Attribute.DEXTERITY, 10)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 14)
                .addStartingAttributes(Attribute.ATTUNEMENT, 4)
                .addStartingAttributes(Attribute.INTELLIGENCE, 8);

        new PersonallityTrait("Blacksmith", Blocks.ANVIL, 2, 2)
                .addStartingAttributes(Attribute.STRENGTH, 12)
                .addStartingAttributes(Attribute.DEXTERITY, 12)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 8)
                .addStartingAttributes(Attribute.ATTUNEMENT, 4)
                .addStartingAttributes(Attribute.INTELLIGENCE, 14);

        new PersonallityTrait("Mason", Blocks.STONEBRICK, 2, 3)
                .addStartingAttributes(Attribute.STRENGTH, 16)
                .addStartingAttributes(Attribute.DEXTERITY, 10)
                .addStartingAttributes(Attribute.VIGOR, 8)
                .addStartingAttributes(Attribute.ENDURANCE, 8)
                .addStartingAttributes(Attribute.ATTUNEMENT, 2)
                .addStartingAttributes(Attribute.INTELLIGENCE, 16);

        new PersonallityTrait("Mage", Items.ENDER_PEARL, 2, 4)
                .addStartingAttributes(Attribute.STRENGTH, 4)
                .addStartingAttributes(Attribute.DEXTERITY, 6)
                .addStartingAttributes(Attribute.VIGOR, 6)
                .addStartingAttributes(Attribute.ENDURANCE, 6)
                .addStartingAttributes(Attribute.ATTUNEMENT, 14)
                .addStartingAttributes(Attribute.INTELLIGENCE, 24);

        new PersonallityTrait("Miner", Items.IRON_PICKAXE, 2, 5)
                .addStartingAttributes(Attribute.STRENGTH, 14)
                .addStartingAttributes(Attribute.DEXTERITY, 8)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 14)
                .addStartingAttributes(Attribute.ATTUNEMENT, 6)
                .addStartingAttributes(Attribute.INTELLIGENCE, 8);

        new PersonallityTrait("Alchemist", Items.POTIONITEM, 2, 6)
                .addStartingAttributes(Attribute.STRENGTH, 8)
                .addStartingAttributes(Attribute.DEXTERITY, 8)
                .addStartingAttributes(Attribute.VIGOR, 6)
                .addStartingAttributes(Attribute.ENDURANCE, 8)
                .addStartingAttributes(Attribute.ATTUNEMENT, 12)
                .addStartingAttributes(Attribute.INTELLIGENCE, 18);

        new PersonallityTrait("Chef", Items.BREAD, 3, 1)
                .addStartingAttributes(Attribute.STRENGTH, 8)
                .addStartingAttributes(Attribute.DEXTERITY, 16)
                .addStartingAttributes(Attribute.VIGOR, 10)
                .addStartingAttributes(Attribute.ENDURANCE, 8)
                .addStartingAttributes(Attribute.ATTUNEMENT, 6)
                .addStartingAttributes(Attribute.INTELLIGENCE, 12);
    }

    public static void CreateTrades() {
        new TradeSkill("BasicCrafting", Blocks.OAK_FENCE, 1, 1, 1, 1, MythicSkills.CRAFTING, 0, Alignment.CRAFTMANSHIP,
                "").addCraftable(Items.SADDLE, Items.LEAD, Items.LEATHER_BOOTS,
                Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS, MythriaBlocks.CAMPFIRE,
                MythriaItems.PRIMATIVE_BANDOLIER, MythriaBlocks.PRIMATIVE_CRATE, MythriaItems.TOOL_HANDLE,
                MythriaBlocks.PRIMATIVE_WOODEN_WALL, MythriaBlocks.IMPROVED_CAMPFIRE, Items.SIGN, MythriaBlocks.PRIMATIVE_CRAFTING_TABLE, MythriaBlocks.THATCH_BLOCK, MythriaBlocks.THATCH_STAIR)
                .addPlaceable(Blocks.WOOL, Blocks.OAK_FENCE, Blocks.DARK_OAK_FENCE, Blocks.ACACIA_FENCE,
                        Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE, Blocks.JUNGLE_FENCE, MythriaBlocks.CAMPFIRE,
                        MythriaBlocks.PRIMATIVE_CRATE, MythriaBlocks.PRIMATIVE_WOODEN_WALL,
                        MythriaBlocks.IMPROVED_CAMPFIRE, MythriaBlocks.THATCH_BLOCK, MythriaBlocks.THATCH_STAIR, Blocks.LOG, Blocks.LOG2)
                .addBreakable(Blocks.LOG, Blocks.LOG2).setMinimumAge(14)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 8).addRequiredAttribute(Attribute.DEXTERITY, 7);
        new TradeSkill("AdvancedCrafting", Items.WOODEN_AXE, 1, 2, 1, 0, MythicSkills.CRAFTING, 25,
                Alignment.CRAFTMANSHIP, "BasicCrafting").addCraftable(Items.FISHING_ROD, Items.BOWL, Items.ARROW, Items.BOW, Blocks.PLANKS, Blocks.CRAFTING_TABLE,
                Blocks.OAK_FENCE, Blocks.DARK_OAK_FENCE, Blocks.ACACIA_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE,
                Blocks.JUNGLE_FENCE, MythriaBlocks.SAW_HORSE).addPlaceable(Blocks.PLANKS, Blocks.WOODEN_SLAB, Blocks.DOUBLE_WOODEN_SLAB).addAttributeFlag(AttributeFlag.CARPENTRY)
        .addRequiredAttribute(Attribute.INTELLIGENCE, 12).addRequiredAttribute(Attribute.DEXTERITY, 10).addRequiredAttribute(Attribute.STRENGTH, 10);
        new TradeSkill("SophisticatedCrafting", Item.getItemFromBlock(Blocks.LOG), 1, 3, 1, 0, MythicSkills.CRAFTING,
                50, Alignment.CRAFTMANSHIP, "AdvancedCrafting").addPlaceable(Blocks.BIRCH_STAIRS,
                Blocks.DARK_OAK_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.ACACIA_STAIRS,
                Blocks.OAK_STAIRS, Blocks.DARK_OAK_DOOR, Blocks.DARK_OAK_FENCE_GATE, MythriaBlocks.OAK_DOOR,
                MythriaBlocks.SPRUCE_DOOR, Blocks.SPRUCE_FENCE_GATE, MythriaBlocks.JUNGLE_DOOR, Blocks.JUNGLE_FENCE_GATE,
                MythriaBlocks.DARK_OAK_DOOR, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE,
                MythriaBlocks.SPRUCE_DOOR, Blocks.SPRUCE_FENCE_GATE, Blocks.ACACIA_DOOR, Blocks.ACACIA_FENCE_GATE,
                Blocks.BIRCH_DOOR, Blocks.BIRCH_FENCE_GATE, Blocks.PLANKS)
                .addCraftable(Items.PAPER, MythriaItems.OAK_DOOR, MythriaItems.ACACIA_DOOR, MythriaItems.BIRCH_DOOR, MythriaItems.DARK_OAK_DOOR,
                        MythriaItems.JUNGLE_DOOR, MythriaItems.SPRUCE_DOOR)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 17).addRequiredAttribute(Attribute.DEXTERITY, 12).addRequiredAttribute(Attribute.STRENGTH, 14);
        new TradeSkill("SuperiorCrafting", Item.getItemFromBlock(Blocks.LADDER), 1, 4, 1, 0, MythicSkills.CRAFTING, 75,
                Alignment.CRAFTMANSHIP, "SophisticatedCrafting")
                .addCraftable(Blocks.LADDER, Items.BED, Items.BOAT, Items.ACACIA_BOAT, Items.BIRCH_BOAT,
                        Items.DARK_OAK_BOAT, Items.JUNGLE_BOAT, Items.SPRUCE_BOAT).setMinimumAge(16)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 20).addRequiredAttribute(Attribute.DEXTERITY, 16).addRequiredAttribute(Attribute.STRENGTH, 16);
        new TradeSkill("MasterlyCrafting", Items.ARROW, 1, 5, 1, 0, MythicSkills.CRAFTING, 100, Alignment.CRAFTMANSHIP,
                "SuperiorCrafting").addCraftable(Blocks.CARPET, Items.ITEM_FRAME, Items.ARMOR_STAND, Items.BANNER)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 22).addRequiredAttribute(Attribute.DEXTERITY, 20);
        new TradeSkill("ArtisanCrafting", Items.PAINTING, 1, 6, 1, 0, MythicSkills.CRAFTING, 125,
                Alignment.CRAFTMANSHIP, "MasterlyCrafting")
                .addCraftable(Blocks.JUKEBOX, Items.PAINTING, Blocks.BOOKSHELF)
                .addPlaceable(Blocks.BOOKSHELF, Blocks.JUKEBOX).setMinimumAge(18).setLearnTime(3)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 24).addRequiredAttribute(Attribute.DEXTERITY, 24);
        new TradeSkill("BasicStonecraft", Blocks.STONE, 2, 1, 1, 2, MythicSkills.STONECRAFT, 0, Alignment.FORTIFICATION, "")
                .addCraftable(Blocks.FURNACE, Blocks.STONE, MythriaItems.STONE_AXE_HEAD, MythriaItems.STONE_HAMMER_HEAD, MythriaItems.STONE_SAW_HEAD, MythriaItems.STONE_DAGGER_BLADE,
                        MythriaItems.STONE_HOE_HEAD, MythriaItems.STONE_PICKAXE_HEAD, MythriaItems.STONE_SHOVEL_HEAD,
                        MythriaItems.STONE_SWORD_BLADE)
                .addPlaceable(Blocks.FURNACE, Blocks.COBBLESTONE, Blocks.STONE).setMinimumAge(16)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 12).addRequiredAttribute(Attribute.DEXTERITY, 10).addRequiredAttribute(Attribute.STRENGTH, 12);
        new TradeSkill("AdvancedStonecraft", Blocks.STONEBRICK, 2, 2, 1, 0, MythicSkills.STONECRAFT, 25,
                Alignment.FORTIFICATION, "BasicStonecraft")
                .addCraftable(Blocks.HARDENED_CLAY, Blocks.STONEBRICK, Blocks.STONE_BRICK_STAIRS, Blocks.BRICK_BLOCK,
                        Blocks.BRICK_STAIRS, Items.GLASS_BOTTLE, Blocks.GLASS_PANE, Items.FLOWER_POT,
                        Blocks.STONE_SLAB, Blocks.STONE_SLAB2)
                .addPlaceable(Blocks.STONEBRICK, Blocks.STONE_BRICK_STAIRS, Blocks.BRICK_BLOCK,
                        Blocks.BRICK_STAIRS, Blocks.STONE_SLAB, Blocks.STONE_SLAB2, Blocks.DOUBLE_STONE_SLAB,
                        Blocks.DOUBLE_STONE_SLAB2)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.DEXTERITY, 12).addRequiredAttribute(Attribute.STRENGTH, 16);
        new TradeSkill("SophisticatedStonecraft", Items.BRICK, 2, 3, 1, 0, MythicSkills.STONECRAFT, 50,
                Alignment.FORTIFICATION, "AdvancedStonecraft")
                .addCraftable(Blocks.STAINED_HARDENED_CLAY, Blocks.STAINED_GLASS,
                        Blocks.STAINED_GLASS_PANE)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 16).addRequiredAttribute(Attribute.DEXTERITY, 13);
        new TradeSkill("SuperiorStonecraft", Blocks.BRICK_BLOCK, 2, 4, 1, 0, MythicSkills.STONECRAFT, 75,
                Alignment.FORTIFICATION, "SophisticatedStonecraft").addCraftable(Blocks.BRICK_BLOCK, Blocks.BRICK_STAIRS)
                .addPlaceable(Blocks.BRICK_BLOCK, Blocks.BRICK_STAIRS).setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 18).addRequiredAttribute(Attribute.DEXTERITY, 16).addRequiredAttribute(Attribute.STRENGTH, 20);
        new TradeSkill("MasterlyStonecraft", Blocks.NETHER_BRICK, 2, 5, 1, 0, MythicSkills.STONECRAFT, 100,
                Alignment.FORTIFICATION, "SuperiorStonecraft")
                .addCraftable(Blocks.NETHER_BRICK, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICK_FENCE,
                        Blocks.RED_NETHER_BRICK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS)
                .addPlaceable(Blocks.NETHER_BRICK, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICK_FENCE,
                        Blocks.RED_NETHER_BRICK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS)
                .setLearnTime(2)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 20).addRequiredAttribute(Attribute.DEXTERITY, 20);
        new TradeSkill("ArtisanStonecraft", Blocks.NETHER_BRICK, 2, 6, 2, 0, MythicSkills.STONECRAFT, 125,
                Alignment.FORTIFICATION, "MasterlyStonecraft")
                .addCraftable(Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA,
                        Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA,
                        Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA,
                        Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA,
                        Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA,
                        Blocks.PINK_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA,
                        Blocks.RED_GLAZED_TERRACOTTA, Blocks.SILVER_GLAZED_TERRACOTTA,
                        Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.SEA_LANTERN)
                .addPlaceable(Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA,
                        Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA,
                        Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA,
                        Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA,
                        Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA,
                        Blocks.PINK_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA,
                        Blocks.RED_GLAZED_TERRACOTTA, Blocks.SILVER_GLAZED_TERRACOTTA,
                        Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.SEA_LANTERN)
                .setLearnTime(3)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 22).addRequiredAttribute(Attribute.DEXTERITY, 24);
        new TradeSkill("BasicForging", Blocks.ANVIL, 3, 1, 1, 4, MythicSkills.SMITHING, 0, Alignment.FORGING, "")
                .addCraftable(Items.FLINT_AND_STEEL)
                .setMinimumAge(18).setLearnTime(1).addAttributeFlag(AttributeFlag.SMELTING).addAttributeFlag(AttributeFlag.BASIC_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 14).addRequiredAttribute(Attribute.DEXTERITY, 12).addRequiredAttribute(Attribute.STRENGTH, 10);
        new TradeSkill("AdvancedForging", Blocks.IRON_BLOCK, 3, 2, 1, 0, MythicSkills.SMITHING, 25, Alignment.FORGING,
                "BasicForging")
                .addCraftable(Blocks.GOLD_BLOCK)
                .setLearnTime(1).addAttributeFlag(AttributeFlag.ADVANCED_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 16).addRequiredAttribute(Attribute.DEXTERITY, 14).addRequiredAttribute(Attribute.STRENGTH, 14);
        new TradeSkill("SophisticatedForging", Blocks.GOLD_BLOCK, 3, 3, 1, 0, MythicSkills.SMITHING, 50,
                Alignment.FORGING, "AdvancedForging")
                .addCraftable(Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL,
                        Blocks.GOLDEN_RAIL, Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK)
                .addPlaceable(Blocks.ANVIL).setLearnTime(2).addAttributeFlag(AttributeFlag.SOPHISTICATED_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 20).addRequiredAttribute(Attribute.DEXTERITY, 18).addRequiredAttribute(Attribute.STRENGTH, 15);
        new TradeSkill("SuperiorForging", Items.IRON_CHESTPLATE, 3, 4, 1, 0, MythicSkills.SMITHING, 75,
                Alignment.FORGING, "SophisticatedForging")
                .setLearnTime(3).addAttributeFlag(AttributeFlag.SUPERIOR_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 26).addRequiredAttribute(Attribute.DEXTERITY, 24).addRequiredAttribute(Attribute.STRENGTH, 18);
        new TradeSkill("MasterlyForging", Items.DIAMOND_CHESTPLATE, 3, 5, 2, 0, MythicSkills.SMITHING, 100,
                Alignment.FORGING, "SuperiorForging")
                .setLearnTime(18).addAttributeFlag(AttributeFlag.MASTERLY_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 30).addRequiredAttribute(Attribute.DEXTERITY, 28).addRequiredAttribute(Attribute.STRENGTH, 20);
        new TradeSkill("ArtisanForging", Blocks.OBSIDIAN, 3, 6, 3, 0, MythicSkills.SMITHING, 125, Alignment.FORGING,
                "MasterlyForging").addCraftable(Blocks.ENCHANTING_TABLE, Blocks.ENDER_CHEST).setLearnTime(36).addAttributeFlag(AttributeFlag.ARTISAN_SMITHING)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 34).addRequiredAttribute(Attribute.DEXTERITY, 30).addRequiredAttribute(Attribute.STRENGTH, 22);
        new TradeSkill("BasicEngineering", Blocks.LEVER, 4, 1, 1, 5, MythicSkills.ENGINEERING, 0, Alignment.ENGINEERING,
                "").addCraftable(Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Items.CHEST_MINECART,
                Items.MINECART, Items.CHEST_MINECART)
                .addPlaceable(Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Blocks.RAIL)
                .setMinimumAge(16).setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 16).addRequiredAttribute(Attribute.DEXTERITY, 14);
        new TradeSkill("AdvancedEngineering", Items.FURNACE_MINECART, 4, 2, 1, 0, MythicSkills.ENGINEERING, 25,
                Alignment.ENGINEERING, "BasicEngineering")
                .addCraftable(Items.FURNACE_MINECART, Blocks.DISPENSER, Blocks.DROPPER, Items.HOPPER_MINECART,
                        Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.TRIPWIRE_HOOK, Blocks.TRAPPED_CHEST,
                        Blocks.NOTEBLOCK, Items.CLOCK, Items.COMPASS)
                .addPlaceable(Blocks.DISPENSER, Blocks.DROPPER, Blocks.PISTON, Blocks.STICKY_PISTON,
                        Blocks.TRIPWIRE_HOOK, Blocks.TRAPPED_CHEST, Blocks.NOTEBLOCK)
                .setLearnTime(3)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 20).addRequiredAttribute(Attribute.DEXTERITY, 16).addRequiredAttribute(Attribute.STRENGTH, 10);
        new TradeSkill("SophisticatedEngineering", Blocks.DAYLIGHT_DETECTOR, 4, 3, 2, 0, MythicSkills.ENGINEERING, 50,
                Alignment.ENGINEERING, "AdvancedEngineering")
                .addCraftable(Blocks.DAYLIGHT_DETECTOR, Blocks.DAYLIGHT_DETECTOR_INVERTED)
                .addPlaceable(Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.DAYLIGHT_DETECTOR,
                        Blocks.DAYLIGHT_DETECTOR_INVERTED)
                .setLearnTime(6)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 28).addRequiredAttribute(Attribute.DEXTERITY, 22).addRequiredAttribute(Attribute.STRENGTH, 12);
        new TradeSkill("SuperiorEngineering", Items.REPEATER, 4, 4, 3, 0, MythicSkills.ENGINEERING, 75,
                Alignment.ENGINEERING, "SophisticatedEngineering").addCraftable(Items.REPEATER)
                .addPlaceable(Blocks.UNPOWERED_REPEATER).setLearnTime(18)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 36).addRequiredAttribute(Attribute.DEXTERITY, 26).addRequiredAttribute(Attribute.STRENGTH, 14);
        new TradeSkill("MasterlyEngineering", Blocks.GOLDEN_RAIL, 4, 5, 3, 0, MythicSkills.ENGINEERING, 100,
                Alignment.ENGINEERING, "SuperiorEngineering").addPlaceable(Blocks.GOLDEN_RAIL).setLearnTime(18)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 42).addRequiredAttribute(Attribute.DEXTERITY, 30).addRequiredAttribute(Attribute.STRENGTH, 15);
        new TradeSkill("ArtisanEngineering", Items.COMPARATOR, 4, 6, 4, 0, MythicSkills.ENGINEERING, 125,
                Alignment.ENGINEERING, "MasterlyEngineering").addCraftable(Items.COMPARATOR)
                .addPlaceable(Blocks.UNPOWERED_COMPARATOR).setLearnTime(18)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 48).addRequiredAttribute(Attribute.DEXTERITY, 30).addRequiredAttribute(Attribute.STRENGTH, 16);

        new TradeSkill("BasicFarming", Items.WHEAT_SEEDS, 5, 1, 1, 1, MythicSkills.FARMING, 0, Alignment.FARMING, "")
                .addCraftable(Blocks.HAY_BLOCK).addPlaceable(Blocks.HAY_BLOCK, Blocks.WHEAT).setMinimumAge(8)
                .addRequiredAttribute(Attribute.ENDURANCE, 10).addRequiredAttribute(Attribute.STRENGTH, 12);
        new TradeSkill("AdvancedFarming", Items.WHEAT, 5, 2, 1, 0, MythicSkills.FARMING, 25, Alignment.FARMING,
                "BasicFarming").addPlaceable(Blocks.POTATOES, Blocks.CARROTS).setLearnTime(1)
                .addRequiredAttribute(Attribute.ENDURANCE, 14).addRequiredAttribute(Attribute.STRENGTH, 18);
        new TradeSkill("SophisticatedFarming", Blocks.HAY_BLOCK, 5, 3, 1, 0, MythicSkills.FARMING, 50,
                Alignment.FARMING, "AdvancedFarming").addCraftable(Items.MELON_SEEDS, Items.PUMPKIN_SEEDS)
                .addPlaceable(Blocks.MELON_STEM).setLearnTime(1)
                .addRequiredAttribute(Attribute.ENDURANCE, 20).addRequiredAttribute(Attribute.STRENGTH, 28);
        new TradeSkill("BasicAlchemy", Items.BREWING_STAND, 6, 1, 1, 4, MythicSkills.ALCHEMY, 0, Alignment.ALCHEMY, "")
                .addCraftable(Items.DYE, Items.SLIME_BALL, Items.GUNPOWDER, Items.BLAZE_POWDER, Items.BREWING_STAND)
                .addPlaceable(Blocks.BREWING_STAND).setMinimumAge(14)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 18).addRequiredAttribute(Attribute.ATTUNEMENT, 12);
        new TradeSkill("AdvancedAlchemy", Blocks.TNT, 6, 2, 1, 0, MythicSkills.ALCHEMY, 25, Alignment.ALCHEMY,
                "BasicAlchemy").addCraftable(Items.TNT_MINECART, Items.FIREWORKS, Items.FIREWORK_CHARGE, Blocks.TNT)
                .addPlaceable(Blocks.TNT).setMinimumAge(18).setLearnTime(6)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 24).addRequiredAttribute(Attribute.ATTUNEMENT, 20);
        new TradeSkill("BasicMagic", Items.GOLDEN_APPLE, 7, 1, 1, 4, MythicSkills.MAGIC, 0, Alignment.ARCANERY,
                "").addCraftable(Items.GOLDEN_CARROT, Items.MAGMA_CREAM, Items.SPECKLED_MELON).setMinimumAge(12)
                .setLearnTime(1)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 30).addRequiredAttribute(Attribute.ATTUNEMENT, 12);
        new TradeSkill("AdvancedMagic", Items.EXPERIENCE_BOTTLE, 7, 2, 1, 0, MythicSkills.MAGIC, 25,
                Alignment.ARCANERY, "BasicMagic").addCraftable(Items.EXPERIENCE_BOTTLE, Items.ENDER_EYE)
                .setLearnTime(6)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 42).addRequiredAttribute(Attribute.ATTUNEMENT, 18);
        new TradeSkill("SophisticatedMagic", Blocks.BEACON, 7, 3, 2, 0, MythicSkills.MAGIC, 50,
                Alignment.ARCANERY, "AdvancedMagic").addCraftable(Blocks.BEACON).setLearnTime(18)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 52).addRequiredAttribute(Attribute.ATTUNEMENT, 24);
        new TradeSkill("SuperiorMagic", Blocks.ENCHANTING_TABLE, 7, 4, 3, 0, MythicSkills.MAGIC, 75,
                Alignment.ARCANERY, "SophisticatedMagic").setLearnTime(72)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 60).addRequiredAttribute(Attribute.ATTUNEMENT, 30);
        new TradeSkill("MasterlyMagic", Blocks.GLOWSTONE, 7, 5, 5, 0, MythicSkills.MAGIC, 100, Alignment.ARCANERY,
                "SuperiorMagic").addCraftable(Blocks.GLOWSTONE).addPlaceable(Blocks.GLOWSTONE).setLearnTime(120)
                .addRequiredAttribute(Attribute.INTELLIGENCE, 84).addRequiredAttribute(Attribute.ATTUNEMENT, 42);
        new TradeSkill("BasicMining", Blocks.COAL_ORE, 8, 1, 1, 3, MythicSkills.MINING, 0, Alignment.MINING, "")
                .addBreakable(Blocks.DIRT, Blocks.GRAVEL, Blocks.COAL_ORE, MythriaBlocks.COPPER_ORE, MythriaBlocks.TIN_ORE, Blocks.STONE, Blocks.NETHERRACK, MythriaBlocks.COPPER_ORE)
                .setMinimumAge(18)
                .addRequiredAttribute(Attribute.ENDURANCE, 14).addRequiredAttribute(Attribute.STRENGTH, 12);
        new TradeSkill("AdvancedMining", MythriaBlocks.IRON_ORE, 8, 2, 1, 0, MythicSkills.MINING, 25, Alignment.MINING,
                "BasicMining").addBreakable(MythriaBlocks.IRON_ORE, Blocks.EMERALD_ORE, MythriaBlocks.SILVER_ORE)
                .addRequiredAttribute(Attribute.ENDURANCE, 16).addRequiredAttribute(Attribute.STRENGTH, 16);
        new TradeSkill("SophisticatedMining", Blocks.GOLD_ORE, 8, 3, 2, 0, MythicSkills.MINING, 50, Alignment.MINING,
                "AdvancedMining").addBreakable(MythriaBlocks.GOLD_ORE).setLearnTime(1)
                .addRequiredAttribute(Attribute.ENDURANCE, 14).addRequiredAttribute(Attribute.STRENGTH, 12).addRequiredAttribute(Attribute.INTELLIGENCE, 14);
        new TradeSkill("SuperiorMining", Blocks.LAPIS_ORE, 8, 4, 3, 0, MythicSkills.MINING, 75, Alignment.MINING,
                "SophisticatedMining").addBreakable(Blocks.LAPIS_ORE, MythriaBlocks.PLATINUM_ORE).setLearnTime(2)
                .addRequiredAttribute(Attribute.ENDURANCE, 18).addRequiredAttribute(Attribute.STRENGTH, 14).addRequiredAttribute(Attribute.INTELLIGENCE, 18);
        new TradeSkill("MasterlyMining", Blocks.REDSTONE_ORE, 8, 5, 3, 0, MythicSkills.MINING, 100, Alignment.MINING,
                "SuperiorMining")
                .addBreakable(Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.QUARTZ_ORE, Blocks.MAGMA, MythriaBlocks.TITANIUM_ORE)
                .setLearnTime(3)
                .addRequiredAttribute(Attribute.ENDURANCE, 20).addRequiredAttribute(Attribute.STRENGTH, 18).addRequiredAttribute(Attribute.INTELLIGENCE, 22);
        new TradeSkill("ArtisanMining", Blocks.DIAMOND_ORE, 8, 6, 4, 0, MythicSkills.MINING, 125, Alignment.MINING,
                "MasterlyMining").addBreakable(Blocks.DIAMOND_ORE, Blocks.GLOWSTONE, MythriaBlocks.TUNGSTEN_ORE).setLearnTime(6)
                .addRequiredAttribute(Attribute.ENDURANCE, 24).addRequiredAttribute(Attribute.STRENGTH, 22).addRequiredAttribute(Attribute.INTELLIGENCE, 24);
        new TradeSkill("BasicCooking", Items.BREAD, 9, 1, 1, 1, MythicSkills.COOKING, 0, Alignment.COOKING, "")
                .setMinimumAge(10).addAttributeFlag(AttributeFlag.COOKING1)
                .addRequiredAttribute(Attribute.DEXTERITY, 14).addRequiredAttribute(Attribute.INTELLIGENCE, 12);
        new TradeSkill("AdvancedCooking", Items.COOKIE, 9, 2, 1, 0, MythicSkills.COOKING, 25, Alignment.COOKING,
                "BasicCooking")
                .addRequiredAttribute(Attribute.DEXTERITY, 16).addRequiredAttribute(Attribute.INTELLIGENCE, 15);
        new TradeSkill("SophisticatedCooking", Items.CAKE, 9, 3, 1, 0, MythicSkills.COOKING, 50, Alignment.COOKING,
                "AdvancedCooking").addRequiredAttribute(Attribute.DEXTERITY, 20).addRequiredAttribute(Attribute.INTELLIGENCE, 18);
        new TradeSkill("SuperiorCooking", Items.PUMPKIN_PIE, 9, 4, 1, 0, MythicSkills.COOKING, 75, Alignment.COOKING,
                "SophisticatedCooking")
                .addRequiredAttribute(Attribute.DEXTERITY, 18).addRequiredAttribute(Attribute.INTELLIGENCE, 18);
        new TradeSkill("MasterlyCooking", Items.MUSHROOM_STEW, 9, 5, 1, 0, MythicSkills.COOKING, 100, Alignment.COOKING,
                "SuperiorCooking").setLearnTime(1)
                .addRequiredAttribute(Attribute.DEXTERITY, 20).addRequiredAttribute(Attribute.INTELLIGENCE, 20);
        new TradeSkill("ArtisanCooking", Items.GOLDEN_APPLE, 9, 6, 2, 0, MythicSkills.COOKING, 125, Alignment.COOKING,
                "MasterlyCooking").setLearnTime(3)
                .addRequiredAttribute(Attribute.DEXTERITY, 24).addRequiredAttribute(Attribute.INTELLIGENCE, 22);
    }

    public static Perk getAttribute(final String s) {
        final List<Perk> list = getAttributes();
        for (final Perk pa : list)
            if (s.contains(pa.getName()))
                return pa;
        return null;
    }

    public static List<Perk> getAttributes() {
        final List<Perk> list = new ArrayList<>();
        list.addAll(tradeList);
        list.addAll(lifeList);
        list.addAll(combatList);
        list.addAll(magicList);
        list.addAll(personalityList);
        list.addAll(blessingList);
        return list;
    }

    public static List<CombatSkill> getCombatList() {
        return combatList;
    }

    public static CombatSkill getCombatSkill(final String name) {
        for (final CombatSkill pa : combatList)
            if (pa.getName().equals(name))
                return pa;
        return null;
    }

    public static List<LifeSkill> getLifeList() {
        return lifeList;
    }

    public static LifeSkill getLifeSkill(final String name) {
        for (final LifeSkill pa : lifeList)
            if (pa.getName().equals(name))
                return pa;
        return null;
    }

    public static List<MagicSkill> getMagicList() {
        return magicList;
    }

    public static List<TradeSkill> getTradeList() {
        return tradeList;
    }

    public static TradeSkill getTradeSkill(final String name) {
        for (final TradeSkill pa : tradeList)
            if (pa.getName().equals(name))
                return pa;
        return null;
    }

    public static boolean hasAttributeFlag(final IProfile capability, final AttributeFlag walljump1) {
        for (final Perk pa : capability.getPlayerSkills()) {
            if (pa == null)
                continue;
            if (pa.getAttributeFlags().contains(walljump1))
                return true;
        }
        return false;
    }

    public static void addPersonality(PersonallityTrait personallityTrait) {
        personalityList.add(personallityTrait);
    }

    public static List<PersonallityTrait> getPersonalityList() {
        return personalityList;
    }

    public static void addBlessingPerk(BlessingPerk blessingPerk) {
        blessingList.add(blessingPerk);
    }
}
