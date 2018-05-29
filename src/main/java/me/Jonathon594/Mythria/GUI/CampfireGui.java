package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.TileEntity.TileEntityCookingFireBasic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class CampfireGui extends GuiContainer {

    private final ResourceLocation gui_tex;
    private TileEntityCookingFireBasic campfire;

    public CampfireGui(final Container container, TileEntityCookingFireBasic campfire) {
        super(container);
        this.campfire = campfire;
        gui_tex = new ResourceLocation("mythria:textures/gui/container/container_campfire.png");

        xSize = 176;
        ySize = 166;
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

        final double v = (double) campfire.getTicksLeft() / (double) campfire.getMaxTicks();

        drawTexturedModalRect(guiLeft + 80, guiTop + 37 + (int) ((1 - v) * 14), 176, (int) (14 * (1 - v)), 14,
                (int) (14 * v));
    }
}
