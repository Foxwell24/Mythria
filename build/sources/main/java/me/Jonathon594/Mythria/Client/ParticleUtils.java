package me.Jonathon594.Mythria.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleUtils {

    public static void renderAimingCone(final EnumParticleTypes type, final Vec3d location, final World worldObj,
                                        final float angle, final double range, final int res) {
        ParticleUtils.renderLine(type, location.add(new Vec3d(0, +0.2, 0)), worldObj,
                Math.toRadians(Minecraft.getMinecraft().player.rotationYaw + 90 - angle), range, res);
        ParticleUtils.renderLine(type, location.add(new Vec3d(0, +0.2, 0)), worldObj,
                Math.toRadians(Minecraft.getMinecraft().player.rotationYaw + 90 + angle), range, res);

        for (int i = 0; i < res; i++) {
            final double a = Math
                    .toRadians(-angle + angle * 2 / res * i + Minecraft.getMinecraft().player.rotationYaw + 90);
            final double x = Math.cos(a);
            final double z = Math.sin(a);

            worldObj.spawnParticle(type, x * range + location.x, location.y + 0.2, z * range + location.z, 0, 0, 0);
        }
    }

    public static void renderLine(final EnumParticleTypes type, final Vec3d location, final World worldObj,
                                  final double a, final double l, final int r) {
        for (int i = 0; i < r; i++) {
            final double l1 = l / r * i;
            final double x = Math.cos(a);
            final double z = Math.sin(a);

            worldObj.spawnParticle(type, x * l1 + location.x, location.y, z * l1 + location.z, 0, 0, 0);
        }
    }

}
