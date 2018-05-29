package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Client.Model.ModelBlackBear;
import me.Jonathon594.Mythria.Entity.EntityBlackBear;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBlackBear<T extends EntityBlackBear> extends RenderLivingBase<T>
{
    private static final ResourceLocation BLACK_BEAR_TEXTURE = new ResourceLocation("mythria:textures/entity/bear/blackbear.png");

    public RenderBlackBear(RenderManager renderManager)
    {
        super(renderManager, new ModelBlackBear(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBlackBear entity) {
        return BLACK_BEAR_TEXTURE;
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
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }
}