package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.GUI.Container.ContainerVessel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiVessel extends GuiContainer {

    private final ResourceLocation gui_tex;
    private InventoryPlayer inventoryPlayer;
    private IVessel vessel;

    public GuiVessel(InventoryPlayer inventoryPlayer) {
        super(new ContainerVessel(inventoryPlayer));

        vessel =  inventoryPlayer.getCurrentItem().getCapability(VesselProvider.VESSEL_CAP, null);
        boolean liquid = vessel.hasMetal();


       gui_tex = new ResourceLocation("mythria:textures/gui/gui_vessel.png");

        xSize = 176;
        ySize = 166;

        this.inventoryPlayer = inventoryPlayer;
    }

    @Override
    public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        mc.getTextureManager().bindTexture(gui_tex);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
