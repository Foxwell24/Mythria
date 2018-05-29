package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MouseHelper;

public class TorpidityManager {
    private static boolean knockedOut = false;

    private static Minecraft mc = Minecraft.getMinecraft();
    public static void onPlayerTickClient() {
        IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
        double torpor = profile.getConsumables().get(Consumable.TORPOR);
        if (torpor == 20) {
            knockedOut = true;
        } else if (torpor == 0) {
            if(knockedOut) mc.mouseHelper = new MouseHelper();
            knockedOut = false;
        }

        if(knockedOut) {
            KeyBinding.unPressAllKeys();
            MouseHelper frozenMouse = new MouseHelper() {
                @Override
                public void mouseXYChange() {
                    deltaX = 0;
                    deltaY = 0;
                }
            };
            mc.mouseHelper = frozenMouse;
        }
    }
}
