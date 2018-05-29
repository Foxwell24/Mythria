package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Enum.AttributeType;
import me.Jonathon594.Mythria.GUI.Container.*;
import me.Jonathon594.Mythria.TileEntity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
                                      final int y, final int z) {
        if (ID == MythriaGui.MYTHRIA_SHOW_TRADESKILLS_GUI.ordinal())
            return new PerkMenuGuiContainer(new InventoryBasic(null, false, 56), AttributeType.TRADE, player);
        if (ID == MythriaGui.MYTHRIA_SHOW_PERSONALITIES_GUI.ordinal())
            return new PerkMenuGuiContainer(new InventoryBasic(null, false, 56), AttributeType.PERSONALITY, player);
        if (ID == MythriaGui.MYTHRIA_SHOW_LIFESKILLS_GUI.ordinal())
            return new PerkMenuGuiContainer(new InventoryBasic(null, false, 56), AttributeType.LIFE, player);
        if (ID == MythriaGui.MYTHRIA_SHOW_COMBATSKILLS_GUI.ordinal())
            return new PerkMenuGuiContainer(new InventoryBasic(null, false, 56), AttributeType.COMBAT, player);
        if (ID == MythriaGui.MYTHRIA_SHOW_MAGICSKILLS_GUI.ordinal())
            return new PerkMenuGuiContainer(new InventoryBasic(null, false, 56), AttributeType.MAGIC, player);
        if (ID == MythriaGui.MYTHRIA_OPEN_CAMPFIRE_GUI.ordinal()) {
            final TileEntityCookingFireBasic campfire = (TileEntityCookingFireBasic) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new CampfireContainer(campfire, player.inventory);
        }
        if (ID == MythriaGui.MYTHRIA_OPEN_CAMPFIRE_IMPROVED_GUI.ordinal()) {
            final TileEntityCookingFireAdvanced campfire = (TileEntityCookingFireAdvanced) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new CampfireContainerImproved(campfire, player.inventory);
        }

        if(ID == MythriaGui.MYTHRIA_CUSTOM_CRAFTING_GUI.ordinal()) {
            return new ContainerCustomWorkbench(player.inventory, player.world, new BlockPos(x,y,z));
        }

        if(ID == MythriaGui.MYTHRIA_SMALL_VESSEL_GUI.ordinal()) {
            return new ContainerVessel(player.inventory);
        }
        if(ID == MythriaGui.MYTHRIA_PITKILN_GUI.ordinal()) {
            final TileEntityPitKiln pitKiln = (TileEntityPitKiln) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new ContainerPitkiln(pitKiln, player.inventory);
        }
        if(ID == MythriaGui.MYTHRIA_FURNACE_GUI.ordinal()) {
            final TileEntityMythriaFurnace furnace = (TileEntityMythriaFurnace) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new ContainerFurnace(furnace, player.inventory);
        }
        if(ID == MythriaGui.ANVIL_GUI.ordinal()) {
            final TileEntityAnvil anvil = (TileEntityAnvil) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new ContainerAnvil(anvil, player.inventory);
        }
        if(ID == MythriaGui.SAWHORSE_GUI.ordinal()) {
            final TileEntitySawHorse sawHorse = (TileEntitySawHorse) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new ContainerSawhorse(sawHorse, player.inventory);
        }

        if (ID == MythriaGui.MYTHRIA_SHOW_ATTRIBUTE_GUI.ordinal())
            return new ContainerAttribute(new InventoryBasic(null, false, 6), player);
        return null;
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
                                      final int y, final int z) {
        if (ID == MythriaGui.MYTHRIA_MENU_GUI.ordinal())
            return new MythriaMenuGui();
        if (ID == MythriaGui.MYTHRIA_PROFILE_CREATION_GUI.ordinal())
            return new MythriaProfileCreationGui();
        if (ID == MythriaGui.MYTHRIA_SHOW_PROFILE_GUI.ordinal())
            return new ProfileShowGui();
        if (ID == MythriaGui.MYTHRIA_SHOW_TRADESKILLS_GUI.ordinal())
            return new PerkMenuGui(new PerkMenuGuiContainer(
                    new InventoryBasic(new TextComponentString("Trade Skills"), 56), AttributeType.TRADE, player));
        if (ID == MythriaGui.MYTHRIA_SHOW_PERSONALITIES_GUI.ordinal())
            return new PerkMenuGui(new PerkMenuGuiContainer(
                    new InventoryBasic(new TextComponentString("Personalities"), 56), AttributeType.PERSONALITY, player));
        if (ID == MythriaGui.MYTHRIA_SHOW_LIFESKILLS_GUI.ordinal())
            return new PerkMenuGui(new PerkMenuGuiContainer(
                    new InventoryBasic(new TextComponentString("Life Skills"), 56), AttributeType.LIFE, player));
        if (ID == MythriaGui.MYTHRIA_SHOW_COMBATSKILLS_GUI.ordinal())
            return new PerkMenuGui(new PerkMenuGuiContainer(
                    new InventoryBasic(new TextComponentString("Combat Skills"), 56), AttributeType.COMBAT, player));
        if (ID == MythriaGui.MYTHRIA_OPEN_CAMPFIRE_GUI.ordinal()) {
            final TileEntityCookingFireBasic campfire = (TileEntityCookingFireBasic) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new CampfireGui(new CampfireContainer(campfire, player.inventory), campfire);
        }
        if (ID == MythriaGui.MYTHRIA_OPEN_CAMPFIRE_IMPROVED_GUI.ordinal()) {
            final TileEntityImprovedCampfire campfire = (TileEntityImprovedCampfire) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new CampfireGuiImproved(new CampfireContainerImproved(campfire, player.inventory), campfire);
        }
        if (ID == MythriaGui.MYTHRIA_SHOW_MAGICSKILLS_GUI.ordinal())
            return new PerkMenuGui(new PerkMenuGuiContainer(
                    new InventoryBasic(new TextComponentString("Magic Skills"), 56), AttributeType.MAGIC, player));
        if(ID == MythriaGui.MYTHRIA_CUSTOM_CRAFTING_GUI.ordinal()) {
            return new GuiCustomCrafting(player.inventory, player.world, new BlockPos(x,y,z));
        }
        if(ID == MythriaGui.MYTHRIA_SMALL_VESSEL_GUI.ordinal()) {
            return new GuiVessel(player.inventory);
        }
        if(ID == MythriaGui.MYTHRIA_PITKILN_GUI.ordinal()) {
            final TileEntityPitKiln pitKiln = (TileEntityPitKiln) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new GuiPitkiln(new ContainerPitkiln(pitKiln, player.inventory));
        }
        if(ID == MythriaGui.MYTHRIA_FURNACE_GUI.ordinal()) {
            final TileEntityMythriaFurnace furnace = (TileEntityMythriaFurnace) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new GuiFurnace(new ContainerFurnace(furnace, player.inventory), furnace);
        }
        if(ID == MythriaGui.ANVIL_GUI.ordinal()) {
            final TileEntityAnvil anvil = (TileEntityAnvil) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new GuiAnvil(new ContainerAnvil(anvil, player.inventory), anvil);
        }
        if(ID == MythriaGui.SAWHORSE_GUI.ordinal()) {
            final TileEntitySawHorse sawHorse = (TileEntitySawHorse) world
                    .getTileEntity(new BlockPos(x, y, z));
            return new GuiSawhorse(new ContainerSawhorse(sawHorse, player.inventory));
        }

        if (ID == MythriaGui.MYTHRIA_SHOW_ATTRIBUTE_GUI.ordinal())
            return new GuiAttribute(new ContainerAttribute(new InventoryBasic(null, false, 6), player));
        return null;
    }

}
