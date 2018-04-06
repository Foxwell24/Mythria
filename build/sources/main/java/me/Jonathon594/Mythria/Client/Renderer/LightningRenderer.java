package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class LightningRenderer {
    private static EntityPlayer mc = Minecraft.getMinecraft().player;

    public static void renderLightningBolt(Vec3d startPos, Vec3d endPos, long seed, World world, boolean canBranch, float rotationYaw, float rotationPitch) {
        Random random = new Random(seed);

        drawBolt(startPos, endPos, seed, world, canBranch, rotationYaw, rotationPitch, random);
    }

    public static void drawBolt(Vec3d startPos, Vec3d endPos, long seed, World world, boolean canBranch, float rotationYaw, float rotationPitch, Random random) {
        Vec3d cp = startPos;
        double d = startPos.distanceTo(endPos);
        int dbs = (int) Math.ceil(d);
        for (int v = 0; v < dbs; v++) {
            double prop = (double) (v + 1) / (double) dbs;
            Vec3d npos = endPos.subtract(startPos).scale(prop).add(startPos);
            Vec3d opos = new Vec3d(random.nextDouble() - 0.5, 0, random.nextDouble() - 0.5).scale(1).rotatePitch(rotationPitch).rotateYaw(rotationYaw);
            Vec3d tpos = npos.add(opos);
            if(random.nextDouble() < 0.5 && canBranch) {
                Vec3d cvec = tpos.subtract(cp).normalize();
                float cyaw = (float) MythriaUtil.getYawFromVec3d(cvec);
                float cpitch = (float) MythriaUtil.getPitchFromVec3d(cvec);
                drawBolt(tpos, tpos.add(cvec.scale(random.nextDouble()*10)), seed, world, false, cyaw, cpitch, random);
            }
            if(v == dbs-1) {
                tpos = endPos;
            }
            int p = (int) Math.ceil(cp.distanceTo(tpos))*10;
            for (int par = 0; par < p; par++) {
                Vec3d ppos = tpos.subtract(cp).scale((double) (par + 1) / (double) p).add(cp);
                world.spawnParticle(EnumParticleTypes.REDSTONE, ppos.x, ppos.y, ppos.z, 0, 0, 0, 1, 0, 0);
            }
            cp = tpos;
        }
    }
}
