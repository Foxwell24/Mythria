package me.Jonathon594.Mythria.GUI;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiListener {
    @SubscribeEvent
    public static void onRenderGui(final RenderGameOverlayEvent.Pre event) {
        if (event.getType().equals(ElementType.EXPERIENCE)) {
            new GuiHud(Minecraft.getMinecraft());
            event.setCanceled(true);
        }
    }
}
