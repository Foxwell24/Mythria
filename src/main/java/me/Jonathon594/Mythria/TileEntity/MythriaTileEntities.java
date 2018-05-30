package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Client.Renderer.TESR.RenderBrickPile;
import me.Jonathon594.Mythria.Client.Renderer.TESR.RenderIngotPile;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MythriaTileEntities {
    public static final void RegisterTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCampfire.class, "tileentity_campfire");
        GameRegistry.registerTileEntity(TileEntityImprovedCampfire.class, "tileentity_campfire_improved");
        GameRegistry.registerTileEntity(TileEntityPrimativeCrate.class, "tileentity_crate");
        GameRegistry.registerTileEntity(TileEntityPitKiln.class, "tileentity_pitkiln");
        GameRegistry.registerTileEntity(TileEntityMythriaFurnace.class, "tileentity_furnace");
        GameRegistry.registerTileEntity(TileEntityAnvil.class, "tileentity_anvil");
        GameRegistry.registerTileEntity(TileEntitySawHorse.class, "tileentity_sawhorse");
        GameRegistry.registerTileEntity(TileEntityIngotPile.class, "tileentity_ingotpile");
        GameRegistry.registerTileEntity(TileEntityDoor.class, "tileentity_door");

        GameRegistry.registerTileEntity(TileEntityAlter.class, "tileentity_alter");
    }

    @SideOnly(Side.CLIENT)
    public static void registerSpecialRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIngotPile.class, new RenderIngotPile());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrickPile.class, new RenderBrickPile());
    }
}
