package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelPlayerCustom extends ModelBiped {
    private final ModelRenderer bipedCape;
    private final ModelRenderer bipedDeadmau5Head;
    private final boolean smallArms;
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;

    public ModelPlayerCustom(final float modelSize, final boolean smallArmsIn) {
        super(modelSize, 0.0F, 64, 64);
        smallArms = smallArmsIn;
        bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
        bipedCape = new ModelRenderer(this, 0, 0);
        bipedCape.setTextureSize(64, 32);
        bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);

        if (smallArmsIn) {
            bipedLeftArm = new ModelRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            bipedRightArm = new ModelRenderer(this, 40, 16);
            bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
        } else {
            bipedLeftArm = new ModelRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
            bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
        }

        bipedLeftLeg = new ModelRenderer(this, 16, 48);
        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        bipedRightLegwear = new ModelRenderer(this, 0, 32);
        bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        bipedBodyWear = new ModelRenderer(this, 16, 32);
        bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
        bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount,
                       final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.pushMatrix();

        if (isChild) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            bipedLeftLegwear.render(scale);
            bipedRightLegwear.render(scale);
            bipedLeftArmwear.render(scale);
            bipedRightArmwear.render(scale);
            bipedBodyWear.render(scale);
        } else {
            if (entityIn.isSneaking())
                GlStateManager.translate(0.0F, 0.2F, 0.0F);

            bipedLeftLegwear.render(scale);
            bipedRightLegwear.render(scale);
            bipedLeftArmwear.render(scale);
            bipedRightArmwear.render(scale);
            bipedBodyWear.render(scale);
        }

        GlStateManager.popMatrix();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks,
                                  final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        copyModelAngles(bipedLeftLeg, bipedLeftLegwear);
        copyModelAngles(bipedRightLeg, bipedRightLegwear);
        copyModelAngles(bipedLeftArm, bipedLeftArmwear);
        copyModelAngles(bipedRightArm, bipedRightArmwear);
        copyModelAngles(bipedBody, bipedBodyWear);

        if (entityIn.isSneaking())
            bipedCape.rotationPointY = 2.0F;
        else
            bipedCape.rotationPointY = 0.0F;
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        bipedLeftArmwear.showModel = visible;
        bipedRightArmwear.showModel = visible;
        bipedLeftLegwear.showModel = visible;
        bipedRightLegwear.showModel = visible;
        bipedBodyWear.showModel = visible;
        bipedCape.showModel = visible;
        bipedDeadmau5Head.showModel = visible;
    }

    @Override
    public void postRenderArm(final float scale, final EnumHandSide side) {
        final ModelRenderer modelrenderer = getArmForSide(side);

        if (smallArms) {
            final float f = 0.5F * (side == EnumHandSide.RIGHT ? 1 : -1);
            modelrenderer.rotationPointX += f;
            modelrenderer.postRender(scale);
            modelrenderer.rotationPointX -= f;
        } else
            modelrenderer.postRender(scale);
    }

    public void renderCape(final float scale) {
        bipedCape.render(scale);
    }

    public void renderDeadmau5Head(final float scale) {
        copyModelAngles(bipedHead, bipedDeadmau5Head);
        bipedDeadmau5Head.rotationPointX = 0.0F;
        bipedDeadmau5Head.rotationPointY = 0.0F;
        bipedDeadmau5Head.render(scale);
    }
}
