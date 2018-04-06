package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;

public class PainManager {
    public static void onClientTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc == null || mc.player == null) return;
        if (mc.world.getWorldTime() % 2 == 0) {
            IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
            double pain = profile.getConsumables().get(Consumable.PAIN);
            if(pain > 10) {
                float volume = (float) Math.max(pain - 10, 0) / 10f;
                mc.player.playSound(SoundEvents.ENTITY_GHAST_HURT, volume, 1f);
            }
        }
    }
}
