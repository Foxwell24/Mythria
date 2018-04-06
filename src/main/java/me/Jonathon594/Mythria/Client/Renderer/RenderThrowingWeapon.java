package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Entity.EntityThrowingWeapon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderThrowingWeapon<T extends Entity> extends Render<T> {
    private final RenderItem itemRenderer;
    private ItemStack itemRender;

    public RenderThrowingWeapon(final RenderManager renderManagerIn, final RenderItem itemRendererIn) {
        super(renderManagerIn);
        this.itemRenderer = itemRendererIn;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw,
                         final float partialTicks) {
        if (entity instanceof EntityThrowingWeapon)
            itemRender = ((EntityThrowingWeapon) entity).getItem();
        else
            itemRender = new ItemStack(Items.IRON_SWORD);
        final float a = (float) MathHelper.wrapDegrees((double) System.currentTimeMillis() * 3);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.rotate(entity.rotationYaw + 90, 0, 1, 0);
        GlStateManager.rotate(90, 0, 0, 1);
        GlStateManager.rotate(a, 0, 0, 1);

        // System.out.println(a);

        GlStateManager.enableRescaleNormal();
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entity));
        }

        this.itemRenderer.renderItem(itemRender, ItemCameraTransforms.TransformType.GROUND);

        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
