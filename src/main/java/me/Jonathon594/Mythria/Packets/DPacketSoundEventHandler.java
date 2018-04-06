package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Managers.SoundManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DPacketSoundEventHandler implements IMessageHandler<DPacketSoundEvent, IMessage> {

    @Override
    public IMessage onMessage(final DPacketSoundEvent message, final MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            SoundManager.playForAllNearby(ctx.getServerHandler().player, message.getSoundEvent());
        });
        return null;
    }

}
