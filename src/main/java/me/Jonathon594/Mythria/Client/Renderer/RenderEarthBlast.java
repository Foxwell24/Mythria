package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Entity.Magic.EntityEarthBlast;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderEarthBlast<T extends Entity> extends Render<T> {

    public RenderEarthBlast(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final EntityEarthBlast e = (EntityEarthBlast) entity;
        final Block b = e.getBlock();

        if (b != null) {
            final IBlockState iblockstate = b.getDefaultState();

            if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL) {
                final World world = entity.world;

                if (iblockstate != world.getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
                    bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    final Tessellator tessellator = Tessellator.getInstance();
                    final BufferBuilder bufferbuilder = tessellator.getBuffer();

                    bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
                    final BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ);
                    GlStateManager.translate((float) (x - blockpos.getX() - 0.5D), (float) (y - blockpos.getY()), (float) (z - blockpos.getZ() - 0.5D));
                    final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, bufferbuilder, false);
                    tessellator.draw();

                    if (renderOutlines) {
                        GlStateManager.disableOutlineMode();
                        GlStateManager.disableColorMaterial();
                    }

                    GlStateManager.enableLighting();
                    GlStateManager.popMatrix();
                    super.doRender(entity, x, y, z, entityYaw, partialTicks);
                }
            }
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
