package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Managers.MythriaInventoryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;

import java.util.List;

public class GuiLockedSlot extends GuiButton {

    private final GuiContainer gui;

    public GuiLockedSlot(final GuiContainer gui) {
        super(-111, 0, 0, 16, 16, "");
        this.gui = gui;

        enabled = false;
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        if (visible) {
            final List<Slot> slotList = gui.inventorySlots.inventorySlots;
            if (slotList != null && slotList.size() > 0)
                for (int i = 0; i < slotList.size(); i++) {
                    final Slot slot = gui.inventorySlots.getSlot(i);
                    if (slot != null && slot.isHere(mc.player.inventory, slot.getSlotIndex()))
                        if (!MythriaInventoryManager.isSlotOpen(mc.player, slot.getSlotIndex())) {
                            drawLockedSlot(mc, slot.xPos, slot.yPos);
                        }
                }
        }
    }

    public final void drawLockedSlot(final Minecraft mc, final int slotX, final int slotY) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.5f);
        this.drawTexturedModalRect(gui.getGuiLeft() + slotX, gui.getGuiTop() + slotY, 0, 0, 16, 16);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

}
