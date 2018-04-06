package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Managers.AbilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AbilityListener {
    @SubscribeEvent
    public static void onFall(final LivingFallEvent event) {
        if (event.getEntityLiving().world.isRemote)
            return;
        if (event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            final IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            AbilityManager.handleFallDamage(event, player, profile);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player.world.isRemote) return;
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);

        Entity e = event.getTarget();
        if(e instanceof EntityPlayer) {
            if(player.isSneaking()) {
                EntityPlayer target = (EntityPlayer) e;
                IProfile targetProfile = target.getCapability(ProfileProvider.PROFILE_CAP, null);
                AbilityManager.handlePlayerInteractPlayer(player, target, profile, targetProfile);
            }
        }
    }

}
