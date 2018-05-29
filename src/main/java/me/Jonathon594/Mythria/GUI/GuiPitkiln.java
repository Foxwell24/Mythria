package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.GUI.Container.ContainerPitkiln;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPitkiln extends GuiContainer {

    private final ResourceLocation gui_tex;
    private InventoryPlayer inventoryPlayer;

    public GuiPitkiln(ContainerPitkiln containerPitkiln) {
        super(containerPitkiln);

        gui_tex = new ResourceLocation("mythria:textures/gui/container/gui_pitkiln.png");

        xSize = 176;
        ySize = 166;

        this.inventoryPlayer = inventoryPlayer;
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
