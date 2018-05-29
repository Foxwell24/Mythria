package me.Jonathon594.Mythria.Client.Model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelRex extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer leftLeg;

    public ModelRex () {
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-10, -40, -20, 20,20,60);
        this.body.rotateAngleX = 0.3f;

        this.leftLeg = new ModelRenderer(this, 0, 0);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.6F, 0.0F);
        this.body.render(scale);

        GlStateManager.popMatrix();
    }
}
