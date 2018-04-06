package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Client.Model.ModelBrownBear;
import me.Jonathon594.Mythria.Client.Model.ModelRex;
import me.Jonathon594.Mythria.Entity.EntityBrownBear;
import me.Jonathon594.Mythria.Entity.monster.EntityRex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderRex<T extends EntityRex> extends RenderLivingBase<T>
{
    private static final ResourceLocation REX_TEXTURE = new ResourceLocation("mythria:textures/entity/monster/rex.png");

    public RenderRex(RenderManager renderManager)
    {
        super(renderManager, new ModelRex(), 1F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRex entity) {
        return REX_TEXTURE;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.mainModel = new ModelRex();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1F, 1F, 1F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }
}