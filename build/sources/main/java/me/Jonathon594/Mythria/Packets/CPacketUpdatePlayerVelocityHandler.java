package me.Jonathon594.Mythria.Packets;

import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUpdatePlayerVelocityHandler implements IMessageHandler<CPacketUpdatePlayerVelocity, IMessage> {

    @Override
    public IMessage onMessage(final CPacketUpdatePlayerVelocity message, final MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().player;
        player.motionX = message.getMotionX();
        player.motionY = message.getMotionY();
        player.motionZ = message.getMotionZ();

        final EntityTracker e = player.getServerWorld().getEntityTracker();
        for (final EntityPlayer tracker : e.getTrackingPlayers(player)) {
            final EntityPlayerMP trackerMP = (EntityPlayerMP) tracker;
            trackerMP.connection.sendPacket(
                    new SPacketEntityVelocity(player.getEntityId(), player.motionX, player.motionY, player.motionZ));
        }
        return null;
    }

}
