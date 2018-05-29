package me.Jonathon594.Mythria.Managers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundManager {

    public static void playForAllNearby(final EntityLivingBase livingBase, final SoundEvent blockFireExtinguish) {
        livingBase.world.playSound(null, livingBase.posX, livingBase.posY, livingBase.posZ, blockFireExtinguish, SoundCategory.MASTER,
                1.0f, 1.0f);
    }

    public static void playForPlayerOnly(final EntityPlayerMP player, final SoundEvent sound, float vol) {
        playForPlayerOnly(player, player.posX, player.posY, player.posZ, sound, vol, 1.0f);
    }

    private static void playForPlayerOnly(final EntityPlayerMP player, final double posX, final double posY,
                                          final double posZ, final SoundEvent sound, final float volume, final float pitch) {
        player.connection
                .sendPacket(new SPacketSoundEffect(sound, SoundCategory.MASTER, posX, posY, posZ, volume, pitch));
    }

}
