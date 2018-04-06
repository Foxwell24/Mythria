package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Enum.RenderState;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderDeityPlayer extends RenderLivingBase<AbstractClientPlayer> {
    public RenderDeityPlayer(final RenderManager renderManager) {
        this(renderManager, false);
    }

    public RenderDeityPlayer(final RenderManager renderManager, final boolean useSmallArms) {
        super(renderManager, new ModelPlayerCustom(0.0F, useSmallArms), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerCustomHead(getMainModel().bipedHead));
        this.addLayer(new LayerElytra(this));
        this.addLayer(new LayerEntityOnShoulder(renderManager));
    }

    @Override
    public ModelPlayerCustom getMainModel() {
        return (ModelPlayerCustom) super.getMainModel();
    }

    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    @Override
    public void doRender(final AbstractClientPlayer entity, final double x, final double y, final double z,
                         final float entityYaw, final float partialTicks) {
        if (!entity.isUser() || renderManager.renderViewEntity == entity) {
            double d0 = y;
            setModelVisibilities(entity);
            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            super.doRender(entity, x, d0, z, entityYaw, partialTicks);
            GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        }
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    @Override
    protected void renderLivingAt(final AbstractClientPlayer entityLivingBaseIn, final double x, final double y,
                                  final double z) {
        if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping())
            super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX,
                    y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
        else
            super.renderLivingAt(entityLivingBaseIn, x, y, z);
    }

    @Override
    protected void applyRotations(final AbstractClientPlayer entityLiving, final float p_77043_2_,
                                  final float rotationYaw, final float partialTicks) {
        if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping()) {
            GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (entityLiving.isElytraFlying()) {
            super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
            float f = (float) entityLiving.getTicksElytraFlying() + partialTicks;
            float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
            Vec3d vec3d = entityLiving.getLook(partialTicks);
            double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
            double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;

            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
                double d3 = entityLiving.motionX * vec3d.z - entityLiving.motionZ * vec3d.x;
                GlStateManager.rotate((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
            }
        } else {
            super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
        }
    }

    /**
     * Allows the render to do state modifications necessary before the model is
     * rendered.
     */
    @Override
    protected void preRenderCallback(final AbstractClientPlayer entitylivingbaseIn, final float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }

    private void setModelVisibilities(final AbstractClientPlayer clientPlayer) {
        final ModelPlayerCustom modelplayer = getMainModel();

        if (clientPlayer.isSpectator()) {
            modelplayer.setVisible(false);
            modelplayer.bipedHead.showModel = true;
            modelplayer.bipedHeadwear.showModel = true;
        } else {
            final ItemStack itemstack = clientPlayer.getHeldItemMainhand();
            final ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
            modelplayer.setVisible(true);
            modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
            modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
            modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            modelplayer.isSneak = clientPlayer.isSneaking();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

            if (!itemstack.isEmpty()) {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0) {
                    final EnumAction enumaction = itemstack.getItemUseAction();

                    if (enumaction == EnumAction.BLOCK)
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    else if (enumaction == EnumAction.BOW)
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }

            if (!itemstack1.isEmpty()) {
                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0) {
                    final EnumAction enumaction1 = itemstack1.getItemUseAction();

                    if (enumaction1 == EnumAction.BLOCK)
                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                    else if (enumaction1 == EnumAction.BOW)
                        modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }

            if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
                modelplayer.rightArmPose = modelbiped$armpose;
                modelplayer.leftArmPose = modelbiped$armpose1;
            } else {
                modelplayer.rightArmPose = modelbiped$armpose1;
                modelplayer.leftArmPose = modelbiped$armpose;
            }
        }
    }

    @Override
    protected void renderEntityName(final AbstractClientPlayer entityIn, final double x, double y, final double z,
                                    final String name, final double distanceSq) {
        if (distanceSq < 100.0D) {
            final Scoreboard scoreboard = entityIn.getWorldScoreboard();
            final ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);

            if (scoreobjective != null) {
                final Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
                renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z,
                        64);
                y += getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F;
            }
        }

        super.renderEntityName(entityIn, x, y, z, name, distanceSq);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        int id = entity.getEntityId();
        Deity d = DeityManager.getSelectedDeities().get(id);
        return new ResourceLocation("mythria:textures/entity/deity/" + d.name().toLowerCase() + ".png");
    }

    public void renderLeftArm(final AbstractClientPlayer clientPlayer) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        final ModelPlayerCustom modelplayer = getMainModel();
        setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

    public void renderRightArm(final AbstractClientPlayer clientPlayer) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        final ModelPlayerCustom modelplayer = getMainModel();
        setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }
}
