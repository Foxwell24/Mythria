package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderSingularitySpell<T extends Entity> extends Render<T> {


    public RenderSingularitySpell(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw,
                         final float partialTicks) {
        for (int i = 0; i < 3; i++) {
            Vec3d vec = new Vec3d(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            vec = vec.normalize();
            vec.scale(Math.random());
            entity.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
                    entity.posX + vec.x / 8,
                    entity.posY + vec.y / 8,
                    entity.posZ + vec.z / 8,
                    0, 0, 0);
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(final T entity) {
        return null;
    }
}
