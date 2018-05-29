package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.GUI.Container.ContainerSawhorse;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.CPacketSawhorseButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiSawhorse extends GuiContainer {

    private final ResourceLocation gui_tex;

    private GuiButton sawButton;


    public GuiSawhorse(final ContainerSawhorse container) {
        super(container);
        gui_tex = new ResourceLocation("mythria:textures/gui/container/gui_saw_horse.png");
        xSize = 208;
        ySize = 224;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.equals(sawButton)) {
            MythriaPacketHandler.INSTANCE.sendToServer(new CPacketSawhorseButton());
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(sawButton = new GuiButton(0, guiLeft + 86, guiTop + 53, 36, 20, "SAW"));
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
