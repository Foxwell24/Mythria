package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderEmpty<T extends Entity> extends Render<T> {


    public RenderEmpty(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw,
                         final float partialTicks) {
    }

    @Override
    protected ResourceLocation getEntityTexture(final T entity) {
        return null;
    }
}
