package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Mythria;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class PerkMenuGui extends GuiContainer {

    private final ResourceLocation gui_tex;
    private final Container inventory;
    private GuiButton buttonBack;

    public PerkMenuGui(final Container inventory) {
        super(inventory);
        gui_tex = new ResourceLocation("mythria:textures/gui/container/perk_menu_gui_tex.png");

        this.inventory = inventory;
        xSize = 176;
        ySize = 132;
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button == buttonBack) {
            mc.displayGuiScreen(null);
            mc.player.openGui(Mythria.instance, MythriaGui.MYTHRIA_MENU_GUI.ordinal(), mc.world, 0, 0, 0);
        }
    }

    public Container getInventory() {
        return inventory;
    }

    @Override
    public void initGui() {
        buttonList.add(buttonBack = new GuiButton(1, width / 2 - 100, height / 2 + 100, "Back"));
        super.initGui();
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
