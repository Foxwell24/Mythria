package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEarthBlast extends ModelBase {

    ModelRenderer renderer;

    public ModelEarthBlast() {
        renderer = new ModelRenderer(this);
        renderer.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1, false);
    }

    @Override
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.translate(0.0F, 0.001F, 0.0F);
        renderer.render(scale);
    }
}
