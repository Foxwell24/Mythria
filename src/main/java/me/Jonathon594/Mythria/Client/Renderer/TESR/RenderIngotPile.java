package me.Jonathon594.Mythria.Client.Renderer.TESR;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Client.Model.ModelIngotPile;
import me.Jonathon594.Mythria.TileEntity.TileEntityIngotPile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderIngotPile extends TileEntitySpecialRenderer<TileEntityIngotPile> {
    private final ModelIngotPile ingotModel = new ModelIngotPile();

    @Override
    public void render(TileEntityIngotPile te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderIngotPileAt(te, x, y, z, partialTicks);
    }

    public void renderIngotPileAt(TileEntityIngotPile tep, double x, double y, double z, float f) {
        Block block = tep.getBlockType();
        if (tep.getWorld() != null && tep.getStack() != null && block == MythriaBlocks.INGOT_PILE)
        {
            int i = tep.getIngotCount();
            if(tep.getIngotType() == null) return;
            bindTexture(new ResourceLocation("mythria:textures/blocks/metal/" + tep.getIngotType().name().toLowerCase() + ".png")); //texture

            GlStateManager.pushMatrix(); //start
            GlStateManager.translate((float)x + 0.0F, (float)y + 0F, (float)z + 0.0F); //size
            setLightmap(tep);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            ingotModel.renderIngots(i);
            GlStateManager.popMatrix(); //end
        }
    }

    private void setLightmap(TileEntityIngotPile tileEntityIngotPile)
    {
        int i = MathHelper.floor(tileEntityIngotPile.getPos().getX());
        int j = MathHelper.floor(tileEntityIngotPile.getPos().getY());
        int k = MathHelper.floor(tileEntityIngotPile.getPos().getZ());

        int l = tileEntityIngotPile.getWorld().getCombinedLight(new BlockPos(i, j, k), 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i1, (float)j1);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }
}
