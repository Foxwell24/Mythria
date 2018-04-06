package me.Jonathon594.Mythria.Packets;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSetResistanceTicksHandler implements IMessageHandler<CPacketSetResistanceTicks, IMessage> {

    @Override
    public IMessage onMessage(final CPacketSetResistanceTicks message, final MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            ctx.getServerHandler().player.hurtResistantTime = message.getTicks();
        });
        return null;
    }

}
