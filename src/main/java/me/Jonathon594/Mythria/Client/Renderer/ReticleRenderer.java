package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ReticleRenderer extends RenderGlobal {

    public static Reticle reticle = null;

    public ReticleRenderer(final Minecraft mcIn) {
        super(mcIn);
    }

    @SubscribeEvent
    public static void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (reticle == null)
            return;

        // GlStateManager.pushMatrix();
        if (reticle instanceof ConeReticle)
            renderConeReticle((ConeReticle) reticle);
        if (reticle instanceof CrossHairCircleReticle)
            renderMouseCircleReticle((CrossHairCircleReticle) reticle);

        // GlStateManager.popMatrix();
    }

    private static void renderConeReticle(final ConeReticle reticle) {
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(4f);
        GlStateManager.disableDepth();
        GlStateManager.color(reticle.r, reticle.g, reticle.b, reticle.a);
        final Vec3d start = new Vec3d(0, 0, 0);
        final float currentAngle = Minecraft.getMinecraft().player.rotationYaw + 90;
        GlStateManager.glBegin(GL11.GL_LINES);
        renderLine(start, reticle.length,
                (float) Math.toRadians(MathHelper.wrapDegrees(-reticle.maxAngle + currentAngle)));
        renderLine(start, reticle.length,
                (float) Math.toRadians(MathHelper.wrapDegrees(reticle.maxAngle + currentAngle)));
        renderPartialCircle(start, reticle.length, currentAngle, reticle.maxAngle);
        GlStateManager.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
    }

    private static void renderMouseCircleReticle(final CrossHairCircleReticle reticle) {
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(4f);
        GlStateManager.disableDepth();
        GlStateManager.color(reticle.r, reticle.g, reticle.b, reticle.a);
        GlStateManager.glBegin(GL11.GL_LINES);
        final EntityPlayer p = Minecraft.getMinecraft().player;
        final double r = Math.toRadians(p.rotationYaw + 90);
        final double f = MythriaUtil.getPitchAimFactor(p.rotationPitch);
        final double d = reticle.getRange() * f;
        final Vec3d v = MythriaUtil.getPitchAimTargetPosition(r, d);
        renderCircle(v, reticle.getRange(), reticle.getArea());
        GlStateManager.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
    }

    private static void renderLine(final Vec3d start, final float length, final float f) {
        GlStateManager.glVertex3f((float) start.x, (float) start.y, (float) start.z);
        GlStateManager.glVertex3f((float) Math.cos(f) * length + (float) start.x, (float) start.y,
                (float) Math.sin(f) * length + (float) start.z);
    }

    private static void renderPartialCircle(final Vec3d start, final float length, final float f,
                                            final float maxAngle) {
        for (int i = 0; i <= 30; i++)
            for (int r = 0; r <= 1; r++) {
                if (r == 1 && i == 30)
                    return;
                final float angle = (float) Math.toRadians(maxAngle * 2 / 30 * (i + r) + f - maxAngle);
                final float x = (float) Math.cos(angle) * length;
                final float z = (float) Math.sin(angle) * length;
                GlStateManager.glVertex3f((float) start.x + x, (float) start.y, (float) start.z + z);
            }

    }

    private static void renderCircle(final Vec3d start, final int range, final int area) {
        for (int i = 0; i < 90; i++)
            for (int r = 0; r <= 1; r++) {
                if (r == 1 && i == 90)
                    return;
                final float angle = (float) Math.toRadians((i + r) * (360 / 90));
                final float x = (float) Math.cos(angle) * area;
                final float z = (float) Math.sin(angle) * area;
                GlStateManager.glVertex3f((float) start.x + x, (float) start.y, (float) start.z + z);
            }
    }

}
