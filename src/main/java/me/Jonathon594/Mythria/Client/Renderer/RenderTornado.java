package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTornado<T extends Entity> extends Render<T> {

    public RenderTornado(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.translate(x,y,z);

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
