package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.TileEntity.TileEntityMythriaFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {

    private final ResourceLocation gui_tex;
    private final TileEntityMythriaFurnace furnace;

    public GuiFurnace(final Container container, final TileEntityMythriaFurnace furnace) {
        super(container);
        gui_tex = new ResourceLocation("mythria:textures/gui/container/gui_furnace.png");
        this.furnace = furnace;
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

        final double v = (double) furnace.getTicksLeft() / (double) furnace.getMaxTicks();

        if (furnace.isBurning())
            drawTexturedModalRect(guiLeft + 80, guiTop + 37 + (int) ((1 - v) * 14), 176, (int) (14 * (1 - v)), 14,
                    (int) (14 * v));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        mc.getTextureManager().bindTexture(gui_tex);
        double temp = furnace.getTemp();
        double temprel = Math.max(temp - 250, 0);
        double prop = temprel / 3750;

        drawTexturedModalRect(8, 8 + (int) (((1-prop) * 64)), 190, 0, 15, 5);
    }
}
