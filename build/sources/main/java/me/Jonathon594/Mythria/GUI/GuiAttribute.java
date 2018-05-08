package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.GUI.Container.ContainerAttribute;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiAttribute extends GuiContainer {
    private final ResourceLocation gui_tex;
    private Container container;

    public GuiAttribute(final ContainerAttribute container) {
        super(container);
        gui_tex = new ResourceLocation("mythria:textures/gui/container/gui_attribute.png");
        xSize = 176;
        ySize = 190;
        this.container = container;
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

    public Container getInventory() {
        return container;
    }
}
