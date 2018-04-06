package me.Jonathon594.Mythria.Packets;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUpdateFallDistanceHandler implements IMessageHandler<CPacketUpdateFallDistance, IMessage> {

    @Override
    public IMessage onMessage(final CPacketUpdateFallDistance message, final MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            final EntityLivingBase e = (EntityLivingBase) ctx.getServerHandler().player.getEntityWorld()
                    .getEntityByID(message.getEntityID());
            e.fallDistance = message.getFallDistance();
        });
        return null;
    }

}
