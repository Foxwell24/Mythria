package me.Jonathon594.Mythria.Util;

import net.minecraft.util.math.MathHelper;

public class TrigUtil {

    public static boolean isWithin2DCone(final float rotationYaw, double a, final float min, final float max) {
        a -= rotationYaw;
        a = MathHelper.wrapDegrees(a);
        return a > min && a < max;
    }

}
