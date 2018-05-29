package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DeityRenderManager {

    public static RenderDeityPlayer render;

    public static void Initialize() {
        render = new RenderDeityPlayer(Minecraft.getMinecraft().getRenderManager(), false);
    }

    @SideOnly(Side.CLIENT)
    public static void onRenderPlayer(final Pre event) {
        render.doRender((AbstractClientPlayer) event.getEntityPlayer(), event.getX(), event.getY(), event.getZ(),
                event.getEntity().rotationYaw, event.getPartialRenderTick());
    }

}
