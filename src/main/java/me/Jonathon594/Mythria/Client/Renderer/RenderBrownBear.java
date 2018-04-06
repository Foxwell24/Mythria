package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Client.Model.ModelBrownBear;
import me.Jonathon594.Mythria.Entity.EntityBlackBear;
import me.Jonathon594.Mythria.Entity.EntityBrownBear;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBrownBear<T extends EntityBrownBear> extends RenderLivingBase<T>
{
    private static final ResourceLocation BROWN_BEAR_TEXTURE = new ResourceLocation("mythria:textures/entity/bear/brownbear.png");

    public RenderBrownBear(RenderManager renderManager)
    {
        super(renderManager, new ModelBrownBear(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBrownBear entity) {
        return BROWN_BEAR_TEXTURE;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1.4F, 1.4F, 1.4F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }
}