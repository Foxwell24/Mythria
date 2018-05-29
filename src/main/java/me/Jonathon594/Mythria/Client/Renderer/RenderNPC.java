package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Entity.EntityNPCPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderNPC<T extends EntityNPCPlayer> extends RenderLivingBase<T> {

    public RenderNPC(final RenderManager renderManager) {
        super(renderManager, new ModelPlayerCustom(0.0f, false), 0.5f);
    }

    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw,
                         final float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        mainModel.swingProgress = getSwingProgress(entity, partialTicks);
        final boolean shouldSit = entity.isRiding() && entity.getRidingEntity() != null
                && entity.getRidingEntity().shouldRiderSit();
        mainModel.isRiding = shouldSit;
        mainModel.isChild = entity.isChild();
        try {
            float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            final float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                final EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
                f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset,
                        partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                    f3 = -85.0F;

                if (f3 >= 85.0F)
                    f3 = 85.0F;

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                    f += f3 * 0.2F;

                f2 = f1 - f;
            }

            final float f7 = entity.prevRotationPitch
                    + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            renderLivingAt(entity, x, y, z);
            final float f8 = handleRotationFloat(entity, partialTicks);
            applyRotations(entity, f8, f, partialTicks);
            final float f4 = prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            if (!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                    f6 *= 3.0F;

                if (f5 > 1.0F)
                    f5 = 1.0F;
                f2 = f1 - f; // Forge: Fix MC-1207
            }

            GlStateManager.enableAlpha();
            mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

            if (renderOutlines) {
                final boolean flag1 = setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entity));

                if (!renderMarker)
                    renderModel(entity, f6, f5, f8, f2, f7, f4);

                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if (flag1)
                    unsetScoreTeamColor();
            } else {
                final boolean flag = setDoRenderBrightness(entity, partialTicks);
                renderModel(entity, f6, f5, f8, f2, f7, f4);

                if (flag)
                    unsetBrightness();

                GlStateManager.depthMask(true);

                renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
            }

            GlStateManager.disableRescaleNormal();
        } catch (final Exception exception) {
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(final T entity) {
        return entity.getNPCTexture();
    }

}
